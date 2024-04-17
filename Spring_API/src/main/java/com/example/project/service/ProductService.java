package com.example.project.service;

import java.util.List;
import java.util.Optional;

import com.example.project.dto.ProductDto;

public interface ProductService {

	List<ProductDto> findAll();

	ProductDto save(ProductDto dto);

	Optional<ProductDto> findById(int id);

	void deleteById(int id);
}
