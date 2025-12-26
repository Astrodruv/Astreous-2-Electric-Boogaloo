package conditions.buffs;

import animations.AnimationManager;
import animations.effects.CommandAuraIcon;
import conditions.Buff;

public class Powerful extends Buff
{
	float POWER = .3f;

	public Powerful(int duration)
	{
		super(duration);
		this.power = POWER;
	}
	
	public Powerful(int duration, int delay)
	{
		super(duration, delay);
	}
	
	public void updateFrame()
	{
		AnimationManager.add(new CommandAuraIcon(getOwner()));
//		getOwner().dbgMessage(damageScalar);
	}
	
}
