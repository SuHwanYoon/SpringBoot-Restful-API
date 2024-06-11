package com.yoon.rest.webservices.restful_web_services.exception;

import java.time.LocalDateTime;

//예외 발생시 반환할 커스텀 구조
public class ErrorDetails {

	//요청 날짜 및 시간
	private LocalDateTime timeStamp;
	//요청 User id
	private String message;
	//요청 URL
	private String details;
	
	public ErrorDetails(LocalDateTime timeStamp, String message, String details) {
		super();
		this.timeStamp = timeStamp;
		this.message = message;
		this.details = details;
	}

	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}

	public String getMessage() {
		return message;
	}

	public String getDetails() {
		return details;
	}
	
	
	
	
	
}
