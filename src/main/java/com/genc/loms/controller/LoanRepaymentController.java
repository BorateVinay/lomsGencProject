package com.genc.loms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.genc.loms.entity.LoanRepayment;
import com.genc.loms.service.LoanRepaymentService;

@RestController
public class LoanRepaymentController {

    
    private static final Logger logger = LoggerFactory.getLogger(LoanRepaymentController.class);

    @Autowired
    LoanRepaymentService loanRepaymentService;

    @PutMapping("/loan-repayment/{applicationId}")
    public ResponseEntity<Map<String,Object>> payLoan(@PathVariable int applicationId){
        logger.info("Received PUT request to process loan repayment for Application ID: {}", applicationId);
        
        Map<String,Object> response = new HashMap<String, Object>();
        try {
        	 LoanRepayment repay = loanRepaymentService.loanRepayment(applicationId);
        	 response.put("Message", repay);
             logger.info("Successfully processed repayment for Application ID: {}", applicationId);
		} catch (Exception e) {
            
            logger.error("Error processing loan repayment for ID: {}. Error: {}", applicationId, e.getMessage(), e);
			response.put("Message", e.getMessage());
		}
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK); 
    }
    
    @PostMapping("/set-DueDate/{applicationId}")
    public ResponseEntity<Map<String,Object>> setDueDate(@PathVariable int applicationId){
        logger.info("Received POST request to set due date for Application ID: {}", applicationId);
    	Map<String,Object> response = new HashMap<String, Object>();
        
    	try {
    		LoanRepayment loanRepayment = loanRepaymentService.setdueDate(applicationId);
    		response.put("Message",loanRepayment);
            logger.info("Due date set successfully for loan: {}", applicationId);
		} catch (Exception e) {
            
			 logger.error("Error setting due date for ID: {}. Error: {}", applicationId, e.getMessage(), e);
			 response.put("Message", e.getMessage());
		}
    	return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
    }
    
    @GetMapping("/payment-history/{id}")
    public List<LoanRepayment> getRepaymentHistory(@PathVariable int id){
        logger.debug("Received GET request to fetch repayment history for loan ID: {}", id);
        return loanRepaymentService.getRepaymentHistory(id);
    }
}