package com.study.blog.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.study.blog.model.RoleType;
import com.study.blog.model.User;
import com.study.blog.repository.UserRepository;

@Service // 스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해줌(IOC를 해줌)
public class UserService {
	@Autowired // DI(Dependent Injection) 수행
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Transactional // 트랜잭션으로 설정.(전체가 성공해야 commit이 될 수 있다)
	public void signUp(User user) {
		String rawPassword = user.getPassword(); // 패스워드 원문
		String encPassword = encoder.encode(rawPassword); // 해쉬로 암호화
		user.setPassword(encPassword);
		user.setRole(RoleType.USER);
		userRepository.save(user);
	}
	
	@Transactional(readOnly = true)
	public User findUser(String username) {
		User user = userRepository.findByUsername(username).orElseGet(()->{
			return new User();
		});
		return user;
	}
	
	@Transactional // 트랜잭션으로 설정.(전체가 성공해야 commit이 될 수 있다)
	public void updateUser(User user) {
		/* 수정시에는 영속성 컨텍스트에다 User 객체를 영속화킨 후에, 이 영속화된 객체를 수정한다.
		 * select를 해서 User 객체를 DB로부터 가져오는 이유가 바로 영속화를 하기 위해서다.
		 * 영속화된 객체를 변경하면 트랜잭션 종료시 자동으로 DB에 update가 된다
		 */
		User persistence = userRepository.findById(user.getId())
					.orElseThrow(()->{
						return new IllegalArgumentException("회원찾기 실패");
					});
		
		// Validation 체크 : oauth에 값이 없으면 수정 가능
		if (persistence.getOauth() == null || persistence.getOauth().equals("")) {
			String rawPassword = user.getPassword();
			String encPassword = encoder.encode(rawPassword);
			persistence.setPassword(encPassword);
			persistence.setEmail(user.getEmail());
		}
		
		

		// updateUser 함수가 종료될 때(서비스 종료될 때, 트랜잭션 종료될 때) commit이 자동으로 된다
	}
	
	// 전통적인 방식의 로그인 수행
	// select시 트랜잭션 시작, 서비스 종료시에 트랜잭션도 종료(정합성)
	/*	@Transactional(readOnly = true) 
		public User login(User user) {
			return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
		}
	*/
	
}
