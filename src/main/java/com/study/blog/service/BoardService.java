package com.study.blog.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.study.blog.dto.ReplySaveRequestDto;
import com.study.blog.model.Board;
import com.study.blog.model.Reply;
import com.study.blog.model.RoleType;
import com.study.blog.model.User;
import com.study.blog.repository.BoardRepository;
import com.study.blog.repository.ReplyRepository;
import com.study.blog.repository.UserRepository;

@Service // 스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해줌(IOC를 해줌)
public class BoardService {
	@Autowired // DI(Dependent Injection) 수행
	private BoardRepository boardRepository;
	
	@Autowired
	private ReplyRepository replyRepository;
	
	@Transactional(readOnly = true)
	public Board seeDetails(int id) { // 해당 글의 상세정보 조회
		return boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글 상세보기 실패 : id를 찾을 수 없습니다!");
				});
	}
	
	@Transactional // 트랜잭션으로 설정.(전체가 성공해야 commit이 될 수 있다)
	public void writePost(Board board, User user) { // title과 content를 입력받음
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board);
	}
	
	@Transactional // 트랜잭션으로 설정.(전체가 성공해야 commit이 될 수 있다)
	public void deletePost(int id) { // title과 content를 입력받음
		System.out.println("글 삭제하기 : " + id);
		boardRepository.deleteById(id);
	}
	
	@Transactional // 트랜잭션으로 설정.(전체가 성공해야 commit이 될 수 있다)
	public void updatePost(Board requestBoard, int id) { // title과 content를 입력받음
		System.out.println("글 수정하기 : " + id);
		Board board = boardRepository.findById(id) // 영속화 시키기
				.orElseThrow(()->{
					return new IllegalArgumentException("글 찾기 실패 : 해당 id의 글은 찾을 수 없습니다!");
				});
		board.setTitle(requestBoard.getTitle());
		board.setContent(requestBoard.getContent());
		// 해당 함수 종료시(Service가 종료될 때) 트랜잭션이 종료된다. 이것을 더 티체킹(자동으로 update 정보를 flush)이라고 한다
	}
	
	@Transactional(readOnly = true)
	public Page<Board> getPosts(Pageable pageable){ // 글목록 구하기
		return boardRepository.findAll(pageable);
	}
	
	@Transactional
	public void deleteReply(int replyId) {
		replyRepository.deleteById(replyId);
	}
	
	@Transactional
	public void writeReply(ReplySaveRequestDto replySaveRequestDto) {
		int boardId = replySaveRequestDto.getBoardId();
		int userId = replySaveRequestDto.getUserId();
		String content = replySaveRequestDto.getContent();
		
//		Board board = boardRepository.findById(boardId).orElseThrow(()->{
//			return new IllegalArgumentException("댓글 쓰기 실패 : 게시글 ID를 찾을 수 없습니다!");
//		}); // 영속화 완료
//		User user = userRepository.findById(userId).orElseThrow(()->{
//			return new IllegalArgumentException("댓글 쓰기 실패 : 유저 ID를 찾을 수 없습니다!");
//		}); // 영속화 완료
//		Reply reply = Reply.builder().user(user).board(board)
//				.content(content).build(); // builder 패턴으로 객체 만들기
//		replyRepository.save(reply);
		
		int reply = replyRepository.mSave(replySaveRequestDto);
	}
}
