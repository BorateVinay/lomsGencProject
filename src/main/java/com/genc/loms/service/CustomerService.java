package com.genc.loms.service;

import java.util.Optional;

import javax.management.RuntimeErrorException;
import com.genc.loms.repository.UserRepo;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.genc.loms.entity.Customer;
import com.genc.loms.repository.CustomerRepo;

@RestController
public class CustomerService {


	
	@Autowired
	CustomerRepo repo;

    
	public Customer registeCustomer(Customer newCustomer){
//		Check Email exist
		Customer data = repo.findByEmail(newCustomer.getEmail());
		if(data != null) {
			throw new RuntimeErrorException(null,"Email Id Already Exist");
		}else {
			repo.save(newCustomer);
		}
		return newCustomer;
	}
	
	
	public Customer authenticateCustomer(String email,String password) {
		Customer customerData = repo.findByEmail(email);
		
		if(customerData != null && customerData.getPassword().equals(password)) {
			return customerData;
		}
		return null;
	}
	
//	public Customer getCustomerById(int customerId) {
//		Optional<Customer> data = repo.findById(customerId);
//		if(data.isEmpty()) {
//			return null;
//		}
//		return data.get();
//	}
	
	public Customer updatePhoneAddressById(Customer customer) {
		Optional<Customer> data = repo.findById(customer.getCustomerId());
		Customer customerData= data.get();
		
		customerData.setPhone(customer.getPhone());
		customerData.setAddress(customer.getAddress());
		
		repo.save(customerData);
		return customerData;
	}
	
	@Transactional
    public void updatePhoneAddress(Customer customerDetailsFromRequest) {
        // 1. Find the existing customer
        Customer existingCustomer = repo.findById(customerDetailsFromRequest.getCustomerId())
            .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + customerDetailsFromRequest.getCustomerId()));
        
        // 2. Update only the phone and address fields
        existingCustomer.setPhone(customerDetailsFromRequest.getPhone());
        existingCustomer.setAddress(customerDetailsFromRequest.getAddress());
        
        // 3. Save the updated customer
        repo.save(existingCustomer);
    }
    
    // Optional utility method to fetch the Customer
    public Customer getCustomerById(int customerId) {
        return repo.findById(customerId)
            .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + customerId));
    }
	
	

   
}
