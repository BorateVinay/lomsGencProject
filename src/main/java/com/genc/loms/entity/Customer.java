package com.genc.loms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customerId;
    
    private String name;
    
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    
    private String phone;
    private String address;
    

    @OneToMany(mappedBy = "customer")
    @JsonIgnore
    private List<LoanApplication> loanApplications;

//  @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
//  private CreditEvaluation creditEvaluation;

	public Customer() {
		super();
	}
    
	public Customer(int customerId, String name, @NotBlank String email, @NotBlank String password, String phone,
			String address, List<LoanApplication> loanApplications) {
		super();
		this.customerId = customerId;
		this.name = name;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.address = address;
		this.loanApplications = loanApplications;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<LoanApplication> getLoanApplications() {
		return loanApplications;
	}

	public void setLoanApplications(List<LoanApplication> loanApplications) {
		this.loanApplications = loanApplications;
	}

	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", name=" + name + ", email=" + email + ", password=" + password
				+ ", phone=" + phone + ", address=" + address + ", loanApplications=" + loanApplications + "]";
	}

  
}
