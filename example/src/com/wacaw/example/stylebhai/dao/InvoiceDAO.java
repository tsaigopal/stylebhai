package com.wacaw.example.stylebhai.dao;

import java.util.List;

import com.wacaw.example.stylebhai.entity.Invoice;

public interface InvoiceDAO {

	List<Invoice> findByCustId(int custId);

}
