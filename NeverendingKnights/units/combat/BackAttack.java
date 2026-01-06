package teams.student.NeverendingKnights.units.combat;

import components.mod.offense.EosMod;
import components.mod.offense.ZeusMod;
import components.weapon.energy.HeavyLaser;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import teams.student.NeverendingKnights.NeverendingKnights;
import teams.student.mrfeastsfleet.units.Sniper;

public class BackAttack extends Fighter
{

    @Override
    public void design()
    {
        setFrame(Frame.MEDIUM);
        setModel(Model.ARTILLERY);
        setStyle(Style.CANNON);

        add(HeavyLaser.class);
        if(getAverageEnemyMaxSpeed() > 50)
        {
            add(EosMod.class);
        } else {
            add(ZeusMod.class);
        }

    }

    @Override
    public void movement() {

        Unit enemy = getBestTargetEnemy(getMaxRange() * 2);

        if (enemy != null) {
            if (getDistance(enemy) > getMaxRange() && behindSniper()) {
                moveTo(enemy);
            }
            else if (!behindSniper())
            {
                moveTo(getHomeBase());
            }
            else {
                turnTo(enemy);
                turnAround();
                move();
            }
        }
        else
        {
            enemy = getNearestEnemy();
            if (getDistance(enemy) > getMaxRange() && behindSniper()) {
                moveTo(enemy);
            }
            else if (!behindSniper())
            {
                moveTo(getHomeBase());
            }
            else {
                turnTo(enemy);
                turnAround();
                move();
            }
        }
    }

    //   stay behind Sniper
    public boolean behindSniper()
    {
        Unit nearestSniper = getNearestAlly(Sniper.class);
        if (nearestSniper != null)
        {
            return nearestSniper.getDistance(getHomeBase()) > getDistance(getHomeBase());
        }
        return true;
    }

    public boolean isToClose(Unit target)
    {
        return getDistance(target) < .75f * getWeaponOne().getMaxRange();
    }
//
}