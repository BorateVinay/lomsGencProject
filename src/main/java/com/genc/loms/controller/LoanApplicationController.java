package com.genc.loms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.genc.loms.dto.LoanApplicationSubmissionDTO;
import com.genc.loms.entity.Customer;
import com.genc.loms.entity.LoanApplication;
import com.genc.loms.service.CustomerService;
import com.genc.loms.service.LoanApplicationService;

@RestController
@CrossOrigin(origins ="*")
public class LoanApplicationController{
	
	@Autowired
	LoanApplicationService loanApplicationService;
	
	@Autowired
	CustomerService customerService;
	
	List<LoanApplication> getAllLoanApplications(){
		return loanApplicationService.getAllLoanApplication();
	}
	
//	@PostMapping("/submitloanapplication")
//	public ResponseEntity<Map<String,Object>> submitApplication(@RequestBody LoanApplication loanApplication,@RequestBody Customer customer){
////		Update the Phone Address
//		customerService.updatePhoneAddressById(customer);
////		Saving the LoanApplication
//		loanApplicationService.submitLoanApplication(customer,loanApplication);
//		
//		Map<String,Object> response = new HashMap<String, Object>();
//		response.put("Message",customer);
//		response.put("Message", loanApplication);
//	 return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);	
//	}
	
	 @PostMapping("/submitloanapplication")
	    public ResponseEntity<Map<String,Object>> submitApplication(
	        @RequestBody LoanApplicationSubmissionDTO submissionDTO) {
	        
	        // 1. Extract the Customer and LoanApplication from the DTO
	        Customer customerToUpdate = submissionDTO.getCustomer();
	        LoanApplication newLoanApplication = submissionDTO.getLoanApplication();
	        
	        // 2. Update the Customer's phone and address
	        // The customerToUpdate object should contain the customerId, phone, and address
	        // This method will typically fetch the existing customer and apply the updates.
	        customerService.updatePhoneAddressById(customerToUpdate);
	        
	        // 3. Submit the LoanApplication
	        // The service layer will link the LoanApplication to the fully-loaded Customer entity.
	        LoanApplication savedApplication = loanApplicationService.submitLoanApplication(customerToUpdate.getCustomerId(), newLoanApplication);
	        
	        // 4. Prepare Response
	        Map<String, Object> response = new HashMap<>();
	        response.put("message", "Loan application submitted successfully.");
	        response.put("applicationId", savedApplication.getApplicationId());
	        response.put("loanType", savedApplication.getLoanType());
	        response.put("customerId", customerToUpdate.getCustomerId());
	        
	        // Note: I've corrected the error in your original controller where you overwrote the "Message" key.
	        
	        return new ResponseEntity<>(response, HttpStatus.OK);    
	    }

}
