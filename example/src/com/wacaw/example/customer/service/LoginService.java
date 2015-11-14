package com.wacaw.example.customer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wacaw.example.customer.dao.LoginDAO;
import com.wacaw.example.customer.entity.User;

@Service
public class LoginService {

	@Autowired
	private LoginDAO loginDao;
	
	public User login(String userId, String password) {
		return loginDao.checkCredentials(userId, password);
	}
}
