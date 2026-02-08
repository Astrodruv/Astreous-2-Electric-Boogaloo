package teams.student.NeverendingKnights.units;

import components.upgrade.Plating;
import components.upgrade.Shield;
import components.weapon.explosive.Missile;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import teams.student.NeverendingKnights.NeverendingKnightsUnit;

public class Creak extends NeverendingKnightsUnit {

    public String stage;
    public String release;
    private Unit unitToAttack;
    private Unit resourceUnit;
    private int rand;
    private int timer;


    public void design() {
        setFrame(Frame.MEDIUM);
        setModel(Model.DESTROYER);
        setStyle(Style.ROCKET);

        add(Shield.class);
        add(Plating.class);
        add(Missile.class);

        stage = "Waiting";
        release = "false";
        unitToAttack = null;
        resourceUnit = null;
        rand = (int) (Math.random() * 2);
        timer = 0;
        creakActive(false);
//        rand = 0;
    }

    public void action() {

        unitToAttack = getLowestAttackingEnemy(getWeaponOne().getMaxRange() * 3);
        resourceUnit = getLowestSafeEnemyWorker(getWeaponOne().getMaxRange() * 2);

        if(getHomeBase().getDistance(getEnemyBase()) < 4000 || getEnemyBase().getPercentEffectiveHealth() < .9f)
        {
            moveTo(getEnemyBase());
            getWeaponOne().use(getNearestEnemy());
        }
        if (stage.equals("Waiting")){

            if(rand == 0) moveTo(getHomeBase().getCenterX(), 3500);
            else moveTo(getHomeBase().getCenterX(), -3500);

            getWeaponOne().use(unitToAttack);
            getWeaponOne().use(resourceUnit);
//            getWeaponTwo().use(unitToAttack);

//            if((this.getPosition().getY() > getHomeBase().getCenterY() + 3250 && this.getPosition().getY() < getHomeBase().getCenterY() + 3750)
//                    || (this.getPosition().getY() < getHomeBase().getCenterY() - 3250 && this.getPosition().getY() > getHomeBase().getCenterY() - 3750))
//            {
//                stage = "Flanking";
//            }

            if (getAlliesInRadius(400, Creak.class).size() >= 3 ){
                stage = "Flanking";
            }
        }
        if (stage.equals("Flanking")){

            timer++;

            getWeaponOne().use(unitToAttack);
            if(unitToAttack == null) {
                getWeaponOne().use(resourceUnit);
            }
//            getWeaponTwo().use(unitToAttack);

            if(rand == 0) {
                moveTo(getEnemyBase().getCenterX(), 3500);
                if (getDistance(getEnemyBase().getCenterX(), 3500) < 300){
                    stage = "Attacking";
                }
            }
            else {
                moveTo(getEnemyBase().getCenterX(), -3500);
                if (getDistance(getEnemyBase().getCenterX(), -3500) < 300){
                    stage = "Attacking";
                }
            }

            if(timer > 300)
            {
                creakActive(true);
                release = "true";
            }
        }
        if (stage.equals("Attacking")){
            if (unitToAttack != null) {
                if (getDistance(getEnemyBase()) > 500){
                    if (getDistance(unitToAttack) > ((float) getWeaponOne().getMaxRange() / 5) * 3) {
                        moveTo(unitToAttack);
                    } else {
                        turnTo(unitToAttack);
                        turn(180);
                        move();
                    }
                }
                else if(unitToAttack == null)
                {
                    turnTo(getLowestSafeEnemyWorker(getWeaponOne().getMaxRange()));
                    turn(180);
                    move();
                }
                else{
                    turnTo(getEnemyBase());
                    turn(180);
                    move();
                }
                getWeaponOne().use(unitToAttack);
//                getWeaponTwo().use(unitToAttack);
            }
            else{
                if (getDistance(getNearestEnemy()) > ((float) getWeaponOne().getMaxRange() / 5) * 4) {
                    moveTo(getNearestEnemy());
                } else {
                    turnTo(getNearestEnemy());
                    turn(180);
                    move();
                }
                moveTo(getNearestEnemy());
                getWeaponOne().use(getNearestEnemy());
//                getWeaponTwo().use(getNearestEnemy());
            }
        }

        suicideCheck(400, this);
    }

    public void draw(Graphics g) {
        dbgMessage(stage);
        g.setColor(Color.white);
        if (unitToAttack != null) {
            g.drawLine(getCenterX(), getCenterY(), unitToAttack.getCenterX(), unitToAttack.getCenterY());
        }
        dbgMessage(release);
    }
}