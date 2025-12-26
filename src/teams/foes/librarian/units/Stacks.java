package teams.foes.librarian.units;

import components.upgrade.HeavyShield;
import components.weapon.energy.HeavyLaser;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import teams.foes.librarian.LibrarianUnit;

public class Stacks extends LibrarianUnit
{
	public void design()
	{
		setFrame(Frame.HEAVY);
		setModel(Model.CRUISER);
		setStyle(Style.WEDGE);
		add(HeavyLaser.class);
		add(HeavyShield.class);
	}
}
