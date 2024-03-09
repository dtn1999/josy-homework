package com.project.mediahub.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
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
public class Tag extends BaseEntity {
    @Column(name = "label", nullable = false)
    private String label;

    @Builder.Default
    @ToString.Exclude
    @ManyToMany(mappedBy = "tags", fetch = FetchType.EAGER)
    private Set<Note> notes = new HashSet<>();

    public void addNote(Note note) {
        notes.add(note);
        note.getTags().add(this);
    }

    public void removeNote(Note note) {
        notes.remove(note);
        note.getTags().remove(this);
    }
}
