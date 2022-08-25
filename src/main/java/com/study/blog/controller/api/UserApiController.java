package com.study.blog.controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.study.blog.config.auth.PrincipalDetail;
import com.study.blog.dto.ResponseDto;
import com.study.blog.model.RoleType;
import com.study.blog.model.User;
import com.study.blog.service.UserService;

@RestController // 데이터 리턴
public class UserApiController {
	@Autowired // DI(의존성 주입) 수행
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
//	@Autowired
//	private HttpSession session; // 이렇게 적어도 httpSession 사용 가능(의존성 주입) 
	
	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(@RequestBody User user) {
		System.out.println("UserApiController : save 호출");
		
		//실제로 DB에 insert 작업 수행
		userService.signUp(user);
		
		// 자바오브젝트는 클라이언트에게 리턴시 Jackson 라이브러리에 의해 JSON으로 변환된 후 리턴된다
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); 
	}
	
	@PutMapping("/user")
	public ResponseDto<Integer> update(@RequestBody User user) {
		userService.updateUser(user);
		
		// DB에 commit 후 세션에 새로운 authentication 객체를 넣어줌(직접 세션값 변경)
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); 
	}
	
	// 1. 전통적인 로그인 방식 구현!
	/*
		@PostMapping("/api/user/login") // HttpSession 사용하고 싶을 때 이렇게 매개변수로 받아 사용가능
		public ResponseDto<Integer> login(@RequestBody User user, HttpSession session) {
			System.out.println("UserApiController : login 호출");
			
			//실제로 DB에 insert 작업 수행
			User principal = userService.login(user); // principal(접근주체)
			if (principal != null) {
				session.setAttribute("principal", principal); // "principal"이 key값
			}
			
			// 자바오브젝트는 클라이언트에게 리턴시 Jackson 라이브러리에 의해 JSON으로 변환된 후 리턴된다
			return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); 
		}
	*/

	// 2. 스프링 시큐리티를 이용한 로그인 방식 구현!
	
	
}
