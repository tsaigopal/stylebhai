package com.wacaw.example.customer.dao;

import java.util.List;

import com.wacaw.example.customer.entity.Customer;

public interface CustomerSearchDAO {
	List<Customer> searchCustomers(String name, String address, boolean pending);
}
