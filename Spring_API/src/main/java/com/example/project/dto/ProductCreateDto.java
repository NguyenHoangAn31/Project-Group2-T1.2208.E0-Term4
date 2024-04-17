package com.example.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreateDto {

	private int id;
	private String name;
	private int price;
	private String image;
	private int categoryId;

}
