package com.wacaw.example.stylebhai.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@SequenceGenerator(name = "INVOICE_SEQ", sequenceName = "INVOICE_SEQ", allocationSize = 1, initialValue = 1000)
public class Invoice {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "INVOICE_SEQ")
	private int invoiceId;

	private int custId;

	private float totalAmount;
	private float roundedAmount;
	@Temporal(TemporalType.DATE)
	private Date date;
	private String status;
	private String description;

	public int getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(int invoiceId) {
		this.invoiceId = invoiceId;
	}

	public int getCustId() {
		return custId;
	}

	public void setCustId(int custId) {
		this.custId = custId;
	}

	public float getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(float totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public float getRoundedAmount() {
		return roundedAmount;
	}

	public void setRoundedAmount(float roundedAmount) {
		this.roundedAmount = roundedAmount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getPendingAmount() {
		float paid = 0; // TODO calculate based on payments
		return roundedAmount - paid;
	}
}
