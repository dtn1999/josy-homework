package com.project.mediahub.controller;

import com.project.mediahub.model.payload.ApiResponse;
import com.project.mediahub.model.payload.CreateNotePayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/notes")
public class NoteController {

    @PostMapping("/")
    public ResponseEntity<ApiResponse> create(
            @RequestParam("image") MultipartFile image,
            @RequestParam("payload") CreateNotePayload payload
    ) {
        log.info("Creating note with the following information: {}", payload);
        return ResponseEntity.ok(ApiResponse.builder().build());
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

}
