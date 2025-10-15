package com.genc.loms.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.genc.loms.entity.LoanApplication;
import com.genc.loms.repository.LoanApplicationRepo;

@Service
public class LoanApplicationService {
	
	@Autowired
	LoanApplicationRepo repo;
	
	public LoanApplication getLoanApplicationById(int id) {
		Optional<LoanApplication> application= repo.findById(id);
		 return application.orElseThrow(() -> new RuntimeException("Loan Application not found for ID: " + id));
	}
	
	public List<LoanApplication> getAllLoanApplication(){
		List<LoanApplication> applications = repo.findAll();
		return applications;
	}

}
