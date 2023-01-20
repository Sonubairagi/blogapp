package com.blog.applicatication.payload;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

	private int categoryId;
	
	@NotNull(message = "category title field must not be null")
	@NotEmpty(message = "category title field must not be Empty")
	@Size(min = 3, message = "password must be minimum 3 charactor")
	private String categoryTitle;
	
	@NotNull(message = "category description field must not be null")
	@NotEmpty(message = "category description field must not be Empty")
	@Size(min = 10, message = "password must be minimum 10 charactor")
	private String categoryDescription;
}
