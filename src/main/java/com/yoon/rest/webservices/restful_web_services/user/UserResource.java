package com.yoon.rest.webservices.restful_web_services.user;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserResource {

	UserDaoService userDaoService;

	public UserResource(UserDaoService userDaoService) {
		super();
		this.userDaoService = userDaoService;
	}
	
	@GetMapping("/users")
	public List<User> retriveAllUsers(){
		return userDaoService.findAll();
	}
	
	
}
