package teams.student.NeverendingKnights.units;

import components.mod.offense.AchillesMod;
import components.mod.offense.AresMod;
import components.mod.offense.CerberusMod;
import components.mod.offense.HadesMod;
import components.upgrade.HeavyPlating;
import components.upgrade.HeavyShield;
import components.weapon.Weapon;
import components.weapon.energy.Laser;
import components.weapon.explosive.Missile;
import components.weapon.kinetic.Autocannon;
import components.weapon.utility.AntiMissileSystem;
import engine.states.Game;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import org.newdawn.slick.Graphics;
import teams.student.NeverendingKnights.NeverendingKnights;
import teams.student.NeverendingKnights.NeverendingKnightsUnit;

public class Tank extends NeverendingKnightsUnit {

    private String stage = "";
    private float spreadY;
    private int tanksInRange = 0;


    // Attack at same x value (in a line)

    private int myID;

    public void design() {
        // Detect for minis - Achillies mod & Heavy Autocannon
        // Detect for no antimissiles
        // else HeavyLaser

        setFrame(Frame.HEAVY);
        setModel(Model.BASTION);
        setStyle(Style.CRESCENT);

        if (hasManyRushUnits()) {
            add(Autocannon.class);
            add(AchillesMod.class);
        }
        else {
            if (hasManyMissiles()) {
                add(Autocannon.class);
                add(AntiMissileSystem.class);
                //maybe change to the mod that increases range?
            }
            else {
                if (!hasAntiMissiles()) {
//                    if (Math.random() > 0.5f) {
                        add(Missile.class);
                        add(CerberusMod.class);
//                    }
//                    else{
//                        add(Missile.class);
//                        add(CerberusMod.class);
//                    }
                    // Used to be missiles + Cerberus, but that's in missile launcher
                }
                else {
                    add(Laser.class);
                    add(HadesMod.class);
                }
            }
        }
        add(HeavyPlating.class);

        if (!stage.equals("Attacking")) stage = "Waiting";

        myID = NeverendingKnights.tanksCount + 1; // Sets the spreadY

        if (myID == 1 || myID % 5 == 1){
            spreadY = 300;
        }
        else if (myID == 2 || myID % 5 == 2){
            spreadY = 150;
        }
        else if (myID == 3 || myID % 5 == 3){
            spreadY = 0;
        }
        else if (myID == 4 || myID % 5 == 4){
            spreadY = -150;
        }
        else if (myID == 5 || myID % 5 == 0){
            spreadY = -300;
        }
    }

    public void action() {
        updateStage();
        updateLine();
        setMaxX();
        setCurrentTarget();
        movement();
        attack(getWeaponOne());
        attack(getWeaponTwo());
    }

    public void attack(Weapon w) {
        if(currentTarget != null && w != null){
            w.use(currentTarget);
        }
    }

    public void movement() {
        if (stage.equals("Waiting")){
            if (getPlayer().isLeftPlayer()) moveTo(getHomeBase().getCenterX() + 300, getHomeBase().getCenterY() + spreadY);
            else if (getPlayer().isRightPlayer()) moveTo(getHomeBase().getCenterX() - 300, getHomeBase().getCenterY() + spreadY);
        }
        else if (stage.equals("Attacking")){
            if (currentTarget != null) {
                if (NeverendingKnights.furthestTank.equals(this)) {
                    dbgMessage("Chosen One");
                    dbgMessage("Tanks: " + tanksInRange);
                    dbgMessage("Tanks in Radius: " + (getAlliesInRadius(2000, Tank.class).size() - 1));
                    if (tanksInRange <= getAlliesInRadius(2000, Tank.class).size() - 1) { // give leeway here
                        turnTo(currentTarget);
                        turnAround();
                        move();
                    } else {
                        if (getDistance(currentTarget) > getWeaponOne().getMaxRange()) {
                            moveTo(currentTarget.getX(), currentTarget.getCenterY() + spreadY);
                        } else {
                            turnTo(currentTarget.getX(), currentTarget.getCenterY());
                            turnAround();
                            move();
                        }
                    }
                }
                else {
                    if (getDistance(currentTarget) > (getMaxRange())) {
                        if ((getPlayer().isLeftPlayer() && currentTarget.getX() > maxX) || (getPlayer().isRightPlayer() && currentTarget.getX() < maxX)) {
                            moveTo(maxX, currentTarget.getCenterY() + spreadY);
                        } else {
                            moveTo(currentTarget.getX(), currentTarget.getCenterY() + spreadY);
                        }
                    }
                    else {
                        turnTo(currentTarget.getX(), currentTarget.getCenterY());
                        turnAround();
                        move();
                    }
                }
            }

//                if (currentTarget != null) {
//                    if (getDistance(currentTarget) > (getMaxRange() * 1.25f)) {
//                        moveTo(currentTarget.getX(), currentTarget.getCenterY() + spreadY);
//                        dbgMessage("MOVING");
//                    }
//                    else if (getDistance(currentTarget) > (getMaxRange())){
//                        if (getCurSpeed() >= (getMaxSpeed() / 4)) {
//                            turnTo(currentTarget.getX(), currentTarget.getCenterY() + spreadY);
//                            turnAround();
//                            move();
//                        }
//                        else{
//                            moveTo(currentTarget.getX(), currentTarget.getCenterY() + spreadY);
//                        }
//                        dbgMessage("STUTTERING");
//                    }
//                    else if (getDistance(currentTarget) > (getMaxRange() * 0.95f)){
//                        attack(getWeaponOne());
//                        attack(getWeaponTwo());
//                        dbgMessage("ATTACKING");
//                    }
//                    else {
//                        turnTo(currentTarget.getX(), currentTarget.getCenterY());
//                        turnAround();
//                        move();
//                        dbgMessage("FLEEING");
//                    }
//                }
        }

        spreadOut();
    }

    public void setCurrentTarget(){
        currentTarget = getBestTargetEnemy(getWeaponOne().getMaxRange() * 2);
        if (currentTarget == null) currentTarget = getEnemyBase();
    }

    public void spreadOut(){
//        if (getDistance(getNearestAlly(Tank.class)) < 50){
//            turnTo(getNearestAlly(Tank.class));
//            turn(180);
//            move();
//        }
    }

    public void updateStage(){
        if (getAlliesInRadius(1000, Tank.class).size() >= 9 || getDistance(getNearestRealEnemy()) < 1750){
            stage = "Attacking";
        }
        dbgMessage(stage);
        dbgMessage(myID);
    }

    public void updateLine(){
        if (Game.getTime() % 5 == 0) {
            tanksInRange = 0;
            int range = 200;
            for (Unit t : NeverendingKnights.tanks) {
                if (getPlayer().isLeftPlayer() && t.getCenterX() + range > getCenterX()) tanksInRange++;
                else if (getPlayer().isRightPlayer() && t.getCenterX() - range < getCenterX()) tanksInRange++;
            }
        }
    }

    public void draw(Graphics g) {
//        float getMaxRange = getWeaponOne().getMaxRange();
//        g.setColor(Color.yellow);
//        g.drawOval(getCenterX() - (getMaxRange * 0.95f), getCenterY() - (getMaxRange * 0.95f), getMaxRange * 1.9f, getMaxRange * 1.9f);
//        g.drawOval(getCenterX() - getMaxRange, getCenterY() - getMaxRange, getMaxRange * 2, getMaxRange * 2);
    }

    public String getStage() {
        return stage;
    }
}