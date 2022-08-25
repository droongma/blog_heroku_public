package com.study.blog.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
//import lombok.Getter;
//import lombok.Setter;

//@Getter
//@Setter
@Data // getter와 setter를 동시에 만들어줌
// @AllArgsConstructor // 모든 필드의 값을 다 설정하는 생성자
@NoArgsConstructor // 빈 생성자
// @RequiredArgsConstructor // final 붙은 애들에 대한 생성자 만들어줌
public class Member {
	private int id;
	private String username;
	private String password;
	private String email;
	
	 /* builder를 쓸 때의 장점
	  * 1. 객체에 값을 넣을때 순서를 지키지 않아도 된다. 따라서 파라미터 순서를 헷갈려서 객체의 값을 잘못 넣는 실수를 방지한다.
	  * 2. 또한 원하는 파라미터만 쓰기도 가능. 이 경우 나머지 값은 default 값(String : null, int : 0 등)으로 설정.
	  */
	@Builder
	public Member(int id, String username, String password, String email) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
	}
}
