package com.blog.applicatication.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.blog.applicatication.payload.ApiResponse;
import com.blog.applicatication.payload.AppConstants;
import com.blog.applicatication.payload.CategoryDto;
import com.blog.applicatication.services.CategoryService;

@Controller
@RequestMapping("/api/")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	//@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/create/categories")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
		return new ResponseEntity<>(this.categoryService.createCategory(categoryDto), HttpStatus.CREATED);
	}
	
	@GetMapping("/categories")
	public ResponseEntity<List<CategoryDto>> getAllCategory(
			@RequestParam(value = "pageNO", defaultValue = AppConstants.DEFAULT_PAGE_NO, required = false) int pageNo,
			@RequestParam(value = "pageNO", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "pageNO", defaultValue = AppConstants.DEFAULT_SORT_BY_FOR_CATEGORY, required = false) String sortBy,
			@RequestParam(value = "pageNO", defaultValue = AppConstants.DEFAULT_SORT_DIR, required = false) String sortDir
			){
		return new ResponseEntity<>(this.categoryService.getAllCategory(), HttpStatus.OK);
	}
	
	@GetMapping("/category/{categoryId}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable("categoryId") int categoryId){
		return new ResponseEntity<>(this.categoryService.getCategoryById(categoryId), HttpStatus.OK);
	}
	
	//@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/update/categories/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @PathVariable int categoryId, @RequestBody CategoryDto categoryDto){
		return new ResponseEntity<>(this.categoryService.updateCategoryById(categoryId, categoryDto), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/delete/categories/{categoryId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable int categoryId){
		this.categoryService.deleteCategoryById(categoryId);
		return new ResponseEntity<>(new ApiResponse(String.format("category delete succesefully with id : %s", categoryId), true), HttpStatus.OK);
	}
	
}
