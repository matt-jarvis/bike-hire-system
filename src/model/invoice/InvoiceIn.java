package model.invoice;
import model.hire.Hire;

import java.io.Serializable;
import java.text.DecimalFormat;

import org.joda.time.LocalDate;

import controller.Controller;


/**
 * InvoiceIn is a serializable class representing an invoice
 * that is generated when a hired bike is returned.
 * 
 * Maintains various attributes of the invoice and
 * keeps a reference to the system's Controller 
 * class, which assists in generating each invoice's 
 * unique ID. 
 */
public class InvoiceIn implements Serializable
{
	private static final long serialVersionUID = 1L;
	private transient Controller controller;
	
	// InvoiceIn attributes
	private String invoiceID;
	private int invoiceNo;
	private Hire hire;
	private boolean bikeDamaged;
	private transient final DecimalFormat POUND = new DecimalFormat("£###,###,#00.00");

	/*
	 * Constructs an InvoiceIn object with a unique invoiceID
	 * 
	 * @param 	hire	the hire for which the invoice is to be generated for
	 */
	public InvoiceIn(Hire hire)
	{
		controller = Controller.getInstance();
		generateInvoiceInID();
		setHire(hire);
		setBikeDamaged(hire.getBike().isDamaged());	
	}
	
	/*
	 * Gets and increments the invoiceNo of the last InvoiceIn 
	 * added to the controller, then uses it to generate 
	 * and assign a unique invoiceID.
	 * 
	 * @see 	controller.Controller#getLastInvoiceInNo()
	 */
	private void generateInvoiceInID()
	{	
		invoiceNo = controller.getLastInvoiceInNo() + 1;
		invoiceID = "INV-IN-" + invoiceNo;
	}

	public String getInvoiceInID()
	{
		return invoiceID;
	}
	
	/*
	 * invoiceNo is used to generate each invoice's unique id.
	 */
	public int getInvoiceNo()
	{
		return invoiceNo;
	}
	
	private void setHire(Hire hire)
	{
		this.hire = hire;
	}
	
	public Hire getHire()
	{
		return hire;
	}
	
	/* 
	 * Calculates the late fee that the customer is 
	 * required to pay as a result of the hired bike being
	 * returned late.
	 * 	
	 * 		late fee = (amount of days late * hired bikes daily rate) * 1.5
	 * 
	 * @return	the late fee for the hire 
	 * 			(0 if the hire was not returned late)
	 */
	public double getLateFee()
	{
		return (hire.getDaysLate() * hire.getBike().getDailyRate()) * 1.5;
	}

	/* 
	 * Determines whether the bike was returned damaged or not.
	 *
	 * @return	true if the bike was returned damaged.
	 * 			false otherwise.
	 */
	public boolean wasBikeDamaged()
	{
		return bikeDamaged;
	}

	private void setBikeDamaged(boolean bikeDamaged)
	{
		this.bikeDamaged = bikeDamaged;
	}

	/*
	 * Generates a String to represent the details of the invoice.
	 * 
	 * @see java.lang.Object#toString()
	 * @return	a String representation of the invoice details.
	 */
	public String toString()
	{
		String invoice = "-- INVOICE -- \n";
		invoice += "\nInvoice ID: " 		+ invoiceID;
		invoice += "\nDate created: " 		+ LocalDate.now().toString();
		invoice += "\n\nCustomer ID: "		+ hire.getCustomer().getCustomerID();
		invoice += "\nName: "				+ hire.getCustomer().getFirstName() + " " + hire.getCustomer().getLastName();
		invoice += "\nAddress: "			+ hire.getCustomer().getAddressString();
		invoice += "\n\nHire ID: " 			+ hire.getHireID();
		invoice += "\nStart date: "			+ hire.getStartDate().toString();
		invoice += "\nEnd date: "			+ hire.getEndDate().toString();
		invoice += "\n\nBike ID: "			+ hire.getBike().getBikeID();
		invoice += "\nDescription: "		+ hire.getBike().getDescription();
		invoice += "\nDaily rate: "			+ POUND.format(hire.getBike().getDailyRate());
		invoice += "\n\nReturned on: "		+ LocalDate.now().toString();
		invoice += "\nDays late: "			+ hire.getDaysLate();		
		invoice += "\nLate fee: "			+ POUND.format(getLateFee());
		invoice += "\nDeposit refunded: ";
		
		if (bikeDamaged)
			invoice += "no";
		else
			invoice += "yes";
	
		return invoice;
	}	
	
}
