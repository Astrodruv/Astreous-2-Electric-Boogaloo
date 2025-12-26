package teams.foes.arsonist.units;

import components.mod.offense.CerberusMod;
import components.weapon.explosive.HeavyMissile;
import components.weapon.explosive.Missile;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import teams.foes.arsonist.TheArsonistUnit;

public class Sizzler extends TheArsonistUnit
{
	public void design()
	{
		setFrame(Frame.HEAVY);
		setModel(Model.ARTILLERY);
		setStyle(Style.DRAGON);
		add(HeavyMissile.class);
		add(Missile.class);
		add(CerberusMod.class);
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
