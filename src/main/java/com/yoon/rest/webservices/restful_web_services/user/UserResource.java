package com.yoon.rest.webservices.restful_web_services.user;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserResource {

	UserDaoService userDaoService;

	public UserResource(UserDaoService userDaoService) {
		super();
		this.userDaoService = userDaoService;
	}
	
	//모든 User를 반환하는 컨트롤러 메서드
	@GetMapping("/users")
	public List<User> retriveAllUsers(){
		return userDaoService.findAll();
	}

	//@PathVariable로 찾을 path 변수이름은 id
	@GetMapping("/users/{id}")
	public User retriveOneUsers(@PathVariable int id){
		return userDaoService.findOne(id);
	}
	
	//새로운 User객체를 생성요청하는 컨트롤러 메서드
	@PostMapping("/users")
	public void createUser(@RequestBody User user) {
		userDaoService.saveUser(user);
	}
	
	
}
