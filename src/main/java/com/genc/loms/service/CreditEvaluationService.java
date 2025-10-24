package com.genc.loms.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.genc.loms.entity.CreditScore;
import com.genc.loms.entity.Customer;
import com.genc.loms.entity.LoanApplication;
import com.genc.loms.entity.LoanRepayment;
import com.genc.loms.repository.CreditScoreRepo;
import com.genc.loms.repository.LoanApplicationRepo;
import com.genc.loms.repository.LoanRepaymentRepo;

@Service
public class CreditEvaluationService {

    @Autowired
    private CreditScoreRepo creditScoreRepo;

    @Autowired
    private LoanApplicationRepo loanApplicationRepo;

    @Autowired
    private LoanRepaymentRepo loanRepaymentRepo;

    @Autowired
    private CustomerService customerService;

    public CreditScore evaluateCreditScore(int customerId) {
        Customer customer = customerService.getCustomerById(customerId);

        int baseScore = 300;
        int finalScore = baseScore;

        // Factor 1: Loan Application History
        List<LoanApplication> loanApplications = loanApplicationRepo.findByCustomerCustomerId(customerId);
        int loanCount = loanApplications.size();
        if (loanCount <= 3) {
            finalScore += 50;
        } else if (loanCount <= 6) {
            finalScore += 30;
        } else {
            finalScore += 10;
        }

        // Factor 2: Repayment History Analysis
        int repaymentScore = calculateRepaymentScore(customerId);
        finalScore += repaymentScore;

        finalScore = Math.max(300, Math.min(850, finalScore));

        Optional<CreditScore> latestScore = creditScoreRepo.findFirstByCustomerCustomerIdOrderByEvaluationDateDesc(customerId);
        CreditScore creditScore;

        if(latestScore.isPresent()) {
            
            creditScore = latestScore.get();
            creditScore.setCreditScore(finalScore);
            
            creditScore.setEvaluationDate(LocalDate.now());
        }
        else {
            
            creditScore = new CreditScore(customer, finalScore);
        }


        return creditScoreRepo.save(creditScore);
    }

    private int calculateRepaymentScore(int customerId) {
        List<LoanRepayment> repayments = loanRepaymentRepo.findByCustomerId(customerId);
        if (repayments.isEmpty()) return 0;

        int onTimePayments = 0;
        int scoreAdjustment = 0;

        for (LoanRepayment repayment : repayments) {
            if (repayment.getPaymentDate() != null && repayment.getDueDate() != null) {
                if (!repayment.getPaymentDate().isAfter(repayment.getDueDate())) {
                    onTimePayments++;
                } else {
                    long daysLate = ChronoUnit.DAYS.between(repayment.getDueDate(), repayment.getPaymentDate());
                    scoreAdjustment -= (daysLate <= 7) ? 5 : (daysLate <= 30) ? 15 : 30;
                }
            }
        }

        double onTimeRatio = (double) onTimePayments / repayments.size();
        if (onTimeRatio >= 0.95) scoreAdjustment += 50;
        else if (onTimeRatio >= 0.90) scoreAdjustment += 30;
        else if (onTimeRatio >= 0.80) scoreAdjustment += 15;
        else if (onTimeRatio >= 0.70) scoreAdjustment += 5;
        else if (onTimeRatio < 0.50) scoreAdjustment -= 25;

        return scoreAdjustment;
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