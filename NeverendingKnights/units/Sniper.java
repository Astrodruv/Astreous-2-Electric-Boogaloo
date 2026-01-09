package teams.student.NeverendingKnights.units;

import components.upgrade.Munitions;
import components.upgrade.Shield;
import components.weapon.Weapon;
import components.weapon.energy.HeavyLaser;
import components.weapon.energy.Laser;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import org.newdawn.slick.Graphics;
import teams.student.NeverendingKnights.NeverendingKnights;
import teams.student.NeverendingKnights.NeverendingKnightsUnit;

public class Sniper extends NeverendingKnightsUnit {
    private String stage;
    private float spreadY;

    // Attack at same x (in a line) behind tanks

    public void design()
    {
        setFrame(Frame.HEAVY);
        setModel(Model.ARTILLERY);
        setStyle(Style.WEDGE);

        add(HeavyLaser.class);
        add(Munitions.class);
        add(Shield.class);
//        add(PoseidonMod.class);

        stage = "Waiting";

        spreadY = (float) ((Math.random() * 200) - 100);
    }

    public void action() {   // move back if too low so that healers can heal them
        setMaxX();
        updateStage();
        setCurrentTarget();
        movement();
        attack(getWeaponOne());
        attack(getWeaponTwo());
    }

    public void attack(Weapon w) {
        if(currentTarget != null && w != null && getDistance(currentTarget) < getMaxRange()){
            w.use(currentTarget);
        }
    }

    public void movement() {
        if (stage.equals("Waiting")) {
            if (getPlayer().isLeftPlayer()) moveTo(getHomeBase().getCenterX() + 300, getHomeBase().getCenterY());
            else if (getPlayer().isRightPlayer()) moveTo(getHomeBase().getCenterX() - 300, getHomeBase().getCenterY());
        }
        else if (stage.equals("Attacking")) {
            if (getDistance(currentTarget) > (getMaxRange())) {
                if (((getPlayer().isLeftPlayer() && currentTarget.getX() > maxX) || (getPlayer().isRightPlayer() && currentTarget.getX() < maxX)) && getDistance(currentTarget) > 2000) {
                    if (getPlayer().isLeftPlayer()) moveTo(maxX - getMaxRange(), currentTarget.getCenterY() + spreadY);
                    else if (getPlayer().isRightPlayer()) moveTo(maxX + getMaxRange(), currentTarget.getCenterY() + spreadY);
                } else {
                    moveTo(currentTarget.getX(), currentTarget.getCenterY() + spreadY);
                }
            }
            else {
                turnTo(currentTarget.getX(), currentTarget.getCenterY());
                turnAround();
                move();
            }



//            if (currentTarget != null) {
//                if (getDistance(currentTarget) > (getMaxRange() * 1.15f)) {
//                    if (((getPlayer().isLeftPlayer() && currentTarget.getX() > maxX) || (getPlayer().isRightPlayer() && currentTarget.getX() < maxX)) && getDistance(currentTarget) > (getMaxRange() * 2.5f)) {
//                        if (getPlayer().isLeftPlayer()) moveTo(maxX - getMaxRange(), currentTarget.getCenterY() + spreadY);
//                        else if (getPlayer().isRightPlayer()) moveTo(maxX + getMaxRange(), currentTarget.getCenterY() + spreadY);
//                    }
//                    else {
//                        moveTo(currentTarget.getX(), currentTarget.getCenterY() + spreadY);
//                    }
//                    dbgMessage("MOVING");
//                }
//                else if (getDistance(currentTarget) > (getMaxRange())){
//                    if (getCurSpeed() >= (getMaxSpeed() / 4)) {
//                        turnTo(currentTarget.getX() - getMaxRange(), currentTarget.getCenterY() + spreadY);
//                        turnAround();
//                        move();
//                    }
//                    else{
//                        moveTo(currentTarget.getX(), currentTarget.getCenterY() + spreadY);
//                    }
//                    dbgMessage("STUTTERING"); // This helps to get the ship to slow down so that they can retreat quickly after firing
//                }
//                else if (getDistance(currentTarget) > (getMaxRange() * 0.95f)){
//                    attack(getWeaponOne());
//                    attack(getWeaponTwo());
//                    dbgMessage("ATTACKING");
//                }
//                else {
//                    turnTo(currentTarget.getX(), currentTarget.getCenterY());
//                    turnAround();
//                    move();
//                    dbgMessage("FLEEING");
//                }
//            }


//        if (stage.equals("Waiting")) {
//            if (getPlayer().isLeftPlayer()) moveTo(getHomeBase().getCenterX() + 300, getHomeBase().getCenterY());
//            else if (getPlayer().isRightPlayer()) moveTo(getHomeBase().getCenterX() - 300, getHomeBase().getCenterY());
//        }
//        else if (stage.equals("Attacking")) {
//            float farthestX;
//            if (!NeverendingKnights.gameStage.equals("Endgame")) {
//                if (NeverendingKnights.furthestTank != null) {
//                    farthestX = NeverendingKnights.furthestTank.getCenterX();
//                }
//                else {
//                    farthestX = getEnemyBase().getCenterX();
//                }
//                float sniperX = farthestX - (float) getMaxRange() /2;
//                if (getDistance(getNearestAlly(Sniper.class)) < (float) getMaxRange() / 10) {
//                    spreadOut();
//                }
//                if (Math.abs(sniperX - getCenterX()) < (float) getMaxRange() /2 && Math.abs(spreadY - getCenterY()) < (float) getMaxRange() /2) {
//                    if (currentTarget != null) {
//                        if (!currentTarget.isDead()) {
//                            if (getDistance(currentTarget) > (getMaxRange())) {
//                                moveTo(currentTarget.getX(), currentTarget.getY());
//                            } else {
//                                turnTo(currentTarget);
//                                turnAround();
//                                move();
//                            }
//                        }
//                    }
//                }
//                else {
//                    dbgMessage("going to sniperX And spreadY");
//                    moveTo(sniperX,spreadY);
//                }
//            }
//            else {
//                if (getNearestAlly(Tank.class) == null) {
//                    moveTo(getHomeBase());
//                }
//                else {
//                    moveTo(getNearestAlly(Tank.class));
//                }
//            }
//        }
        }

//        else if (stage.equals("Healing")) {
//            float farthestX;
//            if (getFarthestTank() != null) {
//                farthestX = getFarthestTank().getCenterX();
//            }
//            else {
//                farthestX = getHomeBase().getCenterX();
//            }
//
//            float sniperX = farthestX - (float) getMaxRange() *4;
//            if (Math.abs(sniperX - getCenterX()) < 50 && Math.abs(spreadY - getCenterY()) < 50) {
//                if (currentTarget != null) {
//                    if (getDistance(currentTarget) > (getMaxRange())) {
//                        moveTo(currentTarget.getX(), currentTarget.getY() + spreadY);
//                    } else {
//                        turnTo(currentTarget);
//                        turnAround();
//                        move();
//                    }
//                }
//            }
//            else {
//                moveTo(sniperX,spreadY);
//            }
//        }
    }

    public void spreadOut(){
        if (getDistance(getNearestAlly(Sniper.class)) < 50){
            turnTo(getNearestAlly(Sniper.class));
            float chance = (float) Math.random();
            if (chance > 0.5) {
                turn(90);
            }
            else {
                turn(-90);
            }
            moveTo(x,y+50);
        }
    }

    private void updateStage() {
        //whatever tank is closest to enemy base follow them

            if ((NeverendingKnights.furthestTank != null && NeverendingKnights.furthestTank.getStage().equals("Attacking"))) { //  || getEnemiesInRadius(500, Sniper.class).size() > 8
                stage = "Attacking";
            }
//            if (getCurEffectiveHealth() < getMaxEffectiveHealth()*0.4) {
//                stage = "Healing";
//            }
//            else {
//                stage = "Waiting";
//            }

    }

    public void setCurrentTarget(){
//        if (currentTarget == null || currentTarget.isDead() || getDistance(currentTarget) > getMaxRange()*3) {
//            currentTarget = getBestTargetEnemy((float) (getMaxRange()*3));
//        }
        currentTarget = getBestTargetEnemy(getMaxRange()*2);
        if (currentTarget == null) currentTarget = getEnemyBase();
    }

    public void draw(Graphics g) {
//        g.setColor(Color.white);
//        if (currentTarget != null) g.drawLine(getCenterX(),getCenterY(),currentTarget.getCenterX(),currentTarget.getCenterY());
    }

    public String getStage() {return stage;}    //hook this up to healers

}