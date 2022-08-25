package com.study.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/* 
	1.  모든 패키지는 반드시 com.cos.blog.xxx.yyy ... 형태처럼 해당 패키지 이하로 패키지 이름을 만들어야 함! 
	  그렇지 않으면 스프링이 얘를 읽어서(스캔해서) 메모리에 올려줄 수가 없다.
	2. 스프링이 com.cos.blog 패키지 이하를 스캔해서 모두 메모리에 올리는 것은 아니다.
	  특정 어노테이션이 붙은 클래스 파일만 new해서(IOC) 메모리에 올리며, 이는 스프링 컨테이너가 관리한다.
*/
@RestController
public class BlogControllerTest {
	
	/*  HTTP 매핑주소 : http://localhost:8080/test/hello  */
	@GetMapping("/test/hello")
	 public String hello() {
		 return "<h1>hello spring boot!</h1>";
	 }
}
