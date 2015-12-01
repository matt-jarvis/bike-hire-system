package model.hire;
import java.io.Serializable;

/**
 * PaymentPending is a serializable class that implements the 
 * methods/behaviour associated with a hire that is 
 * currently awaiting payment.
 */
public class PaymentPending implements HireState, Serializable
{

	private static final long serialVersionUID = 1L;
	private Hire hire;
	
	/* 
	 * Constructs a PaymentPending state object.
	 * 
	 * @param	hire	the hire that is currently awaiting payment.
	 */
	public PaymentPending(Hire hire) 
	{
		this.hire = hire;
	}

	/*
	 * Represents a hire being paid for. The hire's state should be 
	 * changed accordingly after payment has been made.
	 * 
	 * @see hire.HireState#payForHire()
	 * @return 	only returns true; this hire is suitable for payment
	 */
	public boolean payForHire() 
	{
		hire.setState(new Active(hire));
		return true;
	}

	/*
	 * Confirms that the hire is not currently late.
	 *
	 * @see 	hire.HireState#isLate()
	 * @return	only returns false; the hire has not yet been paid for,
	 * 			hence it cannot be late
	 */
	public boolean isLate() 
	{
		return false;
	}

	/*
	 * 
	 * Denies the return of the hire because a hire can 
	 * only be returned after it has been paid for and 
	 * activated.
	 *
	 * @see 	hire.HireState#returnHire(boolean)
	 * @param	bikeIsDamaged	insignificant. 
	 * @return	only returns false; the hire has not yet
	 * 			been paid for/activated
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
		return "Payment pending";
	}

}