package teams.student.neverendingKnights.units;

import components.upgrade.Shield;
import components.weapon.energy.Laser;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import teams.student.neverendingKnights.NeverendingKnightsUnit;

public class Pest extends NeverendingKnightsUnit
{

    public void design()
    {
        setFrame(Frame.LIGHT);
        setModel(Model.DESTROYER);
        setStyle(Style.DAGGER);

        add(Laser.class);
        add(Shield.class);
    }

    public void act(){
        currentTarget = nearestEnemyWorker;
        attack(getWeaponOne());
        attack(getWeaponTwo());
        movement();
    }

    public void movement() {
        if (currentTarget != null) {
            Unit threat = getNearestEnemyThreat();
            if (threat != null && getDistance(threat) > threat.getMaxRange() * 3f) {
                moveTo(pestRallyX, pestRallyY);
            }
            else{
                if (isInBounds()) {
                    turnTo(getNearestEnemyThreat());
                    turnAround();
                    move();
                }
                else{
                    moveTo(getHomeBase());
                }
            }
        }
        else{
            moveTo(pestRallyX, pestRallyY);
        }
    }

    public void draw(Graphics g){
        g.setColor(Color.white);
        g.drawLine(getCenterX(), getCenterY(), pestRallyX, pestRallyY);
    }


}