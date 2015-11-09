package com.wacaw.example.stylebhai;

import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.wacaw.example.stylebhai.entity.User;
import com.wacaw.example.stylebhai.screen.LoginScreen;
import com.wacaw.example.stylebhai.screen.MainScreen;
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
