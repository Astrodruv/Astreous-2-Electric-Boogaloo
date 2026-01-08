package teams.student.NeverendingKnights.units;

import components.mod.offense.AchillesMod;
import components.mod.offense.CerberusMod;
import components.mod.offense.HadesMod;
import components.upgrade.HeavyPlating;
import components.weapon.Weapon;
import components.weapon.energy.HeavyLaser;
import components.weapon.energy.Laser;
import components.weapon.explosive.HeavyMissile;
import components.weapon.explosive.Missile;
import components.weapon.kinetic.Autocannon;
import components.weapon.utility.AntiMissileSystem;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import org.newdawn.slick.Graphics;
import teams.student.NeverendingKnights.NeverendingKnights;
import teams.student.NeverendingKnights.NeverendingKnightsUnit;

public class Tank extends NeverendingKnightsUnit {

    private String stage;
    private float spreadY;

    public void design()
    {
        // Detect for minis - Achillies mod & Heavy Autocannon
        // Detect for no antimissiles
        // else HeavyLaser
        //sean
        //4 slots

        setFrame(Frame.HEAVY);
        setModel(Model.BASTION);
        setStyle(Style.CRESCENT);

        boolean hasManyMinis = getAverageEnemyMaxSpeed() < SPEED/4;
        boolean hasMissiles = false;
        boolean hasAntiMissiles = false;
        int totalMissiles = 0;
        for (Unit l: getEnemies()) {
            if (l.hasComponent(Missile.class) || l.hasComponent(HeavyMissile.class)) {
                totalMissiles++;
            }
            if (l.hasComponent(AntiMissileSystem.class)) {
                hasAntiMissiles = true;
            }
        }
        if ((double) totalMissiles / getEnemies().size() >= 0.2) {
            hasMissiles = true;
        }

        if (hasManyMinis) {

            add(Autocannon.class);
            add(AchillesMod.class);
        }
        else {
            if (hasMissiles) {
                add(AntiMissileSystem.class);
                add(Autocannon.class);
                //maybe change to the mod that increases range?
            }
            else {
                if (!hasAntiMissiles) {
                    add(Missile.class);
                    add(CerberusMod.class);
                }
                else {
                    add(Laser.class);
                    add(HadesMod.class);
                }
            }
        }
        add(HeavyPlating.class);




//        add(HeavyMissile.class);
//        add(HeavyPlating.class);
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

    public String getStage() {
        return stage;
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
                    if (getDistance(currentTarget) > ((float) getMaxRange() /2)) {
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
        if (getAlliesInRadius(500, Tank.class).size() >= 3 || NeverendingKnights.gameStage.equals("EndGame")){
            stage = "Attacking";
        }
//        dbgMessage(stage);
    }

    public void draw(Graphics g) {

    }
}
