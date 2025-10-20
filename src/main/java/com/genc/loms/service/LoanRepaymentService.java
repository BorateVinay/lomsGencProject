package com.genc.loms.service;

import com.genc.loms.entity.LoanApplication;
import com.genc.loms.entity.LoanRepayment;
import com.genc.loms.repository.LoanApplicationRepo;
import com.genc.loms.repository.LoanRepaymentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class LoanRepaymentService {

    @Autowired
    LoanRepaymentRepo loanRepaymentRepo;
    
    @Autowired
    LoanApplicationRepo loanApplicationRepo;
    
    @Autowired
    LoanApplicationService loanApplicationService;

//    public LoanRepayment loanRepayment(int applicationId){
////    	get the Loan Loanrepayment By application Id
//    	LoanRepayment loanLoanRepayment =loanRepaymentRepo.findRepaymentByLoanApplicationId(applicationId).get();
//    	System.out.println(loanLoanRepayment);
//    	
//    	double loanAmount = loanApplicationService.getLoanAmount(applicationId);
//    	System.out.println(loanAmount);
//    	
//    	loanLoanRepayment.setAmountPaid(loanAmount);
//    	loanLoanRepayment.setPaymentDate(LocalDate.now());
//    	
//    	return loanLoanRepayment;
//    	
//    }
    
 // In @Service public class LoanRepaymentService

    public LoanRepayment loanRepayment(int applicationId){
//    	get the Loan Loanrepayment By application Id
    	
    	LoanApplication loanApplication = loanApplicationRepo.findByApplicationId(applicationId).get(0);
    	LoanRepayment loanLoanRepayment = loanRepaymentRepo.findByLoanApplication(loanApplication).get(0);
        
        // ⭐ Improvement: Handle the Optional from the repository call
    	//LoanRepayment loanLoanRepayment = loanRepaymentRepo.findRepaymentByLoanApplicationId(applicationId)
        //    .orElseThrow(() -> new RuntimeException("Loan Repayment record not found for application ID: " + applicationId));

    	System.out.println(loanLoanRepayment);
    	
    	double loanAmount = loanApplicationService.getLoanAmount(applicationId);
    	System.out.println(loanAmount);
    	
    	loanLoanRepayment.setAmountPaid(5000);
    	loanLoanRepayment.setPaymentDate(LocalDate.now());
    	
    	// ⭐ CRITICAL FIX: Save the updated entity to persist changes
    	loanRepaymentRepo.save(loanLoanRepayment);
    	return loanLoanRepayment;
    }
    
    public LoanRepayment setdueDate(int applicationId){
//    	Set Due date and Application Id of Loan 
    	LoanRepayment loanRepayment = new LoanRepayment();
    	LocalDate currentDate = LocalDate.now();
    	LocalDate dueDate = currentDate.plusMonths(1);
    	loanRepayment.setDueDate(dueDate);
    	
    	LoanApplication loanApplication = loanApplicationService.getLoanApplicationById(applicationId);
    	loanRepayment.setLoanApplication(loanApplication);
    	
    	loanRepaymentRepo.save(loanRepayment);
    	
    	return loanRepayment;
    	
    }
    
      

    public List<LoanRepayment> getRepaymentHistory(int id) {
        List<LoanRepayment> list = new ArrayList<>();
        return list;
    }
}
 