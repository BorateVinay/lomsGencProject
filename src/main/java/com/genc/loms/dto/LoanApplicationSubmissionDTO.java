package com.genc.loms.dto;

import com.genc.loms.entity.Customer;
import com.genc.loms.entity.LoanApplication;


public class LoanApplicationSubmissionDTO {
 
 // Maps to the "customer" JSON key
 private Customer customer; 
 
 // Maps to the "loanApplication" JSON key
 private LoanApplication loanApplication;

 // Getters and Setters (REQUIRED for Spring to deserialize the JSON)

 public Customer getCustomer() {
     return customer;
 }

 public void setCustomer(Customer customer) {
     this.customer = customer;
 }

 public LoanApplication getLoanApplication() {
     return loanApplication;
 }

 public void setLoanApplication(LoanApplication loanApplication) {
     this.loanApplication = loanApplication;
 }
}
