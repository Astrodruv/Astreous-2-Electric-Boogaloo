package teams.student.TestTeam;

import engine.states.Game;
import objects.entity.node.NodeManager;
import objects.entity.unit.Unit;
import objects.resource.ResourceManager;
import org.newdawn.slick.Graphics;
import player.Player;
import teams.student.TestTeam.units.*;
import teams.student.TestTeam.units.resource.Gatherer;
import teams.student.TestTeam.units.resource.Miner;
import teams.student.TestTeam.units.resource.MinerBuffer;
import teams.student.TestTeam.units.resource.ResourceGrabber;

import java.util.ArrayList;

public class TestTeam extends Player
{

    public static ResourceAssigner resourceAssigner;
    public static String gameStage;
    public static int resourceGrabberCount;

    public static int estimatedMapResources;

    public static ArrayList<Unit> tanks;

    public void setup()
    {
        setName("Test Team");
        setTeamImage("src/teams/student/TestTeam/teamLogo.png");
        setTitle("Newbie Team");

        setColorPrimary(0, 255, 255);
        setColorSecondary(0, 255, 255);
        setColorAccent(255, 128, 128);

        resourceAssigner = new ResourceAssigner(this);

        gameStage = "Instant";
        estimatedMapResources = (getAllNodes().size() * 12) + ResourceManager.getResources().size();
    }

    public void strategy() {
        resourceGrabberCount = countMyUnits(ResourceGrabber.class);

        if (countUnit(this, ResourceGrabber.class) < 2) {
            buildUnit(ResourceGrabber.class);
        }
        if (countUnit(this, MinerBuffer.class) < 1) {
            buildUnit(MinerBuffer.class);
        }


        if (timer < 180 * 60) {
            if (getFleetValueUnitPercentage(Miner.class) < 0.25f) {
                buildUnit(Miner.class);
            }
            if (getFleetValueUnitPercentage(Gatherer.class) < 0.25f) {
                buildUnit(Gatherer.class);
            }
        } else if (timer < 300 * 60) {
            if (getFleetValueUnitPercentage(Miner.class) < 0.15f) {
                buildUnit(Miner.class);
            }
            if (getFleetValueUnitPercentage(Gatherer.class) < 0.2f) {
                buildUnit(Gatherer.class);
            }
        }
        else if (timer < 480 * 60) {
            if (getFleetValueUnitPercentage(Miner.class) < 0.1f) {
                buildUnit(Miner.class);
            }
            if (getFleetValueUnitPercentage(Gatherer.class) < 0.1f) {
                buildUnit(Gatherer.class);
            }
        }
        else if (timer < 700 * 60) {
            if (getFleetValueUnitPercentage(Miner.class) < 0.05f) {
                buildUnit(Miner.class);
            }
            if (getFleetValueUnitPercentage(Gatherer.class) < 0.15f) {
                buildUnit(Gatherer.class);
            }
        }
        else if (timer < 900 * 60) {
            if (getFleetValueUnitPercentage(Gatherer.class) < 0.1f) {
                buildUnit(Gatherer.class);
            }
        }

        if (TestTeamUnit.teamStrategy.equals("Anti-Rush")) {
            if (getFleetValueUnitPercentage(Pest.class) < 0.3f) {
                buildUnit(Pest.class);
            }
            if (getFleetValueUnitPercentage(Sniper.class) < 0.3f){
                buildUnit(Sniper.class);
            }
            if (getFleetValueUnitPercentage(Tank.class) < 0.2f) {
                buildUnit(Tank.class);
            }
        }
        else if (TestTeamUnit.teamStrategy.equals("Buildup")) {
            if (TestTeamUnit.enemyMissileThreat.equals("High") || TestTeamUnit.enemyMissileThreat.equals("Medium")){
                if (getFleetValueUnitPercentage(Tank.class) < 0.15f) {
                    buildUnit(Tank.class);
                }
            }
            if (getFleetValueUnitPercentage(Sniper.class) < 0.6f) {
                buildUnit(Sniper.class);
            }
            if (getFleetValueUnitPercentage(MissileLauncher.class) < 0.15f) {
                buildUnit(MissileLauncher.class);
            }
            if (getFleetValueUnitPercentage(Commander.class) < 0.05f) {
                buildUnit(Commander.class);
            }
        } else if (TestTeamUnit.teamStrategy.equals("Range")) {
            if (TestTeamUnit.enemyMissileThreat.equals("High") || TestTeamUnit.enemyMissileThreat.equals("Medium")){
                if (getFleetValueUnitPercentage(Tank.class) < 0.15f) {
                    buildUnit(Tank.class);
                }
            }
            if (getFleetValueUnitPercentage(MissileLauncher.class) < 0.25f) {
                buildUnit(MissileLauncher.class);
            }
            if (getFleetValueUnitPercentage(Sniper.class) < 0.9f) {
                buildUnit(Sniper.class);
            }
            if (getFleetValueUnitPercentage(Commander.class) < 0.05f) {
                buildUnit(Commander.class);
            }
        } else if (TestTeamUnit.teamStrategy.equals("Rush")) {
            if ((timer < 700 * 60)) {
                if (getFleetValueUnitPercentage(Pest.class) < 0.1) {
                    buildUnit(Pest.class);
                }
            }
            if (getFleetValueUnitPercentage(Tank.class) < 0.25f) {
                buildUnit(Tank.class);
            }
            if (getFleetValueUnitPercentage(Sniper.class) < 0.8f) {
                buildUnit(Sniper.class);
            }
            if (getFleetValueUnitPercentage(Commander.class) < 0.05f) {
                buildUnit(Commander.class);
            }

        }
        else if (TestTeamUnit.teamStrategy.equals("DPS Buildup")) {
            if (getFleetValueUnitPercentage(Tank.class) < 0.15f) {
                buildUnit(Tank.class);
            }
            if (getFleetValueUnitPercentage(MissileLauncher.class) < 0.2f) {
                buildUnit(Sniper.class);
            }
            if (getFleetValueUnitPercentage(Sniper.class) < 0.6f) {
                buildUnit(Sniper.class);
            }
            if (getFleetValueUnitPercentage(Commander.class) < 0.05f) {
                buildUnit(Commander.class);
            }
        }
        else if (TestTeamUnit.teamStrategy.equals("Range Buildup")) {
            if (getFleetValueUnitPercentage(Tank.class) < 0.15f) {
                buildUnit(Tank.class);
            }
            if (getFleetValueUnitPercentage(Sniper.class) < 0.8f) {
                buildUnit(Sniper.class);
            }
            if (getFleetValueUnitPercentage(Commander.class) < 0.05f) {
                buildUnit(Commander.class);
            }
        }
        else if (TestTeamUnit.teamStrategy.equals("Undecided")) {
            if (getFleetValueUnitPercentage(Tank.class) < 0.15f) {
                buildUnit(Tank.class);
            }

            if (getFleetValueUnitPercentage(Sniper.class) < 0.9f) {
                buildUnit(Sniper.class);
            }
        }
    }

    public void draw(Graphics g)
    {
//        addMessage("Assigned: " + resourceAssigner.assignedResources.size());
//        addMessage("Total Counted: " + resourceAssigner.resourcesByDistToHomeBase.size());
//        addMessage("Total Counted + Assigned: " + (resourceAssigner.resourcesByDistToHomeBase.size() + resourceAssigner.assignedResources.size()));
//        addMessage("Actual Total: " + ResourceManager.getResources().size());
//        if (Gatherer.allDumpedResources != null) addMessage("Total Dumped: " + Gatherer.allDumpedResources.size());
//
//        addMessage("Game Stage: " + gameStage);

        addMessage("Team Strategy: " + TestTeamUnit.teamStrategy);
        addMessage("Team Alert: " + TestTeamUnit.teamAlert);
        addMessage("Main Push Strength: " + TestTeamUnit.myMainPushStrength);
        addMessage("Main Push Density: " + TestTeamUnit.myMainPushDensity);
        addMessage("Main Push : " + TestTeamUnit.mainPushState);
        addMessage("Attack State : " + TestTeamUnit.attackState);
        addMessage(" ");
        addMessage("Enemy Scheme: " + TestTeamUnit.enemyAttackScheme);
        addMessage("Enemy Workers: " + TestTeamUnit.enemyWorkerStrength);
        addMessage("Nearest Enemy: " + TestTeamUnit.nearestEnemyThreatDist);
        addMessage("Missile Threat: " + TestTeamUnit.enemyMissileThreat);
        addMessage(" ");
        addMessage("My Attackers: " + TestTeamUnit.myAttackers.size());
        addMessage("My Threats: " + TestTeamUnit.enemyThreats.size());
        addMessage(" ");
        addMessage("Light Threats: " + TestTeamUnit.lightEnemyThreats);
        addMessage("Medium Threats: " + TestTeamUnit.mediumEnemyThreats);
        addMessage("Heavy Threats: " + TestTeamUnit.heavyEnemyThreats);
        addMessage("Assault Threats: " + TestTeamUnit.assaultEnemyThreats);
        addMessage(" ");
        addMessage("Nodes: " + NodeManager.getNodes().size());
        addMessage("Resources: " + ResourceManager.getResources().size());
        addMessage("Estimated Resources: " + estimatedMapResources);

    }

}
