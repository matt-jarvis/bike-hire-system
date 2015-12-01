package model.hire;
import java.io.Serializable;

import org.joda.time.LocalDate;

/**
 * Active is a serializable class that implements the 
 * methods/behaviour associated with a hire that is 
 * currently active.
 */
public class Active implements HireState, Serializable
{

	private static final long serialVersionUID = 1L;
	private Hire hire;
	
	/* 
	 * Constructs an Active state object. Changes the state
	 * of the hired bike accordingly.
	 * 
	 * @param	hire	the hire that is currently active.
	 */
	public Active(Hire hire) 
	{
		this.hire = hire;
		hire.getBike().collectBike();
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
	 * Checks if a hire is late. The hire's status should be altered
	 * if it is late.
	 *
	 * @see 	hire.HireState#isLate()
	 * @return	true if the hire is currently late.
	 * 			false otherwise.
	 */
	public boolean isLate() 
	{
		if (hire.getEndDate().isBefore(LocalDate.now()))
		{
			hire.setState(new Late(hire));
			return true;
		}
		return false;
	}

	/* 
	 * Represents the hire being returned. The hire's state
	 * should be changed to Complete once the hire has been 
	 * returned successfully.
	 *
	 * @see 	@see hire.HireState#returnHire(boolean)
	 * @param	bikeIsDamaged	true if the hired bike has been been returned damaged.
	 * 							false otherwise.
	 * @return	only returns true; the hire is suitable for return.
	 */
	public boolean returnHire(boolean bikeIsDamaged) 
	{
		hire.setState(new Complete(hire, bikeIsDamaged));
		return true;
	}
	
	/* 
	 * @see java.lang.Object#toString()
	 * @return	A String describing this particular hire state
	 */
	@Override
	public String toString()
	{
		return "Active";
	}

}