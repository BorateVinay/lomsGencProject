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

@Service
public class LoanDisbursalService {
	
	@Autowired
	LoanDisbursalRepo repo;
	
	@Autowired
	LoanApplicationRepo apprepo;
	
	
	@Transactional
	public void approveLoan(LoanApplication loanApplication){
		LoanDisbursal obj = new LoanDisbursal();
		obj.setApplicationId(loanApplication);
		obj.setApprovedBy("Rahul");
		obj.setDisbursalAmount(4500.0);
		obj.setDisbursalDate(new Date());
		
		repo.save(obj);
	
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
