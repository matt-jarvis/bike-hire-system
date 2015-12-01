package tests;

import static org.junit.Assert.assertTrue;
import model.hire.Complete;
import model.hire.Hire;
import model.invoice.InvoiceIn;
import model.invoice.InvoiceOut;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import model.bike.Bike;
import model.customer.Customer;

public class CostTests
{

	Customer customer;
	Bike bike;
	Hire hire;
	InvoiceOut invOut;
	InvoiceIn invIn;
	
	@Before
	public void setUp() throws Exception
	{
		customer = new Customer("test", "test", "test", "test", "test");
		bike = new Bike("test", "test", "test", "test", "test", 15.25, 20.50);
		hire = new Hire(customer, bike, new LocalDate(LocalDate.now().plusDays(4)));
		invOut = new InvoiceOut(hire);
	}

	@After
	public void tearDown() throws Exception
	{
		customer = null;
		bike = null;
		hire = null;
		invOut = null;
		invIn
		= null;
	}

	/*
	 * getDuration() is frequently used to calculate the 
	 * cost of a hire. 
	 * It should return the amount of days between
	 * the hire's start and end dates, in this case 4
	 */
	@Test
	public void getHireDuration()
	{
		assertTrue(hire.getDuration() == 4);
	}
	
	
	/*
	 * getDaysLate() is frequently used to calculate the 
	 * late fee of a hire. 
	 * It should return the amount of days the hire was 
	 * late by, which will be 0 if it is still on hire
	 * or was returned on time.
	 */
	@Test
	public void getDaysLate0()
	{
		assertTrue(hire.getDaysLate() == 0);
	}
	
	/*
	 * getDaysLate() is frequently used to calculate the 
	 * late fee of a hire. 
	 * It should return the amount of days the hire was 
	 * late by, which will be 0 if it is still on hire
	 * or was returned on time.
	 */
	@Test
	public void getDaysLate2()
	{
		// Complete hire and set its return date to 2 days after its end date (Late)
		hire.setState(new Complete(hire, false));
		hire.setDateReturned(LocalDate.now().plusDays(6));
		
		assertTrue(hire.getDaysLate() == 2);
	}
	
	
	/*
	 * getCost() should return 15.25 * 4 = 61
	 */
	@Test
	public void getBikeCost()
	{
		assertTrue(hire.getCost() == 61);
	}
	
	/*
	 * getTotal() should return bike cost (15.25 * 4) + deposit (20.50) = 81.50
	 */
	@Test
	public void getTotalCost()
	{
		assertTrue(invOut.getTotal() == 81.50);
	}
	
	
	/*
	 * getLateFee() should return 0 when the hire was 
	 * returned on time.
	 */
	@Test
	public void getLateFee0()
	{
		hire.setState(new Complete(hire, false));
		
		invIn = new InvoiceIn(hire);
		assertTrue(invIn.getLateFee() == 0);
	}
	
	/*
	 * getLateFee() should return daysLate (2) * 
	 * the hired bike's daily rate (15.25) * 1.5
	 * = 45.75
	 */
	@Test
	public void getLateFee2()
	{
		// Complete hire and set its return date to 2 days after its end date (Late)
		hire.setState(new Complete(hire, false));
		hire.setDateReturned(LocalDate.now().plusDays(6));
		
		invIn = new InvoiceIn(hire);
		assertTrue(invIn.getLateFee() == 45.75);
	}
	

}
