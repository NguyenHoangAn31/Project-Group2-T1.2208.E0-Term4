package com.example.project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

//	@Query("SELECT o FROM User o WHERE o.email=:email")
//	Optional<User> findEmail(@Param("email") String email);

	Optional<User> findByEmail(String email);
}
