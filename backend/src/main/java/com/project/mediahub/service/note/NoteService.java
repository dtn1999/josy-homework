package com.project.mediahub.service.note;

import com.project.mediahub.controller.NoteController;
import com.project.mediahub.model.entity.Note;
import com.project.mediahub.model.entity.Tag;
import com.project.mediahub.model.entity.Upload;
import com.project.mediahub.model.payload.ApiResponse;
import com.project.mediahub.model.payload.CreateNotePayload;
import com.project.mediahub.model.payload.NoteResponse;
import com.project.mediahub.repository.NoteRepository;
import com.project.mediahub.repository.TagRepository;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;
    private final TagRepository tagRepository;
    private final FilesStorageService storageService;

    public ApiResponse createNote(@NonNull MultipartFile image, @Valid CreateNotePayload payload) {
        // save the image to the file system
        Upload uploadedImage = this.storageService.save(image);
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
                .peek(tag -> {
                    tag.getNotes().add(savedNote);
                })
                .collect(Collectors.toSet());
        List<Tag> saveTags = this.tagRepository.saveAll(tags);
        savedNote.setTags(new HashSet<>(saveTags));
        this.noteRepository.save(savedNote);

        Resource file = storageService.load(uploadedImage.getFilename());
        String url = MvcUriComponentsBuilder
                .fromMethodName(NoteController.class, "getFile", file.getFilename())
                .build()
                .toString();
        NoteResponse noteResponse = NoteResponse.from(savedNote);
        noteResponse.setImageUrl(url);
        // return the response
        return ApiResponse.success("Note created successfully", noteResponse);
    }


    public Tag createTag(@NonNull String label) {
        return this.tagRepository.findByLabel(label)
                .orElseGet(() -> Tag.builder().label(label).build());
    }
}
