package com.study.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.study.blog.config.auth.PrincipalDetailService;

// 빈 등록 : 스프링 컨테이너에서 객체를 관리할 수 있게 등록하는 것
@Configuration // 빈으로 등록(IOC 관리)
@EnableWebSecurity// 시큐리티 필터를 등록함. 즉, 컨트롤러로 가기 전에, 이 클래스가 동작하여 요청 URL에 따라 요청 허용 여부를 결정함
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 주소로 접근시 권한 및 인증을 미리 체크
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private PrincipalDetailService principalDetailService;
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean // IOC 컨테이너가 관리
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder();
	}
	
	// 스프링 시큐리티가 password를 가로채기하여 해쉬로 암호화하여 DB에 저장
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http 
			.csrf().disable()// csrf 토큰 비활성화(테스트시 disable로 설정하는게 좋음)
			.authorizeRequests()
				.antMatchers("/", "/auth/**", "/js/**", "/css/**", "/image/**", "/dummy/**") // auth가 포함된 url은 누구나 들어올 수 있다.
				.permitAll()
				.anyRequest() // 그 외의 요청은 인증이 된 유저에게만 허락한다.
				.authenticated()
			.and()
				.formLogin() 
				.loginPage("/auth/loginForm") // 인증이 요구되는 경우 이 페이지로 이동함
				.loginProcessingUrl("/auth/loginProc") // 스프링 시큐리티가 해당 주소로 요청오는 로그인을 가로채서 대신 로그인 수행
				.defaultSuccessUrl("/");  // 로그인 성공시 이 URL로 이동
				//.failureUrl("/fail"); // 실패시 여기로 이동
	}
}
