package com.genc.loms.controller;

import java.net.http.HttpRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.genc.loms.entity.LoanApplication;
import com.genc.loms.entity.LoanDisbursal;
import com.genc.loms.service.LoanApplicationService;
import com.genc.loms.service.LoanDisbursalService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

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
	    LoanApplication approveLoan(@PathVariable int applicationId , HttpServletRequest request) {
		LoanApplication loanApplication= loanApplicationService.getLoanApplicationById(applicationId);
		
		LoanDisbursal loanDisbursal = new LoanDisbursal();
		HttpSession session = request.getSession();
		loanDisbursal.setApprovedBy((String)session.getAttribute("employeeName"));
		loanDisbursalService.approveLoan(loanApplication,loanDisbursal);
		return loanApplication;
	}
	
	@PutMapping("reject/{applicationId}")
	LoanApplication rejectLoan(@PathVariable int applicationId) {
		LoanApplication loanApplication = loanApplicationService.getLoanApplicationById(applicationId);
		loanDisbursalService.rejectLoan(loanApplication);
		return loanApplication;		
	}
	
}
