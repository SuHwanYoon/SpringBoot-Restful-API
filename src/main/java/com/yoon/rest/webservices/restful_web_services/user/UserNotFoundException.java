package com.yoon.rest.webservices.restful_web_services.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//존재하지않는 User id를 조회했을때 발생하는 커스텀 Exception
//브라우저에서 404 신호를 보내기 위한 어노테이션
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

	//전달받는 message문자열을 RuntimeException에 전달
	public UserNotFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	
	
}
