package com.genc.loms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.genc.loms.entity.LoanApplication;
import com.genc.loms.entity.LoanApplication.Status;

import java.util.List;
import java.util.Optional;

public interface LoanApplicationRepo extends JpaRepository<LoanApplication,Integer>{
    List<LoanApplication> findByCustomerCustomerId(int customerId);
    
    List<LoanApplication> findByStatus(Status status);
   
    @Query("SELECT l.amountRequested FROM LoanApplication l WHERE l.customer.customerId = :customerId")
    List<Double> findAmountRequestedByCustomerId(@Param("customerId") int customerId);
    
 // ⭐ NEW CORRECT METHOD: Fetch amount by Application ID
    @Query("SELECT l.amountRequested FROM LoanApplication l WHERE l.applicationId = :applicationId")
    Optional<Double> findAmountRequestedByApplicationId(@Param("applicationId") int applicationId);
    
    List<LoanApplication> findByApplicationId(int applicationId);

}