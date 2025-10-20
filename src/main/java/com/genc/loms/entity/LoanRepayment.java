package com.genc.loms.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class LoanRepayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int repaymentId;

    private double amountPaid;
    private LocalDate paymentDate;
    private LocalDate dueDate;

    @OneToOne
    @JoinColumn(name = "loan_id")
    private LoanApplication loanApplication;

    public LoanRepayment() {
    }

	public LoanRepayment(double amountPaid, LocalDate paymentDate, LocalDate dueDate,
			LoanApplication loanApplication) {
		super();
		this.amountPaid = amountPaid;
		this.paymentDate = paymentDate;
		this.dueDate = dueDate;
		this.loanApplication = loanApplication;
	}

	public int getRepaymentId() {
		return repaymentId;
	}

	public void setRepaymentId(int repaymentId) {
		this.repaymentId = repaymentId;
	}

	public double getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(double amountPaid) {
		this.amountPaid = amountPaid;
	}

	public LocalDate getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(LocalDate paymentDate) {
		this.paymentDate = paymentDate;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public LoanApplication getLoanApplication() {
		return loanApplication;
	}

	public void setLoanApplication(LoanApplication loanApplication) {
		this.loanApplication = loanApplication;
	}

	@Override
	public String toString() {
		return "LoanRepayment [repaymentId=" + repaymentId + ", amountPaid=" + amountPaid + ", paymentDate="
				+ paymentDate + ", dueDate=" + dueDate + ", loanApplication=" + loanApplication + "]";
	}
	
	
}
 