package com.cos.security1.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.security1.model.User;

//시큐리티가 /login 주소 요청이오면 낚아체서 로그인을 진행
//로그인 진행이 완료되면 시큐리티 session을 만듬(Security ContextHolder)
//오브젝트 타입 => Authentication 타입 객체
//User 오브젝트 타입 => UserDetails 타입객체

//Security Session => Authentication => UserDetails(PrincipalDetails) 
public class PrincipalDetails implements UserDetails{
  
  private User user;//콤포지션
  
  public PrincipalDetails(User user) {
    this.user = user;
  }
  //해당유저의 권한을 리턴
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    Collection<GrantedAuthority> collect = new ArrayList<>();
    collect.add(new GrantedAuthority() {
      
      @Override
      public String getAuthority() {
        // TODO Auto-generated method stub
        return user.getRole();
      }
    });
    return collect;
  }

  @Override
  public String getPassword() {
    // TODO Auto-generated method stub
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    // TODO Auto-generated method stub
    return user.getUsername();
  }

  @Override
  public boolean isAccountNonExpired() {
    // TODO Auto-generated method stub
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    // TODO Auto-generated method stub
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    // TODO Auto-generated method stub
    return true;
  }

  @Override
  public boolean isEnabled() {
    // 우리사이트에서 회원이 1년동안 로그인이 안되었다면 휴면계정으로 쓸때
    //현제시간 - 로그인시간 > 1년 => false반환 
    return true;
  }
 
}
