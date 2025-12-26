package conditions.debuffs;

import conditions.Debuff;
import objects.GameObject;
import objects.entity.unit.Unit;

public class Pull extends Debuff
{
	float theta;
	float maxPull;

	GameObject origin;

	public Pull(int duration, int delay, float maxPull, GameObject origin)
	{
		super(duration, delay);
		this.theta = theta;
		this.maxPull = maxPull;
		this.origin = origin;

//		System.out.println("Pushing added");
		stopsAction = false;
		stopsMovement = true;

		if(getOwner() instanceof Unit)
		{
			((Unit) getOwner()).markPulled();
		}
	}

	public void updateFrame()
	{
		float curPull = maxPull * getTimeLeftPercent();
		float massScalar = 100;

		if(getOwner() instanceof Unit)
		{
			massScalar = ((Unit)getOwner()).getMass() / 100.0f;
		}

//		System.out.println("Pushing toward " + theta);

		float theta = getOwner().getAngleToward(origin.getCenterX(), origin.getCenterY());


//		System.out.println(theta);
		getOwner().changeSpeedForcedMovement(curPull * massScalar, theta);

//		float size = getOwner().getSize();
//		AnimationManager.add(new Flames(getOwner().getCenterX() + Utility.random(-size/6, size/6), getOwner().getCenterY() + Utility.random(-size/6, size/6), Utility.random(8, 15)));
	}
	


}
