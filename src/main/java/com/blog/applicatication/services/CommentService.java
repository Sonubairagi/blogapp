package com.blog.applicatication.services;

import java.util.List;

import com.blog.applicatication.payload.CommentDto;

public interface CommentService {

	//create comment
	
	CommentDto createComment(int userId, int postId, CommentDto commentDto);
	
	//get all comments
	
	List<CommentDto> getAllPost();
	
	//get comment by commentId
	
	CommentDto getCommentByCommentId(int commentId);
	
	//get comments by post
	
	List<CommentDto> getCommentsByPost(int postId);
	
	//get comments by user
	
	List<CommentDto> getCommentsByUser(int userId);
	
	//get comment by post and commentId
	
	CommentDto getCommentByPostAndCommentId(int postId, int commentId);
	
	//get comment by user and commentId
	
	CommentDto getCommentByUserAndCommentId(int userId, int commentId);
	
	//get comment by user and post and commentId
	
	CommentDto getCommentByUserAndPostAndCommentId(int userId, int postId, int commmentId);
	
	//get comments by userId and postId
	
	List<CommentDto> getCommentsByUserAndPost(int userId, int postId);
	
	//update comment
	
	CommentDto updateCommentById(int userId, int postId, int commentId, CommentDto commentDto);
	
	//delete comment
	
	void deleteCommentById(int userId, int postId, int commentId);
	
	//search comment
	
	List<CommentDto> searchComment(String keyword);
	
	//count comment like
	
	//count comment dislike
	
	//create comment reply
}
