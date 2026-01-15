package teams.student.NeverendingKnights.units;

import components.upgrade.Shield;
import components.weapon.energy.Laser;
import components.weapon.utility.SpeedBoost;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import teams.student.NeverendingKnights.NeverendingKnightsUnit;

public class Destroyer extends NeverendingKnightsUnit {

    public void design() {
        setFrame(Frame.MEDIUM);
        setModel(Model.ARTILLERY);
        setStyle(Style.ARROW);

        add(Laser.class);
        add(SpeedBoost.class);
        add(Shield.class);
    }

}
