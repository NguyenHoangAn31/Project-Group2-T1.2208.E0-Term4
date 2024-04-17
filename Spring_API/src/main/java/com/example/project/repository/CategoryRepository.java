package com.example.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
