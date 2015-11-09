//package com.wacaw.example.stylebhai.screen;
//
//import com.wacaw.example.stylebhai.entity.User;
//import com.wacaw.stylebhai.core.AbstractScreen;
//import com.wacaw.stylebhai.widget.TextBox;
//
//public class LoginScreen extends AbstractScreen {
//	private User user;
//	private TextBox txtUser;
//	private TextBox txtPassword;
//	
//	public LoginScreen() {
//		super("Login", null);
//	}
//	
//	@Override
//	public void executeAction(String action) {
//		if (action.equals("ok")) {
//			try {
//				user = new LoginService().login(txtUser.getText(), txtPassword.getText());
//				this.setReturnValue(user);
//				this.getWindowHandle().close();
//			} catch(Exception e) {
//				getWindowHandle().showMessage("Error", "Invalid UserId/Password", null);
//			}
//		}
//	}
//	
//	public User getUser() {
//		return user;
//	}
//	
//	public void setUser(User user) {
//		this.user = user;
//	}
//	
//	@Override
//	public void initialize(Object...params) throws Exception {
//		
//	}
//}
