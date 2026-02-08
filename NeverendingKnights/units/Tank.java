package teams.student.NeverendingKnights.units;

import components.mod.offense.AresMod;
import components.upgrade.HeavyMunitions;
import components.upgrade.HeavyPlating;
import components.upgrade.Plating;
import components.weapon.energy.HeavyLaser;
import components.weapon.energy.Laser;
import components.weapon.kinetic.Autocannon;
import components.weapon.utility.AntiMissileSystem;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import teams.student.NeverendingKnights.NeverendingKnightsUnit;

public class Tank extends NeverendingKnightsUnit {

    public void design() {



        // max tank equipment
        if (getPlayer().getStoredResources() >= 6) {
            setFrame(Frame.ASSAULT); // 6 resources
            setModel(Model.BASTION);
            setStyle(Style.CRESCENT);

            add(HeavyPlating.class);
            add(Plating.class);

            if (enemyMissileThreat.equals("High") || enemyMissileThreat.equals("Medium")){
                add(Autocannon.class);
                add(AntiMissileSystem.class);
            }
            else {
                add(Autocannon.class);
                add(AresMod.class);
            }
        }
        else{
            setFrame(Frame.HEAVY);
            setModel(Model.BASTION);
            setStyle(Style.CRESCENT);

            add(HeavyPlating.class);

            if (enemyMissileThreat.equals("High") || enemyMissileThreat.equals("Medium")){
                add(Autocannon.class);
                add(AntiMissileSystem.class);
            }
            else {
                add(Autocannon.class);
                add(AresMod.class);
            }
        }

    }



}