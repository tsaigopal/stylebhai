package com.wacaw.example.one;

import com.wacaw.stylebhai.core.AbstractBindableScreen;

public class CustomerDetailScreen extends AbstractBindableScreen<Cutomer>{

	public CustomerDetailScreen() {
		super("Cutomer", null);
	}

	@Override
	public void initialize(Object... objects) throws Exception {
		Cutomer c = new Cutomer();
		c.setCustId(12);
		c.setCustName("my name");
		setModel(c);
	}

}
