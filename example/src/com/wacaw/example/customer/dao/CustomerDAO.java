package com.wacaw.example.customer.dao;

import java.util.List;

import com.wacaw.example.customer.entity.Customer;

public interface CustomerDAO {
	Customer findOne(int custId);
	Customer save(Customer c);
	List<Customer> searchCustomers(String name, String address, boolean pending);
}
