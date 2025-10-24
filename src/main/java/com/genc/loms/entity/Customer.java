package com.genc.loms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Base64;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customerId;
    
    @Size(min = 2,max = 30 , message = "Name Between 2 - 30 cgaracter")
    @Pattern(regexp = "^[A-Za-z ]+$" , message = "Name Should contain Alphabets")
    private String name;
    
    @NotBlank(message = "Email cannot be Blank")
    @Email(message="please enter the proper email address")
    private String email;
    
    @NotBlank(message = "Password cannot be blank")
    private String password;
    
    @Pattern(regexp = "\\d+" , message = "Must Be Number")
    @Size(min = 10,max = 10 , message = "Must Be 10 digit")
    private String phone;
    
    @Lob
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

//	public void setPassword(String password) {
//		this.password = password;
//	}
    public void setPassword(String encPassword) {
	this.password = Base64.getEncoder().encodeToString(encPassword.getBytes());
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

//	@Override
//	public String toString() {
//		return "Customer [customerId=" + customerId + ", name=" + name + ", email=" + email + ", password=" + password
//				+ ", phone=" + phone + ", address=" + address + ", loanApplications=" + loanApplications + "]";
//	}

  
}
