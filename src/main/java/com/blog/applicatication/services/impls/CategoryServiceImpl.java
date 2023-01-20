package com.blog.applicatication.services.impls;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.applicatication.entities.Category;
import com.blog.applicatication.exceptions.ResourceNotFoundException;
import com.blog.applicatication.payload.CategoryDto;
import com.blog.applicatication.repositories.CategoryRepository;
import com.blog.applicatication.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	private CategoryRepository categoryRepo;

	@Autowired
	private ModelMapper mapper;
	
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category category = this.mapper.map(categoryDto, Category.class);
		Category newCategory = this.categoryRepo.save(category);
		return this.mapper.map(newCategory, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		List<Category> categories = this.categoryRepo.findAll();
		return categories.stream().map(category->this.mapper.map(category, CategoryDto.class)).collect(Collectors.toList());
	}

	@Override
	public CategoryDto getCategoryById(int categoryId) {
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category", "category id", categoryId));
		return this.mapper.map(category, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategoryById(int categoryId, CategoryDto categoryDto) {
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category", "category id", categoryId));
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		Category newCategory = this.categoryRepo.save(category);
		return this.mapper.map(newCategory, CategoryDto.class);
	}

	@Override
	public void deleteCategoryById(int categoryId) {
	Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category", "category id", categoryId));
	this.categoryRepo.delete(category);
	}

	
}
