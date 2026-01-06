package teams.student.NeverendingKnights.units.combat;

import components.upgrade.Plating;
import components.upgrade.Shield;
import components.weapon.energy.Laser;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import teams.student.NeverendingKnights.NeverendingKnightsUnit;
import teams.student.myTeam.MyTeamUnit;

public class Fighter extends NeverendingKnightsUnit
{
	public void design()
	{	
		setFrame(Frame.HEAVY);
		setModel(Model.CRUISER);
		setStyle(Style.ARROW);

		add(Laser.class);
		add(Laser.class);
		add(Shield.class);
		add(Plating.class);

	}



}
