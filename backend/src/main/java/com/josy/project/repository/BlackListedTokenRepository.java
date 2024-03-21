package com.josy.project.repository;

import com.josy.project.model.entity.BlackListedToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlackListedTokenRepository extends JpaRepository<BlackListedToken, Long> {

    @Query("SELECT t FROM BlackListedToken t WHERE t.token = :token")
    Optional<BlackListedToken> findByToken(@Param("token") String token);

}
