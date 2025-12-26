package teams.foes.rock.units;


import components.upgrade.Plating;
import components.weapon.utility.ElectromagneticPulse;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import teams.foes.rock.RockUnit;

public class Drive extends RockUnit
{
	public void design()
	{
		setFrame(Frame.MEDIUM);
		setModel(Model.CRUISER);
		setStyle(Style.ARROW);
		add(ElectromagneticPulse.class);
		add(Plating.class);
	}

	public void action()
	{
		Unit u = getNearestEnemy();

		if(getWeaponOne().onCooldown())
		{
			moveTo(getHomeBase());
		}
		else
		{
			moveTo(u);
		}

		if(getDistance(u) < getWeaponOne().getRadius())
		{
			getWeaponOne().use();
		}
	}



}
