package teams.student.neverendingKnights.units;

import components.mod.offense.AresMod;
import components.upgrade.Plating;
import components.weapon.kinetic.Autocannon;
import components.weapon.kinetic.HeavyAutocannon;
import components.weapon.utility.AntiMissileSystem;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import teams.student.neverendingKnights.NeverendingKnightsUnit;

public class Tank extends NeverendingKnightsUnit {

    public void design() {

        setFrame(Frame.HEAVY);
        setModel(Model.BASTION);
        setStyle(Style.CRESCENT);

        if (enemyMissileThreat.equals("High") || enemyMissileThreat.equals("Medium")){
            add(Autocannon.class);
            add(AntiMissileSystem.class);
        }
        else {
//            add(Autocannon.class);
//            add(Autocannon.class);
            add(HeavyAutocannon.class);
        }

        add(AresMod.class);
        add(Plating.class);
    }

}