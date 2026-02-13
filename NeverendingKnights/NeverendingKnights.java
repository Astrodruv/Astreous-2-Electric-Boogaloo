package teams.student.NeverendingKnights;

import engine.states.Game;
import objects.entity.node.Node;
import objects.entity.node.NodeManager;
import objects.entity.unit.Unit;
import objects.resource.Resource;
import objects.resource.ResourceManager;
import org.newdawn.slick.Graphics;
import player.Player;
import teams.student.NeverendingKnights.units.*;
import teams.student.NeverendingKnights.units.buffs.*;
import teams.student.NeverendingKnights.units.resource.Gatherer;
import teams.student.NeverendingKnights.units.resource.Miner;
import teams.student.NeverendingKnights.units.resource.MinerBuffer;
import teams.student.NeverendingKnights.units.resource.ResourceGrabber;

import java.util.ArrayList;

public class NeverendingKnights extends Player
{

    public static ResourceAssigner resourceAssigner;
    public static String gameStage;
    public static String resourceGameStage;
    public static int resourceGrabberCount;

    public static int estimatedMapResources;
    public static boolean spawnMiners;

    public static ArrayList<Unit> tanks;

    public void setup()
    {
        setName("Neverending Knights");
        setTeamImage("src/teams/student/neverendingKnights/teamLogo.png");
        setTitle("Neverending Knights");

        setColorPrimary(255, 188, 0);
        setColorSecondary(0, 255, 255);
        setColorAccent(255, 128, 128);

        resourceAssigner = new ResourceAssigner(this);

        gameStage = "Instant";
        estimatedMapResources = (getAllNodes().size() * 12) + ResourceManager.getResources().size();
        spawnMiners = true;
    }

    @Override
    public void opening() {
        resourceGrabberCount = countMyUnits(ResourceGrabber.class);

        if (countUnit(this, ResourceGrabber.class) < 2) {
            buildUnit(ResourceGrabber.class);
        }
        if (countUnit(this, MinerBuffer.class) < 1) {
            buildUnit(MinerBuffer.class);
        }
        if (getResourcesInSideOfMap() > getNodesInSideOfMap() * 1.5) {
            if (getFleetValueUnitPercentage(Miner.class) < 0.2f && countMyUnits(Miner.class) < 17 && spawnMiners) {
                buildUnit(Miner.class);
            }
            if (getFleetValueUnitPercentage(Gatherer.class) < 0.6f && countMyUnits(Gatherer.class) < 15) {
                buildUnit(Gatherer.class);
            }
        }
        else if (getResourcesInSideOfMap() * 1.5 < getNodesInSideOfMap()) {
            if (getFleetValueUnitPercentage(Miner.class) < 0.6f && countMyUnits(Miner.class) < 17 && spawnMiners) {
                buildUnit(Miner.class);
            }
            if (getFleetValueUnitPercentage(Gatherer.class) < 0.2f && countMyUnits(Gatherer.class) < 15) {
                buildUnit(Gatherer.class);
            }
        }
        else {
            if (getFleetValueUnitPercentage(Miner.class) < 0.4f && countMyUnits(Miner.class) < 17 && spawnMiners) {
                buildUnit(Miner.class);
            }
            if (getFleetValueUnitPercentage(Gatherer.class) < 0.4f && countMyUnits(Gatherer.class) < 15) {
                buildUnit(Gatherer.class);
            }
        }

    }

    private void determineGameStage() {
        if (timer < 180 * 60) {
            resourceGameStage = "Early Game";
        }
        else if (timer < 300 * 60) {
            resourceGameStage = "Mid Early Game";
        }
        else if (timer < 480 * 60) {
            resourceGameStage = "Mid Game";
        }
        else if (timer < 700 * 60) {
            resourceGameStage = "Late Mid Game";
        }
        else if (timer < 900 * 60) {
            resourceGameStage = "Late Game";
        }
    }

    public void strategy() {
        resourceGrabberCount = countMyUnits(ResourceGrabber.class);
        determineGameStage();
        resourceStrategy();
        combatStrategy();
    }

    public void resourceStrategy() {
        if (countUnit(this, ResourceGrabber.class) < 2) {
            buildUnit(ResourceGrabber.class);
        }
        if (countUnit(this, MinerBuffer.class) < 1) {
            buildUnit(MinerBuffer.class);
        }

        if (resourceGameStage.equals("Early Game")) {
            if (getResourcesInSideOfMap() > getNodesInSideOfMap() * 1.5) {
                if (getFleetValueUnitPercentage(Miner.class) < 0.25f && countMyUnits(Miner.class) < 20 && spawnMiners) {
                    buildUnit(Miner.class);
                }
                if (getFleetValueUnitPercentage(Gatherer.class) < 0.35f && countMyUnits(Gatherer.class) < 20) {
                    buildUnit(Gatherer.class);
                }
            }
            else if (getResourcesInSideOfMap() * 1.5 < getNodesInSideOfMap()) {
                if (getFleetValueUnitPercentage(Miner.class) < 0.35f && countMyUnits(Miner.class) < 20 && spawnMiners) {
                    buildUnit(Miner.class);
                }
                if (getFleetValueUnitPercentage(Gatherer.class) < 0.25f && countMyUnits(Gatherer.class) < 20) {
                    buildUnit(Gatherer.class);
                }
            }
            else {
                if (getFleetValueUnitPercentage(Miner.class) < 0.3f && countMyUnits(Miner.class) < 20 && spawnMiners) {
                    buildUnit(Miner.class);
                }
                if (getFleetValueUnitPercentage(Gatherer.class) < 0.3f && countMyUnits(Gatherer.class) < 20) {
                    buildUnit(Gatherer.class);
                }
            }
        }
        else if (resourceGameStage.equals("Mid Early Game")) {
            if (getFleetValueUnitPercentage(Miner.class) < 0.25f && countMyUnits(Miner.class) < 17 && spawnMiners) {
                buildUnit(Miner.class);
            }
            if (getFleetValueUnitPercentage(Gatherer.class) < 0.25f && countMyUnits(Gatherer.class) < 20) {
                buildUnit(Gatherer.class);
            }
        }
        else if (resourceGameStage.equals("Mid Game")) {
            if (getFleetValueUnitPercentage(Miner.class) < 0.1f && countMyUnits(Miner.class) < 12 && spawnMiners) {
                buildUnit(Miner.class);
            }
            if (getFleetValueUnitPercentage(Gatherer.class) < 0.15f && countMyUnits(Gatherer.class) < 14) {
                buildUnit(Gatherer.class);
            }
        }
        else if (resourceGameStage.equals("Late Mid Game")) {
            if (getFleetValueUnitPercentage(Miner.class) < 0.05f && countMyUnits(Miner.class) < 7 && spawnMiners) {
                buildUnit(Miner.class);
            }
            if (getFleetValueUnitPercentage(Gatherer.class) < 0.15f && countMyUnits(Gatherer.class) < 10) {
                buildUnit(Gatherer.class);
            }
        }
        else if (resourceGameStage.equals("Late Game")) {
            if (getFleetValueUnitPercentage(Gatherer.class) < 0.1f && countMyUnits(Gatherer.class) < 10) {
                buildUnit(Gatherer.class);
            }
        }
    }

    public void combatStrategy() {
        if (countMyUnits(Destroyer.class) < NeverendingKnightsUnit.enemyRush.size() * 0.75f) {
            buildUnit(Destroyer.class);
        }

        if (getFleetValueUnitPercentage(MissileLauncher.class) < 0.175f && getFleetValueUnitPercentage(Sniper.class) > 0.25f && getFleetValueUnitPercentage(Tank.class) > 0.15f){
            buildUnit(MissileLauncher.class);
        }
        else if (getFleetValueUnitPercentage(Sniper.class) < 0.4f && getFleetValueUnitPercentage(Tank.class) > .15f){
            buildUnit(Sniper.class);
        }
        else if (getFleetValueUnitPercentage(Tank.class) < 0.2f){
            buildUnit(Tank.class);
        }
        else{
            buildUnit(Sniper.class);
        }

        if (Game.getTime() > 210 * 60) {
            if (getFleetValueUnit(Commander.class) < 1) {
                buildUnit(Commander.class);
            }
            if (getFleetValueUnit(OmenHealer.class) < 1) {
                buildUnit(OmenHealer.class);
            }
            if (getFleetValueUnit(RevelryHealer.class) < 1) {
                buildUnit(RevelryHealer.class);
            }
            if (getFleetValueUnit(GloryHealer.class) < 1) {
                buildUnit(GloryHealer.class);
            }
            if (getFleetValueUnitPercentage(TankHealer.class) < 0.05 && countMyUnits(Tank.class) > 4) {
                buildUnit(TankHealer.class);
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


        addMessage("Team Strategy: " + NeverendingKnightsUnit.teamStrategy);
        addMessage("Team Alert: " + NeverendingKnightsUnit.teamAlert);
        addMessage("Main Push Strength: " + NeverendingKnightsUnit.myMainPushStrength);
        addMessage("Main Push Density: " + NeverendingKnightsUnit.myMainPushDensity);
        addMessage("Main Push : " + NeverendingKnightsUnit.mainPushState);
        addMessage("Attack State : " + NeverendingKnightsUnit.attackState);
        addMessage("Healers Percentage: " + (float) NeverendingKnightsUnit.enemyHealers.size() / (float) getEnemyUnits().size());
//        addMessage("Main Push Avg Dist : " + (NeverendingKnightsUnit.mainPushAvgDist > getMyBase().getDistance(getEnemyBase()) / 3));
//        addMessage(" ");
//        addMessage("Enemy Scheme: " + NeverendingKnightsUnit.enemyAttackScheme);
//        addMessage("Enemy Workers: " + NeverendingKnightsUnit.enemyWorkerStrength);
//        addMessage("Nearest Enemy: " + NeverendingKnightsUnit.nearestEnemyThreatDist);
//        addMessage("Missile Threat: " + NeverendingKnightsUnit.enemyMissileThreat);
        addMessage(" ");
        addMessage("Attacker Value: " + NeverendingKnightsUnit.relativeAttackerStrength);
        addMessage("Threat Value: " + NeverendingKnightsUnit.relativeEnemyThreatStrength);
//        addMessage(" ");
//        addMessage("Light Threats: " + NeverendingKnightsUnit.lightEnemyThreats);
//        addMessage("Medium Threats: " + NeverendingKnightsUnit.mediumEnemyThreats);
//        addMessage("Heavy Threats: " + NeverendingKnightsUnit.heavyEnemyThreats);
//        addMessage("Assault Threats: " + NeverendingKnightsUnit.assaultEnemyThreats);
//        addMessage(" ");
//        addMessage("Nodes: " + NodeManager.getNodes().size());
//        addMessage("Resources: " + ResourceManager.getResources().size());
//        addMessage("Estimated Resources: " + estimatedMapResources);
//        addMessage(" ");
//        addMessage("Front X: " + NeverendingKnightsUnit.furthestTankX);
//        addMessage("Front Y: " + NeverendingKnightsUnit.furthestTankY);

    }

    public float getStoredResources(){
        return getMinerals();
    }

    public int getResourcesInSideOfMap() {
        if (this.isLeftPlayer()) {
            int totalResources = 0;
            for (Resource r: getAllResources()) {
                if (r.getCenterX() <= 0) {
                    totalResources++;
                }
            }
            return totalResources;
        }
        else {
            int totalResources = 0;
            for (Resource r: getAllResources()) {
                if (r.getCenterX() >= 0) {
                    totalResources++;
                }
            }
            return totalResources;
        }
    }

    public int getNodesInSideOfMap() {
        if (this.isLeftPlayer()) {
            int totalNodes = 0;
            for (Node r: getAllNodes()) {
                if (r.getCenterX() <= 0) {
                    totalNodes++;
                }
            }
            return totalNodes;
        }
        else {
            int totalNodes = 0;
            for (Node r: getAllNodes()) {
                if (r.getCenterX() >= 0) {
                    totalNodes++;
                }
            }
            return totalNodes;
        }
    }

}



//package teams.student.NeverendingKnights;
//
//import engine.states.Game;
//import objects.entity.node.NodeManager;
//import objects.entity.unit.Unit;
//import objects.resource.ResourceManager;
//import org.newdawn.slick.Graphics;
//import player.Player;
//import teams.student.NeverendingKnights.units.*;
//import teams.student.NeverendingKnights.units.buffs.*;
//import teams.student.NeverendingKnights.units.resource.Gatherer;
//import teams.student.NeverendingKnights.units.resource.Miner;
//import teams.student.NeverendingKnights.units.resource.MinerBuffer;
//import teams.student.NeverendingKnights.units.resource.ResourceGrabber;
//
//import java.util.ArrayList;
//
//public class NeverendingKnights extends Player
//{
//
//    public static ResourceAssigner resourceAssigner;
//    public static String gameStage;
//    public static int resourceGrabberCount;
//
//    public static int estimatedMapResources;
//    public static boolean spawnMiners;
//    public static boolean spawnBuffer;
//
//    public static ArrayList<Unit> tanks;
//
//    public void setup()
//    {
//        setName("Neverending Knights");
//        setTeamImage("src/teams/student/neverendingKnights/teamLogo.png");
//        setTitle("Neverending Knights");
//
//        setColorPrimary(255, 188, 0);
//        setColorSecondary(0, 255, 255);
//        setColorAccent(255, 128, 128);
//
//        resourceAssigner = new ResourceAssigner(this);
//
//        gameStage = "Instant";
//        estimatedMapResources = (getAllNodes().size() * 12) + ResourceManager.getResources().size();
//        spawnMiners = true;
//        spawnBuffer = true;
//    }
//
//    public void strategy() {
//        resourceGrabberCount = countMyUnits(ResourceGrabber.class);
//
//        if (countUnit(this, ResourceGrabber.class) < 2) {
//            buildUnit(ResourceGrabber.class);
//        }
//        if (countUnit(this, MinerBuffer.class) < 1 && spawnBuffer) {
//            buildUnit(MinerBuffer.class);
////            spawnBuffer = false;
//        }
//
//        if (Game.getTime() > 240 * 60) {
//            if (getFleetValueUnit(Commander.class) < 1) {
//                buildUnit(Commander.class);
//            }
//            if (getFleetValueUnit(OmenHealer.class) < 1) {
//                buildUnit(OmenHealer.class);
//            }
//            if (getFleetValueUnit(RevelryHealer.class) < 1) {
//                buildUnit(RevelryHealer.class);
//            }
//            if (getFleetValueUnit(GloryHealer.class) < 1) {
//                buildUnit(GloryHealer.class);
//            }
//            if (getFleetValueUnitPercentage(TankHealer.class) < 0.05 && countMyUnits(Tank.class) > 4) {
//                buildUnit(TankHealer.class);
//            }
//        }
//
//        if (timer < 180 * 60) {
//            if (getFleetValueUnitPercentage(Miner.class) < 0.35f && countMyUnits(Miner.class) < 17 && spawnMiners) {
//                buildUnit(Miner.class);
//            }
//            if (getFleetValueUnitPercentage(Gatherer.class) < 0.2f && countMyUnits(Gatherer.class) < 15) {
//                buildUnit(Gatherer.class);
//            }
////            if (getFleetValueUnitPercentage(Pest.class) < 0.1f){
////                buildUnit(Pest.class);
////            }
//
//        } else if (timer < 300 * 60) {
//            if (getFleetValueUnitPercentage(Miner.class) < 0.2f && countMyUnits(Miner.class) < 12 && spawnMiners) {
//                buildUnit(Miner.class);
//            }
//            if (getFleetValueUnitPercentage(Gatherer.class) < 0.15f && countMyUnits(Gatherer.class) < 14) {
//                buildUnit(Gatherer.class);
//            }
////            if (getFleetValueUnitPercentage(Pest.class) < 0.05f){
////                buildUnit(Pest.class);
////            }
//        }
//        else if (timer < 480 * 60) {
//            if (getFleetValueUnitPercentage(Miner.class) < 0.1f && countMyUnits(Miner.class) < 10 && spawnMiners) {
//                buildUnit(Miner.class);
//            }
//            if (getFleetValueUnitPercentage(Gatherer.class) < 0.1f && countMyUnits(Gatherer.class) < 12) {
//                buildUnit(Gatherer.class);
//            }
//        }
//        else if (timer < 700 * 60) {
//            if (getFleetValueUnitPercentage(Miner.class) < 0.05f && countMyUnits(Miner.class) < 7 && spawnMiners) {
//                buildUnit(Miner.class);
//            }
//            if (getFleetValueUnitPercentage(Gatherer.class) < 0.15f && countMyUnits(Gatherer.class) < 8) {
//                buildUnit(Gatherer.class);
//            }
//        }
//        else if (timer < 900 * 60) {
//            if (getFleetValueUnitPercentage(Gatherer.class) < 0.1f && countMyUnits(Gatherer.class) < 8) {
//                buildUnit(Gatherer.class);
//            }
//        }
//
//        if (countMyUnits(Destroyer.class) < NeverendingKnightsUnit.enemyRush.size() * 0.75f) {
//            buildUnit(Destroyer.class);
//        }
//
//
//        if (getFleetValueUnitPercentage(MissileLauncher.class) < 0.175f && getFleetValueUnitPercentage(Sniper.class) > 0.25f && getFleetValueUnitPercentage(Tank.class) > 0.15f){
//            buildUnit(MissileLauncher.class);
//        }
//        else if (getFleetValueUnitPercentage(Sniper.class) < 0.4f && getFleetValueUnitPercentage(Tank.class) > .15f){
//            buildUnit(Sniper.class);
//        }
//        else if (getFleetValueUnitPercentage(Tank.class) < 0.2f){
//            buildUnit(Tank.class);
//        }
//        else{
//            buildUnit(Sniper.class);
//        }
//
//    }
//
//    public void draw(Graphics g)
//    {
////        addMessage("Assigned: " + resourceAssigner.assignedResources.size());
////        addMessage("Total Counted: " + resourceAssigner.resourcesByDistToHomeBase.size());
////        addMessage("Total Counted + Assigned: " + (resourceAssigner.resourcesByDistToHomeBase.size() + resourceAssigner.assignedResources.size()));
////        addMessage("Actual Total: " + ResourceManager.getResources().size());
////        if (Gatherer.allDumpedResources != null) addMessage("Total Dumped: " + Gatherer.allDumpedResources.size());
////
////        addMessage("Game Stage: " + gameStage);
//
//
//        addMessage("Team Strategy: " + NeverendingKnightsUnit.teamStrategy);
//        addMessage("Team Alert: " + NeverendingKnightsUnit.teamAlert);
//        addMessage("Main Push Strength: " + NeverendingKnightsUnit.myMainPushStrength);
//        addMessage("Main Push Density: " + NeverendingKnightsUnit.myMainPushDensity);
//        addMessage("Main Push : " + NeverendingKnightsUnit.mainPushState);
//        addMessage("Attack State : " + NeverendingKnightsUnit.attackState);
////        addMessage("Main Push Avg Dist : " + (NeverendingKnightsUnit.mainPushAvgDist > getMyBase().getDistance(getEnemyBase()) / 3));
////        addMessage(" ");
////        addMessage("Enemy Scheme: " + NeverendingKnightsUnit.enemyAttackScheme);
////        addMessage("Enemy Workers: " + NeverendingKnightsUnit.enemyWorkerStrength);
////        addMessage("Nearest Enemy: " + NeverendingKnightsUnit.nearestEnemyThreatDist);
////        addMessage("Missile Threat: " + NeverendingKnightsUnit.enemyMissileThreat);
//        addMessage(" ");
//        addMessage("Attacker Value: " + NeverendingKnightsUnit.relativeAttackerStrength);
//        addMessage("Threat Value: " + NeverendingKnightsUnit.relativeEnemyThreatStrength);
//        addMessage(" ");
//        addMessage("Healers Percentage: " + NeverendingKnightsUnit.enemyHealers.size() / getEnemyUnits().size());
////        addMessage("Light Threats: " + NeverendingKnightsUnit.lightEnemyThreats);
////        addMessage("Medium Threats: " + NeverendingKnightsUnit.mediumEnemyThreats);
////        addMessage("Heavy Threats: " + NeverendingKnightsUnit.heavyEnemyThreats);
////        addMessage("Assault Threats: " + NeverendingKnightsUnit.assaultEnemyThreats);
////        addMessage(" ");
////        addMessage("Nodes: " + NodeManager.getNodes().size());
////        addMessage("Resources: " + ResourceManager.getResources().size());
////        addMessage("Estimated Resources: " + estimatedMapResources);
////        addMessage(" ");
////        addMessage("Front X: " + NeverendingKnightsUnit.furthestTankX);
////        addMessage("Front Y: " + NeverendingKnightsUnit.furthestTankY);
//
//    }
//
//    public float getStoredResources(){
//        return getMinerals();
//    }
//
//}
