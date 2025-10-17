package com.genc.loms.repository;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.genc.loms.entity.CreditScore;
import com.genc.loms.entity.Customer;

public interface CreditScoreRepo extends JpaRepository<CreditScore, Integer> {

    List<CreditScore> findByCustomer(Customer customer);

    List<CreditScore> findByCustomerCustomerId(int customerId);

    Optional<CreditScore> findFirstByCustomerOrderByEvaluationDateDesc(Customer customer);

    Optional<CreditScore> findFirstByCustomerCustomerIdOrderByEvaluationDateDesc(int customerId);

    List<CreditScore> findByCreditScoreBetween(int minScore, int maxScore);

    List<CreditScore> findByEvaluationDateBetween(LocalDate startDate, LocalDate endDate);

    List<CreditScore> findAllByOrderByEvaluationDateDesc();

    @Query("SELECT cs FROM CreditScore cs WHERE cs.creditScore < :threshold ORDER BY cs.creditScore ASC")
    List<CreditScore> findLowCreditScores(@Param("threshold") int threshold);
}


 
