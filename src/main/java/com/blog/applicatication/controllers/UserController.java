package com.blog.applicatication.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blog.applicatication.payload.ApiResponse;
import com.blog.applicatication.payload.AppConstants;
import com.blog.applicatication.payload.UserDto;
import com.blog.applicatication.payload.UserResponse;
import com.blog.applicatication.services.UserService;

@RestController
@RequestMapping("/api/")
public class UserController {
	
	@Autowired
	private UserService userService;

//	@PostMapping("/")
//	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
//		return new ResponseEntity<>(this.userService.createUser(userDto), HttpStatus.CREATED);
//	}
	
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/users")
	public UserResponse getAllUser(
			@RequestParam(value = "pageNO", defaultValue = AppConstants.DEFAULT_PAGE_NO, required = false) int pageNo,
			@RequestParam(value = "pageNO", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "pageNO", defaultValue = AppConstants.DEFAULT_SORT_BY_FOR_USER, required = false) String sortBy,
			@RequestParam(value = "pageNO", defaultValue = AppConstants.DEFAULT_SORT_DIR, required = false) String sortDir
			){
		return this.userService.getAllUser(pageNo, pageSize, sortBy, sortDir);
	}
	
	@GetMapping("/users/{userId}")
	public ResponseEntity<UserDto> getUser(@PathVariable("userId") int userId){
		return new ResponseEntity<>(this.userService.getUserById(userId), HttpStatus.OK);
	}
	
	
	@PutMapping("/update/users/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @PathVariable("userId") int userId, @RequestBody UserDto userDto){
		return new ResponseEntity<>(this.userService.updateUserById(userId, userDto), HttpStatus.OK);
	}
	
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/delete/users/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable int userId){
		this.userService.deleteUserById(userId);
		return new ResponseEntity<>(new ApiResponse(String.format("delete user succesefully with given id : %s", userId), true), HttpStatus.OK);
	}
}
