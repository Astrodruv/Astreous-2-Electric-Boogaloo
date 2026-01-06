package teams.student.NeverendingKnights.units.combat.strategies;

import components.weapon.energy.HeavyLaser;
import components.weapon.energy.Laser;
import components.weapon.explosive.HeavyMissile;
import components.weapon.explosive.Missile;
import components.weapon.kinetic.Autocannon;
import components.weapon.kinetic.HeavyAutocannon;
import components.weapon.utility.*;
import objects.entity.unit.BaseShip;
import objects.entity.unit.Model;
import objects.entity.unit.Unit;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import teams.student.NeverendingKnights.units.combat.CombatManager;

import java.util.ArrayList;

public class CoordinateTargetedEnemyStrategy extends CombatStrategy{
    private Unit target;
    private float pastTargetX;
    private float pastTargetY;
    public CoordinateTargetedEnemyStrategy(CombatManager combatManager) {
        super(combatManager);
        target = null;
    }

    public void update() {
        super.update();
        if (target == null || target instanceof BaseShip) {
            target = decideTarget();
        }
        if (target.isDead()) {
            target = decideTarget();
        }
    }

    public Unit getTarget() {return target;}

    public Unit decideTarget() {
        //choose the best unit to attack
        ArrayList<Unit> enemies = enemyUnits;
        int bestPoints = 0;
        Unit bestEnemy = null;
        for (Unit e: enemies) {
            int totalPoints = statusAndRangeScore(e) + typeShipScore(e) + speedScore(e) + damageScore(e) + healthScore(e) + distanceScore(e) + numberEnemiesScore(e);
            if (bestPoints == 0 && bestEnemy == null) {
                bestPoints = totalPoints;
                bestEnemy = e;
            }
            else {
                if (totalPoints >= bestPoints) {
                    bestEnemy = e;
                    bestPoints = totalPoints;
                }
            }
        }
        return bestEnemy;
    }

    private int numberEnemiesScore(Unit u) {
        if (u instanceof BaseShip) {
            if (enemyUnits.size() > 10) {
                return -1000000;
            }
        }
        return 0;
    }

    private int distanceScore(Unit u) {
        if (u.getDistance(player.getMyBase()) < 200) {
            return 4000;
        }
        if (u.getDistance(player.getMyBase()) < 500) {
            return 3000;
        }
        if (u.getDistance(player.getMyBase()) < 1000) {
            return 2000;
        }
        if (u.getDistance(player.getMyBase()) < 2000) {
            return 500;
        }
        if (u.getDistance(player.getMyBase()) < 4000) {
            return 10;
        }
        return 0;
    }

    private int statusAndRangeScore(Unit u) {
        if (u.hasComponent(CommandRelay.class)) {
            return 4000;
        }
        else if (u.hasComponent(Pullbeam.class)) {
            return 3800;
        }
        else if (u.hasComponent(SpeedBoost.class)) {
            return 3500;
        }
        else if (u.hasComponent(ElectromagneticPulse.class)) {
            return 3000;
        }
        else if (u.hasComponent(GravitationalRift.class)) {
            return 2500;
        }
        else if (u.hasComponent(HeavyRepairBeam.class)) {
            return 2400;
        }
        else if (u.getWeaponOne() instanceof RepairBeam && u.getWeaponTwo() instanceof RepairBeam) {
            return 2300;
        }
        else if (u.getWeaponOne() instanceof RepairBeam || u.getWeaponTwo() instanceof RepairBeam) {
            return 1000;
        }
        return 0;
    }

    private int typeShipScore(Unit u) {
        if (u.getModel().equals(Model.STRIKER)) {
            return 2500;
        }
        else if (u.getModel().equals(Model.ARTILLERY)) {
            return 1900;
        }
        else if (u.getModel().equals(Model.BASTION)) {
            return 1700;
        }
        else if (u.getModel().equals(Model.DESTROYER)) {
            return 1500;
        }
        else if (u.getModel().equals(Model.TRANSPORT)) {
            return 1100;
        }
        return 1000;
    }

    private int speedScore(Unit u) {
        if (u.getFrame().getMass()  <= 12) {
            return 3000;
        }
        else if (u.getFrame().getMass() <= 25) {
            return 2700;
        }
        else if (u.getFrame().getMass() <= 35) {
            return 2200;
        }
        else if (u.getFrame().getMass() <= 45) {
            return 1900;
        }
        return 1500;
    }

    private int damageScore(Unit u) {
        if (u.getWeaponOne() instanceof HeavyMissile || u.getWeaponTwo() instanceof HeavyMissile ) {
            return 2000;
        }
        else if (u.getWeaponOne() instanceof Missile && u.getWeaponTwo() instanceof Missile) {
            return 1900;
        }
        else if (u.getWeaponOne() instanceof HeavyLaser || u.getWeaponTwo() instanceof HeavyLaser) {
            return 1800;
        }
        else if (u.getWeaponOne() instanceof Laser && u.getWeaponTwo() instanceof Laser) {
            return 1700;
        }
        else if (u.getWeaponOne() instanceof Missile || u.getWeaponTwo() instanceof Missile) {
            return 1500;
        }
        else if (u.getWeaponOne() instanceof Laser || u.getWeaponTwo() instanceof Laser) {
            return 1000;
        }
        else if (u.getWeaponOne() instanceof HeavyAutocannon || u.getWeaponTwo() instanceof HeavyAutocannon) {
            return 500;
        }
        else if (u.getWeaponOne() instanceof Autocannon && u.getWeaponTwo() instanceof Autocannon) {
            return 10;
        }
        return 5;
    }

    private int healthScore(Unit u) {
        //gets a score to deduct based on health of the enemy
        if (u.getPercentEffectiveHealth() / u.getMaxEffectiveHealth() < 0.2) {
            return 2500;
        }
        else if (u.getPercentEffectiveHealth() / u.getMaxEffectiveHealth() < 0.5) {
            return 1500;
        }
        else if (u.getPercentEffectiveHealth() / u.getMaxEffectiveHealth() < 0.7) {
            return 1000;
        }
        return 200;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.white);
        g.drawOval(target.getCenterX(),target.getCenterY(),100,100);
    }
}
