package com.genc.loms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.genc.loms.entity.LoanApplication;

public interface LoanApplicationRepo extends JpaRepository<LoanApplication,Integer>{
	

}
