package conditions.buffs;

import animations.AnimationManager;
import animations.effects.AnimationOmen;
import animations.effects.Flames;
import conditions.Buff;
import engine.Utility;

public class Omen extends Buff
{
	final float ACCURACY_BONUS = .50f;
	final float RANGE_BONUS = 100;

	public Omen(int duration)
	{
		super(duration);
		accuracy = ACCURACY_BONUS;
		range = RANGE_BONUS;
	}

	public void updateFrame()
	{
		AnimationManager.add(new AnimationOmen(getOwner()));
	}
}
