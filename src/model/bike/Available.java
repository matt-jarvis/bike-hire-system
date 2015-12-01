package model.bike;

import java.io.Serializable;

/**
 * Available: a serializable class that implements the 
 * methods/behaviour associated with a bike that is 
 * currently available.
 */
public class Available implements BikeState, Serializable
{

	private static final long serialVersionUID = 1L;
	private Bike bike;
	
	
	/* 
	 * Constructs an Available state object.
	 * 
	 * @param	bike	the bike that is currently Available.
	 */
	public Available(Bike bike) 
	{
		this.bike = bike;
	}

	/* 
	 * Represents an Available bike being collected. The
	 * bike's state is changed to OnHire.
	 *
	 * @see 	bike.BikeState#collectBike()
	 * @return	only returns true; the bike is available 
	 * 			for collection.
	 */
	public boolean collectBike() 
	{
		bike.setState(new OnHire(bike));
		return true;
	}

	/* 
	 * Denies the return of the bike because a bike can 
	 * only be returned after it has been collected.
	 *
	 * @see 	bike.BikeState#returnBike(boolean)
	 * @param	isDamaged	insignificant. 
	 * @return	only returns false; the bike has not yet
	 * 			been collected
	 */
	public boolean returnBike(boolean isDamaged) 
	{
		return false;
	}
	
	/*
	 * Confirms that the bike is not currently damaged; because
	 * it is currently available.
	 *
	 * @see 	bike.BikeState#isDamaged()
	 * @return	only returns false; the bike is currently available.
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
	 * @see 	java.lang.Object#toString()
	 * @return	A String describing this particular bike state
	 */
	@Override
	public String toString()
	{
		return "Available";
	}
	
	

}