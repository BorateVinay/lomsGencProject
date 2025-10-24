package com.genc.loms.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.ToString;

 
@Entity
@ToString(exclude = {"customer"})
public class LoanApplication {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int applicationId;
	
	@ManyToOne
	@JoinColumn(name = "customerId")
	private Customer customer;
	
	
	public enum LoanType
	{PERSONAL, HOME, AUTO}
	@Enumerated(EnumType.STRING)
	private LoanType loanType;
	
	@Positive(message="Amount should be positive")
	private double amountRequested;
	
	public enum Status
	{PENDING, APPROVED, REJECTED, PAID}
	@Enumerated(EnumType.STRING)
	private Status status;
	
	public LoanApplication(Customer customer, LoanType loanType, double amountRequested, Status status) {
		super();
		this.customer = customer;
		this.loanType = loanType;
		this.amountRequested = amountRequested;
		this.status = status;
	}
	public int getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(int applicationId) {
		this.applicationId = applicationId;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public LoanType getLoanType() {
		return loanType;
	}
	public void setLoanType(LoanType loanType) {
		this.loanType = loanType;
	}
	public double getAmountRequested() {
		return amountRequested;
	}
	public void setAmountRequested(double amountRequested) {
		this.amountRequested = amountRequested;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public LoanApplication() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "LoanApplication [applicationId=" + applicationId + ", customer=" + customer + ", loanType="
				+ loanType + ", amountRequested=" + amountRequested + ", status=" + status + "]";
	}
	
 
 
}
