package teams.student.NeverendingKnights.units.combat;

import components.upgrade.Munitions;
import components.weapon.energy.HeavyLaser;
import components.weapon.explosive.HeavyMissile;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import teams.student.NeverendingKnights.units.combat.strategies.CoordinateTargetedEnemyStrategy;
import teams.student.NeverendingKnights.units.combat.strategies.RallyStrategy;

public class MissleLauncher extends Fighter{
    public void design()
    {
        setFrame(Frame.MEDIUM);
        setModel(Model.ARTILLERY);
        setStyle(Style.WEDGE);

        add(HeavyMissile.class);
        add(Munitions.class);

    }

//    @Override
//    public void movement() {
//        if (combatManager != null) {
//            if (combatManager.getStrategy() instanceof RallyStrategy) {
////                moveTo(((RallyStrategy) combatManager.getStrategy()).getRallyX(),((RallyStrategy) combatManager.getStrategy()).getRallyPointY());
//                moveTo(getNearestAlly(teams.student.NeverendingKnights.units.combat.Tank.class));
//            }
//            if (combatManager.getStrategy() instanceof CoordinateTargetedEnemyStrategy) {
//                moveTo(((CoordinateTargetedEnemyStrategy) combatManager.getStrategy()).getTarget());
//            }
//        }
//
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
////		else{
////			moveTo(getNearestEnemy());
////		}
//
//    }
//
//    private boolean behindTank()
//    {
//        Unit nearestTank = getNearestAlly(teams.student.mrfeastsfleet.units.Tank.class);
//        if (nearestTank != null)
//        {
//            return getDistance(getNearestEnemy()) < getDistance(nearestTank) && getHomeBase().getDistance(this) < getHomeBase().getDistance(nearestTank);
//        }
//        return true;
//    }
}
