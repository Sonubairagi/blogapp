package com.blog.applicatication.services;

import java.util.List;

import com.blog.applicatication.payload.CategoryDto;

public interface CategoryService {

	CategoryDto createCategory(CategoryDto categoryDto);
	
	List<CategoryDto> getAllCategory();
	
	CategoryDto getCategoryById(int categoryId);
	
	CategoryDto updateCategoryById(int categoryId, CategoryDto categoryDto);

	void deleteCategoryById(int categoryId);
}

