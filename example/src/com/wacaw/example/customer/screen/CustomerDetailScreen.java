package com.wacaw.example.customer.screen;

import org.springframework.beans.factory.annotation.Autowired;

import com.wacaw.example.customer.entity.Customer;
import com.wacaw.example.customer.entity.Invoice;
import com.wacaw.example.customer.service.CustomerService;
import com.wacaw.stylebhai.core.AbstractBindableScreen;
import com.wacaw.stylebhai.event.EventListener;
import com.wacaw.stylebhai.event.UIEvent;
import com.wacaw.stylebhai.widget.TableModel;

public class CustomerDetailScreen extends AbstractBindableScreen<Customer> {
	@Autowired
	CustomerService service;
	
	private TableModel<?, Invoice> invoices;
	
	public CustomerDetailScreen() {
		super("Customer Details", "cust.png");
	}

	@Override
	public void initialize(Object...params) throws Exception {
		Integer custId = null;
		if (params.length>0) {
			custId = (Integer) params[0];
		}
		Customer customer = null;
		if (custId == null) {
			customer = new Customer();
			customer.setName("New Customer");
			customer.setAddress("");
		} else {
			customer = service.getCustomer(custId);
		}
		setModel(customer);
	}

	@EventListener(widget="btnSave", eventType=UIEvent.Click)
	public void saveCustomer() {
		Customer saved = service.saveCustomer(getModel());
		setModel(saved); 
	}
	
	@EventListener(widget="invoices", eventType=UIEvent.DoubleClick)
	public void openInvoice() {
		try {
			getWindowHandle().getParent().openChild(null, invoices.getSelection().getInvoiceId());
		} catch (Exception e) {
			getWindowHandle().showMessage("Invoice Details", "Error opening details" + e.getMessage(), e);
		}
	}
}
