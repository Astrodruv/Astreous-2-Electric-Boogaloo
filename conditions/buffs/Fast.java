package conditions.buffs;

import conditions.Buff;
import engine.Utility;

public class Fast extends Buff
{	
	final float SPEED_BOOST = 2.5f;
	float startingScalar;

	public Fast(int duration)
	{
		super(duration);
		startingScalar = SPEED_BOOST;
		mobility = SPEED_BOOST;
	}

	public Fast(int duration, float scalar)
	{
		super(duration);
		startingScalar = SPEED_BOOST;
		mobility = SPEED_BOOST;
	}
	
	public void update()									
	{
		super.update();
		if(isActive())
		{			
			float percentComplete = (float) getTimeLeft() / (float) getDuration();
			mobility = Utility.scaleBetweenBounded(percentComplete, 1, startingScalar, 0, 1);
		}
	}
}
