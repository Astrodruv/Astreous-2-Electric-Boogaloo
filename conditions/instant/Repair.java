package conditions.instant;

import conditions.Instant;

public class Repair extends Instant
{	
	private final float amount;
	private final boolean restoresShields;

	public Repair(float amount, boolean restoresShields)
	{
		this(amount, 1, restoresShields);}
	
	public Repair(float amount, int delay, boolean restoresShields)
	{
		super(delay);
		this.amount = amount;
		this.restoresShields = restoresShields;
	}
	
	public void begin()
	{	
		getOwner().repairHull(amount, restoresShields);
	}
			

}
