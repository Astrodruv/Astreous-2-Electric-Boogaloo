package teams.student.NeverendingKnights;

import objects.entity.node.NodeManager;
import objects.entity.unit.Unit;
import objects.resource.ResourceManager;
import org.newdawn.slick.Graphics;
import player.Player;
import teams.student.NeverendingKnights.units.*;
import teams.student.NeverendingKnights.units.resource.Gatherer;
import teams.student.NeverendingKnights.units.resource.Miner;
import teams.student.NeverendingKnights.units.resource.MinerBuffer;
import teams.student.NeverendingKnights.units.resource.ResourceGrabber;

import java.util.ArrayList;

public class NeverendingKnights extends Player
{

    public static ResourceAssigner resourceAssigner;
    public static String gameStage;
    public static int resourceGrabberCount;

    public static int estimatedMapResources;

    public static ArrayList<Unit> tanks;

    public void setup()
    {
        setName("Neverending Knights");
        setTeamImage("src/teams/student/neverendingKnights/teamLogo.png");
        setTitle("Neverending Knights");

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
            if (getFleetValueUnitPercentage(Pest.class) < 0.05f){
                buildUnit(Pest.class);
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

        if (getFleetValueUnitPercentage(Tank.class) < 0.2f) {
            buildUnit(Tank.class);
        }
        if (getFleetValueUnitPercentage(Sniper.class) < 0.5f){
            buildUnit(Sniper.class);
        }
        if (getFleetValueUnitPercentage(MissileLauncher.class) < 0.15f) {
            buildUnit(MissileLauncher.class);
        }


//
//        if (TestTeamUnit.teamStrategy.equals("Anti-Rush")) {
//            if (getFleetValueUnitPercentage(Tank.class) < 0.15f) {
//                buildUnit(Tank.class);
//            }
//            if (getFleetValueUnitPercentage(Sniper.class) < 0.6f){
//                buildUnit(Sniper.class);
//            }
//            if (getFleetValueUnitPercentage(MissileLauncher.class) < 0.15f) {
//                buildUnit(MissileLauncher.class);
//            }
//
//        } else if (TestTeamUnit.teamStrategy.equals("Buildup")) {
//            if (getFleetValueUnitPercentage(Tank.class) < 0.2f) {
//                buildUnit(Tank.class);
//            }
//            if (getFleetValueUnitPercentage(Sniper.class) < 0.45f) {
//                buildUnit(Sniper.class);
//            }
//            if (getFleetValueUnitPercentage(MissileLauncher.class) < 0.15f) {
//                buildUnit(MissileLauncher.class);
//            }
//        } else if (TestTeamUnit.teamStrategy.equals("Range")) {
//            if (getFleetValueUnitPercentage(Tank.class) < 0.2f) {
//                buildUnit(Tank.class);
//            }
//            if (getFleetValueUnitPercentage(Sniper.class) < 0.4f) {
//                buildUnit(Sniper.class);
//            }
//            if (getFleetValueUnitPercentage(MissileLauncher.class) < 0.25f) {
//                buildUnit(MissileLauncher.class);
//            }
//        } else if (TestTeamUnit.teamStrategy.equals("Rush")) {
//            if (getFleetValueUnitPercentage(Tank.class) < 0.25f) {
//                buildUnit(Tank.class);
//            }
//            if (getFleetValueUnitPercentage(Sniper.class) < 0.5f) {
//                buildUnit(Sniper.class);
//            }
//
//        } else if (TestTeamUnit.teamStrategy.equals("DPS Buildup")) {
//            if (getFleetValueUnitPercentage(Tank.class) < 0.2f) {
//                buildUnit(Tank.class);
//            }
//            if (getFleetValueUnitPercentage(Sniper.class) < 0.5f) {
//                buildUnit(Sniper.class);
//            }
//            if (getFleetValueUnitPercentage(MissileLauncher.class) < 0.2f) {
//                buildUnit(MissileLauncher.class);
//            }
//        } else if (TestTeamUnit.teamStrategy.equals("Range Buildup")) {
//            if (getFleetValueUnitPercentage(Tank.class) < 0.2f) {
//                buildUnit(Tank.class);
//            }
//            if (getFleetValueUnitPercentage(Sniper.class) < 0.5f) {
//                buildUnit(Sniper.class);
//            }
//            if (getFleetValueUnitPercentage(MissileLauncher.class) < 0.2f) {
//                buildUnit(MissileLauncher.class);
//            }
//        } else if (TestTeamUnit.teamStrategy.equals("Undecided")) {
//            if (getFleetValueUnitPercentage(Tank.class) < 0.15f) {
//                buildUnit(Tank.class);
//            }
//
//            if (getFleetValueUnitPercentage(Sniper.class) < 0.5f) {
//                buildUnit(Sniper.class);
//            }
//
//            if (getFleetValueUnitPercentage(MissileLauncher.class) < 0.2f) {
//                buildUnit(MissileLauncher.class);
//            }
//        }
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

        addMessage("Team Strategy: " + NeverendingKnightsUnit.teamStrategy);
        addMessage("Team Alert: " + NeverendingKnightsUnit.teamAlert);
        addMessage("Main Push Strength: " + NeverendingKnightsUnit.myMainPushStrength);
        addMessage("Main Push Density: " + NeverendingKnightsUnit.myMainPushDensity);
        addMessage("Main Push : " + NeverendingKnightsUnit.mainPushState);
        addMessage("Attack State : " + NeverendingKnightsUnit.attackState);
        addMessage("Main Push Avg Dist : " + (NeverendingKnightsUnit.mainPushAvgDist > getMyBase().getDistance(getEnemyBase()) / 3));
        addMessage(" ");
        addMessage("Enemy Scheme: " + NeverendingKnightsUnit.enemyAttackScheme);
        addMessage("Enemy Workers: " + NeverendingKnightsUnit.enemyWorkerStrength);
        addMessage("Nearest Enemy: " + NeverendingKnightsUnit.nearestEnemyThreatDist);
        addMessage("Missile Threat: " + NeverendingKnightsUnit.enemyMissileThreat);
        addMessage(" ");
        addMessage("Attacker Value: " + NeverendingKnightsUnit.relativeAttackerStrength);
        addMessage("Threat Value: " + NeverendingKnightsUnit.relativeEnemyThreatStrength);
        addMessage(" ");
        addMessage("Light Threats: " + NeverendingKnightsUnit.lightEnemyThreats);
        addMessage("Medium Threats: " + NeverendingKnightsUnit.mediumEnemyThreats);
        addMessage("Heavy Threats: " + NeverendingKnightsUnit.heavyEnemyThreats);
        addMessage("Assault Threats: " + NeverendingKnightsUnit.assaultEnemyThreats);
        addMessage(" ");
        addMessage("Nodes: " + NodeManager.getNodes().size());
        addMessage("Resources: " + ResourceManager.getResources().size());
        addMessage("Estimated Resources: " + estimatedMapResources);

    }

}
