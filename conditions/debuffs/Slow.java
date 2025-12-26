package conditions.debuffs;

import conditions.Debuff;

public class Slow extends Debuff
{	
	
	public Slow(int duration, float scalar)
	{
		super(duration);
		mobility = scalar;
	}
	
	public Slow(int duration, float scalar, int delay)
	{
		super(duration, delay);
		mobility = scalar;
	}
	
}
