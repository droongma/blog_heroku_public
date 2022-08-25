package com.study.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import com.study.blog.dto.ReplySaveRequestDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Reply { // 답변 테이블
	@Id // Primary Key
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 프로젝트에서 연결된 DB의 넘버링 전략을 따라간다.
	private int id; // 시퀀스(oracle), auto_increment(MySQL)
	
	@Column(nullable = false, length = 200)
	private String content;
	
	@ManyToOne // 여러개의 답변이 하나의 게시글(board)에 존재할 수 있다.
	@JoinColumn(name="boardId")
	private Board board; 

	@ManyToOne
	@JoinColumn(name="userId")
	private User user;
	
	@CreationTimestamp
	private Timestamp createDate;

	@Override
	public String toString() {
		return "Reply [id=" + id + ", content=" + content + ", board=" + board + ", user=" + user + ", createDate="
				+ createDate + "]";
	}
	
}
