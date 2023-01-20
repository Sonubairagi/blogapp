package com.blog.applicatication.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blog.applicatication.payload.ApiResponse;
import com.blog.applicatication.payload.AppConstants;
import com.blog.applicatication.payload.PostDto;
import com.blog.applicatication.payload.PostResponse;
import com.blog.applicatication.services.FileService;
import com.blog.applicatication.services.PostService;

@RestController
@RequestMapping("/api/")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;

	//create post
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@PathVariable int userId, @PathVariable int categoryId, @RequestBody PostDto postDto){
	return new ResponseEntity<>(this.postService.createPost(userId, categoryId, postDto), HttpStatus.CREATED);	
	}
	
	//get all posts
	
	@GetMapping("/posts")
	public PostResponse getAllPost(
			@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NO, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY_FOR_POST, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIR, required = false) String sortDir
			){
		return this.postService.getAllPost(pageNo, pageSize, sortBy, sortDir);
	}
	
	//get one post by postId
	
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getOnePost(@PathVariable int postId){
		return new ResponseEntity<>(this.postService.getPostByPostId(postId), HttpStatus.OK);
	}
	
	//get post by user
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable int userId){
		return new ResponseEntity<>(this.postService.getPostsByUserId(userId), HttpStatus.OK);
	}
	
	//get post by category
	
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable int categoryId){
		return new ResponseEntity<>(this.postService.getPostsByCategoryId(categoryId), HttpStatus.OK);
	}
	
	//get post by user and category
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByUserAndCategory(@PathVariable int userId, @PathVariable int categoryId){
		return new ResponseEntity<>(this.postService.getPostByUserIdAndCategoryId(userId, categoryId), HttpStatus.OK);
	}
	
	//update post by postId
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@PathVariable int postId, @RequestBody PostDto postDto){
		return new ResponseEntity<>(this.postService.updatePostById(postId, postDto), HttpStatus.OK);
	}
	
	//update category in post
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/category/{categoryId}/posts/{postId}")
	public ResponseEntity<PostDto> updateCategoryOfPost(@PathVariable int postId, @PathVariable int categoryId){
		return new ResponseEntity<>(this.postService.updateCategoryOfPost(postId, categoryId), HttpStatus.OK);
	}
	
	//update image in post
	
//	@PutMapping("/posts/{postId}")
//	public ResponseEntity<PostDto> updateImagesOfPost(@PathVariable int postId, @RequestParam String image){
//		return new ResponseEntity<>(this.postService.updateImageOfPost(postId, image), HttpStatus.OK);
//	}
	
	//delete post by postId
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable int postId){
		this.postService.deletePostById(postId);
		return new ResponseEntity<>(new ApiResponse(String.format("post deleted succesefully with post id : %s", postId), true), HttpStatus.OK);
	}
	
	//searching by String
	
	@RequestMapping("/posts/search/{keyword}")
	public ResponseEntity<List<PostDto>> search(@PathVariable String keyword){
		return new ResponseEntity<>(this.postService.search(keyword), HttpStatus.OK);
	}
	
	//upload images
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/posts/{postId}/image")
	public ResponseEntity<PostDto> uploadImage(@PathVariable int postId, @RequestParam("image") MultipartFile image) throws IOException{
		PostDto postDto = this.postService.getPostByPostId(postId);
		String fileName = this.fileService.uploadImage(path, image);
		postDto.setImage(fileName);
		PostDto updatedPost = this.postService.updatePostById(postId, postDto);
		return new ResponseEntity<>(updatedPost, HttpStatus.OK);
	}
	
	//get images
	
	@GetMapping(value = "posts/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void getImage(@PathVariable String imageName, HttpServletResponse response) throws IOException {
		InputStream resource = this.fileService.getImage(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	} 
	
}
