package com.genc.loms.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// SLF4J Imports
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.genc.loms.entity.CreditScore;
import com.genc.loms.service.CreditEvaluationService;

import jakarta.servlet.http.HttpSession;

@RestController
@CrossOrigin(origins = "*")
public class CreditEvaluationController {

	// 1. Declare a static final Logger instance using SLF4J factory
	private static final Logger logger = LoggerFactory.getLogger(CreditEvaluationController.class);

	@Autowired
	private CreditEvaluationService creditEvaluationService;

	@GetMapping("/evaluate")
	public ResponseEntity<Map<String, Object>> evaluateCreditScore(HttpSession session) {
		// Log method entry
		logger.info("Received request to evaluate credit score.");
		
		Map<String, Object> map = new HashMap<>();

		Object customerIdObj = session.getAttribute("customerId");

		if (customerIdObj == null) {
			// Log an attempt with missing data
			logger.warn("Attempt to evaluate credit score without 'customerId' in session. Returning 401 UNAUTHORIZED.");
			map.put("status", "error");
			map.put("message", "customerId not found");
			return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
		}

		try {
			int customerId = (int) customerIdObj;
			logger.info("Starting credit score evaluation for customerId: {}", customerId);
			
			CreditScore creditScore = creditEvaluationService.evaluateCreditScore(customerId);
			
			
			logger.info("Successfully evaluated and recorded credit score for customerId: {}. Score: {}", customerId, creditScore.getCreditScore());
			
			map.put("status", "Success");
			map.put("message", "Credit score evaluated and recorded successfully.");
			map.put("data", creditScore);

			return new ResponseEntity<>(map, HttpStatus.OK);
		} catch (Exception e) {
			
			logger.error("Error evaluating credit score for customerId: {}. Exception: {}", customerIdObj, e.getMessage(), e);
			map.put("error", "Failed to evaluate credit score: " + e.getMessage());
			return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
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