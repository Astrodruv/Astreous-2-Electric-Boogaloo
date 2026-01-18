package teams.student.neverendingKnights.units;

import components.mod.healing.ApolloMod;
import components.mod.healing.DionysusMod;
import components.mod.healing.PerseusMod;
import components.mod.healing.PythiaMod;
import components.upgrade.Shield;
import components.weapon.Weapon;
import components.weapon.economy.Collector;
import components.weapon.economy.Drillbeam;
import components.weapon.utility.CommandRelay;
import components.weapon.utility.HeavyRepairBeam;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import teams.student.neverendingKnights.NeverendingKnightsUnit;

import java.util.ArrayList;

public class Healer extends NeverendingKnightsUnit {
    private boolean tankHealer;
    private Unit unitToHeal;
    @Override
    public void design() {
        setFrame(Frame.MEDIUM);
        setModel(Model.PROTOTYPE);
        setStyle(Style.BOXY);
        setIfShouldBeTankHealer();

        //4 slots
        add(Shield.class);
        add(HeavyRepairBeam.class);
        //only for tanks ------
        if (tankHealer) {
            add(PerseusMod.class);
        }
        // ---------
        else if (opponentHasLongRangeWeapons()) {
            add(PythiaMod.class);
        }
        else if (getAverageEnemyMaxSpeed() > getAverageUnitMaxSpeed() * 1.4) {
            add(DionysusMod.class);
        }
        else {
            add(ApolloMod.class);
        }
        unitToHeal = null;

    }

    @Override
    public void act() {
        unitToHeal = getBestTargetToHeal();
        attack(getWeaponOne());
//        attack(getWeaponTwo());
        movement();
    }

    @Override
    public void movement() {
        if (unitToHeal != null) {
            moveTo(unitToHeal);
        }
        else {
            moveTo(mainPushRallyX,mainPushRallyY);
        }
    }

    public Unit getBestTargetToHeal() {
        ArrayList<Unit> alliesToHeal = null;
        if (tankHealer) {
            alliesToHeal = getPlayer().getMyUnits(Tank.class);
        }
        else {
            alliesToHeal = getAllies();
        }

        Unit bestTargetToHeal = null;
        int bestScore = 0;
        for (Unit u: alliesToHeal) {
            int score = 0;
            score += damageScoreHealer(u);
            score += distanceScoreHealer(u);
            score += healthScoreHealer(u);
            if (bestTargetToHeal == null) {
                bestTargetToHeal = u;
                bestScore = score;
            }
            else {
                if (score > bestScore) {
                    bestTargetToHeal = u;
                    bestScore = score;
                }
            }
        }
        return bestTargetToHeal;
    }

    protected int distanceScoreHealer(Unit u) {
        if (getDistance(u) < getMaxRange()) {
            return 4000;
        }
        else if (getDistance(u) < getMaxRange() * 1.2) {
            return 3500;
        }
        else if (getDistance(u) < getMaxRange() * 1.7) {
            return 3000;
        }
        else if (getDistance(u) < getMaxRange() * 2.5) {
            return 2400;
        }
        else {
            return 2000;
        }
    }

    protected int healthScoreHealer(Unit u) {
        if (u.getCurEffectiveHealth() < u.getMaxEffectiveHealth()* 0.2) {
            return 5000;
        }
        else if (u.getCurEffectiveHealth() < u.getMaxEffectiveHealth()*0.4) {
            return 4000;
        }
        else if (u.getCurEffectiveHealth() < u.getMaxEffectiveHealth()*0.6) {
            return 3000;
        }
        else if (u.getCurEffectiveHealth() < u.getMaxEffectiveHealth()*0.8) {
            return 1500;
        }
        else {
            return 500;
        }
    }

    protected int damageScoreHealer(Unit u) {
        if (u instanceof Sniper) {
            return 2000;
        }
        if (u instanceof Tank) {
            if (!tankHealer) {
                return 1500;
            }
        }
        if (u instanceof Pest) {
            return 300;
        }
        if (u instanceof MissileLauncher) {
            return 2500;
        }
        if (u instanceof Creak) {
            return 10;
        }
        if (u instanceof Commander) {
            return 3000;
        }
        return 0;
    }

    @Override
    public void attack(Weapon w) {
        if (unitToHeal != null && getDistance(unitToHeal) < getMaxRange() && w!= null) {
            w.use(unitToHeal);
        }
    }

    public void setIfShouldBeTankHealer() {
        //determine if it needs to focus on tanks
        ArrayList<Unit> healers = getPlayer().getMyUnits(Healer.class);
        int totalTankHealers = 0;
        for (Unit h: healers) {
            if (h instanceof Healer) {
                if (((Healer) h).isTankHealer()) {
                    totalTankHealers++;
                }
            }
        }
        if (totalTankHealers >= 3 * healers.size()/4) {
            tankHealer = true;
        }
        else {
            tankHealer = false;
        }
    }

    public boolean isTankHealer() {
        return tankHealer;
    }
}
