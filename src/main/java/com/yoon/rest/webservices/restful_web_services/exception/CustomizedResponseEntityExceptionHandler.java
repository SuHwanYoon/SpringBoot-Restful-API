package com.yoon.rest.webservices.restful_web_services.exception;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
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
		
														//@Valid 로 판단된 User객체 Post의 유효성실패 결과를가져오고 유효성 에러를 일으킨 필드리스트를 가져온다
        List<String> errorMessages = ex.getBindingResult().getFieldErrors()
        		//스트림전환
                .stream()
                //List<FieldError> 요소를 순회하며 함수를 적용하고 List<String>타입으로 변화를 위해 map 메서드 사용,List<FieldError>순회 
                //첫번째요소 indexOf(fieldError) + 1 = 1, 두번째 요소는 2
                .map(fieldError -> "Error " + (ex.getBindingResult().getFieldErrors().indexOf(fieldError) + 1) + ": " + fieldError.getDefaultMessage())
                //Collectors로 List<String>타입으로 전환
                .collect(Collectors.toList());
																																// String.join메서드를 사용해 리스트요소들을  "," 구분자로 결합하여 하나의 문자열로 만든다
        String combinedErrorMessage = "Total Error:" + ex.getErrorCount() +" [" + String.join(", ", errorMessages) + "]";
        
        
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), 
													combinedErrorMessage , 
													request.getDescription(false));
		
		//요청시간, 커스텀한 유효성검사 오류메세지 ,요청URL, 400에러 반환
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}



	
}
