package teams.student.Destiny;

import components.weapon.explosive.HeavyMissile;
import components.weapon.explosive.Missile;
import objects.entity.node.NodeManager;
import objects.entity.unit.Frame;
import objects.entity.unit.Unit;
import org.newdawn.slick.Graphics;
import player.Player;
import teams.student.Destiny.units.Bazooka;
import teams.student.Destiny.units.HealBringer;
import teams.student.Destiny.units.Juggernaut;
import teams.student.Destiny.units.Prince;
import teams.student.Destiny.units.resource.Gatherer;
import teams.student.Destiny.units.resource.Miner;
import teams.student.Destiny.units.resource.ResourceGrabber;
import teams.student.Destiny.units.resource.MinerBuffer;

public class Destiny extends Player
{

    public static int resourceGrabberCount;
    public static ResourceAssigner resourceAssigner;
    public static int nodes;
    public static int enemyMissiles;

    public void setup()
	{		
		setName("Destiny");
		setTeamImage("src/teams/student/simple/teamLogo.png");
		setTitle("Destiny Awaits Those Who Persevere");

		setColorPrimary(135, 200, 235);
		setColorSecondary(255, 255, 50);
		setColorAccent(255, 255, 255);

        resourceAssigner = new ResourceAssigner(this);
        nodes = NodeManager.getNodes().size();
        enemyMissiles = 0;
    }
	
	public void strategy() {
        resourceGrabberCount = countMyUnits(ResourceGrabber.class);
        calculateEnemyMissiles();

        if (countMyUnits(ResourceGrabber.class) < 2) {
            buildUnit(ResourceGrabber.class);
        }
        if (countMyUnits(MinerBuffer.class) < 1) {
            buildUnit(MinerBuffer.class);
        }


        if ((countMyUnits(HealBringer.class) != countMyUnits(Juggernaut.class)) && countMyUnits(HealBringer.class) < 5) {
            buildUnit(HealBringer.class);
        }

        if (NodeManager.getNodes().size() > nodes * 0.75f) {
            if (getFleetValueUnitPercentage(Miner.class) < 0.35f) {
                buildUnit(Miner.class);
            }
            if (getFleetValueUnitPercentage(Gatherer.class) < 0.2f) {
                buildUnit(Gatherer.class);
            }
        }
        else if (NodeManager.getNodes().size() > nodes * 0.5f){
            if (getFleetValueUnitPercentage(Miner.class) < 0.25f) {
                buildUnit(Miner.class);
            }
            if (getFleetValueUnitPercentage(Gatherer.class) < 0.2f) {
                buildUnit(Gatherer.class);
            }
        }
        else if (NodeManager.getNodes().size() > nodes * 0.25f){
            if (getFleetValueUnitPercentage(Miner.class) < 0.15f) {
                buildUnit(Miner.class);
            }
            if (getFleetValueUnitPercentage(Gatherer.class) < 0.15f) {
                buildUnit(Gatherer.class);
            }
        }
        else{
            if (getFleetValueUnitPercentage(Miner.class) < 0.1f) {
                buildUnit(Miner.class);
            }
            if (getFleetValueUnitPercentage(Gatherer.class) < 0.1f) {
                buildUnit(Gatherer.class);
            }
        }

        if (getFleetValueUnitPercentage(Juggernaut.class) < 0.2f){
            buildUnit(Juggernaut.class);
        }

        if (getFleetValueUnitPercentage(Juggernaut.class) > 0.1f && getFleetValueUnitPercentage(Prince.class) > 0.15f && getFleetValueUnitPercentage(Bazooka.class) < 0.15f){
            buildUnit(Bazooka.class);
        }

        if (getFleetValueUnitPercentage(Juggernaut.class) > 0.1f && getFleetValueUnitPercentage(Prince.class) < 0.4f){
            buildUnit(Prince.class);
        }


	}

    public void calculateEnemyMissiles(){
        enemyMissiles = 0;
        for (Unit u : getEnemyUnits()){
            if (u.hasWeapon(Missile.class) || u.hasWeapon(HeavyMissile.class)) {
                if (!u.getFrame().equals(Frame.COLOSSAL)) {
                    enemyMissiles++;
                }
            }
        }
    }
			
	public void draw(Graphics g) 
	{
        addMessage("Enemy Missiles: " + enemyMissiles);
        addMessage("Missile Count: " + getEnemyMissiles().size());
    }
	
}
