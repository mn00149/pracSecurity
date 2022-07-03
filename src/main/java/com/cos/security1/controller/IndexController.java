package com.cos.security1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;

@Controller
public class IndexController {

	@Autowired
	private UserRepository userRepository; 
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@GetMapping("/")
	public String index() {
		//머스테치 기본 폴더 src/main/resources/
		//뷰리졸버설정 생략가능
		return "index";
	}
	
	@GetMapping("/user")
	public String user() {
	
		return "user";
	}
	
	@GetMapping("/manager")
	public String manager() {
	
		return "manager";
	}
	
	@GetMapping("/admin")
	public String admin() {
	
		return "admin";
	}
	
	//스프링 시큐리티가 낚아챔-SecurityConfig작성 후 낚아체는 현상 없어짐!!
	@GetMapping("/loginForm")
	public String loginForm() {
	
		return "loginForm";
	}
	
	@PostMapping("/join")
	public String join(User user) {
		System.out.println(user);
		user.setRole("ROLE_USER");
		String rawPassword = user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);
		user.setPassword(encPassword);
		userRepository.save(user);//회원 가입잘됨 but 시큐리티 못씀 => 비번이 암호화 되지 안았기 때문
		return "redirect:/loginForm";
	}
	

	@GetMapping("/joinForm")
	public String joinForm() {
		
		return "joinForm";
	}
	
	
}
