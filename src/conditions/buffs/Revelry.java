package conditions.buffs;

import conditions.Buff;

public class Revelry extends Buff
{
	final float POWER_BONUS = .5f;
	final float SPEED_SCALAR = .5f;

	public Revelry(int duration) {
		super(duration);
		power = POWER_BONUS;
		mobility += SPEED_SCALAR;
	}

	public void updateFrame()
	{
		//AnimationManager.add(new AnimationRevelry(getOwner()));
	}
}
