package com.genc.loms.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.genc.loms.entity.CreditScore;
import com.genc.loms.entity.Customer;
import com.genc.loms.entity.LoanApplication;
import com.genc.loms.repository.CreditScoreRepo;
import com.genc.loms.repository.LoanApplicationRepo;

@Service
public class CreditEvaluationService {

    @Autowired
    private CreditScoreRepo creditScoreRepo;

    @Autowired
    private LoanApplicationRepo loanApplicationRepo;

    @Autowired
    private CustomerService customerService;

    public CreditScore evaluateCreditScore(int customerId) {
        Customer customer = customerService.getCustomerById(customerId);

        int baseScore = 300;
        int finalScore = baseScore;

        List<LoanApplication> loanApplications = loanApplicationRepo.findByCustomerCustomerId(customerId);

        int loanCount = loanApplications.size();
        if (loanCount <= 3) {
            finalScore += 50;
        } else if (loanCount <= 6) {
            finalScore += 30;
        } else {
            finalScore += 10;
        }

        finalScore = Math.max(300, Math.min(850, finalScore));

        CreditScore creditScore = new CreditScore(customer, finalScore);
        return creditScoreRepo.save(creditScore);
    }

    public CreditScore getCreditReport(int customerId) {
        Optional<CreditScore> latestScore = creditScoreRepo
                .findFirstByCustomerCustomerIdOrderByEvaluationDateDesc(customerId);
        return latestScore.orElseGet(() -> evaluateCreditScore(customerId));
        //return latestScore.get();
    }

//    public List<CreditScore> getCreditHistory(int customerId) {
//        return creditScoreRepo.findByCustomerCustomerId(customerId);
//    }
//
//    public List<CreditScore> getLowCreditScores(int threshold) {
//        return creditScoreRepo.findLowCreditScores(threshold);
//    }
//
//    public List<CreditScore> getAllCreditScores() {
//        return creditScoreRepo.findAllByOrderByEvaluationDateDesc();
//    }
//
//    public List<CreditScore> getCreditScoresByDateRange(LocalDate startDate, LocalDate endDate) {
//        return creditScoreRepo.findByEvaluationDateBetween(startDate, endDate);
//    }
}  