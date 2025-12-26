package teams.foes.arsonist.units;

import components.weapon.explosive.Missile;
import components.weapon.utility.SpeedBoost;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import teams.foes.arsonist.TheArsonistUnit;

public class Nascar extends TheArsonistUnit
{
	public void design()
	{
		setFrame(Frame.LIGHT);
		setModel(Model.STRIKER);
		setStyle(Style.DAGGER);
		add(Missile.class);
		add(SpeedBoost.class);
	}

	public void action()
	{
		// If hit in the last few frames, speed boost and go home
		if(this.getHitTimer() < 30)
		{
			getWeaponTwo().use();
			moveTo(getHomeBase());
		}
		movement();
	}

	public void movement()
	{
		// Try to move to THE FLANK POSITION
		if(getTimeAlive() < 40 * 60)
		{
			moveTo(getHomeBase().getX(), -3000);
			return;
		}

		// Try to move toward MIDDLE
		if(getTimeAlive() < 80 * 60)
		{
			moveTo(0, -3000);
			return;
		}

		Unit enemy = getNearestEnemy();

		// Go to nearest enemy that is out of range
		if(getDistance(enemy) > getMaxRange())
		{
			moveTo(enemy);
		}

		// Fire and get away from it
		else
		{
			getWeaponOne().use(enemy);

			turnTo(enemy);
			turnAround();
			move();

			// Speed boost
			getWeaponTwo().use();
		}
	}
}
