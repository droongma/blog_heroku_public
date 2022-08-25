package com.study.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.study.blog.config.auth.PrincipalDetail;
import com.study.blog.service.BoardService;

@Controller // HTML 파일 리턴(리턴시 viewResolver 작동) 
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	// 컨트롤러에서 세션을 어떻게 찾나?
	// @AuthenticationPrincipal PrincipalDetail principal
	@GetMapping({"", "/"})
	public String index(Model model, @PageableDefault(size=3, sort="id", direction=Sort.Direction.DESC) Pageable pageable) {
		model.addAttribute("boards", boardService.getPosts(pageable)); // model은 JSP에서 request 정보라고 보면 된다.
		return "index"; // index 페이지로 model을 통해 boards 정보가 전달된다.
	}
	
	@GetMapping("/board/{id}")
	public String findById(@PathVariable int id, Model model) {
		model.addAttribute("board", boardService.seeDetails(id));
		return "board/detail";
	}
	
	@GetMapping("/board/{id}/updateForm")
	public String updateForm(@PathVariable int id, Model model) {
		model.addAttribute("board", boardService.seeDetails(id));
		return "board/updateForm";
	}
	
	// user 권한이 필요
	@GetMapping("/board/saveForm")
	public String saveForm() { 
		return "board/saveForm";
	}
}
