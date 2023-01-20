package com.blog.applicatication.services;

import java.util.List;

import com.blog.applicatication.payload.CategoryDto;
import com.blog.applicatication.payload.PostDto;
import com.blog.applicatication.payload.PostResponse;

public interface PostService {

	//create post
	PostDto createPost(int userId, int categoryId, PostDto postDto);
	
	//get all post
	PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir);
	
	//get one post
	PostDto getPostByPostId(int postId);
	
	//get post by user
	List<PostDto> getPostsByUserId(int userId);
	
	//get post by category
	List<PostDto> getPostsByCategoryId(int categoryId);
	
	//get post by user and category
	List<PostDto> getPostByUserIdAndCategoryId(int userId, int categoryId);
	
	//search post
	List<PostDto> search(String keyword);
	
	//update post
	PostDto updatePostById(int postId, PostDto postDto);
	
	//delete post
	void deletePostById(int postId);
	
	//update category of post
	PostDto updateCategoryOfPost(int postId, int categoryId);
	
	//update image of post
	PostDto updateImageOfPost(int postId, String image); 
}
