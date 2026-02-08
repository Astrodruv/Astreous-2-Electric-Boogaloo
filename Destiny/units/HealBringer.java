package teams.student.Destiny.units;

import components.mod.healing.PerseusMod;
import components.mod.offense.PoseidonMod;
import components.upgrade.HeavyShield;
import components.upgrade.Munitions;
import components.upgrade.Plating;
import components.upgrade.Shield;
import components.weapon.energy.Laser;
import components.weapon.kinetic.Autocannon;
import components.weapon.utility.HeavyRepairBeam;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import teams.student.Destiny.DestinyUnit;

public class HealBringer extends DestinyUnit
{

    public void design()
    {
        setFrame(Frame.HEAVY);
        setModel(Model.DESTROYER);
        setStyle(Style.BUBBLE);

        add(HeavyRepairBeam.class);
        add(PerseusMod.class);

        // add(Shield.class); only use if model is prototype
    }

    public void action() {
        Unit allyToHeal = getBestAllyToHealInRadius(getMaxRange() * 3, Juggernaut.class);
        if (allyToHeal == null && !getNearestAlly().getClass().equals(this.getClass())) allyToHeal = getNearestAlly();

        if (allyToHeal != null){
            if (getPlayer().isLeftPlayer()) moveTo(allyToHeal.getCenterX() - (getMaxRange() * 0.5f), allyToHeal.getCenterY());
            else if (getPlayer().isRightPlayer()) moveTo(allyToHeal.getCenterX() + (getMaxRange() * 0.5f), allyToHeal.getCenterY());
            if (getDistance(allyToHeal) < getMaxRange()){
                getWeaponOne().use(allyToHeal);
            }
        }
        else{
            moveTo(getHomeBase());
        }
    }
}
