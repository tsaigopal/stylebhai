package com.wacaw.example.customer.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.wacaw.example.customer.entity.Customer;
import com.wacaw.example.customer.entity.Customer_;
import com.wacaw.stylebhai.util.MiscUtils;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Customer> searchCustomers(String name, String address,
			boolean pending) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Customer> c = builder.createQuery(Customer.class);
		Root<Customer> cust = c.from(Customer.class);
		Predicate criteria = builder.conjunction();
		if (!MiscUtils.isEmptyString(name)) {
			criteria = builder.and(criteria,
					builder.like(cust.get(Customer_.name), "%" + name + "%"));
		}
		if (!MiscUtils.isEmptyString(address)) {
			criteria = builder.and(
					criteria,
					builder.like(cust.get(Customer_.address), "%" + address
							+ "%"));
		}
		if (pending) {
			criteria = builder.and(criteria,
					builder.gt(cust.get(Customer_.balance), 0));
		}
		c.where(criteria);
		return entityManager.createQuery(c).getResultList();
	}

	@Override
	public Customer findOne(int custId) {
		return entityManager.find(Customer.class, custId);
	}
	
	@Override
	public Customer save(Customer c) {
		if (c.getCustId() == 0) {
			entityManager.persist(c);
		} else {
			c = entityManager.merge(c);
		}
		return c;
	}
}
