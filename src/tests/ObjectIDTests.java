package tests;

import static org.junit.Assert.assertEquals;
import model.hire.Hire;
import model.invoice.InvoiceIn;
import model.invoice.InvoiceOut;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import model.bike.Bike;
import controller.Controller;
import model.customer.Customer;

public class ObjectIDTests
{
	
	// Test size
	int SIZE = 10001;
	
	Controller controller = Controller.getInstance();
	
	String expectedID;
	String actualID;
	
	@Before
	public void setUp() throws Exception
	{
		// Populate arrays
		for(int i = 0; i < SIZE; i++)
		{
			Customer c = new Customer("test", "test", "test", "test", "test");
			Bike b = new Bike("test", "test", "test", "test", "test", 1, 1);
			Hire h = new Hire(c, b, new LocalDate(LocalDate.now()));
			InvoiceOut invOut = new InvoiceOut(h);
			InvoiceIn invIn = new InvoiceIn(h);
			
			controller.addCustomer(c);
			controller.addBike(b);
			controller.addHire(h);
			controller.addInvoiceOut(invOut);
			controller.addInvoiceIn(invIn);
		}
	}

	@After
	public void tearDown() throws Exception
	{
		controller = null;
	}

	@Test
	public void customerID()
	{
		for(int i = 0; i < SIZE; i++)
		{
			expectedID = "CUST" + (i+1);
			actualID = controller.getCustomers().get(i).getCustomerID();
			assertEquals(expectedID, actualID);
		}
	}
	
	@Test
	public void bikeID()
	{
		for(int i = 0; i < SIZE; i++)
		{
			expectedID = "BIKE" + (i+1);
			actualID = controller.getBikes().get(i).getBikeID();
			assertEquals(expectedID, actualID);
		}
	}
	
	@Test
	public void hireID()
	{
		for(int i = 0; i < SIZE; i++)
		{
			expectedID = "HIRE" + (i+1);
			actualID = controller.getHires().get(i).getHireID();
			assertEquals(expectedID, actualID);
		}
	}
	
	@Test
	public void InvOutID()
	{
		for(int i = 0; i < SIZE; i++)
		{
			expectedID = "INV-OUT-" + (i+1);
			actualID = controller.getInvoicesOut().get(i).getInvoiceOutID();
			assertEquals(expectedID, actualID);
		}
	}
	
	@Test
	public void InvInID()
	{
		for(int i = 0; i < SIZE; i++)
		{
			expectedID = "INV-IN-" + (i+1);
			actualID = controller.getInvoicesIn().get(i).getInvoiceInID();
			assertEquals(expectedID, actualID);
		}
	}
	

}
