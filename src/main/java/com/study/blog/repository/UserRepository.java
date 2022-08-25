package com.study.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.study.blog.model.User;

/*
 *  의미 : User 테이블을 관리하는 레포지토리. User 테이블의 primary key는 integer!
 *  DAO. 아래 인터페이스는 자동으로 bean 등록(메모리에 띄워줌)이 된다.(@Repository 생략 가능)
 */
public interface UserRepository extends JpaRepository<User, Integer>{
	//  SELECT * FROM USER WHERE username=?1 ;
	Optional<User> findByUsername(String username);
	
	
	// 1. JPA Naming 쿼리
	// 이름을 이렇게만 지어도 SELECT * FROM USER WHERE username=?1 AND password=?2; 라는 쿼리 실행!
	// ?1은 1번째 파라미터, ?2는 2번째 파라미터 의미.
// User findByUsernameAndPassword(String username, String password); 
	
	// 2. 네이티브 쿼리 사용(1과 같은 동작 수행)
//	@Query(value= "SELECT * FROM USER WHERE username=?1 AND password=?2", nativeQuery = true)
//	User login(String username, String password);
}
