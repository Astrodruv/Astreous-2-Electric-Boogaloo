package teams.foes.rock.units;


import components.upgrade.Plating;
import components.weapon.kinetic.HeavyAutocannon;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import teams.foes.rock.RockUnit;

public class Devour extends RockUnit
{
	public void design()
	{
		setFrame(Frame.ASSAULT);
		setModel(Model.ARTILLERY);
		setStyle(Style.WEDGE);
		add(HeavyAutocannon.class);
		add(HeavyAutocannon.class);
		add(Plating.class);

	}

}
