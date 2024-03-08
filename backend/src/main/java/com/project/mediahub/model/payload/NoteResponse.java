package com.project.mediahub.model.payload;

import com.project.mediahub.model.entity.Note;
import com.project.mediahub.model.entity.Tag;
import com.project.mediahub.model.entity.Upload;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NoteResponse {
    private Long id;
    private String title;
    private String content;
    private String createdAt;
    private List<String> tags;
    private String imageUrl;


    public NoteResponse from(Note note) {
        return NoteResponse.builder()
                .id(note.getId())
                .title(note.getTitle())
                .content(note.getText())
                .createdAt(note.getCreatedAt().toString())
                .tags(note.getTags().stream().map(Tag::getLabel).toList())
                .imageUrl(
                        Optional.ofNullable(note.getUpload())
                                .map(Upload::getFilename)
                                .orElse(null)
                )
                .build();
    }
}
