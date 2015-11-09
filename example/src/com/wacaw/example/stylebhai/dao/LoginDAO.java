package com.wacaw.example.stylebhai.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.wacaw.example.stylebhai.entity.User;

@Repository
public class LoginDAO {
	@PersistenceContext
	private EntityManager entityManager;
	
	public User checkCredentials(String user, String password) {
		return entityManager.createNamedQuery("User.byLogin", User.class)
			.setParameter("userId", user)
			.setParameter("password", password)
			.getSingleResult();
	}
}
