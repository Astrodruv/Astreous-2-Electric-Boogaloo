package teams.student.NeverendingKnights.units;

import components.mod.offense.AchillesMod;
import components.mod.offense.AresMod;
import components.upgrade.*;
import components.weapon.energy.Laser;
import components.weapon.kinetic.Autocannon;
import components.weapon.utility.SpeedBoost;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import teams.student.NeverendingKnights.NeverendingKnightsUnit;

public class Destroyer extends NeverendingKnightsUnit {

    private Unit raiderUnit;

    public void design(){
        setFrame(Frame.MEDIUM);
        setStyle(Style.ARROW);

        if (Math.random() < 0.7) {
            setModel(Model.STRIKER);
            add(Laser.class);
            add(SpeedBoost.class);
            add(Shield.class);
        }
        else{
            setModel(Model.PROTOTYPE);
            add(Autocannon.class);
            add(AchillesMod.class);
            add(SpeedBoost.class);
            add(Shield.class);
        }

        raiderUnit = null;
    }

    public void action(){
        if (getEnemyBase().getPercentEffectiveHealth() < .25f) {
            getWeaponOne().use(getEnemyBase());
        }

        getWeaponOne().use(getBiggestThreatInRadius(getMaxRange()));

        movement();
    }

    public void movement() {
        Unit raiderUnit = getNearestRaiderUnit();
        if (isInBounds()) {
            if (getHomeBase().getDistance(raiderUnit) < 5000 || getDistance(raiderUnit) < 3000) {
                getWeapon(SpeedBoost.class).use();
                if (getDistance(getNearestRaiderUnit()) > getMaxRange()) {
                    moveTo(getNearestRaiderUnit());
                    getWeaponTwo().use();
                } else {
                    turnTo(raiderUnit);
                    turnAround();
                    move();
                }
            } else {
                moveTo(getFarthestWorker());
            }
        }
        else{
            moveTo(getHomeBase());
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.yellow);
        if (raiderUnit != null) g.drawLine(getCenterX(), getCenterY(), raiderUnit.getCenterX(), raiderUnit.getCenterY());
    }
}