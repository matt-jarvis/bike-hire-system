package model.customer;

import java.io.Serializable;

import controller.Controller;

/**
 * Customer is a serializable class representing a customer.
 * 
 * Maintains various attributes of the customer and
 * keeps a reference to the system's Controller 
 * class, which assists in generating each customer's 
 * unique ID. 
 */
public class Customer implements Serializable
{

	private static final long serialVersionUID = 1L;
	private transient Controller controller; 
	
	// Customer attributes
	private String customerID;
	private int custNo;
	private String firstName;
	private String lastName;
	private String addressLine1;
	private String addressTown;
	private String addressPostcode;

	/* 
	 * Constructor assigns the controller and generates a unique customerID.
	 */
	public Customer(String firstName, String lastName, String addressLine1, 
					String addressTown, String addressPostcode) 
	{
		controller = Controller.getInstance();
		generateCustomerID();
		setFirstName(firstName);
		setLastName(lastName);
		setAddressLine1(addressLine1);
		setAddressTown(addressTown);
		setAddressPostcode(addressPostcode);
	}

	/*
	 * Gets and increments the custNo of the last customer 
	 * added to the controller, then uses it to generate 
	 * and assign a unique customerID.
	 * 
	 * @see 	controller.Controller#getLastCustomerNo()
	 */
	private void generateCustomerID() 
	{
		custNo = controller.getLastCustomerNo() + 1;
		customerID = "CUST" + custNo;
	}
	
	/*
	 * @return	custNo is used to generate each customer's unique id
	 */
	public int getCustNo()
	{
		return custNo;
	}

	public String getCustomerID()
	{
		return customerID;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public String getAddressLine1()
	{
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1)
	{
		this.addressLine1 = addressLine1;
	}

	public String getAddressTown()
	{
		return addressTown;
	}

	public void setAddressTown(String addressTown)
	{
		this.addressTown = addressTown;
	}

	public String getAddressPostcode()
	{
		return addressPostcode;
	}

	public void setAddressPostcode(String addressPostcode)
	{
		this.addressPostcode = addressPostcode;
	}
	
	/*
	 * @return	a short sentence representing the customer's address
	 */
	public String getAddressString()
	{
		return addressLine1 + ", " + addressTown + ", " + addressPostcode + ".";
	}

}