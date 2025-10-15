package com.genc.loms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.genc.loms.entity.LoanApplication;
import com.genc.loms.service.LoanApplicationService;

@RestController
@CrossOrigin(origins ="*")
public class LoanApplicationController{
	
	@Autowired
	LoanApplicationService loanApplicationService;
	
	List<LoanApplication> getAllLoanApplications(){
		return loanApplicationService.getAllLoanApplication();
	}

}
