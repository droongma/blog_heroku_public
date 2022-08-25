package com.study.blog.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.blog.model.KakaoAccount;
import com.study.blog.model.KakaoProfile;
import com.study.blog.model.OAuthToken;
import com.study.blog.model.User;
import com.study.blog.service.UserService;

/* 인증(로그인)이 안된 사용자들은 다음과 같은 경로 및 파일만 접근 허용함.
 * 1. '/auth'가 들어간 경로만 출입할 수 있게 허용한다.
 * 2. 경로가 '/'인 경우도 허용(index.jsp)
 * 3. static 폴더 이하에 있는 js, css, image 폴더도 접근 허용 
 */ 
// RestTemplate을 이용해 HTTP 요청을 편하게 할 수 있다.
@Controller
public class UserController {
	
	@Value("${cos.key}")
	private String cosKey;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/auth/joinForm")
	public String joinForm() {
		return "user/joinForm";
	}
	
	@GetMapping("/auth/loginForm")
	public String loginForm() {
		return "user/loginForm";
	}
	
	@GetMapping("/user/updateForm")
	public String updateForm() {
		return "user/updateForm";
	}
	
	// ResponseBody : 별도의 뷰 없이 데이터만 전송
	@GetMapping("/auth/kakao/callback")
	public String kakaoCallback(String code) { // Data를 리턴해주는 컨트롤러 함수, code 파라미터 값을 읽어옴
		/*
		 *  POST 방식으로 key=value 타입의 데이터를 카카오에 요청하기
		 */
		RestTemplate rt = new RestTemplate();
		
		// HTTPHeader 오브젝트 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		// HTTPBody 오브젝트 생성
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", "a1a9d5f6a73203a6a2b6af4dafcfec47");
		params.add("redirect_uri",  "http://localhost:8000/auth/kakao/callback");
		params.add("code", code);
		
		// HTTPHeader와 HTTPBody를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =new HttpEntity<>(params, headers);
		
		// 인가 코드를 바탕으로 토큰 요청하기(HTTP POST 방식으로), 그리고 response 변수로 응답을 받는다
		ResponseEntity<String> response = rt.exchange(
				"https://kauth.kakao.com/oauth/token",
				HttpMethod.POST,
				kakaoTokenRequest,
				String.class // 응답 데이터의 타입
		);
		
		// Gson, Json Simple, ObjectMapper(Json 오브젝트를 자바 오브젝트로 바꿔주기)
		ObjectMapper objectMapper = new ObjectMapper();
		OAuthToken oauthToken = null;
		try {
			oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
		} catch (JsonMappingException e) { // 파싱 오류
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // response 읽어서 OAuthToken에 저장
		
		System.out.println("카카오 액세스 토큰 : " + oauthToken.getAccess_token());
		
		
		/* 
		 * 토큰으로 사용자 정보 가져오기(POST 방식 사용)
		 */
		RestTemplate rt2 = new RestTemplate();
		
		// HTTPHeader 오브젝트 생성
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization", "Bearer " + oauthToken.getAccess_token());
		headers2.add("content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		// HTTPHeader만 오브젝트에 담기
		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest =new HttpEntity<>(headers2);
		
		// 토큰을 바탕으로 사용자 정보 가져오기(HTTP POST 방식으로), response2 변수로 응답을 받는다
		ResponseEntity<String> response2 = rt2.exchange(
				"https://kapi.kakao.com/v2/user/me",
				HttpMethod.POST,
				kakaoProfileRequest,
				String.class // 응답 데이터의 타입
		);
		System.out.println("사용자 정보 : " +  response2.getBody());
		// Gson, Json Simple, ObjectMapper(Json 오브젝트를 자바 오브젝트로 바꿔주기)
		ObjectMapper objectMapper2 = new ObjectMapper();
		KakaoProfile kakaoProfile = null;
		try {
			kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
		} catch (JsonMappingException e) { // 파싱 오류
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // response 읽어서 OAuthToken에 저장
		
		long kakaoId = kakaoProfile.getId();
		KakaoAccount userKakaoAccount = kakaoProfile.getKakao_account();
		String kakaoEmail = null;
		String username  = null;
		if (!userKakaoAccount.getEmail_needs_agreement()) { // 이메일 정보에 접근 가능하면 가져옴
			kakaoEmail = userKakaoAccount.getEmail();
			username = kakaoEmail + "_" + kakaoId;
		}
		else {
			username = String.valueOf(kakaoId);
			kakaoEmail = username + "@cosblog.com";
		}
		
		// 블로그 로그인에 필요한 유저정보 : username, password, email
		System.out.println("카카오 아이디(번호) : " + kakaoId);
		System.out.println("카카오 email : " +  kakaoEmail);
		
		System.out.println("Blog에서의 username : " + username); 
		System.out.println("Blog에서의 email : " + kakaoEmail);
//		UUID tempPassword = UUID.randomUUID(); // 랜덤값
		System.out.println("Blog에서의 password : " + cosKey); // 카카오 패스워드를 사용할거라 그냥 아무 값이나 넣을 것임
		
		User kakaoUser = User.builder().username(username)
				.password(cosKey).email(kakaoEmail).oauth("kakao").build();
		User originUser = userService.findUser(username);
		
		if (originUser.getUsername() == null) { // 블로그 비가입자는 카카오 계정을 이용해 회원가입 진행
			System.out.println("신규 블로그 회원입니다. 자동 회원가입을 진행합니다");
			userService.signUp(kakaoUser);
		}
		
		System.out.println("자동 로그인 진행");
		// 로그인 처리
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(), cosKey));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return "redirect:/";
//		return "인가코드 : " +  code + "<br/>access 토큰 요청 후 받은 응답 : " + response.getBody()
//			+ "<br/>사용자 정보 요청 후 받은 응답 : " + response2.getBody()
//			+ "<br/>카카오아이디 : " + kakaoId + "<br/>카카오 email : " + kakaoEmail;
	}
}
