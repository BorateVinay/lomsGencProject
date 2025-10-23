package com.genc.loms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.genc.loms.entity.LoanApplication;
import com.genc.loms.entity.LoanDisbursal;
import com.genc.loms.service.LoanApplicationService;
import com.genc.loms.service.LoanDisbursalService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@CrossOrigin(origins = "*")
public class LoanDisbursalController {

	
	private static final Logger logger = LoggerFactory.getLogger(LoanDisbursalController.class);

	@Autowired
	LoanDisbursalService loanDisbursalService;

	@Autowired
	LoanApplicationService loanApplicationService;

	@GetMapping("getDisbursalById/{disbursalId}")
	LoanDisbursal getDisbursalData(@PathVariable int disbursalId) {
		logger.info("Received request to retrieve loan disbursal data for ID: {}", disbursalId);
		
		LoanDisbursal disbursal = loanDisbursalService.getDisbursalDetails(disbursalId);
		
		if (disbursal != null) {
			logger.info("Successfully retrieved disbursal details for ID: {}", disbursalId);
		} else {
			logger.warn("No disbursal data found for ID: {}", disbursalId);
		}
		return disbursal;
	}

	@PutMapping("approveLoan/{applicationId}")
	LoanApplication approveLoan(@PathVariable int applicationId, HttpServletRequest request) {
		logger.info("Received request to approve loan application ID: {}", applicationId);

		LoanApplication loanApplication = loanApplicationService.getLoanApplicationById(applicationId);
		
		if (loanApplication == null) {
			logger.warn("Loan approval failed: Application not found for ID: {}", applicationId);
			
		}

		LoanDisbursal loanDisbursal = new LoanDisbursal();
		HttpSession session = request.getSession(false); // Use false to avoid creating a new session

		String approvedBy = null;
		if (session != null) {
			approvedBy = (String) session.getAttribute("employeeName");
		}
		
		if (approvedBy == null) {
			logger.warn("Loan approval attempt for application ID {} failed: 'employeeName' not found in session.", applicationId);
			
		} else {
			loanDisbursal.setApprovedBy(approvedBy);
			logger.debug("Loan application ID {} approved by employee: {}", applicationId, approvedBy);
		}
		
		try {
			loanDisbursalService.approveLoan(loanApplication, loanDisbursal);
			logger.info("Loan application ID {} successfully approved and disbursal process initiated.", applicationId);
		} catch (Exception e) {
			logger.error("Error during loan approval for application ID {}. Exception: {}", applicationId, e.getMessage(), e);
			
		}
		
		return loanApplication;
	}

	@PutMapping("reject/{applicationId}")
	LoanApplication rejectLoan(@PathVariable int applicationId) {
		logger.info("Received request to reject loan application ID: {}", applicationId);
		
		LoanApplication loanApplication = loanApplicationService.getLoanApplicationById(applicationId);
		
		if (loanApplication == null) {
			logger.warn("Loan rejection failed: Application not found for ID: {}", applicationId);
			
		}

		try {
			loanDisbursalService.rejectLoan(loanApplication);
			logger.info("Loan application ID {} successfully rejected.", applicationId);
		} catch (Exception e) {
			logger.error("Error during loan rejection for application ID {}. Exception: {}", applicationId, e.getMessage(), e);
			
		}
		
		return loanApplication;
	}

}