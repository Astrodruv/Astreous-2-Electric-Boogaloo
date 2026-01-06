package teams.student.NeverendingKnights.units.combat;

import components.mod.offense.EosMod;
import components.mod.offense.HadesMod;
import components.mod.offense.PoseidonMod;
import components.upgrade.Munitions;
import components.upgrade.Plating;
import components.upgrade.Shield;
import components.weapon.energy.HeavyLaser;
import components.weapon.energy.Laser;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import teams.student.NeverendingKnights.units.combat.strategies.CoordinateTargetedEnemyStrategy;
import teams.student.NeverendingKnights.units.combat.strategies.RallyStrategy;
import teams.student.mrfeastsfleet.units.Tank;

public class Sniper extends Fighter{
    public void design()
    {
        setFrame(Frame.HEAVY);
        setModel(Model.ARTILLERY);
        setStyle(Style.WEDGE);

        add(HeavyLaser.class);
        add(Munitions.class);
        add(PoseidonMod.class);

    }

    @Override
    public void movement() {
        if (combatManager != null) {
            if (combatManager.getStrategy() instanceof RallyStrategy) {
//                moveTo(((RallyStrategy) combatManager.getStrategy()).getRallyX(),((RallyStrategy) combatManager.getStrategy()).getRallyPointY());
                moveTo(getNearestAlly(teams.student.NeverendingKnights.units.combat.Tank.class));
            }
            if (combatManager.getStrategy() instanceof CoordinateTargetedEnemyStrategy) {
                moveTo(((CoordinateTargetedEnemyStrategy) combatManager.getStrategy()).getTarget());
            }
        }

        if (currentTarget == null || currentTarget.isDead()) {
            currentTarget = getBestTargetEnemy(getMaxRange()*2);
        }
        Unit enemy = currentTarget;
//
        if(enemy != null)
        {
            if(getDistance(enemy) > getMaxRange())
            {
                moveTo(enemy);
            }
            else
            {
                turnTo(enemy);
                turnAround();
                move();
            }
        }
//		else{
//			moveTo(getNearestEnemy());
//		}

    }

    private boolean behindTank()
    {
        Unit nearestTank = getNearestAlly(Tank.class);
        if (nearestTank != null)
        {
            return getDistance(getNearestEnemy()) < getDistance(nearestTank) && getHomeBase().getDistance(this) < getHomeBase().getDistance(nearestTank);
        }
        return true;
    }
}
