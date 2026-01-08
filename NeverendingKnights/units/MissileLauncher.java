package teams.student.NeverendingKnights.units;

import components.mod.offense.CerberusMod;
import components.upgrade.Munitions;
import components.weapon.economy.Collector;
import components.weapon.economy.Drillbeam;
import components.weapon.explosive.HeavyMissile;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import org.newdawn.slick.Graphics;
import teams.student.NeverendingKnights.NeverendingKnightsUnit;

import java.util.ArrayList;

public class MissileLauncher extends NeverendingKnightsUnit {

    // For best enemy, should prioritize highly clustered swarms rather than typical bestTarget

    public void design() {
        setFrame(Frame.HEAVY);
        setModel(Model.ARTILLERY);
        setStyle(Style.WEDGE);

        add(HeavyMissile.class);
        add(Munitions.class);
        add(CerberusMod.class);

    }

    public void action() {
        super.action();
    }

    public void draw(Graphics g) {

    }

    @Override
    public Unit getBestTargetEnemy(float radius) {
        Unit bestEnemy = null;
        ArrayList<Unit> enemies = getEnemiesInRadius(radius);
        if (!enemies.isEmpty()) {
            int bestEnemyScore = 0;
            for (Unit enemy : enemies) {
                int totalPoints = 10000;
                totalPoints -= super.healthScore(enemy);
                totalPoints -= damageScore(enemy);
                totalPoints -= speedScore(enemy);
                totalPoints -= typeShipScore(enemy);
                totalPoints -= statusAndRangeScore(enemy);
                totalPoints -= distanceScore(enemy);
                totalPoints -= centralizedScore(enemy);
                if (bestEnemy == null) {
                    bestEnemyScore = totalPoints;
                    bestEnemy = enemy;
                }
                else {
                    if (bestEnemyScore > totalPoints) {
                        bestEnemyScore = totalPoints;
                        bestEnemy = enemy;
                    }
                }
            }
        }
        if (bestEnemy != null && (bestEnemy.hasComponent(Collector.class) || bestEnemy.hasComponent(Drillbeam.class))) return null; // Can change to only gatherers if need be
        return bestEnemy;
    }

    private int centralizedScore(Unit u) {
        float distance = 300;
        if (u.getAlliesInRadius(distance).size() > 10) {
            return 10000;
        }
        else if (u.getAlliesInRadius(distance).size() > 5) {
            return 5000;
        }
        else if (u.getAlliesInRadius(distance).size() > 2) {
            return 1000;
        }
        return 100;
    }
}