package com.blog.applicatication.services.impls;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.applicatication.entities.Comment;
import com.blog.applicatication.entities.Post;
import com.blog.applicatication.entities.User;
import com.blog.applicatication.exceptions.ResourceNotFoundException;
import com.blog.applicatication.payload.CommentDto;
import com.blog.applicatication.repositories.CommentRepository;
import com.blog.applicatication.repositories.PostRepository;
import com.blog.applicatication.repositories.UserRepository;
import com.blog.applicatication.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PostRepository postRepo;
	
	@Autowired
	private CommentRepository commentRepo;
	
	@Autowired
	private ModelMapper mapper;

	@Override
	public CommentDto createComment(int userId, int postId, CommentDto commentDto) {
		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("user", "user id", userId));
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("post", "post id", postId));
		Comment comment = this.mapper.map(commentDto, Comment.class);
		comment.setUser(user);
		comment.setPost(post);
		Comment saveComment = this.commentRepo.save(comment);
		return this.mapper.map(saveComment, CommentDto.class);
	}

	@Override
	public List<CommentDto> getAllPost() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CommentDto getCommentByCommentId(int commentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CommentDto> getCommentsByPost(int postId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CommentDto> getCommentsByUser(int userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CommentDto getCommentByPostAndCommentId(int postId, int commentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CommentDto getCommentByUserAndCommentId(int userId, int commentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CommentDto getCommentByUserAndPostAndCommentId(int userId, int postId, int commmentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CommentDto> getCommentsByUserAndPost(int userId, int postId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CommentDto updateCommentById(int userId, int postId, int commentId, CommentDto commentDto) {
		
		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("user", "user id", userId));
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("post", "post id", postId));
		Comment comment = this.commentRepo.findByUserAndPostAndCommentId(user,post,commentId);
		
		if(comment==null) {
			throw new ResourceNotFoundException("comment", "comment id", commentId);
		}
		
		comment.setBody(commentDto.getBody());
		this.commentRepo.save(comment);
		
		return this.mapper.map(comment, CommentDto.class);
	}

	@Override
	public void deleteCommentById(int userId, int postId, int commentId) {

		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("user", "user id", userId));
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("post", "post id", postId));
		Comment comment = this.commentRepo.findByUserAndPostAndCommentId(user,post,commentId);
		if(comment==null) {
			throw new ResourceNotFoundException("comment", "comment id", commentId);
		}
		this.commentRepo.delete(comment);
	}

	@Override
	public List<CommentDto> searchComment(String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
