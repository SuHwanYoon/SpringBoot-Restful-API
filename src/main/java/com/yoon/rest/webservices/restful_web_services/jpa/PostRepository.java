package com.yoon.rest.webservices.restful_web_services.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yoon.rest.webservices.restful_web_services.user.Post;
																	//id(식별값)이 Integer type인 Post Entity를 Jpa적용대상으로 지정
public interface PostRepository extends JpaRepository<Post, Integer> {

}
