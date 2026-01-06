package teams.student.NeverendingKnights;

import objects.resource.ResourceManager;
import org.newdawn.slick.Graphics;
import player.Player;
import teams.student.NeverendingKnights.units.*;
import teams.student.NeverendingKnights.units.combat.*;

public class NeverendingKnights extends Player
{

    public static ResourceAssigner resourceAssigner;
    private static CombatManager combatManager;

	public void setup()
	{		
		setName("Neverending Knights");
		setTeamImage("src/teams/student/NeverendingKnights/teamLogo.png");
		setTitle("Newbie Team");

		setColorPrimary(50, 125, 255);
		setColorSecondary(255, 255, 255);
		setColorAccent(255, 255, 255);

        resourceAssigner = new ResourceAssigner(this);
        combatManager = new CombatManager(this);
	}
	
	public void strategy()
	{
        boolean lateGame = getMyBase().getDistance(getEnemyBase()) < 700;
        combatManager.update();

        if (lateGame) {
//            if (countUnit(this, MinerBuffer.class) < 1){
//                buildUnit(MinerBuffer.class);
//            }
            if (countUnit(this, ResourceGrabber.class) < 4){
                buildUnit(ResourceGrabber.class);
            }

            if (countUnit(this, Miner.class) < 1){
                buildUnit(Miner.class);
            }

            if(countUnit(this, Gatherer.class) < 1)
            {
                buildUnit(Gatherer.class);
            }

            if (countUnit(this, Pest.class) < 1){
                buildUnit(Pest.class);
            }

//        if (countUnit(this, Fighter.class) < 20) {
//            buildUnit(Fighter.class);
//        }
            if (countUnit(this, Commander.class) < 3) {
                buildUnit(Commander.class);
            }
            if (countUnit(this,Sniper.class) < 17) {
                buildUnit(Sniper.class);
            }
            if (countUnit(this, Tank.class) < 15) {
                buildUnit(Tank.class);
            }
            if (countUnit(this, MissleLauncher.class) < 10) {
                buildUnit(MissleLauncher.class);
            }
        }
        else {
            if (countUnit(this, MinerBuffer.class) < 1){
                buildUnit(MinerBuffer.class);
            }
            if (countUnit(this, ResourceGrabber.class) < 2){
                buildUnit(ResourceGrabber.class);
            }

            if (countUnit(this, Miner.class) < 8){
                buildUnit(Miner.class);
            }

            if(countUnit(this, Gatherer.class) < 12)
            {
                buildUnit(Gatherer.class);
            }

            if (countUnit(this, Pest.class) < 13){
                buildUnit(Pest.class);
            }

//        if (countUnit(this, Fighter.class) < 20) {
//            buildUnit(Fighter.class);
//        }
            if (countUnit(this, Commander.class) < 1) {
                buildUnit(Commander.class);
            }
            if (countUnit(this,Sniper.class) < 10) {
                buildUnit(Sniper.class);
            }
            if (countUnit(this, Tank.class) < 10) {
                buildUnit(Tank.class);
            }
            if (countUnit(this, MissleLauncher.class) < 4) {
                buildUnit(MissleLauncher.class);
            }
            if (countUnit(this,BackAttack.class) < 7) {
                buildUnit(BackAttack.class);
            }
        }

//		else if(getFleetValueUnitPercentage(Miner.class) < .2f)
//		{
//			buildUnit(Miner.class);
//		}
//		else
//		{
////			buildUnit(Gatherer.class);
//		}

	}
			
	public void draw(Graphics g) 
	{
        addMessage("Assigned: " + resourceAssigner.assignedResources.size());
        addMessage("Total Counted: " + resourceAssigner.resourcesByDistToHomeBase.size());
        addMessage("Total Counted + Assigned: " + (resourceAssigner.resourcesByDistToHomeBase.size() + resourceAssigner.assignedResources.size()));
        addMessage("Actual Total: " + ResourceManager.getResources().size());
        addMessage("Total Dumped: " + Gatherer.allDumpedResources.size());
        combatManager.draw(g);
    }
	
}
