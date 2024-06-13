package com.yoon.rest.webservices.restful_web_services.exception;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.method.MethodValidationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.yoon.rest.webservices.restful_web_services.user.UserNotFoundException;

@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler{

	//404를 제외한 모든 예외를 500으로 반환하는 메서드
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ErrorDetails> handleAllException(Exception ex, WebRequest request) throws Exception {
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage() , request.getDescription(false));
		//요청시간, 요청id메세지,요청URL, 500에러 반환
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	
	//id가 존재하지않는 유저를 조회했을때 404를  반환하는 메서드
	@ExceptionHandler(UserNotFoundException.class)
	public final ResponseEntity<ErrorDetails> handleUserNotFoundException(Exception ex, WebRequest request) throws Exception {
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage() , request.getDescription(false));
		
		//요청시간, 요청id메세지,요청URL, 404에러 반환
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.NOT_FOUND);
	}


	//유효성 검사에 걸렸을때 400에러를 반환하는 예외 메서드
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		
//        List<String> errorMessages = ex.getBindingResult().getFieldErrors()
//                .stream()
//                .map(FieldError::getDefaultMessage)
//                .collect(Collectors.toList());

        List<String> errorMessages = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> "Error " + (ex.getBindingResult().getFieldErrors().indexOf(fieldError) + 1) + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.toList());
		
        String combinedErrorMessage = "Total Error:" + ex.getErrorCount() +" [" + String.join(", ", errorMessages) + "]";
        
        
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), 
													combinedErrorMessage , 
													request.getDescription(false));
		
		//요청시간, 요청id메세지,요청URL, 400에러 반환
		return new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);
	}



	
}
