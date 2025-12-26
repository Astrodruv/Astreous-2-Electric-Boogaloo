package conditions.debuffs;

import animations.AnimationManager;
import animations.effects.AnimationBlind;
import animations.effects.AnimationDecay;
import conditions.Debuff;
import engine.Utility;

public class Blind extends Debuff
{
	final float ACCURACY_REDUCTION = .50f;
	final int RANGE_REDUCTION = 100;

	public Blind(int duration)
	{
		this(duration, 0);
	}

	public Blind(int duration, int delay)
	{
		super(duration, delay);
		accuracy -= ACCURACY_REDUCTION;
		range -= RANGE_REDUCTION;
	}

	public void updateFrame()
	{
		float size = getOwner().getSize();
		AnimationManager.add(new AnimationBlind(getOwner().getCenterX() + Utility.random(-size/6, size/6), getOwner().getCenterY() + Utility.random(-size/6, size/6), Utility.random(8, 15)));
	}
	

}
