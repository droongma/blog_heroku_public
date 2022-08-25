package com.study.blog.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.study.blog.dto.ResponseDto;

@ControllerAdvice // Exception 발생시 이 클래스로 들어오게 된다
@RestController
public class GlobalExceptionHandler {
	
	// IllegalArgumentException 발생시 이 메서드가 예외처리를 수행함
//	@ExceptionHandler(value=IllegalArgumentException.class)
//	public String handleArgumentException(IllegalArgumentException e) { 
//		return "<h1>" + e.getMessage() + "</h1>";
//	}
	
	// Exception(위에서 처리되지 않은 어떤 것이든지) 발생시 이 메서드가 예외처리를 수행함
	@ExceptionHandler(value=Exception.class)
	public ResponseDto<String> handleException(Exception e) {
		return new ResponseDto<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
	}
}
