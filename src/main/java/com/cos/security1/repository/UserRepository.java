package com.cos.security1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.security1.model.User;
                                                   //dto      pk
//CRUD함수를 JpaRepository가 들고 있음
//@Repository 필요없음 이유는 JpaRepository가 들고있기 때문
public interface UserRepository extends JpaRepository<User, Integer> {

    //findBy 규칙 -> Username 문법
  // select * from user shere usename = 1?
  public User findByUsername(String username);//Jpa 쿼리 메서드
}
