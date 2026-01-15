package teams.student.neverendingKnights.units;

import components.upgrade.Plating;
import components.upgrade.Shield;
import components.weapon.explosive.Missile;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import teams.student.neverendingKnights.NeverendingKnightsUnit;

public class Creak extends NeverendingKnightsUnit {

    public String stage;
    private Unit unitToAttack;
    private int rand;


    public void design() {
        setFrame(Frame.MEDIUM);
        setModel(Model.DESTROYER);
        setStyle(Style.ROCKET);

        add(Shield.class);
        add(Plating.class);
        add(Missile.class);

    }
}