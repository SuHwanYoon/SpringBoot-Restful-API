package com.yoon.rest.webservices.restful_web_services.filtering;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class FilteringController {

	@GetMapping("/filtering")
	public MappingJacksonValue filtering() {
		
		SomeBean someBean = new SomeBean("value1", "value2", "value3");
		
		//JSON데이터 생성할때 추가적인 설정을 할수있게하는 클래스 SomeBean객체지정
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(someBean);
																											//"field1","field3" 필드를 제외한 나머지를 필터
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field1","field3");
		//필터링객체 							커스텀 필터 객체생성				필터이름(SomeBeanFilter)과 SimpleBeanPropertyFilter타입 변수지정
		FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
		// JSON데이터에 대한  필터링을 설정
		mappingJacksonValue.setFilters(filters);
		//필터링을 설정한 JSON객체를 반환
		return mappingJacksonValue;
	}

	@GetMapping("/filtering-list")
	public MappingJacksonValue filteringList() {
		List<SomeBean> list = Arrays.asList(new SomeBean("value1", "value2", "value3"), new SomeBean("value4", "value5", "value6"));
		
		//JSON데이터 생성할때 추가적인 설정을 할수있게하는 클래스 List<SomeBean>타입 변수지정
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(list);
																											//"field2","field3" 필드를 제외한 나머지를 필터
		SimpleBeanPropertyFilter filterList = SimpleBeanPropertyFilter.filterOutAllExcept("field2","field3");
		//필터링객체 							커스텀 필터 객체생성				필터이름(SomeBeanFilter)과 SimpleBeanPropertyFilter타입 변수지정
		FilterProvider ListFiltersCondition = new SimpleFilterProvider().addFilter("SomeBeanFilter", filterList);
		// JSON데이터에 대한  필터링을 설정
		mappingJacksonValue.setFilters(ListFiltersCondition);
		//필터링을 설정한 JSON객체를 반환
		return mappingJacksonValue;
	}

}
