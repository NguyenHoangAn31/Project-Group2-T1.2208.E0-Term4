package com.example.project.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.project.dto.ProductDto;
import com.example.project.entity.Product;
import com.example.project.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ModelMapper mapper;

	private ProductDto toDto(Product p) {
		return mapper.map(p, ProductDto.class);
	}

	public List<ProductDto> findAll() {
		return productRepository.findAll().stream().map(this::toDto).toList();
	}

	public ProductDto save(ProductDto dto) {
		Product p = mapper.map(dto, Product.class);
		Product result = productRepository.save(p);
		return toDto(result);
	}

	public Optional<ProductDto> findById(int id) {
		Optional<Product> result = productRepository.findById(id);
		return result.map(this::toDto);
	}

	public void deleteById(int id) {
		productRepository.deleteById(id);
	}
}
