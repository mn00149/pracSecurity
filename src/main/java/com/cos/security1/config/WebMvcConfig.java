package com.cos.security1.config;

import org.springframework.boot.web.servlet.view.MustacheViewResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{  

  @Override
  public void configureViewResolvers(ViewResolverRegistry registry) {
      MustacheViewResolver resolver = new MustacheViewResolver();
      //html 파일 인식을 위한 설정
      resolver.setCharset("UTF-8");//기본인코딩 설정
      resolver.setContentType("text/html;charset=UTF-8");//html 던져줄것을 선언
      resolver.setPrefix("classpath:/templates/");//파일의 ㅣ위치
      resolver.setSuffix(".html");//html인식을 위해 Suffix을 바꿔줌 

      registry.viewResolver(resolver);//머스테치가 인식하기 위해 뷰리졸버에 등록
  }
}