package com.genc.loms.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.genc.loms.entity.Customer;
import com.genc.loms.service.CustomerService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/customer")
public class CustomerController {
	
	
	private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
	
	@Autowired
	CustomerService customerService;
	
	@PostMapping("/register")
	public ResponseEntity<Map<String,Object>> registerCustomer(@Valid @RequestBody Customer customer){
		
		logger.info("Received request to register a new customer: {}", customer.getEmail());
		
		Map<String,Object> response = new HashMap<String, Object>();
		try {
			Customer savedCustomer = customerService.registeCustomer(customer);
			
			
			logger.info("Customer registration successful for ID: {}", savedCustomer.getCustomerId());
			
			response.put("Message", savedCustomer);
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
			
		} catch (Exception e) {
			
			logger.error("Error during customer registration for email: {}. Exception: {}", customer.getEmail(), e.getMessage(), e);
			response.put("Message",e.getMessage());
		}
		
		
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public ResponseEntity<Map<String,Object>> login(@Valid @RequestBody Customer loginCustomer,HttpServletRequest request){
		
		
		logger.info("Login attempt for email: {}", loginCustomer.getEmail());
		
		Map<String,Object> response = new HashMap<String, Object>();
		Customer customerData = customerService.authenticateCustomer(loginCustomer.getEmail(),loginCustomer.getPassword());
		
		if(customerData == null) {
			
			logger.warn("Authentication failed for email: {}. Wrong credentials.", loginCustomer.getEmail());
			response.put("Message","wrong Credential");
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.UNAUTHORIZED);
		}else {
			
			logger.info("Customer login successful for ID: {}", customerData.getCustomerId());
			
			response.put("Message","Customer Login Successfully");
			response.put("customer", customerData);
			
			HttpSession session = request.getSession();
			session.setAttribute("customerId",customerData.getCustomerId());
			
			
			logger.debug("Session created for customerId: {}", customerData.getCustomerId());
		}

		
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
		
	}
	
	@GetMapping("/logout")
	public ResponseEntity<String> customerLogout(HttpSession session){
		Integer customerId = (Integer)session.getAttribute("customerId"); 
		
		
		logger.info("Attempting logout for customerId: {}", customerId != null ? customerId : "N/A (session invalid/missing)");
		
		if(customerId != null) {
			session.invalidate();
			
			logger.info("Customer logout successful for ID: {}", customerId);
			return new ResponseEntity<String>("Logout Success",HttpStatus.OK);
		}
		
		
		logger.warn("Logout failed: customerId not found in session. Returning CONFLICT.");
		return new ResponseEntity<String>("Error While Logout",HttpStatus.CONFLICT);
	}
	
}