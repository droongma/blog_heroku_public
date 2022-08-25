package com.study.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.study.blog.dto.ReplySaveRequestDto;
import com.study.blog.model.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {

	// 업데이트된 행의 개수를 리턴해줌(-1 : 오류 의미)
	@Modifying
	@Query(value="insert into reply(userId, boardId, content, createDate) values(:#{#paramDto.userId}, :#{#paramDto.boardId}, :#{#paramDto.content}, now())", nativeQuery = true)
	int mSave(@Param("paramDto") ReplySaveRequestDto replySaveRequestDto); 
}
