package com.genc.loms.service;

import javax.management.RuntimeErrorException;
import com.genc.loms.repository.UserRepo;
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

   
}
