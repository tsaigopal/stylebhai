package com.wacaw.example.customer.dao;

import java.util.List;

import com.wacaw.example.customer.entity.Invoice;

public interface InvoiceDAO {

	List<Invoice> findByCustId(int custId);

}
