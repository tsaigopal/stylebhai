package com.wacaw.example.stylebhai.screen;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.wacaw.example.stylebhai.entity.Customer;
import com.wacaw.example.stylebhai.entity.Invoice;
import com.wacaw.example.stylebhai.service.CustomerService;
import com.wacaw.stylebhai.core.AbstractScreen;
import com.wacaw.stylebhai.event.EventListener;
import com.wacaw.stylebhai.event.UIEvent;
import com.wacaw.stylebhai.util.Logger;
import com.wacaw.stylebhai.widget.DateField;
import com.wacaw.stylebhai.widget.TableModel;
import com.wacaw.stylebhai.widget.TextBox;

@Component
@Scope("prototype")
public class CustomerDetailScreen extends AbstractScreen {
	@Autowired
	CustomerService service;
	
	private TableModel<?, Invoice> tblInvoices;
	Customer customer;
	
	TextBox lblCustId;
	TextBox txtName;
	TextBox txtAddress;
	TextBox lblPending;
	DateField dtDob;
	
	public CustomerDetailScreen() {
		super("Customer Details", "cust.png");
	}

	@Override
	public void executeAction(String action) {
		
	}
	
	@Override
	public void initialize(Object...params) throws Exception {
		Integer custId = null;
		if (params.length>0) {
			custId = (Integer) params[0];
		}
		if (custId == null) {
			customer = new Customer();
			customer.setName("New Customer");
			customer.setAddress("");
		} else {
			customer = service.getCustomer(custId);
		}
		loadCustomerData();
	}

	protected void loadCustomerData() throws Exception {
		lblCustId.setText(String.valueOf(customer.getCustId()));
		txtName.setText(customer.getName());
		txtAddress.setText(customer.getAddress());
		lblPending.setText(String.valueOf(customer.getBalance()));
		dtDob.setDate(customer.getDob());
		tblInvoices.setObjects(service.getInvoices(customer.getCustId()));
	}

	@EventListener(widget="btnSave", eventType=UIEvent.Click)
	public void saveCustomer() {
		customer.setName(txtName.getText());
		customer.setAddress(txtAddress.getText());
		customer.setBalance(Float.valueOf(lblPending.getText()));
		customer.setDob(dtDob.getDate());
		service.saveCustomer(customer);
		try {
			loadCustomerData();
		} catch (Exception e) {
			Logger.log("Error while reloading screen", e);
		}
	}
	
	@EventListener(widget="tblInvoices", eventType=UIEvent.DoubleClick)
	public void openInvoice() {
		try {
			getWindowHandle().getParent().openChild(null, tblInvoices.getSelection().getInvoiceId());
		} catch (Exception e) {
			getWindowHandle().showMessage("Invoice Details", "Error opening details" + e.getMessage(), e);
		}
	}
}
