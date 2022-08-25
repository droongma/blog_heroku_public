package com.study.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// 참고 : 그냥 @Controller는 HTML 파일을 응답한다

// 사용자가 요청하면 데이터를 응답해주는 컨트롤러 
@RestController
public class HttpControllerTest {
	/* 
	 * HttpControllerTest : Get, Post, Put, Delete 요청 테스트
	 * 
	 * MessageConverter가 body나 쿼리스트링에 있는 데이터를 자동으로 파싱해서 (Member 객체에) 넣어줌
	 * 인터넷 브라우저 요청(URL을 주소 입력창에 쳐서 요청)은 무조건 get 요청만 가능!!
	 * get 방식은 URL에서 쿼리 스트링을 통해 원하는 데이터를 요청함. 쿼리 스트링 값은 RequestParam을 이용해 얻어옴
	 * 
	 * 중요 : post 방식은 쿼리 스트링 대신 body에 데이터를 담아 보냄!!!
	 * 예를 들어, html 폼태그 이용(x-www-form-urlencoded), JSON(raw/json) 이용 등등의 방법
	 * post 방식에서, RequestBody를 통해 body에 담은 데이터 값(raw 값)을 특정 클래스 객체로 매핑해서 가져올 수 있음
	 */
	
	private static final String TAG = "HttpController Test : ";
	
	// 주소 : http://localhost:8000/blog/http/lombok
	@GetMapping("/http/lombok") 
	public String lombokTest() {
//		Member m = new Member(1, "ssar",  "1234", "agb2594@naver.com"); // Using all args constructor
		Member m = Member.builder().password("1234").email("ssar@naver.com").build(); // Using build constructor
		System.out.println(TAG + "getter : " + m.getId());
		m.setId(5000);
		System.out.println(TAG + "setter : " + m.getId());
		System.out.println(TAG + "getter : " + m.getUsername());
		m.setUsername("assdsd @@#");
		System.out.println(TAG + "setter : " + m.getUsername());
//		Member m2 = new Member(); // Using no args constructor
		return "lombok test 완료";
	}
	
	/* HTTP 매핑주소(default) : http://localhost:8080/http/get
	 *  HTTP 매핑주소(application.yml의 설정으로부터 매핑) : http://localhost:8000/blog/http/get
	 */
	@GetMapping("/http/get") 
//	public String getTest(@RequestParam int id, @RequestParam String username) { 
	public String getTest(Member m) { // member 객체를 이용해 쿼리 스트링 값 불러옴
		
		return "get 요청 with id = " + m.getId() + ", username : " + m.getUsername() // get : select 수행, RequestParam : 쿼리 스트링 값 얻어오기
			+ ", password : " + m.getPassword() + ", email : " + m.getEmail(); 
	}
	
	/* 1. Postman에서 x-www-form-urlencoded 방식 활용 */
	@PostMapping("/http/postform") // HTTP 매핑주소 : http://localhost:8080/blog/http/post
	public String postformTest(Member m) {
		return "post 요청 with id = " + m.getId() + ", username : " + m.getUsername()
		+ ", password : " + m.getPassword() + ", email : " + m.getEmail();  // post : insert 수행
	}
	
	/* 2. Postman에서 raw(text 방식 활용(일반 문자열) */
//	@PostMapping("/http/post") // HTTP 매핑주소 : http://localhost:8080/blog/http/post
//	public String postTest(@RequestBody String text) { // MIMEtype : text/plain(raw 데이터 보냄)
//		return "post 요청 : " + text; // post : insert 수행
//	}
	
	/* 3. Postman에서 raw(JSON) 방식 활용. requestBody를 이용한다
	 * 스프링이 해당 JSON 데이터를 Java Object로 변환해서 받아줌
	 * 이 때 MessageConverter의 Jackson 라이브러리가 변환을 수행한다.
	 */
	@PostMapping("/http/post") // HTTP 매핑주소 : http://localhost:8080/blog/http/post
	public String postTest(@RequestBody Member m) { // MIMEtype : application/json(json 보냄)
		return "post 요청 with id = " + m.getId() + ", username : " + m.getUsername()
		+ ", password : " + m.getPassword() + ", email : " + m.getEmail(); // post : insert 수행
	}
	
	@PutMapping("/http/put") // HTTP 매핑주소 : http://localhost:8080/blog/http/put
	public String putTest(@RequestBody Member m) {
		return "put 요청 with id = " + m.getId() + ", username : " + m.getUsername()
		+ ", password : " + m.getPassword() + ", email : " + m.getEmail() ; // put : update 수행
	}
	@DeleteMapping("http/delete") // HTTP 매핑주소 : http://localhost:8080/blog/http/delete
	public String deleteTest() {
		return "delete 요청"; // delete : delete 수행
	}
}
