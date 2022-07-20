package com.cos.security1.config.Oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.cos.security1.config.auth.PrincipalDetails;
import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;
  
  @Autowired
  private UserRepository userRepository;
  //구글로부터 받은 userRequest 데이터에 대한 후처리되는 함수
  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    OAuth2User oauth2User = super.loadUser(userRequest);
    //구글로그인 버튼 클릭 -> 구글로그인 창 -> code를 리턴(Oauth-client라이브러리) -> AccessToken요청
    //userRequest정보->loadUser함수 호출->구글로부터 회원프로필을 받음
    System.out.println("userRequest:"+userRequest);
    System.out.println("getAttribute:"+super.loadUser(userRequest).getAttributes());
    //회원가입을 강제로 진행할 예정
    String provider = userRequest.getClientRegistration().getRegistrationId();//google RegistrationId로 어떤 oauth로 로그인했는지 확인 가능
    String providerId = oauth2User.getAttribute("sub");
    String username = provider+"_"+providerId;
    String password = bCryptPasswordEncoder.encode("오인웅짱");
    String email = oauth2User.getAttribute("email");
    String role = "ROLE_USER";
    
    User userEntity = userRepository.findByUsername(username);
    
    if(userEntity == null) {
      System.out.println("구글로그인이 최초입니다");
      userEntity = User.builder()
          .username(username)
          .password(password)
          .email(email)
          .role(role)
          .provider(provider)
          .providerId(providerId)
          .build();
      userRepository.save(userEntity);
    }else {
      System.out.println("구글로그인이 이미 되어있습니다");
    }
    
  
    return new PrincipalDetails(userEntity, oauth2User.getAttributes());
  }

  
}
