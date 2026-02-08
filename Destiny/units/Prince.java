package teams.student.Destiny.units;

import components.upgrade.Munitions;
import components.upgrade.Shield;
import components.weapon.energy.Laser;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import teams.student.Destiny.DestinyUnit;

public class Prince extends DestinyUnit
{

	public void design()
	{	
		setFrame(Frame.HEAVY);
		setModel(Model.DESTROYER);
		setStyle(Style.ARROW);

		add(Laser.class);
		add(Laser.class);
		add(Shield.class);
		add(Munitions.class);
	}


}
