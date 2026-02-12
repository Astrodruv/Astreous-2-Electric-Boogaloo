package teams.student.NeverendingKnights.units.buffs;

import components.mod.healing.PerseusMod;
import components.upgrade.Shield;
import components.weapon.Weapon;
import components.weapon.utility.HeavyRepairBeam;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import teams.student.NeverendingKnights.NeverendingKnightsUnit;
import teams.student.NeverendingKnights.units.Tank;

import java.util.ArrayList;

public class TankHealer extends NeverendingKnightsUnit {
    private Unit unitToHeal;

    public void design() {
        setFrame(Frame.MEDIUM);
        setModel(Model.PROTOTYPE);
        setStyle(Style.BOXY);

        add(Shield.class);
        add(HeavyRepairBeam.class);
        add(PerseusMod.class);
        unitToHeal = null;
    }

    public void act() {
        unitToHeal = getBestTankToHeal();
        attack(getWeaponOne());
        movement();
    }

    public void movement() {
        if (getNearestEnemyThreat() != null && getNearestEnemyThreat() != getEnemyBase()) {
            if (getDistance(getNearestEnemyThreat()) > getNearestEnemyThreat().getMaxRange() * 0.5f) {
                if (unitToHeal != null) {
                    if (getPlayer().isLeftPlayer()) moveTo(unitToHeal.getCenterX() - 300, unitToHeal.getCenterY());
                    else if (getPlayer().isRightPlayer())
                        moveTo(unitToHeal.getCenterX() + 300, unitToHeal.getCenterY());
                } else {
                    if (getPlayer().isLeftPlayer()) moveTo(getNearestAllyAttacker().getCenterX() - 300, getNearestAllyAttacker().getCenterY());
                    else if (getPlayer().isRightPlayer()) moveTo(getNearestAllyAttacker().getCenterX() + 300, getNearestAllyAttacker().getCenterY());
                }
            } else {
                turnTo(getNearestEnemyThreat());
                turnAround();
                move();
            }
        }
        else{
            if (unitToHeal != null) {
                if (getPlayer().isLeftPlayer()) moveTo(unitToHeal.getCenterX() - 300, unitToHeal.getCenterY());
                else if (getPlayer().isRightPlayer())
                    moveTo(unitToHeal.getCenterX() + 300, unitToHeal.getCenterY());
            } else {
                if (getPlayer().isLeftPlayer()) moveTo(getNearestAllyAttacker().getCenterX() - 300, getNearestAllyAttacker().getCenterY());
                else if (getPlayer().isRightPlayer()) moveTo(getNearestAllyAttacker().getCenterX() + 300, getNearestAllyAttacker().getCenterY());
            }
        }
    }

    public Unit getBestTankToHeal(){
        ArrayList<Unit> tanksToHeal = tanks;
        tanksToHeal.sort((a1, a2) -> Float.compare(a1.getPercentEffectiveHealth(), a2.getPercentEffectiveHealth()));

        if (!tanksToHeal.isEmpty()) return tanksToHeal.getFirst();
        else return null;
    }

    public void attack(Weapon w) {
        if (unitToHeal != null && getDistance(unitToHeal) < getMaxRange() && w!= null) {
            w.use(unitToHeal);
        }
    }
}
