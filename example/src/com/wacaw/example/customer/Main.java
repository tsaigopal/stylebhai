package com.wacaw.example.customer;

import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.wacaw.example.customer.entity.User;
import com.wacaw.example.customer.screen.LoginScreen;
import com.wacaw.example.customer.screen.MainScreen;
import com.wacaw.stylebhai.core.StylerApp;
import com.wacaw.stylebhai.core.UILauncher;
import com.wacaw.stylebhai.widget.WidgetBuilder;

@Configuration
//@EnableJpaRepositories
@EnableTransactionManagement
public class Main {
	public static void main(String[] args) {
		StylerApp.run(new UILauncher() {
			@Override
			public void startUI(WidgetBuilder builder) {
				User u = (User) builder.createDialog(LoginScreen.class);
				System.out.println("User: " + u);
				builder.createMDIWindow(MainScreen.class);
			}
		});
	}
	
//	@Bean
//	public EntityManagerFactory entityManagerFactory() {
//		return Persistence.createEntityManagerFactory(null);
//	}
}
