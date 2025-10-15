package com.genc.loms.entity;

import java.util.Date;

import org.hibernate.annotations.ForeignKey;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class LoanDisbursal {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int disbursalId;
	
	@OneToOne
	@JoinColumn(name="applicationId")
	private LoanApplication applicationId;
	
	private double disbursalAmount;
	private Date disbursalDate;
	private String approvedBy;
	
	
	
	public LoanDisbursal() {
		super();
	}

	public LoanDisbursal(LoanApplication applicationId, double disbursalAmount, Date disbursalDate, String approvedBy) {
		super();
		this.applicationId = applicationId;
		this.disbursalAmount = disbursalAmount;
		this.disbursalDate = disbursalDate;
		this.approvedBy = approvedBy;
	}
	
	public int getDisbursalId() {
		return disbursalId;
	}
	public void setDisbursalId(int disbursalId) {
		this.disbursalId = disbursalId;
	}
	public LoanApplication getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(LoanApplication applicationId) {
		this.applicationId = applicationId;
	}
	public double getDisbursalAmount() {
		return disbursalAmount;
	}
	public void setDisbursalAmount(double disbursalAmount) {
		this.disbursalAmount = disbursalAmount;
	}
	public Date getDisbursalDate() {
		return disbursalDate;
	}
	public void setDisbursalDate(Date disbursalDate) {
		this.disbursalDate = disbursalDate;
	}
	public String getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}
	
	@Override
	public String toString() {
		return "LoanDisbursal [disbursalId=" + disbursalId + ", applicationId=" + applicationId + ", disbursalAmount="
				+ disbursalAmount + ", disbursalDate=" + disbursalDate + ", approvedBy=" + approvedBy + "]";
	}
	
	


}
