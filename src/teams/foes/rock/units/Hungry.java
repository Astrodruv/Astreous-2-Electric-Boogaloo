package teams.foes.rock.units;


import components.upgrade.Plating;
import components.weapon.utility.RepairBeam;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import teams.foes.rock.RockUnit;

public class Hungry extends RockUnit
{
	public void design()
	{
		setFrame(Frame.HEAVY);
		setModel(Model.BASTION);
		setStyle(Style.SHARK);
		add(RepairBeam.class);
		add(Plating.class);
		add(Plating.class);
	}

	public void action()
	{
		Unit ally = getHealTarget();
		if(ally != null)
		{
			moveTo(ally);
			getWeaponOne().use(ally);
		}
		else
		{
			moveTo(getHomeBase());
		}
	}

	public Unit getHealTarget()
	{
		// Find any hurt ally, and accept it as good enough once done
		// Doesn't account for level of damage or distance

		for(Unit u : getAllies())
		{
			if(u != this && u.isDamaged())
			{
				return u;
			}
		}

		return null;
	}



}
