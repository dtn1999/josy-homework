package com.project.mediahub.model;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Notice extends BaseEntity {
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "text", nullable = false, columnDefinition = "TEXT")
    private String text;
    private Set<String> links;
    @Column(name = "image_url")
    private String imageUrl;

    @ManyToMany
    @JoinTable(
            name = "notice_tags",
            joinColumns = @JoinColumn(name = "notice_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id")
    )
    private Set<Tag> tags;
}
