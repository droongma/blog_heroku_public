package com.study.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// static 폴더에는 브라우저가 인식할 수 있는 (정적) 파일만 넣어야함!
// 파일리턴 기본경로 : src/main/resources/static
@Controller // 데이터가 아니라 HTML 파일 리턴
public class TempController {
	
	@GetMapping("/temp/home") // 주소 : http://localhost:8000/blog/temp/home
	public String tempHome() {
		System.out.println("tempHome()");
		return "/home.html"; // home.html 파일 리턴(full path : src/main/resources/static/home.html)
	}
	
	@GetMapping("/temp/img")
	public String tempImg() {
		return "/a.png";
	}
	
	@GetMapping("/temp/jsp")
	public String tempJsp() {
		// prefix : /WEB-INF/views/
		// suffix : .jsp
		return "test"; // 아파치 대신 톰캣이 이 jsp(자바) 파일을 컴파일해서 HTML 파일로 던져줌
	}
	
	
}
