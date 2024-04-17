package com.example.project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;
import com.example.project.entity.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {

	@Modifying
	@Transactional
	@Query("UPDATE RefreshToken o SET o.available=false WHERE o.user.id=:userId AND o.available=true")
	void disableOldRefreshTokenFromUser(@Param("userId") int userId);

	@Query("SELECT o FROM RefreshToken o JOIN FETCH o.user u JOIN FETCH u.roles WHERE o.code=:code AND o.available=true")
	Optional<RefreshToken> findRefreshTokenByCode(@Param("code") String code);
}
