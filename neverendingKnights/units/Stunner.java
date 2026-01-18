package teams.student.neverendingKnights.units;

import components.mod.utility.SisyphusMod;
import components.upgrade.Shield;
import components.weapon.Weapon;
import components.weapon.utility.ElectromagneticPulse;
import components.weapon.utility.GravitationalRift;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import teams.student.neverendingKnights.NeverendingKnightsUnit;

import java.util.ArrayList;

public class Stunner extends NeverendingKnightsUnit {
    private int timer;
    private final int MAX_TIMER = 3600;
    @Override
    public void design() {
        setFrame(Frame.MEDIUM);
        setModel(Model.ARTILLERY);
        setStyle(Style.ROCKET);

//        add(Shield.class);
        add(ElectromagneticPulse.class);
        add(SisyphusMod.class);
    }

    @Override
    public void act() {
        movement();
        currentTarget = getNearestEnemy();
        if (getEnemiesInRadius(getMaxRange()).size() > 2) {

            attack(getWeaponOne());
        }
    }

    @Override
    public void attack(Weapon w) {
        super.attack(w);
    }

//    private Unit getClosestUnitToAverage() {
//        Unit bestEnemy = null;
//        float bestDistance = 0;
//        for (Unit u: getEnemies()) {
//            float distance = u.getDistance(getBestXToPlaceEMP(),getBestYToPlaceEMP());
//            if (bestEnemy == null) {
//                bestEnemy = u;
//                bestDistance = distance;
//            }
//            else {
//                if (distance < bestDistance) {
//                    bestDistance = distance;
//                    bestEnemy = u;
//                }
//            }
//        }
//        return bestEnemy;
//    }
//
//    private float getBestXToPlaceEMP() {
//        ArrayList<Unit> enemiesInMyMaxRadius = getRealEnemiesInRadius(getMaxRange());
//        float totalToBeAveraged = 0;
//        for (int i = 0; i<enemiesInMyMaxRadius.size(); i++) {
//            totalToBeAveraged += enemiesInMyMaxRadius.get(i).getCenterX();
//        }
//        return totalToBeAveraged / enemiesInMyMaxRadius.size();
//    }
//
//    private float getBestYToPlaceEMP() {
//        ArrayList<Unit> enemiesInMyMaxRadius = getRealEnemiesInRadius(getMaxRange());
//        float totalToBeAveraged = 0;
//        for (int i = 0; i<enemiesInMyMaxRadius.size(); i++) {
//            totalToBeAveraged += enemiesInMyMaxRadius.get(i).getCenterY();
//        }
//        return totalToBeAveraged / enemiesInMyMaxRadius.size();
//    }
}
