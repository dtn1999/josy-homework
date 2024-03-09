package com.project.mediahub.service.note;

import com.project.mediahub.model.FileProcessingException;
import com.project.mediahub.model.NoteNotFoundException;
import com.project.mediahub.model.entity.Note;
import com.project.mediahub.model.entity.Tag;
import com.project.mediahub.model.entity.Upload;
import com.project.mediahub.model.payload.ApiResponse;
import com.project.mediahub.model.payload.CreateNotePayload;
import com.project.mediahub.model.payload.NoteResponse;
import com.project.mediahub.repository.NoteRepository;
import com.project.mediahub.repository.TagRepository;
import com.project.mediahub.repository.UploadRepository;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.FileAlreadyExistsException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class NoteService {
    public static final String NOTE_NOT_FOUND_ERROR_MESSAGE = "Note not found";
    private final NoteRepository noteRepository;
    private final TagRepository tagRepository;
    private final UploadRepository uploadRepository;
    private final FilesStorageService storageService;

    public ApiResponse createNote(@NonNull MultipartFile image, @Valid CreateNotePayload payload) {
        // save the image to the file system
        Upload uploadedImage = this.getUploadEntity(image);
        // create the note
        Note note = Note.builder()
                .title(payload.getTitle())
                .text(payload.getContent())
                .upload(uploadedImage)
                .build();
        Note savedNote = this.noteRepository.save(note);

        // create tags if they don't exist
        Set<Tag> tags = payload.getTags()
                .stream()
                .map(this::createTag)
                .peek(tag -> tag.addNote(savedNote))
                .collect(Collectors.toSet());
        this.tagRepository.saveAll(tags);
        NoteResponse noteResponse = NoteResponse.from(savedNote);
        // return the response
        return ApiResponse.success("Note created successfully", noteResponse);
    }

    public ApiResponse getAllNotes() {
        List<Note> notes = this.noteRepository.findAll();
        List<NoteResponse> noteResponses = notes.stream()
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

    public ApiResponse updateNote(@NonNull Long noteId, @NonNull MultipartFile image, @Valid CreateNotePayload payload) {
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

    public ApiResponse deleteNoteById(@NonNull Long noteId) {
        Note note = this.noteRepository.findById(noteId)
                .orElseThrow(() -> new NoteNotFoundException(NOTE_NOT_FOUND_ERROR_MESSAGE));
        this.noteRepository.delete(note);
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

    private void updateUpload(@NonNull Note note, @NonNull MultipartFile image) {
        try {
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
}
