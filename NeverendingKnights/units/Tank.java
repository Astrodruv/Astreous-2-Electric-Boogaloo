package teams.student.NeverendingKnights.units;

import components.mod.offense.AresMod;
import components.mod.offense.CerberusMod;
import components.upgrade.HeavyPlating;
import components.upgrade.Munitions;
import components.upgrade.Plating;
import components.weapon.Weapon;
import components.weapon.energy.HeavyLaser;
import components.weapon.energy.Laser;
import components.weapon.explosive.HeavyMissile;
import components.weapon.kinetic.HeavyAutocannon;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import org.newdawn.slick.Graphics;
import teams.student.NeverendingKnights.NeverendingKnightsUnit;

public class Tank extends NeverendingKnightsUnit {

    private String stage;
    private float spreadY;

    public void design()
    {
        // Detect for minis - Achillies mod & Heavy Autocannon
        // Detect for no antimissiles
        // else HeavyLaser

        setFrame(Frame.HEAVY);
        setModel(Model.BASTION);
        setStyle(Style.CRESCENT);

        add(HeavyMissile.class);
        add(HeavyPlating.class);
//        add(CerberusMod.class);

        stage = "Waiting";
        spreadY = (float) ((Math.random() * 200) - 100);
    }

    public void action() {
        updateStage();
        setCurrentTarget();
        attack(getWeaponOne());
        attack(getWeaponTwo());
        movement();
    }

    public void attack(Weapon w) {
        if(currentTarget != null && w != null){
            w.use(currentTarget);
        }
    }

    public void movement() {
        if (stage.equals("Waiting")){
            if (getPlayer().isLeftPlayer()) moveTo(getHomeBase().getCenterX() + 300, getHomeBase().getCenterY());
            else if (getPlayer().isRightPlayer()) moveTo(getHomeBase().getCenterX() - 300, getHomeBase().getCenterY());
        }
        else if (stage.equals("Attacking")){
            if (getDistance(getNearestAlly(Tank.class)) < 50){
                spreadOut();
            }
            else {
                if (currentTarget != null) {
                    if (getDistance(currentTarget) > (getMaxRange())) {
                        moveTo(currentTarget.getX(), currentTarget.getY() + spreadY);
                    } else {
                        turnTo(currentTarget);
                        turnAround();
                        move();
                    }
                }
            }
        }

        spreadOut();
    }

    public void setCurrentTarget(){
        currentTarget = getBestTargetEnemy(getMaxRange());
        if (currentTarget == null) currentTarget = getEnemyBase();
    }

    public void spreadOut(){
        if (getDistance(getNearestAlly(Tank.class)) < 50){
            turnTo(getNearestAlly(Tank.class));
            turn(180);
            move();
        }
    }

    public void updateStage(){
        if (getAlliesInRadius(500, Tank.class).size() >= 3){
            stage = "Attacking";
        }
        dbgMessage(stage);
    }

    public void draw(Graphics g) {

    }
}
