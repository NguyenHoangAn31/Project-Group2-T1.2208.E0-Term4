package com.example.project.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.project.dto.CategoryDto;
import com.example.project.entity.Category;
import com.example.project.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepository categoryRepository;
	private final ModelMapper mapper;

	@Autowired
	public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper mapper) {
		this.categoryRepository = categoryRepository;
		this.mapper = mapper;
	}

	public List<CategoryDto> findAll() {
		return categoryRepository.findAll().stream().map(c -> mapper.map(c, CategoryDto.class)).toList();
	}

	public Optional<CategoryDto> findById(int id) {
		Optional<Category> result = categoryRepository.findById(id);
		return result.map(c -> mapper.map(c, CategoryDto.class));
	}
}
