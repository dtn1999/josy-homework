package com.project.mediahub.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.mediahub.model.payload.ApiResponse;
import com.project.mediahub.model.payload.CreateNotePayload;
import com.project.mediahub.service.note.FilesStorageService;
import com.project.mediahub.service.note.NoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("")
    public ResponseEntity<ApiResponse> create(
            @RequestParam("image") MultipartFile image,
            @RequestParam("payload") String payloadJson
    ) {
        try {
            CreateNotePayload payload = this.mapper.readValue(payloadJson, CreateNotePayload.class);
            log.info("Creating note with the following information: {}, {}", payload, image);
            return ResponseEntity
                    .ok(this.noteService.createNote(image, payload));
        } catch (Exception e) {
            log.error("Error parsing payload: {}", e.getMessage());
            return ResponseEntity
                    .badRequest()
                    .body(ApiResponse.failure("Error parsing payload"));

        }
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse> getAll() {
        log.info("Getting all notes");
        return ResponseEntity
                .ok(this.noteService.getAllNotes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getOne(@PathVariable Long id) {
        log.info("Getting note with id: {}", id);
        return ResponseEntity
                .ok(this.noteService.getNoteById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable Long id, @RequestParam("image") MultipartFile image, @RequestParam("payload") CreateNotePayload payload) {
        log.info("Updating note with the following information: {}", payload);
        try {
            return ResponseEntity
                    .ok(this.noteService.updateNote(id, image, payload));
        } catch (Exception e) {
            log.error("Error updating note: {}", e.getMessage());
            return ResponseEntity
                    .badRequest()
                    .body(ApiResponse.failure("Error updating note"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id) {
        log.info("Deleting note with id: {}", id);
        return ResponseEntity
                .ok(this.noteService.deleteNoteById(id));
    }

    @GetMapping("/tags")
    public ResponseEntity<ApiResponse> getAllTags() {
        log.info("Getting all tags");
        return ResponseEntity
                .ok(this.noteService.getAllTags());
    }


    @GetMapping("/files/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = storageService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
