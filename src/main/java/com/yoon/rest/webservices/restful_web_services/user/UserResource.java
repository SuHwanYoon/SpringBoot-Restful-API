package com.yoon.rest.webservices.restful_web_services.user;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;

@RestController
public class UserResource {

	UserDaoService userDaoService;

	public UserResource(UserDaoService userDaoService) {
		super();
		this.userDaoService = userDaoService;
	}

	// 모든 User를 반환하는 컨트롤러 메서드
	@GetMapping("/users")
	public List<User> retriveAllUsers() {
		return userDaoService.findAll();
	}

	// @PathVariable로 찾을 path 변수이름은 id
	// 특정 id의 유저를 찾는 메서드
	@GetMapping("/users/{id}")
	public User retriveOneUsers(@PathVariable int id) {
		User user = userDaoService.findOneUser(id);
		//userDaoService에서 존재하지않는 id의 User를 조회할경우 
		if (user == null) {
			// 존재하지않는 id 메세지를 포함한 커스텀 Exception class를 던진다
			throw new UserNotFoundException("id:"+id);
		}
		
		return user;
	}

	// @PathVariable로 찾을 path 변수이름은 id
	// 특정 id의 유저를 삭제하는 메서드
	@DeleteMapping("/users/{id}")
	public void deleteOneUser(@PathVariable int id) {
		userDaoService.deleteById(id);
	}

	
	// 새로운 User객체를 생성요청하고 유효성을 검사하는 컨트롤러 메서드
	@PostMapping("/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		User savedUser = userDaoService.saveUser(user);
		// location Hader변수 1.현재 요청 Url 가져오기
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				// 2. 현재요청 Url에 추가하고싶은 쿼리스트링지정
				.path("/{id}")
				// 3. 쿼리스트링에 넣을 id는 User객체 생성시 생성되는 id 값을 가져와서 넣는다 ex) 4,5,6.......
				.buildAndExpand(savedUser.getId())
				// 4. URI 객체로 전환
				.toUri();
		// created에 해당하는 URL 201과 새로생성된 리소스의 location Header 반환하기
		return ResponseEntity.created(location).build();
	}

}
