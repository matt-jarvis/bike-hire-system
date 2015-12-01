package model.bike;

/**
 * BikeState is an interface that encapsulates 
 * the behaviour associated with the states of 
 * a particular Bike.
 */
public interface BikeState 
{

	/*
	 * Represents a hired bike being collected. The 
	 * bike's status should be altered accordingly 
	 * after collection.
	 * 
	 * @return 	true if the bike can be (and is) collected successfully.
	 * 			false otherwise.
	 */
	public boolean collectBike();
	
	/*
	 * Represents a hired bike being returned. The 
	 * bike's status should be altered accordingly 
	 * after return. 
	 * 
	 * @param	isDamaged	true if the bike has been been returned damaged.
	 * 						false otherwise.
	 * @return 	true if the bike can be (and is) returned successfully.
	 * 			false otherwise.
	 */
	public boolean returnBike(boolean isDamaged);
	
	/*
	 * Represents a damaged bike being repaired. The 
	 * bike's status should be altered accordingly 
	 * after a successful repair. 
	 * 
	 * @return 	true if the bike can be (and is) repaired successfully.
	 * 			false otherwise.
	 */
	public boolean repairBike();
	
	/*
	 * Determines if a bike is currently damaged or not.
	 * 
	 * @return 	true if the bike is currently damaged.
	 * 			false otherwise.
	 */
	public boolean isDamaged();

}