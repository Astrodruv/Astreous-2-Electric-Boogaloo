package teams.student.NeverendingKnights.units.combat;

import components.mod.offense.AchillesMod;
import components.upgrade.HeavyPlating;
import components.upgrade.Plating;
import components.upgrade.Shield;
import components.weapon.energy.HeavyLaser;
import components.weapon.explosive.HeavyMissile;
import components.weapon.kinetic.HeavyAutocannon;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import teams.student.NeverendingKnights.units.combat.strategies.CoordinateTargetedEnemyStrategy;
import teams.student.NeverendingKnights.units.combat.strategies.RallyStrategy;

public class Tank extends Fighter{
    public void design()
    {
        setFrame(Frame.HEAVY);
        setModel(Model.BASTION);
        setStyle(Style.CRESCENT);

        add(HeavyLaser.class);
        add(HeavyPlating.class);
//        add(AchillesMod.class);

    }

    public void movement() {
        if (combatManager != null) {
            if (combatManager.getStrategy() instanceof RallyStrategy) {
//                if (getNearestAlly(Commander.class) != null) {
//                    moveTo(getNearestAlly(Commander.class));
//                }
//                else {
//                }
                moveTo(((RallyStrategy) combatManager.getStrategy()).getRallyX(),((RallyStrategy) combatManager.getStrategy()).getRallyPointY());

            }
            if (combatManager.getStrategy() instanceof CoordinateTargetedEnemyStrategy) {
                moveTo(((CoordinateTargetedEnemyStrategy) combatManager.getStrategy()).getTarget());
            }
        }

//        if (currentTarget == null || currentTarget.isDead()) {
//            currentTarget = getBestTargetEnemy(getMaxRange()*2);
//        }
//        Unit enemy = currentTarget;
////
//        if(enemy != null)
//        {
//            if(getDistance(enemy) > getMaxRange())
//            {
//                moveTo(enemy);
//            }
//            else
//            {
//                turnTo(enemy);
//                turnAround();
//                move();
//            }
//        }
//		else{
//			moveTo(getNearestEnemy());
//		}

    }
}
