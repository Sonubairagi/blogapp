package com.blog.applicatication.services.impls;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blog.applicatication.services.FileService;

@Service
public class FileServiceImpl implements FileService {

	//upload file
	
	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
	
		//get file name and create random file name
		
		String name = file.getOriginalFilename();
		String randomID = UUID.randomUUID().toString();
		String fileExtention = name.substring(name.indexOf("."));
//		if(fileExtention.equalsIgnoreCase("jpeg") || fileExtention.equalsIgnoreCase("png")) {
//			
//		}
		String fileName = randomID + fileExtention;
		
		//full path path+fileName
		
		String filePath = path + File.separator + fileName;
		
		//create folder if not exist
		
		File f = new File(path);
		if(!f.exists())
			f.mkdir();
		
		//copy file
		
		Files.copy(file.getInputStream(), Paths.get(filePath));
		
		return fileName;
	}

	//get file
	
	@Override
	public InputStream getImage(String path, String fileName) throws FileNotFoundException {
		
		String filePath = path + File.separator + fileName;
		InputStream inputStream = new FileInputStream(filePath);
		
		return inputStream;
	}

	
}
