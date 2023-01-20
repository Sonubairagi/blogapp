package com.blog.applicatication.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

	private int commentId;
	
	private String body;
	
	private UserDto user;
	
//	private PostDto post;
	
	
}
