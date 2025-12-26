package teams.foes.rock.units;


import components.weapon.economy.Collector;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import objects.resource.Resource;
import teams.foes.rock.RockUnit;

public class Gatherer extends RockUnit
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
