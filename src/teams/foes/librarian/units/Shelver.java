package teams.foes.librarian.units;

import components.upgrade.Shield;
import components.weapon.utility.Pullbeam;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import teams.foes.librarian.LibrarianUnit;

public class Shelver extends LibrarianUnit
{
	public void design()
	{
		setFrame(Frame.MEDIUM);
		setModel(Model.CRUISER);
		setStyle(Style.DAGGER);
		add(Pullbeam.class);
		add(Shield.class);
	}

	public void movement()
	{
		Unit enemy = getNearestEnemy();

		if( getDistance(enemy) > getMaxRange())
		{
			moveTo(enemy);
		}
		else
		{
			moveTo(getHomeBase());
		}
	}
}
