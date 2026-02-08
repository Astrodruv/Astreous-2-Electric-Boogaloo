package teams.student.Destiny.units;

import components.mod.offense.PoseidonMod;
import components.upgrade.HeavyShield;
import components.upgrade.Munitions;
import components.upgrade.Plating;
import components.weapon.energy.Laser;
import components.weapon.kinetic.Autocannon;
import components.weapon.utility.AntiMissileSystem;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import teams.student.Destiny.Destiny;
import teams.student.Destiny.DestinyUnit;

public class Juggernaut extends DestinyUnit
{

    public void design()
    {
        setFrame(Frame.ASSAULT);
        setModel(Model.BASTION);
        setStyle(Style.CRESCENT);

        add(Laser.class);
        if (Destiny.enemyMissiles > 3) {
            add(AntiMissileSystem.class);
        }
        else{
            add(Laser.class);
        }
        add(HeavyShield.class);
        add(PoseidonMod.class);
    }



}
