package com.genc.loms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
@Table(name = "credit_scores")
public class CreditScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "score_id")
    private int scoreId;

    @OneToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "credit_score", nullable = false)
    private int creditScore;

    @Column(name = "evaluation_date", nullable = false)
    private LocalDate evaluationDate;

    public CreditScore() {
    }

    public CreditScore(Customer customer, int creditScore) {
        this.customer = customer;
        this.creditScore = creditScore;
        this.evaluationDate = LocalDate.now();
    }

    public int getScoreId() {
        return scoreId;
    }

    public void setScoreId(int scoreId) {
        this.scoreId = scoreId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(int creditScore) {
        this.creditScore = creditScore;
    }

    public LocalDate getEvaluationDate() {
        return evaluationDate;
    }

    public void setEvaluationDate(LocalDate evaluationDate) {
        this.evaluationDate = evaluationDate;
    }

    @Override
    public String toString() {
        return "CreditScore{" +
                "scoreId=" + scoreId +
                ", customer=" + (customer != null ? customer.getName() : "null") +
                ", creditScore=" + creditScore +
                ", evaluationDate=" + evaluationDate +
                '}';
    }
}
