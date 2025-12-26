package teams.foes.farmer.units;

import components.upgrade.HeavyShield;
import components.upgrade.Shield;
import components.weapon.energy.Laser;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import teams.foes.farmer.TheFarmer;
import teams.foes.farmer.FarmerUnit;

public class Potato extends FarmerUnit
{
	
	public Potato(TheFarmer p)
	{
		super(p);
	}
	
	public void design()
	{
		setFrame(Frame.HEAVY);
		setModel(Model.BASTION);
		setStyle(Style.WEDGE);

		add(Laser.class);
		add(Shield.class);
		add(HeavyShield.class);
	}

}
