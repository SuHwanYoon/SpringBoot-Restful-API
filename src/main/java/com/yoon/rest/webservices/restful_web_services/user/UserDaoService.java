package com.yoon.rest.webservices.restful_web_services.user;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Component;

@Component
public class UserDaoService {

	private static List<User> users = new ArrayList<>();
	
	private static int usersCount = 0;

	static {
		users.add(new User(++usersCount, "Yoon", LocalDate.now().minusYears(32)));
		users.add(new User(++usersCount, "Kim", LocalDate.now().minusYears(20)));
		users.add(new User(++usersCount, "Park", LocalDate.now().minusYears(10)));
	}
	
	//모든 유저를 찾는 서비스 메서드
	public List<User> findAll(){
		return users;
	}
	
	//새로운 유저를 생성하는 서비스메서드
	public User saveUser(User user) {
		user.setId(++usersCount);
		users.add(user);
		return user;
	}
	
	
	//int id 로 하나의 유저를 찾는 메서드
	public User findOne(int id) {
		//1. users List의 필터기준이 되는 filteringOneUser 변수는 User 객체에서 id 취득후 입력된 @PathVariable id 와 비교
		Predicate<? super User> filteringOneUser = user -> user.getId().equals(id) ;
		//2. 필터링된 filteringOneUser 변수에서 findFirst로 Optional type의 User를 찾고 get으로 가져온다
		return users.stream().filter(filteringOneUser).findFirst().get();
	}
	
}
