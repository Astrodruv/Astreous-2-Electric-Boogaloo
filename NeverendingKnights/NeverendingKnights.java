package teams.student.NeverendingKnights;

import objects.resource.ResourceManager;
import org.newdawn.slick.Graphics;
import player.Player;
import teams.student.NeverendingKnights.units.*;
import teams.student.NeverendingKnights.units.resource.Gatherer;
import teams.student.NeverendingKnights.units.resource.Miner;
import teams.student.NeverendingKnights.units.resource.MinerBuffer;
import teams.student.NeverendingKnights.units.resource.ResourceGrabber;

public class NeverendingKnights extends Player
{

    public static ResourceAssigner resourceAssigner;
    public static String gameStage;

    public void setup()
    {
        setName("Neverending Knights");
        setTeamImage("src/teams/student/NeverendingKnights/teamLogo.png");
        setTitle("Newbie Team");

        setColorPrimary(255, 188, 0);
        setColorSecondary(255, 255, 255);
        setColorAccent(0, 0, 128);

        resourceAssigner = new ResourceAssigner(this);

        gameStage = "Start";
    }

    public void strategy()
    {
        // Makes the actual strategy method a little cleaner and easier to understand

        if (gameStage.equals("Start")){
            startStrategy();
        }
        else if (gameStage.equals("Endgame")){
            endgameStrategy();
        }

        if (getMyBase().getDistance(getEnemyBase()) < 700){
            gameStage = "Endgame";
        }

    }

    public void startStrategy(){
        if (countUnit(this, MinerBuffer.class) < 1){
            buildUnit(MinerBuffer.class);
        }
        if (countUnit(this, ResourceGrabber.class) < 2){
            buildUnit(ResourceGrabber.class);
        }
        if (countUnit(this, Miner.class) < 8){
            buildUnit(Miner.class);
        }
        if(countUnit(this, Gatherer.class) < 12) {
            buildUnit(Gatherer.class);
        }
        if (countUnit(this, Pest.class) < 8){
            buildUnit(Pest.class);
        }
//        if (countUnit(this, Commander.class) < 1) {
//            buildUnit(Commander.class);
//        }
//        if (countUnit(this,Sniper.class) < 10) {
//            buildUnit(Sniper.class);
//        }
        if (countUnit(this, Tank.class) <= 4) {
            buildUnit(Tank.class);
        }
//        if (countUnit(this, MissileLauncher.class) < 4) {
//            buildUnit(MissileLauncher.class);
//        }
    }

    public void endgameStrategy(){
        if (countUnit(this, ResourceGrabber.class) < 2){
                buildUnit(ResourceGrabber.class);
            }
            if (countUnit(this, Miner.class) < 1){
                buildUnit(Miner.class);
            }
            if(countUnit(this, Gatherer.class) < 1){
                buildUnit(Gatherer.class);
            }
            if (countUnit(this, Pest.class) < 1){
                buildUnit(Pest.class);
            }
            if (countUnit(this, Commander.class) < 3) {
                buildUnit(Commander.class);
            }
            if (countUnit(this,Sniper.class) < 17) {
                buildUnit(Sniper.class);
            }
            if (countUnit(this, Tank.class) < 15) {
                buildUnit(Tank.class);
            }
            if (countUnit(this, MissileLauncher.class) < 10) {
                buildUnit(MissileLauncher.class);
            }
    }

    public void draw(Graphics g)
    {
        addMessage("Assigned: " + resourceAssigner.assignedResources.size());
        addMessage("Total Counted: " + resourceAssigner.resourcesByDistToHomeBase.size());
        addMessage("Total Counted + Assigned: " + (resourceAssigner.resourcesByDistToHomeBase.size() + resourceAssigner.assignedResources.size()));
        addMessage("Actual Total: " + ResourceManager.getResources().size());
        addMessage("Total Dumped: " + Gatherer.allDumpedResources.size());
    }

}
