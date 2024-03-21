package com.josy.project.repository;

import com.josy.project.model.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Set;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    @Query("SELECT n FROM Note n WHERE n.createdAt BETWEEN :from AND :to")
    Set<Note> findAllByDateRange(@Param("from") LocalDate from, @Param("to") LocalDate to);

}
