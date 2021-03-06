package com.wacaw.example.customer.screen;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.wacaw.example.customer.entity.Customer;
import com.wacaw.example.customer.service.CustomerService;
import com.wacaw.stylebhai.core.AbstractScreen;
import com.wacaw.stylebhai.event.EventListener;
import com.wacaw.stylebhai.event.UIEvent;
import com.wacaw.stylebhai.widget.Button;
import com.wacaw.stylebhai.widget.TableModel;
import com.wacaw.stylebhai.widget.TextBox;

public class CustomerSearchScreen extends AbstractScreen {
	
	public CustomerSearchScreen() {
		super("Customer Search", "custsearch.gif");
	}
	
	@Autowired
	private CustomerService service;

	private TextBox txtName;
	private TextBox txtAddress;
	private Button btnPending;
	private TableModel<?, Customer> tblCustomers;
	
	public void initialize(Object...params) {
	}
	
	@EventListener(widget="btnSearch", eventType=UIEvent.Click)
	public void searchCustomer() {
		List<Customer> customers = service.searchCustomers(txtName.getText(), 
				txtAddress.getText(), btnPending.getSelection());
		tblCustomers.setObjects(customers);
	}
	
	@EventListener(widget="tblCustomers", eventType=UIEvent.DoubleClick)
	public void openCustomer() {
		int custId = tblCustomers.getSelection().getCustId();
		try {
			getWindowHandle().getParent().openChild(CustomerDetailScreen.class, custId);
		} catch (Exception e) {
			getWindowHandle().showMessage("Customer Details", "Error opening screen" + e.getMessage(), e);
		}
	}
}
