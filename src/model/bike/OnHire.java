package model.bike;

import java.io.Serializable;

/**
 * OnHire: a serializable class that implements the 
 * methods/behaviour associated with a bike that is 
 * currently on hire.
 */
public class OnHire implements BikeState, Serializable
{

	private static final long serialVersionUID = 1L;
	private Bike bike;
	

	/* 
	 * Constructs an OnHire state object.
	 * 
	 * @param	bike	the bike that is currently on hire.
	 */
	public OnHire(Bike bike) 
	{
		this.bike = bike;
	}

	/* 
	 * Denies the collection of the bike because if a bike
	 * is currently on hire then it has already been 
	 * collected.
	 *
	 * @see 	bike.BikeState#collectBike(boolean)
	 * @return	only returns true; the bike is available 
	 * 			for collection.
	 */
	public boolean collectBike() 
	{
		return false;
	}

	/* 
	 * Represents the hired bike being returned. Changes
	 * the bike's state to Damaged or Available, as determined
	 * by the condition the bike is returned in.
	 *
	 * @see 	bike.BikeState#returnBike(boolean)
	 * @param	isDamaged	true if the bike has been been returned damaged.
	 * 						false otherwise.
	 * @return	only returns true; the bike can be returned
	 */
	public boolean returnBike(boolean isDamaged) 
	{
		if(isDamaged)
			bike.setState(new Damaged(bike));
		else
			bike.setState(new Available(bike));
		
		return true;
	}

	/*
	 * Confirms that the bike is not currently damaged; because
	 * it is currently on hire.
	 *
	 * @see 	bike.BikeState#isDamaged()
	 * @return	only returns false; the bike is currently on hire.
	 */
	public boolean isDamaged()
	{
		return false;
	}
	
	/*
	 * Disallows a bike from being repaired. The bike is not
	 * currently damaged and therefore does not need repairing.
	 * 
	 * @see 	bike.BikeState#repairBike()
	 * @return 	only returns false; the bike does not need repairing.
	 */
	public boolean repairBike()
	{
		return false;
	}
	
	/* 
	 * @see java.lang.Object#toString()
	 * @return	A String describing this particular bike state
	 */
	@Override
	public String toString()
	{
		return "On hire";
	}

}