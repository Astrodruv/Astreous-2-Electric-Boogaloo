package teams.foes.arsonist.units;

import components.upgrade.HeavyPlating;
import components.weapon.kinetic.Autocannon;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import teams.foes.arsonist.TheArsonistUnit;

public class Titan extends TheArsonistUnit
{
	public void design()
	{
		setFrame(Frame.ASSAULT);
		setModel(Model.BASTION);
		setStyle(Style.ORB);
		add(Autocannon.class);
		add(HeavyPlating.class);
		add(HeavyPlating.class);
	}

	public void movement()
	{
		Unit enemy = getNearestEnemy();

		// Only attack if I have enough units and the enemy is out of reach, otherwise go home
		if(getPlayer().countMyUnits(Titan.class) > 8 && enemy != null && getDistance(enemy) > getMaxRange())
		{
			moveTo(enemy);
		}

		// Defend if the enemy is nearby
		else if(getDistance(enemy) < getMaxRange() * 5 && getDistance(enemy) > getMaxRange())
		{
			moveTo(enemy);
		}

		// Go home
		else
		{
			moveTo(getHomeBase());
		}
	}

}
