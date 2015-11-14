package com.wacaw.example.customer.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.wacaw.example.customer.entity.Invoice;

@Repository
public class InvoiceDAOImpl implements InvoiceDAO {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Invoice> findByCustId(int custId) {
		return entityManager.createQuery("from Invoice i where i.custId = :custId", Invoice.class)
				.setParameter("custId", custId)
				.getResultList();
	}
}
