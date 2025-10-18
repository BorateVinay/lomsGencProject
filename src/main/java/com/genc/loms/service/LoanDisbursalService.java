package com.genc.loms.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.genc.loms.entity.LoanApplication;
import com.genc.loms.entity.LoanDisbursal;
import com.genc.loms.repository.LoanApplicationRepo;
import com.genc.loms.repository.LoanDisbursalRepo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service
public class LoanDisbursalService {
	
	@Autowired
	LoanDisbursalRepo repo;
	
	@Autowired
	LoanApplicationRepo apprepo;
	
	
	@Transactional
	public void approveLoan(LoanApplication loanApplication ,LoanDisbursal loanDisbursal){
		 
		loanDisbursal.setApplicationId(loanApplication);
		//loanDisbursal.setApprovedBy("Rahul");
		loanDisbursal.setDisbursalAmount(4500.0);
		loanDisbursal.setDisbursalDate(new Date());
		
		repo.save(loanDisbursal);
	
//		Update Status
		loanApplication.setStatus(LoanApplication.Status.APPROVED);
		apprepo.save(loanApplication);
	}
	
	
	@Transactional
	public void rejectLoan(LoanApplication loanApplication){
//		Update Status
		loanApplication.setStatus(LoanApplication.Status.REJECTED);
		apprepo.save(loanApplication);
	}
	
	

	
	
	public LoanDisbursal getDisbursalDetails(int disbursalId) {
		Optional<LoanDisbursal> disbursalData =   repo.findById(disbursalId);
		return disbursalData.orElseThrow(() -> new RuntimeException("Disbursal Id Not Found"));
	}
	
	
	
	

}
