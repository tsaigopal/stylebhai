package com.wacaw.example.one;

import com.wacaw.stylebhai.core.StylerApp;
import com.wacaw.stylebhai.core.UILauncher;
import com.wacaw.stylebhai.widget.WidgetBuilder;

public class OneMain {
	public static void main(String[] args) {
		StylerApp.run(new UILauncher() {
			public void startUI(WidgetBuilder builder) {
				builder.createWindow(CustomerDetailScreen.class);
			}
		});
	}
}
