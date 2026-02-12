package teams.student.NeverendingKnights.units.buffs;

import components.mod.healing.ApolloMod;
import components.upgrade.Shield;
import components.weapon.Weapon;
import components.weapon.economy.Collector;
import components.weapon.economy.Drillbeam;
import components.weapon.utility.CommandRelay;
import components.weapon.utility.RepairBeam;
import conditions.buffs.Glory;
import conditions.buffs.Omen;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import teams.student.NeverendingKnights.NeverendingKnightsUnit;
import teams.student.NeverendingKnights.units.Destroyer;
import teams.student.NeverendingKnights.units.Pest;

import java.util.ArrayList;

public class GloryHealer extends NeverendingKnightsUnit {
    private Unit unitToHeal;

    public void design() {
        setFrame(Frame.LIGHT);
        setModel(Model.PROTOTYPE);
        setStyle(Style.BOXY);

        add(Shield.class);
        add(RepairBeam.class);
        add(ApolloMod.class);

        unitToHeal = null;
    }

    public void act() {
        unitToHeal = getBestUnitToHeal();
        attack(getWeaponOne());
        movement();
        dbgMessage("" + unitToHeal);
        dbgMessage("" + getNearestAllyAttacker());
    }

    public void movement() {
        if (getNearestAllyAttacker() != null) {
            if (getNearestEnemyThreat() != null) {
                if (getDistance(getNearestEnemyThreat()) > getNearestEnemyThreat().getMaxRange() * 0.5f) {
                    if (unitToHeal != null) {
                        if (getPlayer().isLeftPlayer()) moveTo(unitToHeal.getCenterX() - 100, unitToHeal.getCenterY());
                        else if (getPlayer().isRightPlayer())
                            moveTo(unitToHeal.getCenterX() + 100, unitToHeal.getCenterY());
                    } else {
                        if (getPlayer().isLeftPlayer())
                            moveTo(getNearestAllyAttacker().getCenterX(), getNearestAllyAttacker().getCenterY());
                        else if (getPlayer().isRightPlayer())
                            moveTo(getNearestAllyAttacker().getCenterX(), getNearestAllyAttacker().getCenterY());
                    }
                } else {
                    turnTo(getNearestEnemyThreat());
                    turnAround();
                    move();
                }
            } else {
                if (unitToHeal != null) {
                    if (getPlayer().isLeftPlayer()) moveTo(unitToHeal.getCenterX() - 100, unitToHeal.getCenterY());
                    else if (getPlayer().isRightPlayer())
                        moveTo(unitToHeal.getCenterX() + 100, unitToHeal.getCenterY());
                } else {
                    if (getPlayer().isLeftPlayer())
                        moveTo(getNearestAllyAttacker().getCenterX(), getNearestAllyAttacker().getCenterY());
                    else if (getPlayer().isRightPlayer())
                        moveTo(getNearestAllyAttacker().getCenterX(), getNearestAllyAttacker().getCenterY());
                }
            }
        }
        else{
            moveTo(getHomeBase());
            unitToHeal = getHomeBase();
        }
    }

    public Unit getBestUnitToHeal(){
        ArrayList<Unit> unitsToHeal = new ArrayList<>();

        for (Unit u : getAlliesInRadius(1000)){
            if (!u.hasCondition(Glory.class) && !u.hasWeapon(RepairBeam.class) && !u.getFrame().equals(Frame.COLOSSAL)
                    && !u.hasComponent(Drillbeam.class) && !u.hasComponent(Collector.class) && !u.hasWeapon(CommandRelay.class)
                    && !(u instanceof Pest) && !(u instanceof Destroyer)){
                unitsToHeal.add(u);
            }
        }

        unitsToHeal.sort((a1, a2) -> Float.compare(getDistance(a1), getDistance(a2)));

        if (!unitsToHeal.isEmpty()) return unitsToHeal.getFirst();
        else return null;
    }

    public void attack(Weapon w) {
        if (unitToHeal != null && getDistance(unitToHeal) < getMaxRange() && w!= null) {
            w.use(unitToHeal);
        }
    }
}
