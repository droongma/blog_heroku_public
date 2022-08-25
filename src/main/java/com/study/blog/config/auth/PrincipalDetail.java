package com.study.blog.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.study.blog.model.User;

import lombok.Getter;

/*
 * 스프링 시큐리티가 로그인 요청을 가로채서 로그인을 진행함. 
 * 완료가 되면 UserDetails 타입의 오브젝트를 스프링 시큐리티의 세션 저장소에 저장함.
 */
@Getter
public class PrincipalDetail implements UserDetails{
	private User user; // composition(객체를 품고 있음)

	public PrincipalDetail(User user) {
		this.user = user;
	}
	
	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	// 계정이 만료되지 않았는지 여부를 리턴(true : not expired)
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	// 계정이 잠기지 않았는지 여부를 리턴(true : not locked)
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	// 비밀번호가 만료되지 않았는지 여부를 리턴(true : not expired)
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	// 계정이 활성화(사용가능)되었는지 여부 리턴(true : 활성화)
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	// 계정의 권한을 리턴(권한이 여러개면 for문 돌려야 하지만 우리는 한개의 권한만 사용)
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collectors = new ArrayList<>();
//		collectors.add(new GrantedAuthority() {
//			@Override
//			public String getAuthority() {
//				return "ROLE_" + user.getRole();
//			}
//		});
		collectors.add(() -> {return "ROLE_" + user.getRole();} );
		
		return collectors;
	}
}
