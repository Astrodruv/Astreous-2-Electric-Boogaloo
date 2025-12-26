package teams.foes.storm.units;


import components.weapon.explosive.HeavyMissile;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import teams.foes.storm.TheStorm;
import teams.foes.storm.TheStormUnit;

public class Procrastination extends TheStormUnit
{
	
	public Procrastination(TheStorm p)
	{
		super(p);
	}
	
	public void design()
	{
		setFrame(Frame.HEAVY);
		setModel(Model.ARTILLERY);
		setStyle(Style.CANNON);
		add(HeavyMissile.class);
		add(HeavyMissile.class);
	}

}
