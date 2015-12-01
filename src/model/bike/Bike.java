package model.bike;

import java.io.Serializable;

import controller.Controller;

/**
 * Bike is a serializable class representing a bike.
 * 
 * Maintains various attributes of the bike and
 * keeps a reference to the system's Controller 
 * class, which assists in generating each bike's 
 * unique ID. 
 * 
 * Implements the BikeState interface. Bike is the context
 * class of the bike state pattern, therefore its behaviour 
 * can change depending on its current state.
 */
public class Bike implements Serializable, BikeState
{
	
	private static final long serialVersionUID = 1L; 
	private transient Controller controller; 
	
	// Bike attributes
	private String bikeID; 
	private int bikeNo; 
	private String make;
	private String model;
	private String function;
	private String demographic;
	private String colour;
	private double dailyRate; 
	private double deposit; 
	private BikeState currentState; 

	/*
	 * Constructs a Bike with a unique bikeID and sets its current state to Available.
	 * 
	 * @param make			 bike's make
	 * @param model			 bike's model
	 * @param function		 bike's function: racing, mountain, bmx etc.
	 * @param demographic	 bike's demographic: men, women, girl, boy etc.
	 * @param colour		 the colour of the bike
	 * @param dailyRate		 cost of hiring the bike for 1 day (£)
	 * @param deposit		 fixed deposit amount (£)
	 */
	public Bike(String make, String model, String function, String demographic, 
				String colour, double dailyRate, double deposit) 
	{
		controller = Controller.getInstance();
		generateBikeID();
		setMake(make);
		setModel(model);
		setFunction(function);
		setDemographic(demographic);
		setColour(colour);
		setDailyRate(dailyRate);
		setDeposit(deposit);
		setState(new Available(this));
	}

	/*
	 * Gets and increments the bikeNo of the last bike 
	 * added to the controller, then uses it to generate 
	 * and assign a unique bikeID.
	 * 
	 * @see 	controller.Controller#getLastBikeNo()
	 */
	private void generateBikeID() 
	{
		bikeNo = controller.getLastBikeNo() + 1;
		bikeID = "BIKE" + bikeNo;
	}
	
	/*
	 * bikeNo is used to generate each bike's unique id.
	 */
	public int getBikeNo()
	{	
		return bikeNo;
	}
	
	public String getBikeID()
	{
		return bikeID;
	}
	
	public String getMake()
	{
		return make;
	}

	public void setMake(String make)
	{
		this.make = make;
	}

	public String getModel()
	{
		return model;
	}

	public void setModel(String model)
	{
		this.model = model;
	}

	public String getFunction()
	{
		return function;
	}

	public void setFunction(String function)
	{
		this.function = function;
	}

	public String getDemographic()
	{
		return demographic;
	}

	public void setDemographic(String demographic)
	{
		this.demographic = demographic;
	}

	public String getColour()
	{
		return colour;
	}

	public void setColour(String colour)
	{
		this.colour = colour;
	}


	public double getDailyRate()
	{
		return dailyRate;
	}
	
	/*
	 * @param 	dailyRate Cost of hiring this bike for 1 day (in £'s)
	 */
	public void setDailyRate(double dailyRate)
	{
		this.dailyRate = dailyRate;
	}

	public double getDeposit()
	{
		return deposit;
	}
	
	/*
	 * @param 	deposit	Fixed deposit amount (in £'s)
	 */
	public void setDeposit(double deposit)
	{
		this.deposit = deposit;
	}

	/*
	 * @return 	A short sentence describing the attributes of this bike 
	 */
	public String getDescription()
	{
		return make + " " + model + ", " + demographic + "'s " + function + " bike in " + colour;
	}
	
	/*
	 * Calls the current BikeState object's method toString()
	 * 
	 * @return 	A string representation of the current state
	 */
	public String getStatus()
	{
		return currentState.toString();
	}
	
	/*
	 * @return	The bike's current state (Available, OnHire etc.)
	 */
	public BikeState getState()
	{
		return currentState;
	}
	
	public void setState(BikeState state)
	{
		currentState = state;
	}
	
	/*
	 * Calls the current BikeState object's method collectBike()
	 * 
	 * @return 	true if current BikeState allows bike to be collected.
	 * 			false otherwise.
	 */
	public boolean collectBike() 
	{
		return currentState.collectBike();
	}

	/*
	 * Calls the current BikeState object's method returnBike()
	 * 
	 * @param 	true if bike has been returned damaged
	 * 			false otherwise
	 * @return 	true if current BikeState allows bike to be returned.
	 *  		false otherwise
	 */
	public boolean returnBike(boolean isDamaged) 
	{
		return currentState.returnBike(isDamaged);
	}
	
	/*
	 * Calls the current BikeState object's method isDamaged()
	 * 
	 * @return 	true if current BikeState is an instance of Damaged.
	 *  		false otherwise
	 */
	public boolean isDamaged()
	{
		return currentState.isDamaged();
	}
	
	/*
	 * Calls the current BikeState object's method repairBike()
	 * 
	 * @return 	true if current BikeState is an instance of Damaged.
	 *  		false otherwise
	 */
	public boolean repairBike()
	{
		return currentState.repairBike();
	}
}