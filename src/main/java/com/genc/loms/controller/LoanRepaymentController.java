package com.genc.loms.controller;

import com.genc.loms.entity.LoanRepayment;
import com.genc.loms.service.LoanRepaymentService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class LoanRepaymentController {

    @Autowired
    LoanRepaymentService loanRepaymentService;

    @PutMapping("/loan-repayment/{applicationId}")
    public ResponseEntity<Map<String,Object>> payLoan(@PathVariable int applicationId){
       Map<String,Object> response = new HashMap<String, Object>();
        try {
        	 LoanRepayment repay = loanRepaymentService.loanRepayment(applicationId);
        	 response.put("Message", repay);
		} catch (Exception e) {
			response.put("Message", e.getMessage());
		}
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK); 
    }
    
    @PostMapping("/set-DueDate/{applicationId}")
    public ResponseEntity<Map<String,Object>> setDueDate(@PathVariable int applicationId){
    	Map<String,Object> response = new HashMap<String, Object>();
    	try {
    		LoanRepayment loanRepayment = loanRepaymentService.setdueDate(applicationId);
    		response.put("Message",loanRepayment);
		} catch (Exception e) {
			 response.put("Message", e.getMessage());
		}
    	return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
    }
    
    

    @GetMapping("/payment-history/{id}")
    public List<LoanRepayment> getRepaymentHistory(@PathVariable int id){
        return loanRepaymentService.getRepaymentHistory(id);
    }
//   handleOverdueAccounts()
}
 
