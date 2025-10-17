package com.genc.loms.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.genc.loms.entity.CreditScore;
import com.genc.loms.service.CreditEvaluationService;

@RestController
@RequestMapping("/api/credit")
@CrossOrigin(origins = "*")
public class CreditEvaluationController {

    @Autowired
    private CreditEvaluationService creditEvaluationService;

    @GetMapping("/evaluate/{customerId}")
    public ResponseEntity<?> evaluateCreditScore(@PathVariable int customerId) {
        try {
            CreditScore creditScore = creditEvaluationService.evaluateCreditScore(customerId);
            return ResponseEntity.ok(creditScore);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to evaluate credit score: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

//    @GetMapping("/report/{customerId}")
//    public ResponseEntity<?> getCreditReport(@PathVariable int customerId) {
//        try {
//            CreditScore creditScore = creditEvaluationService.getCreditReport(customerId);
//            return ResponseEntity.ok(creditScore);
//        } catch (IllegalArgumentException e) {
//            Map<String, String> error = new HashMap<>();
//            error.put("error", e.getMessage());
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
//        } catch (Exception e) {
//            Map<String, String> error = new HashMap<>();
//            error.put("error", "Failed to retrieve credit report: " + e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
//        }
//    }

//    @GetMapping("/history/{customerId}")
//    public ResponseEntity<?> getCreditHistory(@PathVariable int customerId) {
//        try {
//            List<CreditScore> creditHistory = creditEvaluationService.getCreditHistory(customerId);
//            return ResponseEntity.ok(creditHistory);
//        } catch (Exception e) {
//            Map<String, String> error = new HashMap<>();
//            error.put("error", "Failed to retrieve credit history: " + e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
//        }
//    }
//
//    @GetMapping("/low-scores")
//    public ResponseEntity<?> getLowCreditScores(@RequestParam(defaultValue = "600") int threshold) {
//        try {
//            List<CreditScore> lowScores = creditEvaluationService.getLowCreditScores(threshold);
//            return ResponseEntity.ok(lowScores);
//        } catch (Exception e) {
//            Map<String, String> error = new HashMap<>();
//            error.put("error", "Failed to retrieve low credit scores: " + e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
//        }
//    }
//
//    @GetMapping("/all")
//    public ResponseEntity<?> getAllCreditScores() {
//        try {
//            List<CreditScore> creditScores = creditEvaluationService.getAllCreditScores();
//            return ResponseEntity.ok(creditScores);
//        } catch (Exception e) {
//            Map<String, String> error = new HashMap<>();
//            error.put("error", "Failed to retrieve credit scores: " + e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
//        }
//    }
//
//    @GetMapping("/date-range")
//    public ResponseEntity<?> getCreditScoresByDateRange(@RequestParam String startDate, @RequestParam String endDate) {
//        try {
//            LocalDate start = LocalDate.parse(startDate);
//            LocalDate end = LocalDate.parse(endDate);
//            List<CreditScore> creditScores = creditEvaluationService.getCreditScoresByDateRange(start, end);
//            return ResponseEntity.ok(creditScores);
//        } catch (Exception e) {
//            Map<String, String> error = new HashMap<>();
//            error.put("error", "Failed to retrieve credit scores: " + e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
//        }
//    }
} 