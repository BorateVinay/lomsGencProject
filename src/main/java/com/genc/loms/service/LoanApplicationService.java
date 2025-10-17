package com.genc.loms.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.genc.loms.entity.Customer;
import com.genc.loms.entity.LoanApplication;
import com.genc.loms.entity.LoanApplication.Status;
import com.genc.loms.repository.CustomerRepo;
import com.genc.loms.repository.LoanApplicationRepo;

import jakarta.transaction.Transactional;

@Service
public class LoanApplicationService {
	
	@Autowired
	LoanApplicationRepo repo;
	
	@Autowired
	CustomerRepo cusRepo;
	
	@Autowired
	CustomerService customerService;
	
	public LoanApplication getLoanApplicationById(int id) {
		Optional<LoanApplication> application= repo.findById(id);
		 return application.orElseThrow(() -> new RuntimeException("Loan Application not found for ID: " + id));
	}
	
	public List<LoanApplication> getAllLoanApplication(){
		List<LoanApplication> applications = repo.findAll();
		return applications;
	}
	
//	public String submitLoanApplication(Customer customer,LoanApplication loanApplication) {
//		cusRepo.save(customer);
//		loanApplication.setStatus(Status.PENDING);
//		repo.save(loanApplication);
//		return "SuccessFully Submitted LonApplication";
//		
//	}
	
//	 @Transactional
	    public LoanApplication submitLoanApplication(int customerId, LoanApplication loanApplicationDetails) {
	        
	        // 1. Fetch the managed Customer entity to link with the application
	        Customer customer = customerService.getCustomerById(customerId);
	        
	        // 2. Set the customer relationship on the LoanApplication
	        loanApplicationDetails.setCustomer(customer);
	        
	        // 3. Set initial status (if not already set, good practice)
	        if (loanApplicationDetails.getStatus() == null) {
	            loanApplicationDetails.setStatus(LoanApplication.Status.PENDING);
	        }
	        
	        // 4. Save the new LoanApplication
	        return repo.save(loanApplicationDetails);
	    }
	
	
	


}
