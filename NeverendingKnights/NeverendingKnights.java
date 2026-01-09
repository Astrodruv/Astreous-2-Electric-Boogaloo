package teams.student.NeverendingKnights;

import components.weapon.economy.Collector;
import components.weapon.economy.Drillbeam;
import components.weapon.explosive.HeavyMissile;
import components.weapon.explosive.Missile;
import components.weapon.utility.AntiMissileSystem;
import engine.states.Game;
import objects.entity.unit.Frame;
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

    // Detects the furthest of each unit to detect which one should set the maxX
    public static Tank furthestTank;
    public static Sniper furthestSniper;

    public static int tanksCount;
    public static int resourceGrabberCount;

    public static ArrayList<Unit> tanks;

    public void setup()
    {
        setName("Neverending Knights");
        setTeamImage("src/teams/student/NeverendingKnights/teamLogo.png");
        setTitle("Newbie Team");

        setColorPrimary(255, 188, 0);
        setColorSecondary(255, 255, 255);
        setColorAccent(0, 0, 128);

        resourceAssigner = new ResourceAssigner(this);

        gameStage = "Instant";

        furthestTank = null;

        tanksCount = 0;
        resourceGrabberCount = 0;

        tanks = null;

    }

    public void strategy()
    {
        // Makes the actual strategy method a little cleaner and easier to understand

        if (gameStage.equals("Instant")){
            instantStrategy();
        }
        else if (gameStage.equals("Start")){
            startStrategy();
        }
        else if (gameStage.equals("Midgame")){
            midGameStrategy();
        }
        else if (gameStage.equals("Endgame")){
            endgameStrategy();
        }

        updateUnitCounts();
        updateUnitLists();
        furthestTank = (Tank) getFurthestTank();

        if (Game.getTime() > 120){
            gameStage = "Start";
        }
        if (getMyBase().getDistance(getEnemyBase()) < 8000){
            gameStage = "Midgame";
        }
        if (getMyBase().getDistance(getEnemyBase()) < 2500){
            gameStage = "Endgame";
        }

    }

    public void instantStrategy(){
        if (countUnit(this, ResourceGrabber.class) < 2){
            buildUnit(ResourceGrabber.class);
        }
        else if (countUnit(this, MinerBuffer.class) < 1){
            buildUnit(MinerBuffer.class);
        } // 12 resources
        else if (countUnit(this, Miner.class) < 4){
            buildUnit(Miner.class);
        } // 36
        else if (countUnit(this, Gatherer.class) < 4){
            buildUnit(Gatherer.class);
        }
        else if (countUnit(this, Pest.class) < 10){
            buildUnit(Pest.class);
        }
    }

    public void startStrategy(){
        if (countUnit(this, ResourceGrabber.class) < 2){
            buildUnit(ResourceGrabber.class);
        }
        if (countUnit(this, MinerBuffer.class) < 1){
            buildUnit(MinerBuffer.class);
        }
        if (countUnit(this, Pest.class) < 6){
            buildUnit(Pest.class);
        }
        if (countUnit(this, Creak.class) < 4){
            buildUnit(Creak.class);
        }
        if(countUnit(this, Gatherer.class) < 12) {
            buildUnit(Gatherer.class);
        }
        if (countUnit(this, Miner.class) < 8){
            buildUnit(Miner.class);
        }
        if (countUnit(this, Tank.class) < 10) {
            buildUnit(Tank.class);
        }
        if (countUnit(this, Sniper.class) < 15) {
            buildUnit(Sniper.class);
        }
        if (countUnit(this, MissileLauncher.class) < 6) {
            buildUnit(MissileLauncher.class);
        }
    }

    public void midGameStrategy(){
        if (countUnit(this, MinerBuffer.class) < 1){
            buildUnit(MinerBuffer.class);
        }
        if (countUnit(this, ResourceGrabber.class) < 2){
            buildUnit(ResourceGrabber.class);
        }
        if (countUnit(this, Pest.class) < 5){
            buildUnit(Pest.class);
        }
        if (countUnit(this, Creak.class) < 6){
            buildUnit(Creak.class);
        }
        if(countUnit(this, Gatherer.class) < 12) {
            buildUnit(Gatherer.class);
        }
        if (countUnit(this, Miner.class) < 8){
            buildUnit(Miner.class);
        }

        if (getFleetValueUnitPercentage(Sniper.class) < .25f) {
            buildUnit(Sniper.class);
        }
        if (getFleetValueUnitPercentage(MissileLauncher.class) < .15f) {
            buildUnit(MissileLauncher.class);
        }
        if (getFleetValueUnitPercentage(Tank.class) < .2f) {
            buildUnit(Tank.class);
        }
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
            if (countUnit(this,Sniper.class) < 40) {
                buildUnit(Sniper.class);
            }
            if (countUnit(this, MissileLauncher.class) < 15) {
                buildUnit(MissileLauncher.class);
            }
            if (countUnit(this, Tank.class) < 15) {
                buildUnit(Tank.class);
            }
    }

    public void updateUnitCounts(){
        tanksCount = countMyUnits(Tank.class);
        resourceGrabberCount = countMyUnits(ResourceGrabber.class);
    }

    public void updateUnitLists(){
        tanks = getUnits(this, Tank.class);
    }

    public Unit getFurthestTank(){
        ArrayList<Unit> tanks = getMyUnits(Tank.class);

        tanks.sort((tank1, tank2) -> Float.compare(tank1.getDistance(getEnemyBase()), tank2.getDistance(getEnemyBase())));

        if (!tanks.isEmpty()) return tanks.getFirst();
        return null;
    }

    public boolean hasAntiMissiles() {
        for (Unit l : getEnemyUnits()) {
            if (l.hasComponent(AntiMissileSystem.class)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasManyMissiles(){
        int totalMissiles = 0;

        for (Unit l: getEnemyUnits()){
            if (l.hasComponent(Missile.class) || l.hasComponent(HeavyMissile.class)) {
                totalMissiles++;
            }
        }

        if (totalMissiles > 5) return true;
        return false;
    }

    public boolean hasManyRushUnits(){
        int totalRush = 0;

        for (Unit l : getEnemyUnits()){
            if (l.getFrame() == Frame.LIGHT && !l.hasComponent(Collector.class) && !l.hasComponent(Drillbeam.class)){
                totalRush++;
            }
        }

        if (totalRush >= 5){
            return true;
        }
        return false;
    }

    public void draw(Graphics g)
    {
        addMessage("Assigned: " + resourceAssigner.assignedResources.size());
        addMessage("Total Counted: " + resourceAssigner.resourcesByDistToHomeBase.size());
        addMessage("Total Counted + Assigned: " + (resourceAssigner.resourcesByDistToHomeBase.size() + resourceAssigner.assignedResources.size()));
        addMessage("Actual Total: " + ResourceManager.getResources().size());
        if (Gatherer.allDumpedResources != null) addMessage("Total Dumped: " + Gatherer.allDumpedResources.size());

        addMessage("Game Stage: " + gameStage);
    }

}
