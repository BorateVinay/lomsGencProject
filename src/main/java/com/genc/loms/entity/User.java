package com.genc.loms.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

import java.util.Base64;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    
    @NotBlank
    private String name;
    
    @NotBlank
    private String userName;

    @NotBlank	
    private String password;
    

	public User() {
		super();
	}


	public int getUserId() {
		return userId;
	}

	public User(@NotBlank String name, @NotBlank String userName, @NotBlank String password) {
		super();
		this.name = name;
		this.userName = userName;
		this.password = password;
	}


	public void setUserId(int userId) {
		this.userId = userId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getPassword() {
		return password;
	}


//	public void setPassword(String password) {
//		this.password = password;
//	}
	public void setPassword(String encPassword) {
	this.password = Base64.getEncoder().encodeToString(encPassword.getBytes());
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", name=" + name + ", userName=" + userName + ", password=" + password + "]";
	}

}
