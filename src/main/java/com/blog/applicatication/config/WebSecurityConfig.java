package com.blog.applicatication.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.blog.applicatication.security.JwtAuthenticationEntryPoint;
import com.blog.applicatication.security.JwtAuthenticationFilter;
import com.blog.applicatication.security.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	public static final String[] USER_URLS = {
			
			"/api/v1/auth/**",
			"/v3/api-docs",
			"/v2/api-docs",
			"/swagger-resources/**",
			"/swagger-ui/**",
			"/webjars/**",
//			"/api/users/{userId}",
//			"/api/update/users/{userId}",
//			"/api/categories",
//			"/api/category/{categoryId}"
			
	};
//	public static final String[] ADMIN_URLS = {
//			
//			"/api/v1/auth/**",
//			"/v3/api-docs",
//			"/v2/api-docs",
//			"/swagger-resources/**",
//			"/swagger-ui/**",
//			"/webjars/**",
//			"/api/users",
//			//"/api/users/{userId}",
//			//"/api/update/users/{userId}",
//			"/api/delete/users/{userId}",
//			"/api/create/categories",
//			//"/api/categories",
//			//"/api/category/{categoryId}",
//			"/api/update/categories/{categoryId}",
//			"/api/delete/categories/{categoryId}"
//			
//	};
	
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	  @Autowired
	    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	    @Autowired
	    private JwtAuthenticationFilter jwtAuthenticationFilter;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		  http.
          csrf()
          .disable()
          .authorizeHttpRequests()
          .antMatchers(USER_URLS).permitAll()
          .antMatchers(HttpMethod.GET).permitAll()
//          .antMatchers(HttpMethod.PUT).permitAll()
          .anyRequest()
          .authenticated()
          .and()
          .exceptionHandling().authenticationEntryPoint(this.jwtAuthenticationEntryPoint)
          .and()
          .sessionManagement()
          .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		  http.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(this.userDetailsServiceImpl).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	
	
}
