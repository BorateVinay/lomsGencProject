package com.genc.loms.controller;

import java.net.http.HttpRequest;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.genc.loms.entity.Customer;
import com.genc.loms.service.CustomerService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/customer")
public class CustomerController {
	
	@Autowired
	CustomerService customerService;
	
	@PostMapping("/register")
	public ResponseEntity<Map<String,Object>> registerCustomer(@Valid @RequestBody Customer customer){
		Map<String,Object> response = new HashMap<String, Object>();
		try {
			Customer savedCustomer = customerService.registeCustomer(customer);
			response.put("Message", savedCustomer);
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
			
		} catch (Exception e) {
			response.put("Message",e.getMessage());
		}
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public ResponseEntity<Map<String,Object>> login(@Valid @RequestBody Customer loginCustomer){
		
		Map<String,Object> response = new HashMap<String, Object>();
		Customer customerData = customerService.authenticateCustomer(loginCustomer.getEmail(),loginCustomer.getPassword());
		if(customerData == null) {
			response.put("Message","wrong Credential");
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.UNAUTHORIZED);
		}else {
			response.put("Message","Customer Login Successfully");
			response.put("customer", customerData);
//			HttpSession session, HttpServletRequest request
//			session.setAttribute("name", customerData.getName());
//			session.getAttribute("name");
//			session.invalidate();
			//session.g
			//session.
			//request.getSession().setMaxInactiveInterval(2000);
		}
		
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
		
	}
}
