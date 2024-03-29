package com.josy.project.repository;

import com.josy.project.model.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    @Query("SELECT t FROM Tag t WHERE t.label = :label")
    Optional<Tag> findByLabel(@Param("label") String label);


    @Query("SELECT t FROM Tag t WHERE t.label IN :tags")
    Set<Tag> findAllByTagsLabelIn(@Param("tags") Set<String> tags);

}
