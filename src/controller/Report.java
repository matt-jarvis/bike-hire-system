package controller;

import java.io.Serializable;

import model.hire.Active;
import model.hire.Complete;
import model.hire.Hire;
import model.hire.Late;
import model.bike.Available;
import model.bike.Bike;
import model.bike.Damaged;
import model.bike.OnHire;

/*
 * Report is a serializable class that offers 
 * statistics based on the contents of the lists 
 * stored in a controller object.
 */
public class Report implements Serializable
{

	private static final long serialVersionUID = 1L;
	private Controller controller;

	/*
	 * Constructs a Report object.
	 * 
	 * @param 	controller	the controller object for which the
	 * 						statistics are to be calculated.
	 */
	public Report(Controller controller)
	{
		this.controller = controller;
	}

	/*
	 * Calculates the total amount of hires currently 
	 * stored in the controller's hires list.
	 * 
	 * @return	total amount of hires
	 */
	public int getTotalHires()
	{
		return controller.getHires().size();
	}

	/*
	 * Calculates the total amount of bikes currently 
	 * stored in the controller's bikes list.
	 * 
	 * @return	total amount of bikes
	 */
	public int getTotalBikes()
	{
		return controller.getBikes().size();
	}

	/*
	 * Calculates the total amount of customers currently 
	 * stored in the controller's customers list.
	 * 
	 * @return	total amount of customers
	 */
	public int getTotalCustomers()
	{
		return controller.getCustomers().size();
	}

	/*
	 * Calculates the total amount of hires that are
	 * currently active.
	 * 
	 * @return	total amount of active hires
	 */
	public int getNoOfActiveHires()
	{
		int count = 0;
		int size = getTotalHires();
		Hire temp;

		for (int i = 0; i < size; i++)
		{
			temp = controller.getHires().get(i);

			if (temp.getState() instanceof Active)
				count++;
		}

		return count;
	}
	
	/*
	 * Calculates the total amount of hires that are
	 * currently late.
	 * 
	 * @return	total amount of late hires
	 */

	public int getNoOfLateHires()
	{
		int count = 0;
		int size = getTotalHires();
		Hire temp;

		for (int i = 0; i < size; i++)
		{
			temp = controller.getHires().get(i);

			if (temp.getState() instanceof Late)
				count++;
		}

		return count;
	}

	/*
	 * Calculates the total amount of hires that are
	 * currently complete.
	 * 
	 * @return	total amount of completed hires
	 */
	public int getNoOfCompleteHires()
	{
		int count = 0;
		int size = getTotalHires();
		Hire temp;

		for (int i = 0; i < size; i++)
		{
			temp = controller.getHires().get(i);

			if (temp.getState() instanceof Complete)
				count++;
		}

		return count;
	}

	/*
	 * Calculates the total amount of bikes that are
	 * currently available for hire.
	 * 
	 * @return	total amount of available bikes
	 */
	public int getNoOfBikesAvailable()
	{
		int count = 0;
		int size = getTotalBikes();
		Bike temp;

		for (int i = 0; i < size; i++)
		{
			temp = controller.getBikes().get(i);

			if (temp.getState() instanceof Available)
				count++;
		}

		return count;
	}

	/*
	 * Calculates the total amount of bikes that are
	 * currently on hire.
	 * 
	 * @return	total amount of bikes on hire
	 */
	public int getNoOfBikesOnHire()
	{
		int count = 0;
		int size = getTotalBikes();
		Bike temp;

		for (int i = 0; i < size; i++)
		{
			temp = controller.getBikes().get(i);

			if (temp.getState() instanceof OnHire)
				count++;
		}

		return count;
	}

	/*
	 * Calculates the total amount of bikes that are
	 * currently damaged.
	 * 
	 * @return	total amount of bikes damaged
	 */
	public int getNoOfBikesDamaged()
	{
		int count = 0;
		int size = getTotalBikes();
		Bike temp;

		for (int i = 0; i < size; i++)
		{
			temp = controller.getBikes().get(i);

			if (temp.getState() instanceof Damaged)
				count++;
		}

		return count;
	}

	/*
	 * Determines the bike which has been hired the most.
	 * 
	 * @return	the bike ID of the most popular bike
	 */
	public String getMostPopularBike()
	{
		int hireSize = getTotalHires();
		int bikeSize = getTotalBikes();

		if(bikeSize == 0)
			return "N/A";

		// Assume the first bike is the most popular
		Bike mostPopular = controller.getBikes().get(0);

		if(bikeSize == 1)
			return mostPopular.getBikeID();

		Hire hireTemp;
		Bike bikeTemp;
		int count;
		int most = -1;

		for (int i = 0; i < bikeSize; i++)
		{
			count = 0;
			bikeTemp = controller.getBikes().get(i);

			for (int j = 0; j < hireSize; j++)
			{
				hireTemp = controller.getHires().get(j);

				if (hireTemp.getBike().getBikeID().equals(bikeTemp.getBikeID()))
					count++;
			}

			// First iteration; initialise most popular variables
			if (most == -1)
			{
				mostPopular = bikeTemp;
				most = count;
			}

			if(count > most)
			{
				most = count;
				mostPopular = bikeTemp;
			}
		}

		return mostPopular.getBikeID();
	}

	/*
	 * Determines the bike which has been hired the least.
	 * 
	 * @return	the bike ID of the least popular bike
	 */
	public String getLeastPopularBike()
	{

		int hireSize = getTotalHires();
		int bikeSize = getTotalBikes();

		if(bikeSize == 0)
			return "N/A";

		// Assume the first bike is the least popular
		Bike leastPopular = controller.getBikes().get(0);

		if(bikeSize == 1)
			return leastPopular.getBikeID();

		Hire hireTemp;
		Bike bikeTemp;
		int count;
		int least = -1;

		for (int i = 0; i < bikeSize; i++)
		{
			count = 0;
			bikeTemp = controller.getBikes().get(i);

			for (int j = 0; j < hireSize; j++)
			{
				hireTemp = controller.getHires().get(j);

				if (hireTemp.getBike().getBikeID().equals(bikeTemp.getBikeID()))
					count++;
			}

			// First iteration; initialise least popular variables
			if (least == -1)
			{
				leastPopular = bikeTemp;
				least = count;
			}

			if(count < least)
			{
				least = count;
				leastPopular = bikeTemp;
			}
		}

		return leastPopular.getBikeID();
	}
	
}
