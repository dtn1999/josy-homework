package com.josy.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.josy.project.model.payload.FilterOptions;
import com.josy.project.model.payload.ApiResponse;
import com.josy.project.model.payload.CreateNotePayload;
import com.josy.project.service.note.FilesStorageService;
import com.josy.project.service.note.NoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
public class NoteController {
    private final ObjectMapper mapper;
    private final NoteService noteService;
    private final FilesStorageService storageService;

    @PostMapping("/search")
    public ResponseEntity<ApiResponse> search(
            @RequestBody FilterOptions filterOptions,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        log.info("Searching notes with the following filter options: {}", filterOptions);
        return ResponseEntity
                .ok(this.noteService.search(filterOptions, userDetails));
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse> create(
            @RequestParam(name = "image", required = false) MultipartFile image,
            @RequestParam("payload") String payloadJson,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        try {
            CreateNotePayload payload = this.mapper.readValue(payloadJson, CreateNotePayload.class);
            log.info("Creating note with the following information: {}, {}", payload, image);
            return ResponseEntity
                    .ok(this.noteService.createNote(image, payload, userDetails));
        } catch (Exception e) {
            log.error("Error parsing payload: {}", e.getMessage());
            return ResponseEntity
                    .badRequest()
                    .body(ApiResponse.failure("Error parsing payload"));

        }
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse> getAll(@AuthenticationPrincipal UserDetails userDetails) {
        log.info("Getting all notes");
        return ResponseEntity
                .ok(this.noteService.getAllNotes(userDetails));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getOne(@PathVariable Long id) {
        log.info("Getting note with id: {}", id);
        return ResponseEntity
                .ok(this.noteService.getNoteById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> update(
            @PathVariable Long id,
            @RequestParam(name = "image", required = false) MultipartFile image,
            @RequestParam("payload") CreateNotePayload payload,
            @AuthenticationPrincipal UserDetails userDetails) {
        log.info("Updating note with the following information: {}", payload);
        try {
            return ResponseEntity
                    .ok(this.noteService.updateNote(id, image, payload, userDetails));
        } catch (Exception e) {
            log.error("Error updating note: {}", e.getMessage());
            return ResponseEntity
                    .badRequest()
                    .body(ApiResponse.failure("Error updating note"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        log.info("Deleting note with id: {}", id);
        return ResponseEntity
                .ok(this.noteService.deleteNoteById(id, userDetails));
    }

    @GetMapping("/tags")
    public ResponseEntity<ApiResponse> getAllTags() {
        log.info("Getting all tags");
        return ResponseEntity
                .ok(this.noteService.getAllTags());
    }

}
