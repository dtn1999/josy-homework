package com.project.mediahub.repository;

import com.project.mediahub.model.entity.Upload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UploadRepository extends JpaRepository<Upload, Long> {

    @Query("SELECT u FROM Upload u WHERE u.filename = :filename")
    Optional<Upload> findByFilename(@Param(("filename")) String filename);

}
