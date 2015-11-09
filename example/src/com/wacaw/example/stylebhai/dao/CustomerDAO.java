package com.wacaw.example.stylebhai.dao;

import java.util.List;

import com.wacaw.example.stylebhai.entity.Customer;

public interface CustomerDAO {
	Customer findOne(int custId);
	Customer save(Customer c);
	List<Customer> searchCustomers(String name, String address, boolean pending);
}
