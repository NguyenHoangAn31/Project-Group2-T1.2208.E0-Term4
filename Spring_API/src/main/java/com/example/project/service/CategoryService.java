package com.example.project.service;

import java.util.List;
import java.util.Optional;

import com.example.project.dto.CategoryDto;

public interface CategoryService {

	List<CategoryDto> findAll();

	Optional<CategoryDto> findById(int id);
}
