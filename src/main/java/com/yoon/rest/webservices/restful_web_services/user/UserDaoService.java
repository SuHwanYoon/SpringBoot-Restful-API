package com.yoon.rest.webservices.restful_web_services.user;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class UserDaoService {

	private static List<User> users = new ArrayList<>();

	static {
		users.add(new User(1, "Yoon", LocalDate.now().minusYears(32)));
		users.add(new User(2, "Kim", LocalDate.now().minusYears(20)));
		users.add(new User(3, "Park", LocalDate.now().minusYears(10)));
	}
	
	public List<User> findAll(){
		return users;
	}
	
}
