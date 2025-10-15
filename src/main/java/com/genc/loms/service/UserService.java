package com.genc.loms.service;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.genc.loms.entity.User;
import com.genc.loms.repository.UserRepo;

import jakarta.validation.Valid;

@Service
public class UserService {

    @Autowired
    UserRepo userRepo;
    
    public User registerUser(@Valid User user) {
    	if(userRepo.findByUserName(user.getUserName()) != null) {
    		throw new RuntimeErrorException(null, "Username already exist");
    	}
    	userRepo.save(user);
    	return user;
    }
    
    public User authenticate(@Valid String userName,@Valid String password){
    	 User userData= userRepo.findByUserName(userName);
    	 
    	  if(userData != null && userData.getPassword().equals(password)) {
    		  return userData;
    	  }
    	 
    	 return null;
    	
    }

   
}
