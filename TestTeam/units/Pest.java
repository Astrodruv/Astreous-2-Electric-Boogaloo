package teams.student.TestTeam.units;

import components.upgrade.Shield;
import components.weapon.energy.Laser;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import teams.student.NeverendingKnights.NeverendingKnightsUnit;
import teams.student.TestTeam.TestTeamUnit;

public class Pest extends TestTeamUnit
{
    private String stage;
    private Unit unitToAttack;
    private Unit passByUnit;
    public int rand;

	public void design()
	{	
		setFrame(Frame.LIGHT);
		setModel(Model.DESTROYER);
		setStyle(Style.DAGGER);

		add(Laser.class);
		add(Shield.class);

        stage = "Waiting";
        unitToAttack = null;
        passByUnit = null;
        rand = (int) (Math.random() * 2);
//        rand = 0;
	}

    public void action() {
        unitToAttack = getLowestSafeEnemyWorker(getWeaponOne().getMaxRange() * 3);
        passByUnit = getLowestAttackingEnemy(getWeaponOne().getMaxRange() * 2);

        if (stage.equals("Waiting")){

            if(rand == 0)
            {
                moveTo(getHomeBase().getCenterX(), 3500);
            }
            else
            {
                moveTo(getHomeBase().getCenterX(), -3500);
            }

            getWeaponOne().use(unitToAttack);
            if(unitToAttack == null) {
                getWeaponOne().use(passByUnit);
            }
//
            if((this.getPosition().getY() > getHomeBase().getCenterY() + 3250 && this.getPosition().getY() < getHomeBase().getCenterY() + 3750)
                    || (this.getPosition().getY() < getHomeBase().getCenterY() - 3250 && this.getPosition().getY() > getHomeBase().getCenterY() - 3750))
            {
                if (getAlliesInRadius(400, Pest.class).size() > 3){
                    stage = "Flanking";
                }
            }


        }
        if (stage.equals("Flanking")){

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
                if (getDistance(getNearestEnemy()) > ((float) getWeaponOne().getMaxRange() / 5) * 4) {
                    moveTo(getNearestEnemy());
                } else {
                    turnTo(getNearestEnemy());
                    turn(180);
                    move();
                }
                moveTo(getNearestEnemy());
                getWeaponOne().use(getNearestEnemy());
            }
        }
    }

    public void draw(Graphics g){
//        dbgMessage(stage);
        g.setColor(Color.white);
        if (unitToAttack != null){
            g.drawLine(getCenterX(), getCenterY(), unitToAttack.getCenterX(), unitToAttack.getCenterY());
        }
    }


}