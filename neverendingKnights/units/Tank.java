package teams.student.neverendingKnights.units;

import components.mod.offense.AresMod;
import components.mod.offense.KratosMod;
import components.mod.offense.PoseidonMod;
import components.upgrade.HeavyPlating;
import components.upgrade.HeavyShield;
import components.upgrade.Plating;
import components.upgrade.Shield;
import components.weapon.energy.Laser;
import components.weapon.kinetic.Autocannon;
import components.weapon.kinetic.HeavyAutocannon;
import components.weapon.utility.AntiMissileSystem;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import teams.student.neverendingKnights.NeverendingKnightsUnit;

public class Tank extends NeverendingKnightsUnit {

    public void design() {

        setFrame(Frame.ASSAULT);
        setModel(Model.BASTION);
        setStyle(Style.CRESCENT);

//        add(HeavyPlating.class);
        add(Plating.class);
        add(Plating.class);
        add(Plating.class);

//        add(Shield.class);

        if (enemyMissileThreat.equals("High") || enemyMissileThreat.equals("Medium")){
//            add(PoseidonMod.class);
//            add(Laser.class);
//            add(AntiMissileSystem.class);

            add(Autocannon.class);
            add(AntiMissileSystem.class);
        }
        else {
//            add(PoseidonMod.class);
//            add(Laser.class);
//            add(Laser.class);

            add(Autocannon.class);
            add(AresMod.class);
        }
//
//        add(AresMod.class);
//        add(Plating.class);
    }

}