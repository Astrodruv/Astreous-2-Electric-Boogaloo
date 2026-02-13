package teams.student.NeverendingKnights.units;

import components.mod.offense.HadesMod;
import components.mod.offense.NyxMod;
import components.mod.offense.PoseidonMod;
import components.upgrade.HeavyMunitions;
import components.upgrade.Munitions;
import components.upgrade.Shield;
import components.weapon.energy.HeavyLaser;
import components.weapon.energy.Laser;
import engine.states.Game;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import teams.student.NeverendingKnights.NeverendingKnights;
import teams.student.NeverendingKnights.NeverendingKnightsUnit;

public class Sniper extends NeverendingKnightsUnit {

    public void design()
    {

        // max sniper equipment
        if (getPlayer().getStoredResources() > 6 && Game.getTime() > 300 * 60) {
            setFrame(Frame.ASSAULT); // 6 resources
            setModel(Model.ARTILLERY);
            setStyle(Style.WEDGE);

            if (enemyHealers.size() > getEnemies().size() * 0.065f) { // If there are at least 6.5% healers in the fleet, equip hades
                add(HeavyLaser.class);
                add(Laser.class);
                add(Munitions.class);
                add(HadesMod.class);
            }
            else{
                add(HeavyLaser.class);
                add(Laser.class);
                add(HeavyMunitions.class);
            }
        }
        else{
            setFrame(Frame.HEAVY);
            setModel(Model.ARTILLERY);
            setStyle(Style.WEDGE);

            if (enemyHealers.size() > getEnemies().size() * 0.065f) {
                add(HeavyLaser.class);
                add(Munitions.class);
                add(HadesMod.class);
            }
            else{
                add(HeavyLaser.class);
                add(HeavyMunitions.class);
            }
        }



    }

}