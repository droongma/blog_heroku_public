package com.study.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * JPA : ORM이다.
 * ORM : Java object를 table로 매핑해줌
 */
@Data // getter, setter 모두 만들어줌
@NoArgsConstructor
@AllArgsConstructor
@Builder // 빌더 패턴
@Entity // User 클래스를 이용해 MySQL에 테이블을 만들어 준다.
//@DynamicInsert // insert시 null인 필드를 제외!
public class User {
	
	@Id // Primary Key
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 프로젝트에서 연결된 DB의 넘버링 전략을 따라간다. 스프링에서 insert 연산시에 따로 id가 컬럼에 포함되지 않는다
	private int id; // 시퀀스(oracle), auto_increment(MySQL)
	
	@Column(nullable = false, length = 100, unique = true) // 최대 30자까지
	private String username; // 아이디
	
	@Column(nullable = false, length = 100)
	private String password;
	
	@Column(nullable = false, length = 50)
	private String email;
	
/*	
 * @ColumnDefault("'user'") // user가 아니다 'user'임에 주의!
 * private String role; // 사실 Enum을 쓰는게 좋다. role의 종류 : ADMIN, USER 등
*/
	
	// DB는 RoleType이라는 게 없다. 
	@Enumerated(EnumType.STRING) // 그래서 해당 enum이 String 타입임을 알려줌
	private RoleType role; // enum 사용
	
	private String oauth; // 카카오 로그인 vs 일반 로그인 구분
	
	@CreationTimestamp // DB에 데이터 저장시 시간을 자동으로 입력해준다
	private Timestamp createDate;
	
}
