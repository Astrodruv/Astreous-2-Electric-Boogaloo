package teams.student.NeverendingKnights.units;

import components.mod.offense.*;
import components.upgrade.Shield;
import components.weapon.energy.Laser;
import components.weapon.explosive.Missile;
import components.weapon.kinetic.Autocannon;
import components.weapon.utility.SpeedBoost;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import teams.student.NeverendingKnights.NeverendingKnightsUnit;

import java.util.ArrayList;

public class Pest extends NeverendingKnightsUnit
{
    private String stage;
    private static Unit unitToAttack;
    private Unit passByUnit;
    public int rand;
    private String safe;
    public boolean isAntiRush;

    public void design()
    {

        setFrame(Frame.LIGHT);
        setModel(Model.PROTOTYPE);
        setStyle(Style.DAGGER);

        add(Laser.class);
        add(SpeedBoost.class);
        add(Shield.class);

//        shouldBeAntiRush();

        stage = "Waiting";
        unitToAttack = null;
        passByUnit = null;
        rand = (int) (Math.random() * 2);
        safe = "y";
//        rand = 0;
    }

    public void action() {

//        unitToAttack = getLowestSafeEnemyWorker(getWeaponOne().getMaxRange() * 3);
//        if(isAntiRush) {
//            unitToAttack = getLowestAttackingEnemy(getWeaponOne().getMaxRange() * 2);
//        }
//        else {
//            unitToAttack = getIsolatedEnemyWorker();
//        }

        unitToAttack = getIsolatedEnemyWorker(5000);
//        passByUnit = getLowestAttackingEnemy(getWeaponOne().getMaxRange() * 2);

        if(getHomeBase().getDistance(getEnemyBase()) < 6000 || getEnemyBase().getPercentEffectiveHealth() < .5f) {
            stage = "Attacking";
        }

        getWeapon(SpeedBoost.class).use();

//        if(stage.equals("Attacking") && suicideCheck(getWeaponOne().getMaxRange() * 2, this))
//        {
//            stage = "Running";
//            safe = "no";
//        }


        if (stage.equals("Waiting")) {

            if(rand == 0) {
                moveTo(getHomeBase().getCenterX(), 3500);
            }
            else {
                moveTo(getHomeBase().getCenterX(), -3500);
            }

            getWeaponOne().use(unitToAttack);
            if(unitToAttack == null) {
                getWeaponOne().use(passByUnit);
            }

//            if (getRealEnemiesInRadius(8000).size() >= 2) {
//                stage = "Attacking";
//            }

            if (getAlliesInRadius(400, Pest.class).size() > 3){
                stage = "Flanking";
            }


        }
        if (stage.equals("Flanking")){

            if(rand == 0) {
                moveTo(getEnemyBase().getCenterX(), 3500);
                if (getDistance(getEnemyBase().getCenterX(), 3500) < 300 || unitToAttack != null){
                    stage = "Attacking";
                }
            }
            else {
                moveTo(getEnemyBase().getCenterX(), -3500);
                if (getDistance(getEnemyBase().getCenterX(), -3500) < 300 || unitToAttack != null){
                    stage = "Attacking";
                }
            }

            getWeaponOne().use(unitToAttack);
            if(unitToAttack == null) {
                getWeaponOne().use(passByUnit);
            }
        }
        if (stage.equals("Attacking")){
            if (unitToAttack != null) {
                if (getDistance(getEnemyBase()) > 750){
                    if (getDistance(unitToAttack) > ((float) getWeaponOne().getMaxRange() / 5) * 3) {
                        moveTo(unitToAttack);
                    } else {
                        turnTo(unitToAttack);
                        turn(180);
                        move();
                    }
                }
                else{
                    turnTo(getEnemyBase());
                    turn(180);
                    move();
                }
                getWeaponOne().use(unitToAttack);
            }
            else{
                if (getDistance(getNearestEnemyWorker()) > ((float) getWeaponOne().getMaxRange() / 5) * 4) {
                    moveTo(getNearestEnemyWorker());
                } else {
                    turnTo(getNearestEnemyWorker());
                    turn(180);
                    move();
                }
            }
        }
        if(stage.equals("Running"))
        {
            if(getPlayer().isRightPlayer()) {
                moveTo(getPosition().getX() - 5000, getPosition().getY());
            }
            if(getPlayer().isLeftPlayer()) {
                moveTo(getPosition().getX() + 5000, getPosition().getY());
            }
            if(!suicideCheck(getWeaponOne().getMaxRange() * 2, this)) {
                stage = "Attacking";
            }
        }
    }

    public void shouldBeAntiRush() {

        ArrayList<Unit> pests = getPlayer().getMyUnits(Pest.class);
        int totalAntiRushPests = 0;
        for (Unit u: pests) {
            if(u instanceof Pest) {
                if(((Pest) u).isAntiRush) {
                    totalAntiRushPests++;
                }
            }
        }
        if(totalAntiRushPests >= pests.size()/3) {
            isAntiRush = false;
        }
        else {
            isAntiRush = true;
        }
    }

    public void draw(Graphics g){
//        dbgMessage(stage);
        g.setColor(Color.white);
        if (unitToAttack != null){
            g.drawLine(getCenterX(), getCenterY(), unitToAttack.getCenterX(), unitToAttack.getCenterY());
        }
        dbgMessage(safe);
    }


}