package teams.student.simple.units;


import components.weapon.economy.Collector;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import objects.resource.Resource;
import teams.student.simple.Simple;
import teams.student.simple.SimpleUnit;

public class Gatherer extends SimpleUnit
{
	public void design()
	{
		setFrame(Frame.LIGHT);
		setModel(Model.TRANSPORT);
		setStyle(Style.BUBBLE);
		add(Collector.class);
	}

	public void action() 
	{
		returnResources();
		gatherResources();
	}

	public void returnResources()
	{
		if(isFull())
		{
			moveTo(getHomeBase());
			deposit();
		}
	}

	public void gatherResources()
	{
		if(hasCapacity())
		{
			Resource r = getNearestResource();

			// Try and collect the nearest resource
			if(r != null) {
				moveTo(r);
				((Collector) getWeaponOne()).use(r);
			}
		}
	}


}
