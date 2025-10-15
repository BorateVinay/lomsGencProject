package com.genc.loms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.genc.loms.entity.LoanApplication;
import com.genc.loms.entity.LoanDisbursal;
import com.genc.loms.service.LoanApplicationService;
import com.genc.loms.service.LoanDisbursalService;

@RestController
@CrossOrigin(origins = "*") 
public class LoanDisbursalController {
	@Autowired
	LoanDisbursalService loanDisbursalService;
	
	@Autowired
	LoanApplicationService loanApplicationService;
	
	@GetMapping("getDisbursalById/{disbursalId}")
	LoanDisbursal getDisbursalData(@PathVariable int disbursalId) {
		return loanDisbursalService.getDisbursalDetails(disbursalId);
	}
	
	@PutMapping("approveLoan/{applicationId}")
	    LoanApplication approveLoan(@PathVariable int applicationId) {
		LoanApplication loanApplication= loanApplicationService.getLoanApplicationById(applicationId);
		loanDisbursalService.approveLoan(loanApplication);
		return loanApplication;
	}
	
	@PutMapping("reject/{applicationId}")
	LoanApplication rejectLoan(@PathVariable int applicationId) {
		LoanApplication loanApplication = loanApplicationService.getLoanApplicationById(applicationId);
		loanDisbursalService.rejectLoan(loanApplication);
		return loanApplication;
				
	}
	
}
