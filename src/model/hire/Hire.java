package model.hire;

import java.io.Serializable;

import org.joda.time.Days;
import org.joda.time.LocalDate;

import controller.Controller;
import model.bike.Bike;
import model.customer.Customer;

/**
 * Hire is a serializable class representing a hire.
 * 
 * Maintains various attributes of the hire and keeps a reference to the
 * system's Controller class, which assists in generating each hire's unique ID.
 * 
 * Implements the HireState interface. Hire is the context class of the hire
 * state pattern, therefore its behaviour can change depending on its current
 * state.
 * 
 * Uses JodaTime (external library) to store and calculate all Dates.
 */
public class Hire implements Serializable, HireState
{

	private static final long serialVersionUID = 1L;
	private transient Controller controller;

	// Hire attributes
	private String hireID;
	private int hireNo;
	private Customer customer;
	private Bike bike;
	private LocalDate startDate;
	private LocalDate endDate;
	private LocalDate dateReturned;
	private HireState currentState;

	/*
	 * Constructor assigns the controller and generates a unique hireID.
	 * 
	 * @param customer the customer who wishes to hire the bike
	 * 
	 * @param bike the bike that the customer wishes to hire
	 * 
	 * @param endDate the date that the customer wishes to return the hire
	 * 
	 * startDate is set to the current date returnedOn is set to null
	 * currentState is set to PaymentPending
	 */
	public Hire(Customer customer, Bike bike, LocalDate endDate)
	{
		controller = Controller.getInstance();
		generateHireID();
		setCustomer(customer);
		setBike(bike);
		setStartDate();
		setEndDate(endDate);
		setDateReturned(null);
		setState(new PaymentPending(this));
	}

	
	// Used only to create arbitrary data
	public Hire(Customer customer, Bike bike, LocalDate startDate,
			LocalDate endDate, boolean isLate)
	{
		controller = Controller.getInstance();
		generateHireID();
		setCustomer(customer);
		setBike(bike);
		this.startDate = startDate;
		this.endDate = endDate;
		setState(new Complete(this, isLate));
		setDateReturned(endDate);
	}

	/*
	 * Gets and increments the hireNo of the last hire added to the controller,
	 * then uses it to generate and assign a unique hireID.
	 * 
	 * @see controller.Controller#getLastHireNo()
	 */
	private void generateHireID()
	{
		hireNo = controller.getLastHireNo() + 1;
		hireID = "HIRE" + hireNo;
	}

	public String getHireID()
	{
		return hireID;
	}

	/*
	 * hireNo is used to generate each hire's unique id
	 */
	public int getHireNo()
	{
		return hireNo;
	}

	public Customer getCustomer()
	{
		return customer;
	}

	public void setCustomer(Customer customer)
	{
		this.customer = customer;
	}

	public Bike getBike()
	{
		return bike;
	}

	public void setBike(Bike bike)
	{
		this.bike = bike;
	}

	public LocalDate getStartDate()
	{
		return startDate;
	}

	private void setStartDate()
	{
		startDate = LocalDate.now();
	}

	public LocalDate getEndDate()
	{
		return endDate;
	}

	public void setEndDate(LocalDate endDate)
	{
		this.endDate = endDate;
	}

	public int getDuration()
	{
		return Days.daysBetween(startDate, endDate).getDays();
	}

	public LocalDate getDateReturned()
	{
		return dateReturned;
	}

	public void setDateReturned(LocalDate dateReturned)
	{
		this.dateReturned = dateReturned;
	}

	/*
	 * @return The amount of days a hire is/was late by. 0 if a hire is not
	 * late.
	 */
	public int getDaysLate()
	{
		if (isLate())
		{
			if (dateReturned == null)
				return Days.daysBetween(endDate, LocalDate.now()).getDays();
			else
				return Days.daysBetween(endDate, dateReturned).getDays();
		}

		return 0;
	}

	/*
	 * @return The hire's current state as represented by the concrete HireState
	 * object
	 */
	public HireState getState()
	{
		return currentState;
	}

	public void setState(HireState state)
	{
		currentState = state;
	}

	/*
	 * Calls the current HireState object's method payForHire()
	 * 
	 * @see hire.HireState#payForHire()
	 * 
	 * @return true if current HireState allows payment. false otherwise.
	 */
	public boolean payForHire()
	{
		return currentState.payForHire();
	}

	/*
	 * Calls the current HireState object's method isLate()
	 * 
	 * @see hire.HireState#isLate()
	 * 
	 * @return true if the hire is late. false otherwise.
	 */
	public boolean isLate()
	{
		return currentState.isLate();
	}

	/*
	 * Calls the current HireState object's method returnHire()
	 * 
	 * @see hire.HireState#returnHire(boolean)
	 * 
	 * @return true if the current HireState allows the hire to be returned.
	 * false otherwise.
	 */
	public boolean returnHire(boolean bikeIsDamaged)
	{
		return currentState.returnHire(bikeIsDamaged);
	}

	/*
	 * Calculates the cost of a hire: bike's daily rate * duration of hire
	 * 
	 * The deposit is not added here because it will be refunded if the bike is
	 * returned undamaged. Instead, the deposit is added/refunded at the invoice
	 * stages.
	 * 
	 * @return The cost of hiring the bike for the duration of the hire
	 */
	public double getCost()
	{
		return bike.getDailyRate() * getDuration();
	}

}