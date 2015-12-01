package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import model.hire.Active;
import model.hire.Complete;
import model.hire.Hire;
import model.hire.Late;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import model.bike.Bike;
import model.bike.Damaged;
import model.bike.OnHire;
import controller.Controller;
import model.customer.Customer;

public class ReportTests
{
	
	Controller controller = Controller.getInstance();;
	
	String expectedID;
	String actualID;
	
	/*
	 * Setup stats accordingly:
	 * 
	 * Total customers: 		3
	 * 
	 * Total bikes:				4
	 * Bikes on hire:			2
	 * Bikes available:			1
	 * Bikes damaged:			1
	 * Most popular bike:		BIKE1
	 * Least popular bike:		BIKE4
	 * 
	 * Total hires:				4
	 * Complete hires:			2
	 * Active hires:			1
	 * Late hires:				1
	 */
	@Before
	public void setUp() throws Exception
	{	
		// Populate customer array
		for (int i = 0; i < 3; i++)
			controller.addCustomer(new Customer("test", "test", "test", "test", "test"));
		
		// Populate bike array
		for (int i = 0; i < 4; i++)
			controller.addBike(new Bike("test", "test", "test", "test", "test", 1, 1));
		
		// Create 2 'Complete' hires
		Hire h0 = new Hire(controller.getCustomers().get(0), controller.getBikes().get(0), new LocalDate(LocalDate.now()));
		Hire h1 = new Hire(controller.getCustomers().get(1), controller.getBikes().get(1), new LocalDate(LocalDate.now()));
		h0.setState(new Complete(h0, false));
		h1.setState(new Complete(h1, false));
		controller.addHire(h0);
		controller.addHire(h1);
		
		// Create 1 'OnHire' hire 
		Hire h2 = new Hire(controller.getCustomers().get(0), controller.getBikes().get(0), new LocalDate(LocalDate.now()));
		h2.setState(new Active(h2));
		h2.getBike().setState(new OnHire(h2.getBike()));
		controller.addHire(h2);
		
		// Create 1 'Late' hire 
		Hire h3 = new Hire(controller.getCustomers().get(2), controller.getBikes().get(2), new LocalDate(LocalDate.now().minusDays(1)));
		h3.setState(new Late(h3));
		h3.getBike().setState(new OnHire(h3.getBike()));
		controller.addHire(h3);
		
		// Set state of "BIKE4" to 'Damaged'
		controller.getBikes().get(3).setState(new Damaged(controller.getBikes().get(3)));
	}

	@After
	public void tearDown() throws Exception
	{
		controller.getCustomers().clear();
		controller.getBikes().clear();
		controller.getHires().clear();
	}

	/*
	 * Total customers = 3
	 */
	@Test
	public void totalCustomers()
	{
		assertTrue(controller.getReport().getTotalCustomers() == 3);
	}
	
	/*
	 * Total bikes = 4
	 */
	@Test
	public void totalBikes()
	{		
		assertTrue(controller.getReport().getTotalBikes() == 4);
	}
	
	/*
	 * Amount of bikes on hire = 2
	 */
	@Test
	public void bikesOnHire()
	{		
		assertTrue(controller.getReport().getNoOfBikesOnHire() == 2);
	}
	
	/*
	 * Amount of bikes available = 1
	 */
	@Test
	public void bikesAvailable()
	{		
		assertTrue(controller.getReport().getNoOfBikesAvailable() == 1);
	}
	
	/*
	 * Amount of bikes damaged = 1
	 */
	@Test
	public void bikesDamaged()
	{		
		assertTrue(controller.getReport().getNoOfBikesDamaged() == 1);
	}
	
	/*
	 * Most popular bike = BIKE1
	 */
	@Test
	public void mostPopularBike()
	{		
		assertEquals(controller.getReport().getMostPopularBike(), "BIKE1");
	}
	
	/*
	 * Least popular bike = BIKE4
	 */
	@Test
	public void leastPopularBike()
	{		
		assertEquals(controller.getReport().getLeastPopularBike(), "BIKE4");
	}
	
	/*
	 * Total hires = 4
	 */
	@Test
	public void totalHires()
	{		
		assertTrue(controller.getReport().getTotalHires() == 4);
	}
	
	/*
	 * Amount of completed hire = 2
	 */
	@Test
	public void completeHires()
	{		
		assertTrue(controller.getReport().getNoOfCompleteHires() == 2);
	}
	
	/*
	 * Amount of active hires = 1
	 */
	@Test
	public void activeHires()
	{		
		assertTrue(controller.getReport().getNoOfActiveHires() == 1);
	}
	
	/*
	 * Amount of late hires = 1
	 */
	@Test
	public void lateHires()
	{		
		assertTrue(controller.getReport().getNoOfLateHires() == 1);
	}
	
}
