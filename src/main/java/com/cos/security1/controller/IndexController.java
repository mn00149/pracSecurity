package com.cos.security1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.security1.config.auth.PrincipalDetails;
import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;

@Controller
public class IndexController {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @GetMapping("/test/oauth/login")
  public @ResponseBody String testOauthLogin(Authentication authentication, @AuthenticationPrincipal OAuth2User oauth) {// DI
    System.out.println("테스트Oauth로그인===================================");
    OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
    System.out.println("authentication" + oauth2User.getAttributes());
    System.out.println("oauth2User" + oauth.getAttributes());

    return "Oauth세션";
  }

  @GetMapping("/test/login")
  public @ResponseBody String testLogin(Authentication authentication,
      @AuthenticationPrincipal PrincipalDetails userDetails) {// DI
    System.out.println("테스트로그인===================================");
    PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
    System.out.println("authentication" + principalDetails.getUser());

    System.out.println("userDetails:" + userDetails.getUser());
    return "세션";
  }

  @GetMapping("/")
  public String index() {
    // 머스테치 기본 폴더 src/main/resources/
    // 뷰리졸버설정 생략가능
    return "index";
  }
  //Oauth 로그인을 해도 일반로그인을 해도 => PrincipalDetails
  //
  @GetMapping("/user")
  public String user(@AuthenticationPrincipal PrincipalDetails principalDetails) {
    System.out.println("principalDetails:" + principalDetails.getUser());

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

  // 스프링 시큐리티가 낚아챔-SecurityConfig작성 후 낚아체는 현상 없어짐!!
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
    userRepository.save(user);// 회원 가입잘됨 but 시큐리티 못씀 => 비번이 암호화 되지 안았기 때문
    return "redirect:/loginForm";
  }

  @GetMapping("/joinForm")
  public String joinForm() {

    return "joinForm";
  }

  @Secured("ROLE_ADMIN")
  @GetMapping("/info")
  public @ResponseBody String info() {
    return "개인정보";
  }

  @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
  @GetMapping("/data")
  public @ResponseBody String data() {
    return "data";
  }

}
