package com.wacaw.example.stylebhai.screen;

import org.springframework.beans.factory.annotation.Autowired;

import com.wacaw.example.stylebhai.entity.User;
import com.wacaw.example.stylebhai.service.LoginService;
import com.wacaw.stylebhai.core.AbstractScreen;
import com.wacaw.stylebhai.util.Logger;
import com.wacaw.stylebhai.widget.TextBox;

public class LoginScreen extends AbstractScreen {
	@Autowired
	private LoginService loginService;
	
	private User user;
	private TextBox txtUser;
	private TextBox txtPassword;
	
	public LoginScreen() {
		super("Login", null);
	}
	
	@Override
	public void executeAction(String action) {
		if (action.equals("ok")) {
			try {
				user = loginService.login(txtUser.getText(), txtPassword.getText());
				this.setReturnValue(user);
				this.getWindowHandle().close();
			} catch(Exception e) {
				e.printStackTrace();
				getWindowHandle().showMessage("Error", "Invalid UserId/Password", e);
			}
		} else if (action.equals("cancel")) {
			this.setReturnValue(null);
			this.getWindowHandle().close();
		}
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	@Override
	public void initialize(Object...params) throws Exception {
		
	}
}
