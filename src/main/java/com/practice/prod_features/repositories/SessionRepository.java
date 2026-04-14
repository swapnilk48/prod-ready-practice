package com.practice.prod_features.repositories;

import com.practice.prod_features.entities.SessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<SessionEntity, Long> {

    Optional<SessionEntity> findByUser_Id(Long userId);

    boolean existsByUser_IdAndToken(Long userId, String token);

    boolean existsByUser_IdAndRefreshToken(Long userId, String refreshToken);
}
