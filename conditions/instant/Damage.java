package conditions.instant;

import components.DamageType;
import conditions.Instant;

public class Damage extends Instant
{	
	private final float amount;
	private final DamageType type;
	private final float blockPenetration;

	public Damage(float amount, DamageType type)
	{
		this(amount, type, 1);
	}
	
	public Damage(float amount, DamageType type, int delay)
	{
		this(amount, type, delay, 0);
	}

	public Damage(float amount, DamageType type, int delay, float blockPenetration)
	{
		super(delay);
		this.amount = amount;
		this.type = type;
		this.blockPenetration = blockPenetration;
	}


	
	public void begin()
	{	
		getOwner().takeDamage(amount, type, blockPenetration);
	}
			

}
