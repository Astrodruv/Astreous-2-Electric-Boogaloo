package teams.student.TestTeam.units;

import components.mod.offense.CerberusMod;
import components.upgrade.Shield;
import components.weapon.energy.Laser;
import components.weapon.explosive.Missile;
import components.weapon.utility.SpeedBoost;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import teams.student.TestTeam.TestTeamUnit;

public class Destroyer extends TestTeamUnit {

    public void design() {
        setFrame(Frame.MEDIUM);
        setModel(Model.ARTILLERY);
        setStyle(Style.ARROW);

        add(Laser.class);
        add(SpeedBoost.class);
        add(Shield.class);

    }
}
