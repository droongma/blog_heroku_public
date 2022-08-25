package com.study.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.study.blog.model.Board;
import com.study.blog.model.User;

/*
 *  의미 : Board 테이블을 관리하는 레포지토리. 
 *  DAO. 아래 인터페이스는 자동으로 bean 등록(메모리에 띄워줌)이 된다.(@Repository 생략 가능)
 */
public interface BoardRepository extends JpaRepository<Board, Integer>{
	
}
