package teams.foes.farmer.units;

import components.upgrade.Shield;
import components.weapon.explosive.HeavyMissile;
import components.weapon.explosive.Missile;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import teams.foes.farmer.TheFarmer;
import teams.foes.farmer.FarmerUnit;

public class Corn extends FarmerUnit
{
	
	public Corn(TheFarmer p)
	{
		super(p);
	}
	
	public void design()
	{
		setFrame(Frame.HEAVY);
		setModel(Model.ARTILLERY);
		setStyle(Style.DAGGER);

		add(Missile.class);
		add(HeavyMissile.class);
		add(Shield.class);
		
	}

}
