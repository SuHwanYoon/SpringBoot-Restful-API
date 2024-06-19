package com.yoon.rest.webservices.restful_web_services.security;

// 긴 클래스명을 사용하지않고 곧바로 withDefaults()메서드를 사용할수 있게 import static 처리
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

// SpringSecurity 설정 클래스
// @Configuration은 하나이상의 @Bean메서드를 선언한다는 뜻
@Configuration
public class SpringSecurityConfiguration {

	// 사용자 정의 SecurityFilterChain을 반환하는 Bean method
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		//1. 모든 http요청을 인증받도록 설정
		// 기본 로그인 web view 대신 오류페이지가 나온다
		http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated());

		// 2. 만약 요청이 인증되지않았다면 인증을 위한 브라우저 기본 팝업창(JS의 prompt형식)을 보여준다
		http.httpBasic(withDefaults());

		//3. CSRF 를 해제하고 RestAPI  POST, PUT 요청을 가능하게 설정

		// http.csrf().disable(); // SB 3.1.x 부터는 추천되지 않는 방식
		
		// SB 3.1.x 부터는 Lambda식을 사용하거나
		http.csrf(csrf -> csrf.disable()); 
		// 메서드참조 :: 를 사용하는 방식이 추천된다
		// http.csrf(AbstractHttpConfigurer::disable); 

		return http.build();
	}
}
