package controller;

import model.hire.Active;
import model.hire.Hire;
import model.invoice.InvoiceIn;
import model.invoice.InvoiceOut;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import org.joda.time.LocalDate;

import model.bike.Bike;
import model.bike.Damaged;
import model.bike.OnHire;
import model.customer.Customer;

/**
 * Controller is a serializable class that manages the rental system's
 * application data.
 * 
 * Implements the singleton design pattern to ensure that only one instance is
 * created per runtime.
 * 
 * Implements serializable to enable permanent storage and retrieval of the
 * Controller object and its data.
 */
public class Controller implements Serializable
{

	private static final long serialVersionUID = 1L;
	private static Controller instance;
	private String dataFile = "BikeHireData.dat";
	private Report report = new Report(this);

	// Application data
	private ArrayList<Customer> customers = new ArrayList<Customer>();
	private ArrayList<Bike> bikes = new ArrayList<Bike>();
	private ArrayList<Hire> hires = new ArrayList<Hire>();
	private ArrayList<InvoiceOut> invoicesOut = new ArrayList<InvoiceOut>();
	private ArrayList<InvoiceIn> invoicesIn = new ArrayList<InvoiceIn>();

	/*
	 * Constructor is private to prevent instantiation by external objects.
	 * Instead, instantiation is delegated to the getInstance() method.
	 */
	private Controller()
	{

	}

	/*
	 * Manages instantiation of the Controller class and provides a global point
	 * of access to the Controller object.
	 * 
	 * @return a new Controller object if not previously instantiated.
	 * Otherwise, the current Controller object (instance).
	 */
	public static Controller getInstance()
	{
		if (instance == null)
			instance = new Controller();

		return instance;
	}

	public ArrayList<Customer> getCustomers()
	{
		return customers;
	}

	/*
	 * Adds a new Customer object to the customers list.
	 * 
	 * @param customer the new customer to be added.
	 */
	public void addCustomer(Customer customer)
	{
		customers.add(customer);
	}

	/*
	 * Assists the Customer class in generating a unique ID for each customer.
	 * 
	 * @see customer.Customer#generateCustomerID()
	 * 
	 * @return the customer number of the last Customer object added to the
	 * customers list. 0 if the customers list is empty.
	 */
	public int getLastCustomerNo()
	{
		if (customers.isEmpty())
			return 0;

		return customers.get(customers.size() - 1).getCustNo();
	}

	public ArrayList<Bike> getBikes()
	{
		return bikes;
	}

	/*
	 * Adds a new Bike object to the bikes list.
	 * 
	 * @param bike the new bike to be added.
	 */
	public void addBike(Bike bike)
	{
		bikes.add(bike);
	}

	/*
	 * Assists the Bike class in generating a unique ID for each bike.
	 * 
	 * @see bike.Bike#generateBikeID()
	 * 
	 * @return the bike number of the last Bike object added to the bikes list.
	 * 0 if the bikes list is empty.
	 */
	public int getLastBikeNo()
	{
		if (bikes.isEmpty())
			return 0;

		return bikes.get(bikes.size() - 1).getBikeNo();
	}

	public ArrayList<Hire> getHires()
	{
		return hires;
	}

	/*
	 * Adds a new Hire object to the hire list.
	 * 
	 * @param hire the new hire to be added.
	 */
	public void addHire(Hire hire)
	{
		hires.add(hire);
	}

	/*
	 * Assists the Hire class in generating a unique ID for each Hire.
	 * 
	 * @see hire.Hiree#generateHireID()
	 * 
	 * @return the hire number of the last Hire object added to the hires list.
	 * 0 if the hires list is empty.
	 */
	public int getLastHireNo()
	{
		if (hires.isEmpty())
			return 0;

		return hires.get(hires.size() - 1).getHireNo();
	}

	/*
	 * Calls the isLate() method for all Hire objects in the hires list. This
	 * ensures that each hire's state is accurate in relation to the current
	 * date.
	 * 
	 * @see hire.HireState#isLate()
	 */
	public void refreshLateHires()
	{
		for (int i = 0; i < hires.size(); i++)
			hires.get(i).isLate();
	}

	public ArrayList<InvoiceOut> getInvoicesOut()
	{
		return invoicesOut;
	}

	/*
	 * Adds a new InvoiceOut object to the invoicesOut list.
	 * 
	 * @param invoic the new invoice to be added.
	 */
	public void addInvoiceOut(InvoiceOut invoice)
	{
		invoicesOut.add(invoice);
	}

	/*
	 * Assists the InvoiceOut class in generating a unique ID for each invoice.
	 * 
	 * @see invoice.InvoiceOut#generateInvoiceOutID()
	 * 
	 * @return the invoice number of the last InvoiceOut object added to the
	 * invoicesOut list. 0 if the invoiceOut list is empty.
	 */
	public int getLastInvoiceOutNo()
	{
		if (invoicesOut.isEmpty())
			return 0;
		return invoicesOut.get(invoicesOut.size() - 1).getInvoiceNo();
	}

	public ArrayList<InvoiceIn> getInvoicesIn()
	{
		return invoicesIn;
	}

	/*
	 * Adds a new InvoiceIn object to the invoicesIn list.
	 * 
	 * @param invoic the new invoice to be added.
	 */
	public void addInvoiceIn(InvoiceIn invoice)
	{
		invoicesIn.add(invoice);
	}

	/*
	 * Assists the InvoiceIn class in generating a unique ID for each invoice.
	 * 
	 * @see invoice.InvoiceIn#generateInvoiceInID()
	 * 
	 * @return the invoice number of the last InvoiceIn object added to the
	 * invoicesIn list. 0 if the invoiceIn list is empty.
	 */
	public int getLastInvoiceInNo()
	{
		if (invoicesIn.isEmpty())
			return 0;
		return invoicesIn.get(invoicesIn.size() - 1).getInvoiceNo();
	}

	public Report getReport()
	{
		return report;
	}

	/*
	 * Serializes this Controller object to enable permanent storage of the
	 * application data.
	 * 
	 * @throws FileNotFoundException if Controller.dat cannot be created.
	 * 
	 * @throws IOException if Controller.dat serialization fails during an I/O
	 * operation.
	 */
	public void serialiseBikeHireData() throws FileNotFoundException,
			IOException
	{
		FileOutputStream outStream = new FileOutputStream(dataFile);
		ObjectOutputStream objOut = new ObjectOutputStream(outStream);
		objOut.writeObject(this);
		objOut.close();
	}

	/*
	 * Deserializes the Controller object to enable retrieval of the application
	 * data serialized by the serialiseBikeHireData() method.
	 * 
	 * @throws ClassNotFoundException if the the class within the serialized
	 * Controller object cannot be found.
	 * 
	 * @throws FileNotFoundException if Controller.dat is not found in the
	 * current directory.
	 * 
	 * @throws IOException if Controller.dat serialization fails during an I/O
	 * operation.
	 */
	public void deserialiseBikeHireData() throws FileNotFoundException,
			IOException, ClassNotFoundException
	{
		FileInputStream inStream = new FileInputStream(dataFile);
		ObjectInputStream objIn = new ObjectInputStream(inStream);

		Controller previous = (Controller) objIn.readObject();
		setData(previous);
		instance = this;

		objIn.close();
	}

	/*
	 * Assigns all lists to the previous controller's lists accordingly.
	 * 
	 * @param previous the controller serialized by the previous application
	 * usage.
	 */
	private void setData(Controller previous)
	{
		this.customers = previous.getCustomers();
		this.hires = previous.getHires();
		this.bikes = previous.getBikes();
		this.invoicesIn = previous.getInvoicesIn();
		this.invoicesOut = previous.getInvoicesOut();
	}

	public boolean generateData()
	{

		/******************************************************************************
		 * CUSTOMERS
		 ******************************************************************************/
		Customer c1F = new Customer("Jane", "North", "5 Newton Road",
				"Newton Longville", "MK17 9GQ");
		addCustomer(c1F);

		Customer c2M = new Customer("Elvis", "Blakeland", "49 Roxton Road",
				"Great Barford", "MK44 5YJ");
		addCustomer(c2M);

		Customer c3F = new Customer("Samantha", "Heyman", "16 Orchard Way",
				"Buckingham", "MK18 2NG");
		addCustomer(c3F);

		Customer c4M = new Customer("Christopher", "Hamelton",
				"28 Clovelly Court", "Corby", "NN18 8EF");
		addCustomer(c4M);

		Customer c5F = new Customer("Nicky", "Stern", "59 Kingsway", "Luton",
				"LU4 8AU");
		addCustomer(c5F);

		/******************************************************************************
		 * BIKES
		 ******************************************************************************/
		Bike b1m = new Bike("Scott", "Ransom", "mountain", "men", "silver", 30,
				50);
		addBike(b1m);

		Bike b2w = new Bike("Breezer", "Beamer", "mountain", "women",
				"purple pearl", 26.40, 38);
		addBike(b2w);

		Bike b3a = new Bike("Cannondale", "Road Warrior 500", "hybrid",
				"adult", "dark green", 20, 32);
		addBike(b3a);

		Bike b4b = new Bike("Alliant", "XPRESS Pro", "BMX", "boy", "chrome",
				12.70, 20);
		addBike(b4b);

		Bike b5g = new Bike("Breezer", "Venturi", "racing", "girl", "sky blue",
				59, 100);
		addBike(b5g);

		Bike b6c = new Bike("Giant", "Areva 24", "mountain", "children",
				"blue", 14.25, 23);
		addBike(b6c);

		/******************************************************************************
		 * HIRES
		 ******************************************************************************/
		LocalDate now = LocalDate.now();

		Hire pastHireOnTimeNotDamaged = new Hire(c1F, b2w, now.minusDays(50),
				now.minusDays(45), false);
		addHire(pastHireOnTimeNotDamaged);

		Hire pastHireOnTimeDamaged = new Hire(c2M, b1m, now.minusDays(47),
				now.minusDays(46), false);
		b1m.setState(new Damaged(b1m));
		addHire(pastHireOnTimeDamaged);

		Hire pastHireLateNotDamaged = new Hire(c1F, b3a, now.minusDays(20),
				now.minusDays(17), true);
		pastHireLateNotDamaged.setDateReturned(now.minusDays(15));
		addHire(pastHireLateNotDamaged);

		Hire pastHireLateDamaged = new Hire(c3F, b5g, now.minusDays(10),
				now.minusDays(7), true);
		b1m.setState(new Damaged(b5g));
		pastHireLateDamaged.setDateReturned(now.minusDays(2));
		addHire(pastHireLateDamaged);

		Hire activeHireOnTimeDueToday = new Hire(c4M, b6c, now.minusDays(4),
				now, false);
		b6c.setState(new OnHire(b6c));
		activeHireOnTimeDueToday.setState(new Active(activeHireOnTimeDueToday));
		activeHireOnTimeDueToday.setDateReturned(null);
		addHire(activeHireOnTimeDueToday);

		Hire activeHireOnTimeDueTomorrow = new Hire(c1F, b5g, now.minusDays(2),
				now.plusDays(1), false);
		b5g.setState(new OnHire(b5g));
		activeHireOnTimeDueTomorrow.setState(new Active(
				activeHireOnTimeDueTomorrow));
		activeHireOnTimeDueTomorrow.setDateReturned(null);
		addHire(activeHireOnTimeDueTomorrow);

		Hire activeHireLate = new Hire(c5F, b2w, now.minusDays(6),
				now.minusDays(2), true);
		b2w.setState(new OnHire(b2w));
		activeHireLate.setState(new Active(activeHireLate));
		activeHireLate.setDateReturned(null);
		addHire(activeHireLate);

		/******************************************************************************
		 * INVOICES
		 ******************************************************************************/
		// Past hires out
		addInvoiceOut(new InvoiceOut(pastHireOnTimeNotDamaged));
		addInvoiceOut(new InvoiceOut(pastHireOnTimeDamaged));
		addInvoiceOut(new InvoiceOut(pastHireLateNotDamaged));
		addInvoiceOut(new InvoiceOut(pastHireLateDamaged));

		// Past hires in
		addInvoiceIn(new InvoiceIn(pastHireOnTimeNotDamaged));
		addInvoiceIn(new InvoiceIn(pastHireOnTimeDamaged));
		addInvoiceIn(new InvoiceIn(pastHireLateNotDamaged));
		addInvoiceIn(new InvoiceIn(pastHireLateDamaged));

		// Active hire out (yet to be returned)
		addInvoiceOut(new InvoiceOut(activeHireOnTimeDueToday));
		addInvoiceOut(new InvoiceOut(activeHireOnTimeDueTomorrow));
		addInvoiceOut(new InvoiceOut(activeHireLate));

		/******************************************************************************
		 * END
		 ******************************************************************************/

		// Serialise the controller - save the data to disk
		try
		{
			serialiseBikeHireData();
			return true;
		} catch (IOException e)
		{
			e.printStackTrace();
			return false;
		}
	}

}
