package teams.student.NeverendingKnights.units;

import components.upgrade.Munitions;
import components.upgrade.Shield;
import components.weapon.Weapon;
import components.weapon.energy.HeavyLaser;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import org.newdawn.slick.Graphics;
import teams.student.NeverendingKnights.NeverendingKnights;
import teams.student.NeverendingKnights.NeverendingKnightsUnit;

public class Sniper extends NeverendingKnightsUnit {
    private String stage;
    private float spreadY;
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

    public void attack(Weapon w) {
        if(currentTarget != null && w != null && getDistance(currentTarget) < getMaxRange()){
            w.use(currentTarget);
        }
    }

    //move back if too low so that healers can heal them

    public void action() {
        detectStage();
        attack(getWeaponOne());
        movement();
    }

    public String getStage() {return stage;}
    //hook this up to healers

    @Override
    public void movement() {
        if (currentTarget == null || currentTarget.isDead() || getDistance(currentTarget) > getMaxRange()*3) {
            currentTarget = getBestTargetEnemy((float) (getMaxRange()*3));
        }
        if (stage.equals("Waiting")) {
            if (getPlayer().isLeftPlayer()) moveTo(getHomeBase().getCenterX() + 300, getHomeBase().getCenterY());
            else if (getPlayer().isRightPlayer()) moveTo(getHomeBase().getCenterX() - 300, getHomeBase().getCenterY());
        }
        else if (stage.equals("Attacking")) {
            float farthestX;
            if (!NeverendingKnights.gameStage.equals("Endgame")) {
                if (NeverendingKnights.furthestTank != null) {
                    farthestX = NeverendingKnights.furthestTank.getCenterX();
                }
                else {
                    farthestX = getEnemyBase().getCenterX();
                }
                float sniperX = farthestX - (float) getMaxRange() /2;
                if (getDistance(getNearestAlly(Sniper.class)) < (float) getMaxRange() / 10) {
                    spreadOut();
                }
                if (Math.abs(sniperX - getCenterX()) < (float) getMaxRange() /2 && Math.abs(spreadY - getCenterY()) < (float) getMaxRange() /2) {
                    if (currentTarget != null) {
                        if (!currentTarget.isDead()) {
                            if (getDistance(currentTarget) > (getMaxRange())) {
                                moveTo(currentTarget.getX(), currentTarget.getY());
                            } else {
                                turnTo(currentTarget);
                                turnAround();
                                move();
                            }
                        }
                    }
                }
                else {
                    dbgMessage("going to sniperX And spreadY");
                    moveTo(sniperX,spreadY);
                }
            }
            else {
                if (getNearestAlly(Tank.class) == null) {
                    moveTo(getHomeBase());
                }
                else {
                    moveTo(getNearestAlly(Tank.class));
                }
            }
        }
        else if (stage.equals("Healing")) {
            float midX = (NeverendingKnights.furthestTank.getCenterX() + getHomeBase().getCenterX()) / 2;
            moveTo(midX,y);
        }
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

    private void detectStage() {
        if (stage.equals("Healing") && getCurEffectiveHealth() > getMaxEffectiveHealth()*0.6) {
            stage = "Waiting";
        }
        //whatever tank is closest to enemy base follow them

        if (NeverendingKnights.furthestTank != null) {
            if (NeverendingKnights.furthestTank.getStage().equals("Attacking") || getEnemiesInRadius(500, Sniper.class).size() > 8) {
                stage = "Attacking";
                if (getCurEffectiveHealth() < getMaxEffectiveHealth()*0.4) {
                    stage = "Healing";
                }
            }
            else {
                stage = "Waiting";
            }
        }
        else {
            stage = "Attacking";
        }

    }

    public void draw(Graphics g) {
//        g.setColor(Color.white);
//        if (currentTarget != null) g.drawLine(getCenterX(),getCenterY(),currentTarget.getCenterX(),currentTarget.getCenterY());
        dbgMessage(stage);
    }

}
