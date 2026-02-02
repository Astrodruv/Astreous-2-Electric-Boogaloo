package teams.student.NeverendingKnights.units;

import components.upgrade.*;
import components.weapon.energy.Laser;
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
        setModel(Model.DESTROYER);
        setStyle(Style.ARROW);

        add(Laser.class);
        add(SpeedBoost.class);
        add(Shield.class);

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