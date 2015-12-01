package model.hire;
import java.io.Serializable;

import org.joda.time.LocalDate;


/**
 * Complete is a serializable class that implements the 
 * methods/behaviour associated with a hire that is 
 * currently complete.
 */
public class Complete implements HireState, Serializable
{

	private static final long serialVersionUID = 1L;
	private Hire hire;
	
	/* 
	 * Constructs a Complete state object. Changes the state
	 * of the hired bike accordingly.
	 * 
	 * @param	hire	the hire that is currently complete
	 * @param	bikeIsDamaged	true if the hired bike has been 
	 * 							been returned damaged.
	 * 							false otherwise.
	 */
	public Complete(Hire hire, boolean bikeIsDamaged) 
	{
		this.hire = hire;
		this.hire.setDateReturned(LocalDate.now());
		hire.getBike().returnBike(bikeIsDamaged);
	}

	/*
	 * Denies payment for the hire because it has already been
	 * paid for.
	 * 
	 * @see hire.HireState#payForHire()
	 * @return 	only returns false; the hire is not suitable 
	 * 			for payment
	 */
	public boolean payForHire() 
	{
		return false;
	}
	
	/*
	 * Determines if the hire was returned late.
	 *
	 * @see 	hire.HireState#isLate()
	 * @return	true if the hire is was returned late.
	 * 			false otherwise.
	 */
	public boolean isLate() 
	{
		if (hire.getDateReturned().isAfter(hire.getEndDate()))
			return true;
		return false;
	}
	
	/*
	 * Denies the return of the hire because a hire cannot 
	 * be returned again once it has been completed.
	 *
	 * @see 	hire.HireState#returnHire(boolean)
	 * @param	bikeIsDamaged	insignificant. 
	 * @return	only returns false; the hire has already 
	 * 			been returned
	 */
	public boolean returnHire(boolean bikeIsDamaged) 
	{
		return false;
	}
	
	/* 
	 * @see java.lang.Object#toString()
	 * @return	A String describing this particular hire state
	 */
	@Override
	public String toString()
	{
		if (isLate())
			return "Complete (late)";
		
		return "Complete";
	}

}