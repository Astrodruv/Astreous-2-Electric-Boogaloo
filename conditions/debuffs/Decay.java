package conditions.debuffs;

import animations.AnimationManager;
import animations.effects.AnimationDecay;
import conditions.Debuff;
import engine.Utility;

public class Decay extends Debuff
{
	final int TIME_REDUCTION = 1;

	public Decay(int duration)
	{
		super(duration, 0);
		preventsRepair = true;
	}

	public Decay(int duration, int delay)
	{
		super(duration, delay);
		preventsRepair = true;
	}

	public void updateFrame()
	{
		float size = getOwner().getSize();
		AnimationManager.add(new AnimationDecay(getOwner().getCenterX() + Utility.random(-size/6, size/6), getOwner().getCenterY() + Utility.random(-size/6, size/6), Utility.random(8, 15), getOwner()));
		getOwner().getConditions().reduceBuffs(TIME_REDUCTION);
	}
	

}
