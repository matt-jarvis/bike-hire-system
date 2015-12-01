package tests;

import static org.junit.Assert.assertTrue;
import model.hire.Active;
import model.hire.Complete;
import model.hire.Hire;
import model.hire.Late;
import model.hire.PaymentPending;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import model.bike.Available;
import model.bike.Bike;
import model.bike.Damaged;
import model.bike.OnHire;
import model.customer.Customer;


public class StatePatternTests
{

	Customer customer;
	Bike bike;
	Hire hire;
	
	@Before
	public void setUp() throws Exception
	{
		customer = new Customer("test", "test", "test", "test", "test");
		bike = new Bike("test", "test", "test", "test", "test", 1, 1);
	}

	@After
	public void tearDown() throws Exception
	{
		customer = null;
		bike = null;
		hire = null;
	}

	/*
	 *  When a new hire is created: 
	 *  its status should be 'PaymentPending'.
	 */
	@Test
	public void CreateHire()
	{
		Hire hire = new Hire(customer, bike, new LocalDate(LocalDate.now()));
		
		assertTrue(hire.getState() instanceof PaymentPending);
	}
	
	/*
	 *  When a hire's status is 'PaymentPending': 
	 *  payForHire() should return true, 
	 *  change the hire's state to 'Active' 
	 *  and change the bike's state to 'OnHire'.
	 */
	@Test
	public void PaymentPendingPayForHire()
	{
		Hire hire = new Hire(customer, bike, new LocalDate(LocalDate.now()));
		
		assertTrue(hire.payForHire() && 
					hire.getState() instanceof Active &&
					hire.getBike().getState() instanceof OnHire);
	}
	
	/*
	 *  When a hire's status is 'PaymentPending':
	 *  isLate() should return false,
	 *  and the hire's state should remain 'PaymentPending'.
	 */
	@Test
	public void PaymentPendingIsLate()
	{
		Hire hire = new Hire(customer, bike, new LocalDate(LocalDate.now()));
		
		assertTrue(!hire.isLate() &&
					hire.getState() instanceof PaymentPending);
	}
	
	/*
	 *  When a hire's status is 'PaymentPending':
	 *  returnHire(false) should return false ,
	 *  returnHire(true) should return false,
	 *  and the hire's state should remain 'PaymentPending'.
	 */
	public void PaymentPendingReturnHire()
	{
		Hire hire = new Hire(customer, bike, new LocalDate(LocalDate.now()));
		
		assertTrue(!hire.returnHire(false) && 
					!hire.returnHire(true) && 
					hire.getState() instanceof PaymentPending);
	}
	
	/*
	 *  When a hire's status is 'Active': 
	 *  payForHire() should return false, 
	 *  the hire's state should remain 'Active' 
	 *  and the bike's state should remain 'OnHire'.
	 */
	@Test
	public void ActivePayForHire()
	{
		Hire hire = new Hire(customer, bike, new LocalDate(LocalDate.now()));
		
		// Tested previously and passed
		hire.payForHire();
		
		assertTrue(!hire.payForHire() && 
					hire.getState() instanceof Active &&
					hire.getBike().getState() instanceof OnHire);
	}
	
	/*
	 *  When a hire's status is 'Active' and its endDate 
	 *  is on or after the current date:
	 *  isLate() should return false 
	 *  and the hire's state should remain 'Active'.
	 */
	@Test
	public void ActiveIsLateFalse()
	{
		Hire hire = new Hire(customer, bike, new LocalDate(LocalDate.now()));
		
		// Tested previously and passed
		hire.payForHire();
		
		assertTrue(!hire.isLate() && 
					hire.getState() instanceof Active);
	}
	
	/*
	 *  When a hire's status is 'Active' and its endDate 
	 *  is before the current date: 
	 *  isLate() should return true
	 *  and the hire's state should change to 'Late'. 
	 */
	@Test
	public void ActiveIsLateTrue()
	{
		// New hire with an end date of yesterday (Late)
		Hire hire = new Hire(customer, bike, new LocalDate(LocalDate.now().minusDays(1)));
		
		// Tested previously and passed
		hire.payForHire();
		
		assertTrue(hire.isLate() && 
					hire.getState() instanceof Late);
	}
	
	/*
	 *  When a hire's status is 'Active' and its endDate 
	 *	is on or after the current date:
	 *	returnHire(false) should return true, 
	 *	the hire's state should change to 'Complete', 
	 *	the bike's state should change to 'Available'
	 *	and isLate() should return false.
	 */
	@Test
	public void ActiveReturnHireOnTimeUndamaged()
	{
		Hire hire = new Hire(customer, bike, new LocalDate(LocalDate.now()));
		
		// Tested previously and passed
		hire.payForHire();
				
		assertTrue(hire.returnHire(false) 
					&& hire.getState() instanceof Complete
					&& bike.getState() instanceof Available
					&& !hire.isLate());
	}
	
	/*
	 *  When a hire's status is 'Active' and its endDate 
	 *	is before the current date:
	 *	returnHire(false) should return true, 
	 *	the hire's state should change to 'Complete', 
	 *	the bike's state should change to 'Available'
	 *	and isLate() should return true.
	 */
	@Test
	public void ActiveReturnHireLateUndamaged()
	{
		// New hire with an end date of yesterday (Late)
		Hire hire = new Hire(customer, bike, new LocalDate(LocalDate.now().minusDays(1)));
		
		// Tested previously and passed
		hire.payForHire();
				
		assertTrue(hire.returnHire(false) 
					&& hire.getState() instanceof Complete
					&& bike.getState() instanceof Available
					&& hire.isLate());
	}
	
	/*
	 *  When a hire's status is 'Active' and its endDate 
	 *	is on or after the current date:
	 *	returnHire(true) should return true, 
	 *	the hire's state should change to 'Complete'
	 *	and the bike's state should change to 'Damaged'
	 */
	@Test
	public void ActiveReturnHireOnTimeDamaged()
	{
		Hire hire = new Hire(customer, bike, new LocalDate(LocalDate.now()));
		
		// Tested previously and passed
		hire.payForHire();
				
		assertTrue(hire.returnHire(true) 
					&& hire.getState() instanceof Complete
					&& bike.getState() instanceof Damaged
					&& !hire.isLate());
	}
	
	/*
	 *  When a hire's status is 'Active' and its endDate 
	 *	is before the current date:
	 *	returnHire(true) should return true, 
	 *	the hire's state should change to 'Complete'
	 *	and the bike's state should change to 'Damaged'
	 */
	@Test
	public void ActiveReturnHireLateDamaged()
	{
		// New hire with an end date of yesterday (Late)
		Hire hire = new Hire(customer, bike, new LocalDate(LocalDate.now().minusDays(1)));
		
		// Tested previously and passed
		hire.payForHire();
				
		assertTrue(hire.returnHire(true) 
					&& hire.getState() instanceof Complete
					&& bike.getState() instanceof Damaged);
	}
	
	/*
	 *  When a hire's status is 'Late':
	 *	payForHire() should return false, 
	 *	the hire's state should remain 'Late'
	 *	and the bike's state should remain 'OnHire'
	 */
	@Test
	public void LatePayForHire()
	{
		// New hire with an end date of yesterday (Late)
		Hire hire = new Hire(customer, bike, new LocalDate(LocalDate.now().minusDays(1)));
		
		// Tested previously and passed
		hire.payForHire();
		hire.isLate();
		
		assertTrue(!hire.payForHire()
					&& hire.getState() instanceof Late
					&& bike.getState() instanceof OnHire);
	}
	
	/*
	 *  When a hire's status is 'Late':
	 *	isLate() should return true, 
	 *	the hire's state should remain 'Late'
	 *	and the bike's state should remain 'OnHire'
	 */
	@Test
	public void LateIsLate()
	{
		// New hire with an end date of yesterday (Late)
		Hire hire = new Hire(customer, bike, new LocalDate(LocalDate.now().minusDays(1)));
		
		// Tested previously and passed
		hire.payForHire();
		
		
		assertTrue(hire.isLate()
					&& hire.getState() instanceof Late
					&& bike.getState() instanceof OnHire);
	}
	
	/*
	 *  When a hire's status is 'Late': 
	 *	returnHire(false) should return true, 
	 *	the hire's state should change to 'Complete', 
	 *	the bike's state should change to 'Available'
	 *	and isLate() should return true.
	 */
	@Test
	public void LateReturnHireUndamaged()
	{
		// New hire with an end date of yesterday (Late)
		Hire hire = new Hire(customer, bike, new LocalDate(LocalDate.now().minusDays(1)));
		
		// Tested previously and passed
		hire.payForHire();
				
		assertTrue(hire.returnHire(false) 
					&& hire.getState() instanceof Complete
					&& bike.getState() instanceof Available
					&& hire.isLate());
	}
	
	/*
	 *  When a hire's status is 'Late': 
	 *	returnHire(true) should return true, 
	 *	the hire's state should change to 'Complete', 
	 *	the bike's state should change to 'Damaged'
	 *	and isLate() should return true.
	 */
	@Test
	public void LateReturnHireDamaged()
	{
		// New hire with an end date of yesterday (Late)
		Hire hire = new Hire(customer, bike, new LocalDate(LocalDate.now().minusDays(1)));
		
		// Tested previously and passed
		hire.payForHire();
				
		assertTrue(hire.returnHire(true) 
					&& hire.getState() instanceof Complete
					&& bike.getState() instanceof Damaged
					&& hire.isLate());
	}
	
	/*
	 *  When a hire's status is 'Complete':
	 *	payForHire() should return false.
	 */
	@Test
	public void CompletePayForHire()
	{
		Hire hire = new Hire(customer, bike, new LocalDate(LocalDate.now()));
		
		// Tested previously and passed
		hire.payForHire();
		hire.returnHire(false);
				
		assertTrue(!hire.payForHire());
	}
	
	/*
	 *  When a hire's status is 'Complete' and it was 
	 *  returned on time:
	 *  isLate() should return false.
	 */
	@Test
	public void CompleteIsLateFalse()
	{
		Hire hire = new Hire(customer, bike, new LocalDate(LocalDate.now()));
		
		// Tested previously and passed
		hire.payForHire();
		hire.returnHire(false);
				
		assertTrue(!hire.isLate());
	}
	
	/*
	 *  When a hire's status is 'Complete' and it was 
	 *  returned late:
	 *  isLate() should return true.
	 */
	@Test
	public void CompleteIsLateTrue()
	{
		// New hire with an end date of yesterday (Late)
		Hire hire = new Hire(customer, bike, new LocalDate(LocalDate.now().minusDays(1)));
		
		// Tested previously and passed
		hire.payForHire();
		hire.returnHire(false);
				
		assertTrue(hire.isLate());
	}
	
	/*
	 *  When a hire's status is 'Complete':
	 *  returnHire(false) should return false
	 *  and returnHire(true) should return false.
	 */
	@Test
	public void CompleteReturnHireT()
	{
		// New hire with an end date of yesterday (Late)
		Hire hire = new Hire(customer, bike, new LocalDate(LocalDate.now().minusDays(1)));
		
		// Tested previously and passed
		hire.payForHire();
		hire.returnHire(false);
				
		assertTrue(!hire.returnHire(false)
					&& !hire.returnHire(true));
	}
	


}
