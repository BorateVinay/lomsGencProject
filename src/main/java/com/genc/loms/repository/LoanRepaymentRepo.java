package com.genc.loms.repository;

import com.genc.loms.entity.LoanApplication;
import com.genc.loms.entity.LoanRepayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;


@Repository
public interface LoanRepaymentRepo extends JpaRepository<LoanRepayment, Integer> {
	//List<LoanRepayment> findByLoanApplication(LoanApplication loanApplication);
	
//	 @Query("SELECT lr FROM LoanRepayment lr WHERE lr.loanApplication.applicationId = :loanId")
//	 Optional<LoanRepayment> findRepaymentByLoanApplicationId(@Param("loanId") int loanId);
	 

	 List<LoanRepayment> findByLoanApplication(LoanApplication loanApplication);
	
	
}
 