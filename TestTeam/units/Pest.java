package teams.student.TestTeam.units;

import components.upgrade.Shield;
import components.weapon.energy.Laser;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import teams.student.TestTeam.TestTeamUnit;

public class Pest extends TestTeamUnit
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
            if (getNearestEnemyThreat() != null && getDistance(getNearestEnemyThreat()) > getNearestEnemyThreat().getMaxRange() * 1.75f) {
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