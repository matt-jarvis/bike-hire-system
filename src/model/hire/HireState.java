package model.hire;

/**
 * HireState is an interface that encapsulates 
 * the behaviour associated with the states of 
 * a particular Hire.
 */
public interface HireState 
{
	/*
	 * Represents a hire being paid for at time of 
	 * collection. The hire's status should be altered 
	 * accordingly after a successfuly payment is made.
	 * 
	 * @return 	true if the hire can be (and is) paid for successfully.
	 * 			false otherwise.
	 */
	public boolean payForHire();
	
	/*
	 * Determines if a hired bike is currently late or was 
	 * returned late. If necessary, the state of a hire should
	 * be altered accordingly if it is found to be late.
	 * 
	 * @return 	true if the hire is/was late.
	 * 			false otherwise.
	 */
	public boolean isLate();
	
	/*
	 * Represents a hire being returned. The hire's status should be 
	 * altered accordingly after a successful return. 
	 * 
	 * @param	bikeIsDamaged	true if the bike has been been returned damaged.
	 * 							false otherwise.
	 * @return 	true if the hire can be (and is) returned successfully.
	 * 			false otherwise.
	 */
	public boolean returnHire(boolean bikeIsDamaged);

}