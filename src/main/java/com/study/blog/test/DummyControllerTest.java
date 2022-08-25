package com.study.blog.test;

import java.util.List;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.study.blog.model.RoleType;
import com.study.blog.model.User;
import com.study.blog.repository.UserRepository;

@RestController // html 파일이 아닌 데이터 리턴
public class DummyControllerTest {
	/*
	 * DummyControllerTest : HTTP 요청을 토대로 User의 select, update, insert, delete 테스트  
	 */
	
	@Autowired // DummyControllerTest가 메모리에 뜰때 이것도 같이 뜨게 만든다(의존성 주입, DI)
	private UserRepository userRepository;
	
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
		try {
			userRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			return "삭제에 실패하였습니다. 해당 ID는 DB에 없습니다!";
		}
		
		return "삭제되었습니다. id : " + id;
	}
	
	/*
	 *  save 함수는 id를 전달하지 않으면 insert를 수행한다.
	 *  만약 save 함수가 id를 전달할 경우,
	 *  	해당 id에 대한 데이터가 테이블에 있으면 insert 대신 update를 수행한다(없으면 insert 수행)
	 */
	@Transactional // DB의 트랜잭션 시작. 함수 종료시 트랜잭션도 종료된다(즉, commit된다).
	@PutMapping("/dummy/user/{id}") // 데이터(email, password) update 요청
	public User updateUser(@PathVariable int id, @RequestBody User requestUser) { // JSON 데이터를 받아 자바 객체로 변환
		System.out.println("id : " + id);
//		System.out.println("id : " + requestUser.getId());
		System.out.println("password : " + requestUser.getPassword());
		System.out.println("email : " + requestUser.getEmail());
		
		/* Update 1번째 방법 :
		 * 		DB에서 유저를 직접 들고와서 수정한 후 다시 DB에 반영함.
		 * 		코드는 아래와 같다:
		  
				User user = userRepository.findById(id).orElseThrow(()->{
					return new IllegalArgumentException("수정에 실패하였습니다.");
				});
				// 해당 id 값을 가지는 유저의 패스워드, 이메일 변경 
				user.setPassword(requestUser.getPassword());
				user.setEmail(requestUser.getEmail());
				userRepository.save(user); 
		 */
		
		/* Update 2번째 방법 : 더티체킹
		 * 		1번째 방법과 달리 save를 따로 하지 않았지만 update가 된다.
		 * 		-> Transactional이라는 annotation을 걸면 save 안해도 함수 종료시에 DB에 update(commit)가 된다. 
		 * 		-> 즉, 영속화된 객체를 update하고 나서, 트랜잭션 종료시 실제로 DB에 반영이 된다.(더티체킹)
		 */
		User user = userRepository.findById(id).orElseThrow(()->{ // 객체를 가져와서 영속화함
			return new IllegalArgumentException("수정에 실패하였습니다.");
		});
		// 해당 id 값을 가지는 유저의 패스워드, 이메일 변경 
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
		
		return user;
	}
	
	// 주소 : http://localhost:8000/blog/dummy/users
	@GetMapping("/dummy/users")
	public List<User> list(){
		return userRepository.findAll();
	}
	
	// 한페이지당 2건의 데이터를 리턴받음, id 내림차순 순으로
	// 주소 : http://localhost:8000/blog/dummy/user
	@GetMapping("/dummy/user") // 중요 : URL 뒤에 ?page=i을 붙이면 (i+1)번째 페이지 보여줌(안붙이면 page=0일 떄의 결과 출력)
	public List<User> pageList(@PageableDefault(size=2, sort="id", direction=Sort.Direction.DESC) Pageable pageable){
		Page<User> pagingUsers = userRepository.findAll(pageable); // content 정보만 보여줌
		List<User> users = pagingUsers.getContent();
		return users;
	}
	
	// select 연산 수행
	/*
	 *  {id} : 주소로 id 파라미터를 전달받음, http://localhost:8000/blog/dummy/user/3
	 *  여기서 findById 함수는 Optional 타입으로 리턴함(Optional로 user 객체를 감싸서 가져옴)
	 *  Optional 객체의 orElseGet, orElseThrow 함수는 null일 때만 호출된다.
	 *  Supplier는 인터페이스. 
	 */
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
			@Override
			public IllegalArgumentException get() {
				// TODO Auto-generated method stub
				return new IllegalArgumentException("해당 유저는 없습니다. id : " + id);
			}
		});
		
		/* user라는 자바 객체를 리턴한다. 그러나 웹브라우저는 자바 객체를 이해 못한다. 그러면 어떻게?
		 * -> 스프링부트는 MessageConverter라는 애가 응답시에 자동으로 작동!
		 * 그래서 만약 자바 객체를 리턴할 경우 MessageConverter가 Jackson 라이브러리를 호출해서
		 * user 객체를 json으로 변환해 브라우저에게 던져준다.
		 */
		return user; 
		/* 
		 * 람다식 활용시 위의 코드를 아래처럼 더 간결하게 작성 가능!
		 */
//		User user2 = userRepository.findById(id).orElseThrow(
//				()->{
//				return new IllegalArgumentException("해당 유저는 없습니다. id : " + id);
//			});
//		return user2;
	}
	
	/* 
	 * 원래 @RequestParam을 붙여야 하지만, 생략할 수 있다. 이때는 파라미터 이름을 정확히 적으면 된다.
	 * 요청 URL : http://localhost:8000/blog/dummy/join
	 * 
	 * http의 body에 username, password, email 데이터를 가지고 
	 * x-www-form-urlencoded 방식으로 요청(key=value 형태의 데이터로 요청)하면, 
	 * join 함수의 파라미터에 데이터들이 쏙 들어감
	 */
	@PostMapping("dummy/join")
	public String join(String username, String password, String email) {
		System.out.println("username : " + username);
		System.out.println("password : " + password);
		System.out.println("email : " + email);
		return "회원가입이 완료되었습니다!";
	}
	
	/*
	 * 위의 방식과 달리 object로 받아서 처리!(앞에 ModelAttribute를 붙인 것과 같다)
	 */
	@PostMapping("dummy/join2") // 데이터 삽입시 post 사용
	public String join2(User user) { // 파라미터로 값을 못받은 필드는 default 값이 표시된다
		System.out.println("id : " + user.getId()); 
		System.out.println("username : " + user.getUsername());
		System.out.println("password : " + user.getPassword());
		System.out.println("email : " + user.getEmail());
		System.out.println("role : " + user.getRole());
		System.out.println("createDate : " + user.getCreateDate());
		
		user.setRole(RoleType.USER); // 기본적으로 user로 회원가입 진행 
		userRepository.save(user); // save 함수로 insert 수행
		return "회원가입이 완료되었습니다!";
	}
}
