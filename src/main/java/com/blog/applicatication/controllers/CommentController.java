package com.blog.applicatication.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.applicatication.payload.ApiResponse;
import com.blog.applicatication.payload.CommentDto;
import com.blog.applicatication.services.CommentService;

@RestController
@RequestMapping("/api/")
public class CommentController {
	
	@Autowired
	private CommentService commentService;

	@PostMapping("/users/{userId}/posts/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(@PathVariable int userId, @PathVariable int postId, @RequestBody CommentDto commentDto){
		CommentDto saveCommentDto = this.commentService.createComment(userId, postId, commentDto);
		return new ResponseEntity<>(saveCommentDto, HttpStatus.CREATED);
	}
	
	@PutMapping("/users/{userId}/posts/{postId}/comments/{commentId}")
	public ResponseEntity<CommentDto> updateComment(@PathVariable int userId, @PathVariable int postId, @PathVariable int commentId, @RequestBody CommentDto commentDto){
		return new ResponseEntity<CommentDto>(this.commentService.updateCommentById(userId, postId, commentId, commentDto), HttpStatus.OK);
	}
	
	@DeleteMapping("/users/{userId}/posts/{postId}/comments/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable int userId, @PathVariable int postId, @PathVariable int commentId){
		this.commentService.deleteCommentById(userId, postId, commentId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("comment has been deleted successfully",true), HttpStatus.OK);
	}
}
