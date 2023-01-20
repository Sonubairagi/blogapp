package com.blog.applicatication;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.blog.applicatication.entities.Role;
import com.blog.applicatication.payload.AppConstants;
import com.blog.applicatication.repositories.RoleRepository;

@SpringBootApplication
public class BlogApplicaticationApplication implements CommandLineRunner {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepository roleRepo;
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(BlogApplicaticationApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		System.out.println(this.passwordEncoder.encode("7240"));
		
		try {
			
			Role role1 = new Role();
			role1.setRoleId(AppConstants.ROLE_ADMIN);
			role1.setName("ROLE_ADMIN");
			
			Role role2 = new Role();
			role2.setRoleId(AppConstants.ROLE_USER);
			role2.setName("ROLE_USER");
			
			List<Role> roles = new ArrayList<>();
			roles.add(role1);
			roles.add(role2);
			
			List<Role> result = this.roleRepo.saveAll(roles);
			
			result.forEach(r->{
				System.out.println(r.getName());
			});
			
		} catch (Exception e) {
			
		}
	}

}
