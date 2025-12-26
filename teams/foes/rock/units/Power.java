package teams.foes.rock.units;


import components.upgrade.HeavyPlating;
import components.upgrade.Plating;
import components.weapon.utility.Pullbeam;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import teams.foes.rock.RockUnit;

public class Power extends RockUnit
{
	public void design()
	{
		setFrame(Frame.ASSAULT);
		setModel(Model.ARTILLERY);
		setStyle(Style.ORB);
		add(Pullbeam.class);
		add(Plating.class);
		add(HeavyPlating.class);

	}

}
