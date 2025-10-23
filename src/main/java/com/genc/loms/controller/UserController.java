package com.genc.loms.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.genc.loms.entity.User;
import com.genc.loms.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@CrossOrigin(origins = "*") 
public class UserController {

    
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;
    
    @PostMapping("/register")
    public ResponseEntity<Map<String,Object>> registerUser(@Valid @RequestBody User user){
        logger.info("Received POST request to register user with username: {}", user.getUserName());
    	Map<String,Object> response = new HashMap<String, Object>();
    	try {
    		User savedUser = userService.registerUser(user);
            logger.info("User registered successfully: {}", savedUser.getUserName());
        	response.put("Message", savedUser);
        	return new ResponseEntity<>(response,HttpStatus.CREATED);
    	}catch (Exception e) {
            logger.error("Registration failed for user {}. Error: {}", user.getUserName(), e.getMessage(), e);
			response.put("Message",e.getMessage());
		}
		
        return new ResponseEntity<>(response, HttpStatus.CONFLICT); 
    }
    
    @PostMapping("/login")
    public ResponseEntity<Map<String,Object>> login(@RequestBody User loginUser,HttpServletRequest request){
        logger.info("Received POST request for login attempt by username: {}", loginUser.getUserName());
    	Map<String,Object> response = new HashMap<String, Object>();
    	User userdata = userService.authenticate(loginUser.getUserName(),loginUser.getPassword());
        
    	if(userdata == null) {
            logger.warn("Login failed for user: {}. Wrong credentials.", loginUser.getUserName());
    		response.put("Message","wrong Credential");
    		return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED); 
    	}else {
    		HttpSession session = request.getSession();
    		session.setAttribute("employeeName", userdata.getName());
    		
    		response.put("Message","UserLogin Successfully");
    		response.put("user", userdata);
            logger.info("User {} logged in successfully. Session created.", userdata.getName());
    	}
    	return new ResponseEntity<>(response,HttpStatus.OK);
    }
    
    @GetMapping("/logout")
	public ResponseEntity<String> customerLogout(HttpSession session){
        // Integer customerId = (Integer)session.getAttribute("customerId"); 
    	String employeeName = (String)session.getAttribute("employeeName");
        logger.info("Received GET request for logout. Current session user: {}", employeeName != null ? employeeName : "N/A (unauthenticated)");
        
		if(employeeName != null) {
			session.invalidate();
            logger.info("User {} logged out and session invalidated.", employeeName);
			return new ResponseEntity<String>("Logout Success",HttpStatus.OK);
		}
        logger.warn("Logout attempt failed. No valid 'employeeName' found in session.");
		return new ResponseEntity<String>("Error While Logout",HttpStatus.CONFLICT);
	}

   
}