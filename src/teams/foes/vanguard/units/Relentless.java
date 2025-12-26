package teams.foes.vanguard.units;


import components.upgrade.Shield;
import components.weapon.kinetic.Autocannon;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import teams.foes.vanguard.TheVanguardUnit;

public class Relentless extends TheVanguardUnit
{
	int rallyPointY;

	public void design()
	{
		setFrame(Frame.LIGHT);
		setModel(Model.STRIKER);
		setStyle(Style.DAGGER);
		add(Autocannon.class);
		add(Shield.class);

		// Randomly determine top or bottom at creation
		rallyPointY = 3000;
		if(Math.random() < .5)
		{
			rallyPointY *= -1;
		}

	}

	public void action()
	{
		attack();
		movement();
	}

	public void attack()
	{
		Unit enemy = getNearestEnemy();

		if(enemy != null)
		{
			getWeaponOne().use(enemy);
		}
	}

	public void movement()
	{
		// Retreat if my shield is zero
		if(getCurShield() == 0)
		{
			moveTo(getHomeBase());
		}

		// Go to rally if I am just spawning
		if(getTimeAlive() < 50 * 60)
		{
			moveTo(0, rallyPointY);
		}

		Unit enemy = getNearestEnemy();

		if(enemy != null)
		{
			// Go to rally if there are not many of me
			if(getPlayer().countMyUnits(Relentless.class) < 10 && getDistance(enemy) > 2000)
			{
				moveTo(0, rallyPointY);
			}
			// Combat mode - approach if out of range
			else if(getDistance(enemy) > getMaxRange())
			{
				moveTo(enemy);
			}
			// When backing off, go to home base
			else
			{
				moveTo(getHomeBase());
			}
		}
	}


}
