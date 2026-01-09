package teams.student.NeverendingKnights.units;

import components.upgrade.Shield;
import components.weapon.Weapon;
import components.weapon.utility.Pullbeam;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import org.newdawn.slick.Graphics;
import teams.student.NeverendingKnights.NeverendingKnightsUnit;

public class Puller extends NeverendingKnightsUnit {
    @Override
    public void design() {
        setFrame(Frame.MEDIUM);
        setModel(Model.CRUISER);
        setStyle(Style.CRESCENT);
        add(Pullbeam.class);
        add(Shield.class);
        //3 slots


    }

    public void attack(Weapon w) {
        if (currentTarget != null) {
            if (getDistance(currentTarget) < getMaxRange()) {
                w.use(currentTarget);
            }
        }
    }

    @Override
    public void movement() {
        //move to the action area
        if (getNearestAlly(Sniper.class) != null) {
            moveTo(getNearestAlly(Sniper.class));
        }
        else {
            moveTo(getHomeBase());
        }
        
    }

    protected int distanceScore(Unit u) {
        if (getDistance(u) < (float) getMaxRange() /3) {
            return 8000;
        }
        else if (getDistance(u) < (float) (getMaxRange() * 2) /3) {
            return 6000;
        }
        else if (getDistance(u) < getMaxRange()) {
            return 3000;
        }
        else {
            return 0;
        }
    }

    @Override
    public void draw(Graphics g) {

    }
}
