package com.yoon.rest.webservices.restful_web_services.user;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.net.URI;
import java.util.List;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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

	//http://localhost:8080/users
	
	
	// @PathVariable로 찾을 path 변수이름은 id
	// 특정 id의 유저를 찾는 메서드
	@GetMapping("/users/{id}")
	public EntityModel<User> retriveOneUsers(@PathVariable int id) {
		User user = userDaoService.findOneUser(id);
		//userDaoService에서 존재하지않는 id의 User를 조회할경우 
		if (user == null) {
			// 존재하지않는 id 메세지를 포함한 커스텀 Exception class를 던진다
			throw new UserNotFoundException("id:"+id);
		}
		
		//특정 id의 유저를 찾을때 모든 유저를 찾는 retriveAllUsers()메서드를 실행하는 링크정보를 제공하기 
		
		//EntityModel - 도메인 객체를 래핑하여 링크를 추가하는 객체
		//User 도메인객체를 EntityModel<User>타입으로 래핑
		EntityModel<User> entityModel = EntityModel.of(user);
		
		//WebMvcLinkBuilder - Spring MVC 컨트롤러를 가리키는 Link 인스턴스를 빌드할수있는 객체
		//linkTo - 컨트롤러 메서드를 가리키는 WebMvcLinkBuilder를 생성하는 메서드
		//methodOn - 링크를 생성할 클래스,메서드를 지정하는 메서드 여기서는 UserResource객체의 retriveAllUsers()메서드를 지정
		WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retriveAllUsers());
		//래핑한 EntityModel에 "all-users" 라는 이름으로 제공되는 링크 인스턴스를  추가한다
		entityModel.add(link.withRel("all-users"));
		
		//EntityModel 형태로 반환
		return entityModel;
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
