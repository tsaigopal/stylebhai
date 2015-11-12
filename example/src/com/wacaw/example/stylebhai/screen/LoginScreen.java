package com.wacaw.example.stylebhai.screen;

import org.springframework.beans.factory.annotation.Autowired;

import com.wacaw.example.stylebhai.entity.User;
import com.wacaw.example.stylebhai.service.LoginService;
import com.wacaw.stylebhai.core.AbstractScreen;
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
	public boolean executeAction(String action) {
		if (action.equals("ok")) {
			try {
				user = loginService.login(txtUser.getText(), txtPassword.getText());
				this.setReturnValue(user);
				this.getWindowHandle().close();
				return true;
			} catch(Exception e) {
				e.printStackTrace();
				getWindowHandle().showMessage("Error", "Invalid UserId/Password", e);
				return true;
			}
		} else if (action.equals("cancel")) {
			this.setReturnValue(null);
			this.getWindowHandle().close();
			return true;
		}
		return false;
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
