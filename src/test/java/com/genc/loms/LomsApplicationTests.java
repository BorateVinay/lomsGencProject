package com.genc.loms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Base64;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.genc.loms.entity.Customer;
import com.genc.loms.service.CustomerService;

@SpringBootTest
class LomsApplicationTests {
	@Autowired
	CustomerService customerService;

	@Test
	void testCustomerLoginSuccess() {
		String password = "1234";
		String encPassword = Base64.getEncoder().encodeToString(password.getBytes());
		Customer customer = customerService.authenticateCustomer("aditya@gmail.com", encPassword);
		assertNotNull(customer);
		assertEquals("aditya@gmail.com", customer.getEmail());

	}

	@Test
	void testCustomerLoginFailure() {
		String password = "12345678";
		String encPassword = Base64.getEncoder().encodeToString(password.getBytes());
		Customer customer = customerService.authenticateCustomer("aditya@gmail.com", encPassword);
		assertNull(customer);

	}

}
