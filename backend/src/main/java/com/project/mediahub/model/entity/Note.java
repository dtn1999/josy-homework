package com.project.mediahub.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Note extends BaseEntity {
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "text", nullable = false, columnDefinition = "TEXT")
    private String text;
    @OneToOne(cascade = CascadeType.ALL)
    private Upload upload;

    @JoinTable(
            name = "notice_tags",
            joinColumns = @JoinColumn(name = "notice_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id")
    )
    @Builder.Default
    @ToString.Exclude
    @ManyToMany(cascade = {CascadeType.PERSIST}, fetch = FetchType.EAGER)
    private Set<Tag> tags = new HashSet<>();

    public void addTag(Tag tag) {
        tags.add(tag);
        tag.getNotes().add(this);
    }

    public void removeTag(Tag tag) {
        tags.remove(tag);
        tag.getNotes().remove(this);
    }
}
