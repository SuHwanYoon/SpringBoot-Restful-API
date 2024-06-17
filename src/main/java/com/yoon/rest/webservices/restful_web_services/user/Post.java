package com.yoon.rest.webservices.restful_web_services.user;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Size;

//User Entity(특정유저)와 일대다 관계를 가지는 Post(게시물) Entity
@Entity
public class Post {

	@Id
	@GeneratedValue
	private Integer id;
	
	@Size(min = 8)
	private String description;

	//Post(게시물) 엔티티와 User(사용자)엔티티는 다대일 관계이다
	//Post는 User를 참조하고 있기때문에 Jpa가 table create시에 User의  id 필드를 참조해 user_id 컬럼을 생성
	//Defalut는 EAGER(즉시로딩) 하지만 Post(게시물)을 가져올때 User의(사용자)의 정보를 함께 가져오지 않기 위해 지연로딩 지정
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private User user;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Post [id=" + id + ", description=" + description + "]";
	}
	
	
	
}
