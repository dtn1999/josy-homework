package com.project.mediahub.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

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

    @ManyToMany
    @JoinTable(
            name = "notice_tags",
            joinColumns = @JoinColumn(name = "notice_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id")
    )
    @ToString.Exclude
    private Set<Tag> tags;
}
