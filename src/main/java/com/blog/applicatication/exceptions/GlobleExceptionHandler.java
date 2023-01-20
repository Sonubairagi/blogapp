package com.blog.applicatication.exceptions;

import java.io.IOException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.blog.applicatication.payload.ApiResponse;
import com.blog.applicatication.payload.ErrorDetails;

@RestControllerAdvice
public class GlobleExceptionHandler {

	//Specific Exception handler class
	
	//1. handle ResourseNotFoundExeption
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest webRequest){
		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), webRequest.getDescription(false));
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.NOT_FOUND);
	}
	
	//2. handle MethodArgumentNotValidException
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleMethondArgumentNotValidException(MethodArgumentNotValidException ex){
		Map<String, String> response = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error)->{
			String fieldName = ((FieldError)error).getField();
			String message = error.getDefaultMessage();
			response.put(fieldName, message);
		});
		return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
	}
	
	//3. handle PostNotFoundExeption
	@ExceptionHandler(PostNotFoundException.class)
	public ResponseEntity<String> handlePostNotFoundExeption(PostNotFoundException ex){
		String message = ex.getMessage();
		return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
	}
	
	//4. handle IOException
	@ExceptionHandler(IOException.class)
	public ResponseEntity<ApiResponse> handleIOException(IOException ex){
		ApiResponse fileResponse = new ApiResponse("image not uploaded !", false);
		return new ResponseEntity<>(fileResponse, HttpStatus.BAD_REQUEST);
	}
	
	//5. handle ApiException
	@ExceptionHandler(ApiException.class)
	public ResponseEntity<ApiResponse> handleApiException(ApiException ex){
		String message = ex.getMessage();
		ApiResponse apiResponse = new ApiResponse(message, false);
		return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
	}
	
	//Globle Exception handler class
//	@ExceptionHandler(Exception.class)
//	public ResponseEntity<ErrorDetails> handleAllException(Exception exception, WebRequest webRequest){
//		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), webRequest.getDescription(false));
//		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.BAD_REQUEST);
//	}
}
