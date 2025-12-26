package conditions.buffs;

import conditions.Buff;

public class Fortified extends Buff
{
	public final float BLOCK_VALUE = .30f;

	public Fortified(int duration)
	{
		super(duration);
		this.block = BLOCK_VALUE;
	}

	public Fortified(int duration, float scalar)
	{
		super(duration);
		this.block = BLOCK_VALUE * scalar;
	}

	public Fortified(int duration, int delay)
	{
		super(duration, delay);
	}
	
}
