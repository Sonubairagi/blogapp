package com.blog.applicatication.services.impls;

import java.util.List;


import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blog.applicatication.entities.Role;
import com.blog.applicatication.entities.User;
import com.blog.applicatication.exceptions.ResourceNotFoundException;
import com.blog.applicatication.payload.AppConstants;
import com.blog.applicatication.payload.UserDto;
import com.blog.applicatication.payload.UserResponse;
import com.blog.applicatication.repositories.RoleRepository;
import com.blog.applicatication.repositories.UserRepository;
import com.blog.applicatication.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepository roleRepo;
	
	@Override
	public UserDto registerNewUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		Role role = this.roleRepo.findById(AppConstants.ROLE_USER).get();
		user.getRoles().add(role);
		User newUser = this.userRepo.save(user);
		return this.modelMapper.map(newUser, UserDto.class);
	}

	@Override
	public UserResponse getAllUser(int pageNo, int pageSize, String sortBy, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		Page<User> pages = this.userRepo.findAll(pageable);
		List<User> contents = pages.getContent();
		List<UserDto> users = contents.stream().map((content)->this.mapToDto(content)).collect(Collectors.toList());
		UserResponse userResponse = new UserResponse();
		userResponse.setContents(users);
		userResponse.setPageNo(pages.getNumber());
		userResponse.setPageSize(pages.getSize());
		userResponse.setTotalElement(pages.getTotalElements());
		userResponse.setTotalPages(pages.getTotalPages());
		userResponse.setLast(pages.isLast());
		return userResponse;
	}

	@Override
	public UserDto getUserById(int userId) {
		return mapToDto(this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("user","id",userId)));
	}

	@Override
	public UserDto updateUserById(int userId, UserDto userDto) {
		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("user","id",userId));
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		 return mapToDto(this.userRepo.save(user));
	}

	@Override
	public void deleteUserById(int userId) {
		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("user","id",userId));
		this.userRepo.deleteById(user.getUserId());
	}
	
	private User mapToEntity(UserDto userDto) {
		User user = new User();
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		return user;
	}
	
	private UserDto mapToDto(User user) {
		UserDto userDto = new UserDto();
		userDto.setUserId(user.getUserId());
		userDto.setName(user.getName());
		userDto.setEmail(user.getEmail());
		userDto.setPassword(user.getPassword());
		return userDto;
	}

}
