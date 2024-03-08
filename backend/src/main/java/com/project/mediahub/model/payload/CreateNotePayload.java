package com.project.mediahub.model.payload;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateNotePayload {
    private String title;
    private String content;
    private List<String> tags;
}
