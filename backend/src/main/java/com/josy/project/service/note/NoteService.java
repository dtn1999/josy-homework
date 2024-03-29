package com.josy.project.service.note;

import com.josy.project.model.FileProcessingException;
import com.josy.project.model.NoteNotFoundException;
import com.josy.project.model.entity.Upload;
import com.josy.project.model.payload.ApiResponse;
import com.josy.project.model.payload.CreateNotePayload;
import com.josy.project.model.payload.FilterOptions;
import com.josy.project.model.payload.NoteResponse;
import com.josy.project.repository.NoteRepository;
import com.josy.project.repository.TagRepository;
import com.josy.project.repository.UploadRepository;
import com.josy.project.repository.UserRepository;
import com.josy.project.model.entity.Note;
import com.josy.project.model.entity.Tag;
import com.josy.project.model.entity.User;
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.FileAlreadyExistsException;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoteService {
    public static final String NOTE_NOT_FOUND_ERROR_MESSAGE = "Note not found";
    private final UserRepository userRepository;
    private final NoteRepository noteRepository;
    private final TagRepository tagRepository;
    private final UploadRepository uploadRepository;
    private final FilesStorageService storageService;

    public ApiResponse search(FilterOptions options, UserDetails userDetails) {
        Set<NoteResponse> foundNotes = this.fetchNotesByDateRange(options, userDetails)
                .stream()
                .filter(note -> this.match(note, options))
                .map(NoteResponse::from)
                .collect(Collectors.toSet());
        return ApiResponse.success(foundNotes);
    }

    public ApiResponse createNote(
            MultipartFile image,
            @Valid CreateNotePayload payload,
            @NonNull UserDetails userDetails
    ) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow();
        // create the note
        Note note = Note.builder()
                .title(payload.getTitle())
                .text(payload.getContent())
                .user(user)
                .build();

        if (Objects.nonNull(image)) {
            Upload uploadedImage = this.getUploadEntity(image);
            note.setUpload(uploadedImage);
        }

        // save the image to the file system

        Note savedNote = this.noteRepository.save(note);

        // create tags if they don't exist
        Set<Tag> tags = payload.getTags()
                .stream()
                .map(this::createTag)
                .peek(tag -> tag.addNote(savedNote))
                .collect(Collectors.toSet());
        this.tagRepository.saveAll(tags);
        this.noteRepository.save(savedNote);
        NoteResponse noteResponse = NoteResponse.from(savedNote);
        // return the response
        return ApiResponse.success("Note created successfully", noteResponse);
    }

    public ApiResponse getAllNotes(UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow();
        List<NoteResponse> noteResponses = user.getNotes().stream()
                .map(NoteResponse::from)
                .toList();
        return ApiResponse.success("Notes retrieved successfully", noteResponses);
    }

    public ApiResponse getNoteById(@NonNull Long noteId) {
        Note note = this.noteRepository.findById(noteId)
                .orElseThrow(() -> new NoteNotFoundException(NOTE_NOT_FOUND_ERROR_MESSAGE));
        NoteResponse noteResponse = NoteResponse.from(note);
        return ApiResponse.success("Note retrieved successfully", noteResponse);
    }

    public ApiResponse updateNote(@NonNull Long noteId, @NonNull MultipartFile image, @Valid CreateNotePayload payload, UserDetails userDetails) {
        Note note = this.noteRepository.findById(noteId)
                .orElseThrow(() -> new NoteNotFoundException(NOTE_NOT_FOUND_ERROR_MESSAGE));
        note.setText(payload.getContent());
        note.setTitle(payload.getTitle());
        // save the image to the file system
        this.updateUpload(note, image);

        // create tags if they don't exist
        Set<Tag> tags = payload.getTags()
                .stream()
                .map(this::createTag)
                .peek(tag -> tag.addNote(note))
                .collect(Collectors.toSet());
        this.tagRepository.saveAll(tags);
        NoteResponse noteResponse = NoteResponse.from(note);
        // return the response
        return ApiResponse.success("Note updated successfully", noteResponse);
    }

    public ApiResponse filterNotesByTags(@NonNull Set<String> tags) {
        Set<Note> filterResult = new HashSet<>();

        this.tagRepository.findAllByTagsLabelIn(tags)
                .forEach(tag -> filterResult.addAll(tag.getNotes()));

        List<NoteResponse> noteResponses = filterResult.stream()
                .map(NoteResponse::from)
                .toList();

        return ApiResponse.success("Notes retrieved successfully", noteResponses);
    }

    public ApiResponse deleteNoteById(@NonNull Long noteId, @Nonnull UserDetails userDetails) {
        Note note = this.noteRepository.findById(noteId)
                .orElseThrow(() -> new NoteNotFoundException(NOTE_NOT_FOUND_ERROR_MESSAGE));
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow();
        if (!note.getUser().equals(user)) {
            return ApiResponse.failure("You are not authorized to delete this note");
        }
        this.noteRepository.deleteById(noteId);
        return ApiResponse.success("Note deleted successfully", null);
    }

    public ApiResponse getAllTags() {
        List<Tag> tags = this.tagRepository.findAll();
        List<String> tagLabels = tags.stream()
                .map(Tag::getLabel)
                .toList();
        return ApiResponse.success("Tags retrieved successfully", tagLabels);
    }

    private Upload getUploadEntity(MultipartFile image) {
        try {
            return this.storageService.save(image);
        } catch (FileProcessingException exception) {
            if (exception.getCause() instanceof FileAlreadyExistsException) {
                return this.uploadRepository.findByFilename(image.getOriginalFilename())
                        .orElseThrow(() -> new FileProcessingException("File not found"));
            }
            throw exception;
        }
    }

    private void updateUpload(@NonNull Note note, MultipartFile image) {
        try {
            if (Objects.isNull(image)) {
                return;
            }
            Upload uploadedImage = this.storageService.save(image);
            // delete old image
            this.uploadRepository.delete(note.getUpload());
            note.setUpload(uploadedImage);
        } catch (FileProcessingException e) {
            if (e.getCause() instanceof FileAlreadyExistsException) {
                // no need to update the image
                return;
            }
            throw e;
        }
    }

    private Tag createTag(@NonNull String label) {
        return this.tagRepository.findByLabel(label)
                .orElseGet(() -> Tag.builder().label(label).build());
    }

    private Set<Note> fetchNotesByDateRange(FilterOptions options, UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow();
        if (Objects.isNull(options.getFrom()) || Objects.isNull(options.getTo())) {
            return user.getNotes();
        }
        return this.noteRepository.findAllByDateRange(options.getFrom(), options.getTo())
                .stream()
                .filter(note -> note.getUser().equals(user))
                .collect(Collectors.toSet());
    }

    private boolean match(Note note, FilterOptions filterOptions) {
        boolean titleMatch = !StringUtils.hasText(filterOptions.getTitle())
                || containsIgnoreCase(note.getTitle(), filterOptions.getTitle());

        boolean tagMatch = !StringUtils.hasText(filterOptions.getTag())
                || note.getTags().stream().anyMatch(tag -> containsIgnoreCase(tag.getLabel(), filterOptions.getTag()));

        return titleMatch && tagMatch;
    }

    private boolean containsIgnoreCase(String source, String target) {
        return source.toLowerCase().contains(target.toLowerCase());
    }
}
