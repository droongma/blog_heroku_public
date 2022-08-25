package com.study.blog.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder // 빌더 패턴
@Entity
public class Board { 
	/*
	 *  Board(게시글)에는 User 정보와 Reply 정보가 포함되어 있다!
	 *  하나의 게시글에는 한명의 user(작성자)가 대응
	 *  하나의 게시글에는 여러 개의 Reply가 대응
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
	private int id;
	
	@Column(nullable = false, length = 100)
	private String title;
	
	@Lob // 대용량 데이터에 lob 사용
	private String content; // 섬머노트 라이브러리 <html> 태그가 섞여서 디자인이 됨. => 용량 큼!

	private int count; // 조회수
	
	@CreationTimestamp // 현재시간 자동으로 입력
	private Timestamp createDate;
	
	/*
	 * DB는 오브젝트 저장 불가! 그래서 외래키 사용. 그러나 자바는 오브젝트 저장이 가능
	 * ManyToOne는 기본적으로 fetch = FetchType.EAGER 사용
	 * 즉, Board를 select시 user의 데이터 또한 join으로 가져와서 함께 select함
	 */
	@ManyToOne // Board가 many, User가 one(한명의 유저가 여러 게시글 쓰기 가능)
	@JoinColumn(name="userId")
	private User user; 
	
	/*
	 *  mappedBy 의미 : 연관관계의 주인 테이블의 필드 이름
	 *  나(board)는 연관관계의 주인이 아니다(난 FK가 아니다). 따라서 이 DB에서 FK 컬럼을 만들지 않는다
	 *  즉, 그냥 select로 데이터를 가져오기 위해 join할 때만 쓰는 용도. 실제 FK는 Reply 테이블의 board이다!
	 *  
	 *  OneToMany는 기본적으로 fetch = FetchType.LAZY(reply 정보는 나중에 join해서 조회함) 사용
	 *  
	 *  board를 통해 replies에 접근할 경우, replies에서 다시 board와 user를 참조하지 않는다!
	 *  다만 replies에 직접 접근하는 경우에는 board나 user를 참조한다.
	 *  
	 *  cascade.remove : 게시글을 지울 때 거기에 달린 댓글도 같이 다 지움 
	 */
	@JsonIgnoreProperties({"board", "user"}) // replies 안에서 다시 board를 호출하지 않게하여 무한참조를 방지!
	@OneToMany(mappedBy="board", fetch = FetchType.EAGER, cascade=CascadeType.REMOVE)  // 연관관계의 주인이 아니므로 FK 아님!(따라서 실제 DB에 reply 컬럼이 없다)
	@OrderBy("id desc")
	private List<Reply> replies;
}
