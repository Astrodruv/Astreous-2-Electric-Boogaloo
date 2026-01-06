package teams.student.NeverendingKnights.units.combat.strategies;

import components.weapon.economy.Collector;
import components.weapon.economy.Drillbeam;
import objects.entity.unit.Unit;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import teams.student.NeverendingKnights.units.combat.CombatManager;
import teams.student.NeverendingKnights.units.combat.Commander;

public class RallyStrategy extends CombatStrategy{
    private float rallyPointX;
    private float rallyPointY;
    private boolean started;
    private boolean attackingWithRallyPoint;
    public RallyStrategy(CombatManager combatManager) {
        super(combatManager);
        started = false;
    }

    @Override
    public void update() {
        super.update();
        boolean lateGame = player.getMyBase().getDistance(player.getEnemyBase()) < 500;
        if (!started) {
            //if the rally point doesn't exist, move to the base
            rallyPointX = player.getMyBase().getCenterX();
            rallyPointY = player.getMyBase().getCenterY();
            started = true;
        }
        else {
            decideIfAttackingWithRallyPoint();
            if (attackingWithRallyPoint) {
                //if the team is attacking using the rally point, then pick a good point to move to
                Unit closestEnemy = null;
                for (Unit e: enemyUnits) {
                    if (!(e.hasComponent(Drillbeam.class) && !(e.hasComponent(Collector.class)))) {
                        if (closestEnemy == null) {
                            closestEnemy = e;
                        }
                        else {
                            if (player.getMyBase().getDistance(e) < player.getMyBase().getDistance(closestEnemy)) {
                                closestEnemy = e;
                            }

                        }
                    }
                }
                float closestEnemyX = player.getMyBase().getCenterX();
                float closestEnemyY = player.getMyBase().getCenterY();
                if (closestEnemy != null) {
                    closestEnemyX = closestEnemy.getCenterX();
                    closestEnemyY = closestEnemy.getCenterY();
                }

                rallyPointY = closestEnemyY;
                rallyPointX = closestEnemyX;


            }
            else {
                //stay home if not
                float xAddition = 0;
                if (!lateGame) {
                    xAddition = combatUnits.size() - 8 * -400;
                }
                rallyPointX = player.getMyBase().getCenterX() + xAddition;
                rallyPointY = player.getMyBase().getCenterY();
            }
        }
    }

    public float getRallyX() {return rallyPointX;}

    public float getRallyPointY() {return rallyPointY;}

    public void decideIfAttackingWithRallyPoint() {
        if (combatUnits.size() > 10) {
            attackingWithRallyPoint = true;
        }
        else {
            attackingWithRallyPoint = false;
        }
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.white);
        g.drawOval(rallyPointX,rallyPointY,100,100);
    }
}
