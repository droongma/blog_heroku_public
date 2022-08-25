package com.study.blog.test;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.study.blog.model.Board;
import com.study.blog.model.Reply;
import com.study.blog.repository.BoardRepository;
import com.study.blog.repository.ReplyRepository;

@RestController
public class ReplyControllerTest {
	@Autowired
	private ReplyRepository replyRepository;
	
	@Autowired
	private BoardRepository boardRepository;
	
	@GetMapping("/test/board/{id}") // JPA 무한참조 테스트
	public Board getBoard(@PathVariable int id) {
		return boardRepository.findById(id).get(); // 리턴시 jackson 라이브러리(모델의 getter로 얻은 객체를 json으로 리턴) 호출
	}
	
	@GetMapping("/test/reply") // JPA 무한참조 테스트
	public List<Reply> getReply() { // reply를 직접 호출하므로 board랑 user 정보도 가져옴
		return replyRepository.findAll(); // 리턴시 jackson 라이브러리(모델의 getter로 얻은 객체를 json으로 리턴) 호출
	}
}
