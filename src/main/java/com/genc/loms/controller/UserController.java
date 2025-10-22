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

@Controller
@CrossOrigin(origins = "*") 
public class UserController {

    @Autowired
    UserService userService;
    
    @PostMapping("/register")
    public ResponseEntity<Map<String,Object>> registerUser(@Valid @RequestBody User user){
    	Map<String,Object> response = new HashMap<String, Object>();
    	try {
    		User savedUser = userService.registerUser(user);
        	response.put("Message", savedUser);
        	return new ResponseEntity<>(response,HttpStatus.CREATED);
    	}catch (Exception e) {
			response.put("Message",e.getMessage());
		}
		return null;
    }
    
    @PostMapping("/login")
    public ResponseEntity<Map<String,Object>> login(@RequestBody User loginUser,HttpServletRequest request){
    	Map<String,Object> response = new HashMap<String, Object>();
    	User userdata = userService.authenticate(loginUser.getUserName(),loginUser.getPassword());
    	if(userdata == null) {
    		response.put("Message","wrong Credential");
    		return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED); 
    	}else {
    		HttpSession session = request.getSession();
    		session.setAttribute("employeeName", userdata.getName());
    		
    		response.put("Message","UserLogin Successfully");
    		response.put("user", userdata);
    	}
    	return new ResponseEntity<>(response,HttpStatus.OK);
    }
    
    @GetMapping("/logout")
	public ResponseEntity<String> customerLogout(HttpSession session){
//		Integer customerId = (Integer)session.getAttribute("customerId"); 
    	String employeeName = (String)session.getAttribute("employeeName");
		if(employeeName != null) {
			session.invalidate();
			return new ResponseEntity<String>("Logout Success",HttpStatus.OK);
		}
		return new ResponseEntity<String>("Error While Logout",HttpStatus.CONFLICT);
	}

   
}
