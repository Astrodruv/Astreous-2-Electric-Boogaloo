package teams.student.neverendingKnights.units;

import components.mod.offense.AresMod;
import components.upgrade.HeavyMunitions;
import components.upgrade.Munitions;
import components.upgrade.Shield;
import components.weapon.energy.Laser;
import components.weapon.kinetic.Autocannon;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import teams.student.neverendingKnights.NeverendingKnightsUnit;

public class MediumSniper extends NeverendingKnightsUnit {
    @Override
    public void design() {
        setFrame(Frame.MEDIUM);
        setModel(Model.ARTILLERY);
        setStyle(Style.CRESCENT);

        add(Laser.class);
        add(Munitions.class);
        add(Shield.class);
    }
}
