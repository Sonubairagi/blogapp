package com.blog.applicatication.payload;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

	private int postId;
	
	private String postTitle;
	
	private String postContent;
	
	private String image;
	
	private String date;
	
	private CategoryDto category;
	
	private UserDto user;
	
	private List<CommentDto> comments = new ArrayList<CommentDto>();
}
