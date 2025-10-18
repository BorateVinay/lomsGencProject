package com.genc.loms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.genc.loms.entity.LoanApplication;
import com.genc.loms.entity.LoanApplication.Status;

import java.util.List;

public interface LoanApplicationRepo extends JpaRepository<LoanApplication,Integer>{
    List<LoanApplication> findByCustomerCustomerId(int customerId);
    
    List<LoanApplication> findByStatus(Status status);
    

}