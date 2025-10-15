package com.genc.loms;

import com.genc.loms.entity.Customer;
//import com.genc.loms.entity.LoanApplication;
//import com.genc.loms.entity.LoanStatus;
//import com.genc.loms.entity.LoanType;
import com.genc.loms.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class LomsApplication /*implements CommandLineRunner*/ {

	@Autowired
	CustomerService customerService;

	public static void main(String[] args) {
		SpringApplication.run(LomsApplication.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {
//		List<LoanApplication> loan = new ArrayList<>();
//
//		Customer customer = new Customer();
//		customer.setName("somen");
//		customer.setAddress("Odisha");
//		customer.setEmail("somen@gmail.com");
//		customer.setPhone("7008061997");
//
//		LoanApplication application1 = new LoanApplication();
//		application1.setLoanType(LoanType.Personal);
//		application1.setStatus(LoanStatus.Approved);
//		application1.setAmountRequested(5000.0);
//		application1.setCustomer(customer);
//
//		LoanApplication application2 = new LoanApplication();
//		application2.setLoanType(LoanType.Home);
//		application2.setStatus(LoanStatus.Rejected);
//		application2.setAmountRequested(10000.0);
//		application2.setCustomer(customer);
//
//		loan.add(application1);
//		loan.add(application2);
//
//
//		customer.setLoanApplications(loan);
//
//		customerService.addCustomer(customer);
//
//	}
}
