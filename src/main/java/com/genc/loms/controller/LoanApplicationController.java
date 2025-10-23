package com.genc.loms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// SLF4J Imports
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.genc.loms.dto.LoanApplicationSubmissionDTO;
import com.genc.loms.entity.Customer;
import com.genc.loms.entity.LoanApplication;
import com.genc.loms.service.CustomerService;
import com.genc.loms.service.LoanApplicationService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@CrossOrigin(origins ="*")
public class LoanApplicationController{
	
	
	private static final Logger logger = LoggerFactory.getLogger(LoanApplicationController.class);
	
	@Autowired
	LoanApplicationService loanApplicationService;
	
	@Autowired
	CustomerService customerService;
	
	@GetMapping("/getAllApplication")
	List<LoanApplication> getAllLoanApplications(){
		logger.info("Received request to retrieve all loan applications.");
		List<LoanApplication> applications = loanApplicationService.getAllLoanApplication();
		logger.info("Successfully retrieved {} loan applications.", applications.size());
		return applications;
	}
	

	 @PostMapping("/submitloanapplication")
	    public ResponseEntity<Map<String,Object>> submitApplication(
	        @RequestBody LoanApplicationSubmissionDTO submissionDTO,HttpServletRequest request) {
	        
		 	logger.info("Received request to submit a new loan application.");
		 	
	        // 1. Extract the Customer and LoanApplication from the DTO
	        Customer customerToUpdate = submissionDTO.getCustomer();
	        LoanApplication newLoanApplication = submissionDTO.getLoanApplication();
	        
	        // 2. Update the Customer's phone and address
	        // The customerToUpdate object should contain the customerId, phone, and address
	        // This method will typically fetch the existing customer and apply the updates.
	        HttpSession session = request.getSession(false); // Check for existing session

			if (session == null || session.getAttribute("customerId") == null) {
				logger.warn("Loan submission failed: customerId not found in session.");
				Map<String, Object> errorResponse = new HashMap<>();
				errorResponse.put("message", "Authentication required: customerId not found in session.");
				return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
			}

			int customerId = (int)session.getAttribute("customerId");
			logger.debug("Processing loan application for customerId: {}", customerId);

			try {
				customerToUpdate.setCustomerId(customerId);
				customerService.updatePhoneAddressById(customerToUpdate);
				logger.info("Updated phone and address for customerId: {}", customerId);
				
				// 3. Submit the LoanApplication
				// The service layer will link the LoanApplication to the fully-loaded Customer entity.
				LoanApplication savedApplication = loanApplicationService.submitLoanApplication(customerToUpdate.getCustomerId(), newLoanApplication);
				
				// 4. Prepare Response
				Map<String, Object> response = new HashMap<>();
				response.put("message", "Loan application submitted successfully.");
				response.put("applicationId", savedApplication.getApplicationId());
				response.put("loanType", savedApplication.getLoanType());
				response.put("customerId", customerToUpdate.getCustomerId());
				
				
				logger.info("Loan application submitted successfully. Application ID: {} for customerId: {}", savedApplication.getApplicationId(), customerId);
				
				return new ResponseEntity<>(response, HttpStatus.OK); 
				
			} catch (Exception e) {
				logger.error("Error submitting loan application for customerId: {}. Exception: {}", customerId, e.getMessage(), e);
				Map<String, Object> errorResponse = new HashMap<>();
				errorResponse.put("message", "Failed to submit loan application: " + e.getMessage());
				return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
			}  
	    }
	 
	 @GetMapping("/getCustomerLoans")
	 public ResponseEntity<Map<String,Object>> getcustomerLoans(HttpServletRequest request){
		 Map<String,Object> response = new HashMap<String, Object>();
		 HttpSession session = request.getSession(false); // Check for existing session
		 
		 if (session == null || session.getAttribute("customerId") == null) {
			 logger.warn("Attempt to get customer loans without customerId in session.");
			 response.put("Message", "Customer ID not found in session. Unauthorized.");
			 return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
		 }
		 
		 try {
			 int id = (int)session.getAttribute("customerId");
			 logger.info("Retrieving loans for customerId: {}", id);
			
			 List<LoanApplication> applications = loanApplicationService.getCustomerLoanApplications(id);
			 
			 logger.info("Found {} loan applications for customerId: {}", applications.size(), id);
			 
			 response.put("Message", applications);
			 response.put("Id", id);
		 }
		 catch(Exception e){
			 logger.error("Error retrieving customer loans. Exception: {}", e.getMessage(), e);
			 response.put("Message", e.getMessage());
			 return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		 }
		 return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
	 }
	 
	 @GetMapping("/getPendingLoanApplication")
	 public ResponseEntity<Map<String,Object>> getPendingLoanApplications(){
		 Map<String,Object> response = new HashMap<String, Object>();
		 
		 logger.info("Received request to retrieve all pending loan applications.");
		 
		 try {
			 List<LoanApplication> applications = loanApplicationService.getPendingLoanApplications();
			 
			 logger.info("Successfully retrieved {} pending loan applications.", applications.size());
			 
			 response.put("Message", applications);
		} catch (Exception e) {
			 logger.error("Error retrieving pending loan applications. Exception: {}", e.getMessage(), e);
			 response.put("Message",e.getMessage());
			 return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
	 }

}