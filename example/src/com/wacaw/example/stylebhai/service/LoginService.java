package com.wacaw.example.stylebhai.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wacaw.example.stylebhai.dao.LoginDAO;
import com.wacaw.example.stylebhai.entity.User;

@Service
public class LoginService {

	@Autowired
	private LoginDAO loginDao;
	
	public User login(String userId, String password) {
		return loginDao.checkCredentials(userId, password);
	}
}
