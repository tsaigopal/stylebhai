package com.wacaw.example.customer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wacaw.example.customer.dao.CustomerDAO;
import com.wacaw.example.customer.dao.InvoiceDAO;
import com.wacaw.example.customer.entity.Customer;
import com.wacaw.example.customer.entity.Invoice;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerDAO customerDao;
	
	@Autowired
	private InvoiceDAO invoiceDao;

	public Customer getCustomer(int custId) {
		return customerDao.findOne(custId);
	}
	
	public List<Invoice> getInvoices(int custId) {
		return invoiceDao.findByCustId(custId);
	}
	
	@Transactional
	public Customer saveCustomer(Customer customer) {
		return customerDao.save(customer);
	}
	
	public List<Customer> searchCustomers(String name, String address, boolean pending) {
		return customerDao.searchCustomers(name, address, pending);
	}
	
	public List<Invoice> getInvoicesForCustomer(int custId) {
		return invoiceDao.findByCustId(custId);
	}

}
