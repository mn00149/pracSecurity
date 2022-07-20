package com.cos.security1.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cos.security1.config.Oauth.PrincipalOauth2UserService;
//구글로그인 후 뒤의 후처리가 필요 1. 코드받기(인증)  2. 엑세스토큰(권한) 받음
//3.사용자 프로필 정보를 가져옴 4. 그정보를 토대로 회원가입을 자동으로 시키기도 함
//4-2. 추가정보 필요시 더 받아올수도...
@Configuration
@EnableWebSecurity//스프링 시큐리티 필터가 스프링 필터체인에 등록
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)//secured어노테이션 활성화, preAuthorize,postAuthorize 어노테이션 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
  @Autowired
  private PrincipalOauth2UserService principalOauth2UserService;
  
	//빈등록-> 해당 메서드의 리턴되는 오브젝트를 ioc에 들록 해준다
	@Bean
	public BCryptPasswordEncoder encodePwd() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();//csrf 비활성화
		http.authorizeRequests()
			.antMatchers("/user/**").authenticated()//"/user"로 시작하는 주소는 인증 필요
			.antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")//"/manager"로 시작하는 주소는'ROLE_ADMIN 혹은 'ROLE_MANAGER'dml fhfdl vlfdy 
			.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
			.anyRequest().permitAll()//위의 설정 이외엔 다허용, 권한이 없는 페이지에 접근시 403이 뜬다
			.and()//밑의 설정을 통해 권한없는 페이지 접근시 로그인 페이지로 이동
			.formLogin()
			.loginPage("/loginForm")
			.loginProcessingUrl("/login")//login주소가 호출이 되면 시큐리티가 낚아채서 로그인을 진행 => 즉 따로 로그인처리하는 controller를 만들 필요 없다
			.defaultSuccessUrl("/")
			.and()
			.oauth2Login()
			.loginPage("/loginForm") 
			.userInfoEndpoint()
			.userService(principalOauth2UserService);//구글로그인 후 뒤의 후처리가 필요 tip(코드X, 엑세스토큰+사용자 정보 받음)
	}

	
}
