package com.blog.applicatication.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class AppConstants {

	public final static String DEFAULT_PAGE_NO = "0";
	
	public final static String DEFAULT_PAGE_SIZE = "10";
	
	public final static String DEFAULT_SORT_BY_FOR_POST = "postId";
	
	public final static String DEFAULT_SORT_BY_FOR_USER = "userId";
	
	public final static String DEFAULT_SORT_BY_FOR_CATEGORY = "categoryId";
	
	public final static String DEFAULT_SORT_DIR = "ASC";
	
	public final static Integer ROLE_ADMIN = 901;
	
	public final static Integer ROLE_USER = 902;
	
}
