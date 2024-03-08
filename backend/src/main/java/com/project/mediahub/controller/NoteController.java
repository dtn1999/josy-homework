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
            ApiResponse responseNote = this.noteService.createNote(image, payload);
            return ResponseEntity.ok(responseNote);
        } catch (Exception e) {
            log.error("Error parsing payload: {}", e.getMessage());
            return ResponseEntity.badRequest().body(ApiResponse.builder().message("Invalid payload").success(false).build());
        }
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse> getAll() {
        return ResponseEntity.ok(ApiResponse.builder().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.builder().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable Long id, @RequestParam("image") MultipartFile image, @RequestParam("payload") CreateNotePayload payload) {
        log.info("Updating note with the following information: {}", payload);
        return ResponseEntity.ok(ApiResponse.builder().build());
    }

    @GetMapping("/files/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = storageService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
