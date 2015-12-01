package model.bike;

import java.io.Serializable;

/**
 * Damaged: a serializable class that implements the 
 * methods/behaviour associated with a bike that is 
 * currently damaged.
 */
public class Damaged implements BikeState, Serializable
{

	private static final long serialVersionUID = 1L;
	private Bike bike;
	
	/* 
	 * Constructs an Damaged state object.
	 * 
	 * @param	bike	the bike that is currently damaged.
	 */
	public Damaged(Bike bike) 
	{
		this.bike = bike;
	}

	/* 
	 * Denies the collection of the bike because if a bike
	 * is currently damaged then it cannot be collected.
	 *
	 * @see 	bike.BikeState#collectBike(boolean)
	 * @return	only returns false; the bike is not suitable 
	 * 			for hiring.
	 */
	public boolean collectBike() 
	{
		return false;
	}

	/* 
	 * Denies the return of the bike because a bike can 
	 * only be returned after it has been collected.
	 *
	 * @see 	bike.BikeState#returnBike(boolean)
	 * @param	isDamaged	insignificant. 
	 * @return	only returns false; the bike has not
	 * 			been collected because it is currently 
	 * 			damaged.
	 */
	public boolean returnBike(boolean isDamaged) 
	{
		return false;
	}

	/*
	 * Confirms that the bike is currently damaged.
	 *
	 * @see 	bike.BikeState#isDamaged()
	 * @return	only returns true; the bike is currently damaged.
	 */
	public boolean isDamaged()
	{
		return true;
	}
	
	/* 
	 * Represents the damaged bike being repaired. Changes
	 * the bike's state to Available.
	 *
	 * @see 	bike.BikeState#repairBike()
	 * @return	only returns true; the bike is suitable 
	 * 			for repair.
	 */
	public boolean repairBike()
	{
		bike.setState(new Available(bike));
		return true;
	}
	
	/* 
	 * @see java.lang.Object#toString()
	 * @return	A String describing this particular bike state
	 */
	@Override
	public String toString()
	{
		return "Damaged";
	}
}