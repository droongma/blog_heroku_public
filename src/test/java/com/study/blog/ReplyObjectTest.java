package com.study.blog;

import org.junit.jupiter.api.Test;

import com.study.blog.model.Reply;

/* 
 * Reply 객체의 toString 메소드 작동 테스트!
 */
public class ReplyObjectTest {
	
	@Test
	public void testToString() {
		Reply reply = Reply.builder().id(1).user(null).board(null).content("hello world").build();
		System.out.println(reply);
	}
}
