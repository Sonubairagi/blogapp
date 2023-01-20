package com.blog.applicatication.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

	//upload file
	
	String uploadImage(String path, MultipartFile file) throws IOException;
	
	//get file
	
	InputStream getImage(String path, String fileName) throws FileNotFoundException;
}
