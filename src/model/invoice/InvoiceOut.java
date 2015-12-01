package model.invoice;
import model.hire.Hire;

import java.io.Serializable;
import java.text.DecimalFormat;

import org.joda.time.LocalDate;

import controller.Controller;

public class InvoiceOut implements Serializable
{
	private static final long serialVersionUID = 1L;
	private transient Controller controller;
	
	private String invoiceID;
	private int invoiceNo;
	private Hire hire;
	private transient final DecimalFormat POUND = new DecimalFormat("£###,###,#00.00");

	/*
	 * Constructs an InvoiceOut object with a unique invoiceID
	 * 
	 * @param 	hire	the hire for which the invoice is to be generated for
	 */
	public InvoiceOut(Hire hire)
	{
		controller = Controller.getInstance();
		generateInvoiceOutID();
		setHire(hire);
	}
	
	/*
	 * Gets and increments the invoiceNo of the last InvoiceOut
	 * added to the controller, then uses it to generate 
	 * and assign a unique invoiceID.
	 * 
	 * @see 	controller.Controller#getLastInvoiceOutNo()
	 */
	private void generateInvoiceOutID()
	{	
		invoiceNo = controller.getLastInvoiceOutNo() + 1;
		invoiceID = "INV-OUT-" + invoiceNo;
	}
	
	public String getInvoiceOutID()
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
	 * Calculates the total cost of the hire including the deposit:
	 * 	
	 * 		total = hire cost + hired bike's deposit
	 * 
	 * @return	the total cost of the hire (including bike deposit)
	 */
	public double getTotal()
	{
		return hire.getCost() + hire.getBike().getDeposit();
	}
	
	/*
	 * Generates a String to represent the details of the invoice.
	 * 
	 * @see java.lang.Object#toString()
	 * @return	a String representation of the invoice details.
	 */
	@Override
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
		invoice += "\n\nAmount due";
		invoice += "\nBike cost: "			+ POUND.format(hire.getCost());
		invoice += "\nDeposit: "			+ POUND.format(hire.getBike().getDeposit());
		invoice += "\nTotal: "				+ POUND.format(getTotal());
	
		return invoice;
	}
	
	
	
	
	
}
