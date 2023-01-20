package com.blog.applicatication.payload;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

	private int userId;
	
	@NotNull(message = "name field must not be null")
	@NotEmpty(message = "name field must not be Empty")
	@Size(min = 3, max = 100, message = "name atleast 3 charactor")
	@Size(max = 100, message = "name atmost 100 charactor onley")
	private String name;
	
	@Email(message = "email is not valid!!")
	@NotNull(message = "email field must not be null")
	@NotEmpty(message = "email field must not be Empty")
	private String email;
	
	//@Pattern(regexp = "")
	@NotNull(message = "password field must not be null")
	@NotEmpty(message = "password field must not be Empty")
	@Size(min = 4, message = "password must be minimum 4 charactor")
	@Size(max = 10, message = "password must be maximum 10 charactor")
	private String password;
	
	private Set<RoleDto> roles = new HashSet<>();
	
}
