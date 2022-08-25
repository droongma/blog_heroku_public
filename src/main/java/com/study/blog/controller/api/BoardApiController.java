package com.study.blog.controller.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.study.blog.config.auth.PrincipalDetail;
import com.study.blog.dto.ReplySaveRequestDto;
import com.study.blog.dto.ResponseDto;
import com.study.blog.model.Board;
import com.study.blog.model.Reply;
import com.study.blog.service.BoardService;

@RestController // 데이터 리턴
public class BoardApiController {
	@Autowired // DI(의존성 주입) 수행
	private BoardService boardService;
	
//	@Autowired
//	private HttpSession session; // 이렇게 적어도 httpSession 사용 가능(의존성 주입) 
	
	@PostMapping("/api/board")
	public ResponseDto<Integer> save(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail principal) {
		boardService.writePost(board, principal.getUser()); // 누가 글썼는지 알기 위해 user 객체도 부름
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); // 1 리턴 : 정상 의미
	}
	
	@DeleteMapping("/api/board/{id}")
	public ResponseDto<Integer> deleteById(@PathVariable int id) {
		boardService.deletePost(id);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); 
	}
	
	@PutMapping("/api/board/{id}")
	public ResponseDto<Integer> updateById(@RequestBody Board board, @PathVariable int id) {
		boardService.updatePost(board, id);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); 
	}
	
	/* 데이터를 받을 때 컨트롤러에서 dto를 만들어서 받는게 좋다
	 * dto 사용하지 않는 이유는 그냥 간단한 프로젝트기 때문
	 */
	@PostMapping("/api/board/{boardId}/reply")
	public ResponseDto<Integer> replySave(@RequestBody ReplySaveRequestDto replySaveRequestDto) {
		boardService.writeReply(replySaveRequestDto);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); // 1 리턴 : 정상 의미
	}
	
	@DeleteMapping("/api/board/{boardId}/reply/{replyId}")
	public ResponseDto<Integer> replyDelete(@PathVariable int replyId) {
		boardService.deleteReply(replyId);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); // 1 리턴 : 정상 의미
	}
}
