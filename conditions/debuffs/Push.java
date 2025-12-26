package conditions.debuffs;

import conditions.Debuff;
import objects.GameObject;
import objects.entity.unit.Unit;

public class Push extends Debuff
{
	float theta;
	float maxPush;

	GameObject origin;

	public Push(int duration, int delay, float maxPush, GameObject origin)
	{
		super(duration, delay);
		this.theta = theta;
		this.maxPush = maxPush;
		this.origin = origin;

//		System.out.println("Pushing added");
		stopsAction = true;
		stopsMovement = true;

		if(getOwner() instanceof Unit)
		{
			((Unit) getOwner()).markPushed();
		}
	}

	public void updateFrame()
	{
		float curPush = maxPush * getTimeLeftPercent();
		float massScalar = 100;

		if(getOwner() instanceof Unit)
		{
			float mass = ((Unit) getOwner()).getMass();
			massScalar = mass / 100.0f;
		}

//		System.out.println("Pushing toward " + theta);

		float theta = origin.getAngleToward(getOwner().getCenterX(), getOwner().getCenterY());

//		System.out.println(theta);
		getOwner().changeSpeedForcedMovement(curPush * massScalar, theta);

//		float size = getOwner().getSize();
//		AnimationManager.add(new Flames(getOwner().getCenterX() + Utility.random(-size/6, size/6), getOwner().getCenterY() + Utility.random(-size/6, size/6), Utility.random(8, 15)));
	}
	


}
