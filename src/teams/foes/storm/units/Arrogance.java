package teams.foes.storm.units;


import components.upgrade.HeavyPlating;
import components.upgrade.Plating;
import components.weapon.kinetic.Autocannon;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import teams.foes.storm.TheStorm;
import teams.foes.storm.TheStormUnit;

public class Arrogance extends TheStormUnit
{
	
	public Arrogance(TheStorm p)
	{
		super(p);
	}
	
	public void design()
	{
		setFrame(Frame.HEAVY);
		setModel(Model.BASTION);
		setStyle(Style.ARROW);
		add(Autocannon.class);
		add(HeavyPlating.class);
		add(Plating.class);
	}



}
