package com.wacaw.example.stylebhai.dao;

import java.util.List;

import com.wacaw.example.stylebhai.entity.Customer;

public interface CustomerSearchDAO {
	List<Customer> searchCustomers(String name, String address, boolean pending);
}
