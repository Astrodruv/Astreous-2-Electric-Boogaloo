package conditions.debuffs;

import animations.AnimationManager;
import animations.effects.ShatteredIcon;
import conditions.Debuff;

public class Shattered extends Debuff
{	
	public Shattered(float block, int duration, int delay)
	{
		super(duration, delay);
		
		this.block = block;
	}
	
	public void updateFrame()
	{
		AnimationManager.add(new ShatteredIcon(getOwner()));
	}
			

}
