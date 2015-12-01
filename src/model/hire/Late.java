package model.hire;

import java.io.Serializable;

/**
 * Late is a serializable class that implements the 
 * methods/behaviour associated with a hire that is 
 * currently late.
 */
public class Late implements HireState, Serializable
{

	private static final long serialVersionUID = 1L;
	private Hire hire;
	
	/* 
	 * Constructs a Late state object.
	 * 
	 * @param	hire	the hire that is currently late.
	 */
	public Late(Hire hire) 
	{
		this.hire = hire;
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
	 * Confirms that the hire is currently late.
	 *
	 * @see 	hire.HireState#isLate()
	 * @return	only returns true because the hire is late
	 */
	public boolean isLate() 
	{
		return true;
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
		return "Late";
	}

}