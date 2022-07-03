package com.cos.security1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.security1.model.User;
                                                   //dto      pk
//CRUD함수를 JpaRepository가 들고 있음
//@Repository 필요없음 이유는 JpaRepository가 들고있기 때문
public interface UserRepository extends JpaRepository<User, Integer> {

}
