package com.blog.applicatication.services.impls;

import java.util.Date;


import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blog.applicatication.entities.Category;
import com.blog.applicatication.entities.Post;
import com.blog.applicatication.entities.User;
import com.blog.applicatication.exceptions.PostNotFoundException;
import com.blog.applicatication.exceptions.ResourceNotFoundException;
import com.blog.applicatication.payload.CategoryDto;
import com.blog.applicatication.payload.MyComparator;
import com.blog.applicatication.payload.PostDto;
import com.blog.applicatication.payload.PostResponse;
import com.blog.applicatication.repositories.CategoryRepository;
import com.blog.applicatication.repositories.PostRepository;
import com.blog.applicatication.repositories.UserRepository;
import com.blog.applicatication.services.PostService;

@Service
public class PostServiceImpl extends MyComparator implements PostService {
	
	@Autowired
	private PostRepository postRepo;
	
	@Autowired
	private CategoryRepository categoryRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ModelMapper mapper;

	//create post
	
	@Override
	public PostDto createPost(int userId, int categoryId, PostDto postDto) {
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category", "category id", categoryId));
		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("user", "user id", userId));
		Post post = this.mapper.map(postDto, Post.class);
		post.setImage("default.png");
		post.setDate(new Date());
		post.setCategory(category);
		post.setUser(user);
		Post newPost = this.postRepo.save(post);
		return this.mapper.map(newPost, PostDto.class);
	}

	//get all post
	
	@Override
	public PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		Page<Post> pages = this.postRepo.findAll(pageable);
		List<Post> contents = pages.getContent();
		List<PostDto> posts = contents.stream().map((content)->this.mapper.map(content, PostDto.class)).collect(Collectors.toList());
		PostResponse postResponse = new PostResponse();
		postResponse.setContents(posts);
		postResponse.setPageNo(pages.getNumber());
		postResponse.setPageSize(pages.getSize());
		postResponse.setTotalElement(pages.getTotalElements());
		postResponse.setTotalPages(pages.getTotalPages());
		postResponse.setLast(pages.isLast());
		return postResponse;
	}

	//get one post by postId
	
	@Override
	public PostDto getPostByPostId(int postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("post", "post id", postId));
		return this.mapper.map(post, PostDto.class);
	}

	//get post by user
	
	@Override
	public List<PostDto> getPostsByUserId(int userId) {
		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("user", "user id", userId));
		List<Post> posts = this.postRepo.findByUser(user);
		if(posts.isEmpty())
			throw new PostNotFoundException("You didn't create this type of post that's why post is not found");
		return posts.stream().map(post->this.mapper.map(post, PostDto.class)).collect(Collectors.toList());
	}

	//get post by category
	
	@Override
	public List<PostDto> getPostsByCategoryId(int categoryId) {
//		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category", "category id", categoryId));
//		List<Post> posts = this.postRepo.findByCategory(category);
//		return posts.stream().map((post)->this.mapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		List<Post> posts = this.postRepo.findByCategoryId(categoryId);
		if(posts.isEmpty())
			throw new PostNotFoundException("You didn't create this type of post that's why post is not found");
		return posts.stream().map(post->this.mapper.map(post, PostDto.class)).collect(Collectors.toList());
	}

	//get post by user and category
	
	@Override
	public List<PostDto> getPostByUserIdAndCategoryId(int userId, int categoryId) {
		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("user", "user id", userId));
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category", "category id", categoryId));
		List<Post> posts = this.postRepo.findByUserAndCatagory(user, category);
		if(posts.isEmpty())
			throw new PostNotFoundException("You didn't create this type of post that's why post is not found");
		return posts.stream().map((post)->this.mapper.map(post, PostDto.class)).collect(Collectors.toList());
	}

	//searching post
	
	@Override
	public List<PostDto> search(String keyword) {
		List<Post> posts = this.postRepo.findByPostTitleContaining(keyword);
		List<PostDto> postDtos = posts.stream().map((post)->this.mapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	//update post by postId
	
	@Override
	public PostDto updatePostById(int postId, PostDto postDto) {
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("post", "post id", postId));
		post.setPostTitle(postDto.getPostTitle());
		post.setPostContent(postDto.getPostContent());
		post.setImage(postDto.getImage());
		Post newPost = this.postRepo.save(post);
		return this.mapper.map(newPost, PostDto.class);
	}

	//delete post by postId
	
	@Override
	public void deletePostById(int postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("post", "post id", postId));
		this.postRepo.delete(post);
	}

	//update category in post
	
	//firstly confurm this category in database Category Table if this category is availble then update category in post
	@Override
	public PostDto updateCategoryOfPost(int postId, int categoryId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("post", "post id", postId));
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category", "category id", categoryId));
		post.setCategory(category);
		Post newPost = this.postRepo.save(post);
		return this.mapper.map(newPost, PostDto.class);
	}

	//update images in post
	
	@Override
	public PostDto updateImageOfPost(int postId, String image) {
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("post", "post id", postId));
		post.setImage(image);
		Post newPost = this.postRepo.save(post);
		return this.mapper.map(newPost, PostDto.class);
	}

}
