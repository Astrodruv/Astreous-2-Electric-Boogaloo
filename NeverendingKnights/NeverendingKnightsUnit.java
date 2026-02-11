package teams.student.NeverendingKnights;

import components.weapon.Weapon;
import components.weapon.economy.Collector;
import components.weapon.economy.Drillbeam;
import components.weapon.energy.EnergyWeapon;
import components.weapon.energy.HeavyLaser;
import components.weapon.energy.Laser;
import components.weapon.explosive.ExplosiveWeapon;
import components.weapon.explosive.HeavyMissile;
import components.weapon.explosive.Missile;
import components.weapon.kinetic.Autocannon;
import components.weapon.kinetic.HeavyAutocannon;
import components.weapon.kinetic.KineticWeapon;
import components.weapon.utility.*;
import engine.Values;
import engine.states.Game;
import objects.entity.node.Node;
import objects.entity.node.NodeManager;
import objects.entity.unit.BaseShip;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Unit;
import objects.resource.Resource;
import objects.resource.ResourceManager;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import teams.student.NeverendingKnights.units.Creak;
import teams.student.NeverendingKnights.units.Destroyer;
import teams.student.NeverendingKnights.units.Pest;
import teams.student.NeverendingKnights.units.Tank;
import teams.student.NeverendingKnights.units.resource.Gatherer;

import java.util.ArrayList;
import java.util.Comparator;

public abstract class NeverendingKnightsUnit extends Unit
{
    // Side note: Always add the shortest range weapon first

    // Calculations
    public static ArrayList<Unit> enemyThreats = new ArrayList<>();
    public static ArrayList<Unit> enemyRush = new ArrayList<>(); // Lights, non-weapons close to us, not necessarily trying to bum rush our gatherers but our fleet in general (Ivan's team)
    public static ArrayList<Unit> enemyWorkers = new ArrayList<>();
    public static ArrayList<Unit> enemyMissiles = new ArrayList<>();
    public static ArrayList<Unit> myAttackers = new ArrayList<>();
    public static ArrayList<Unit> myWorkers = new ArrayList<>();
    public static ArrayList<Unit> myWorkersInDanger = new ArrayList<>();

    public static ArrayList<Unit> tanks = new ArrayList<>();
    public static ArrayList<Unit> antiMissileTanks = new ArrayList<>();

    public static String enemyAttackScheme = ""; // Can focus on healing later
    public static String enemyWorkerStrength = "";
    public static String nearestEnemyThreatDist = "";
    public static float nearestEnemyThreatNumDist = 0;
    public static Unit nearestEnemyThreat = null;
    public static Unit nearestEnemyWorker = null;
    public static Unit primaryWorkerInDanger = null;
    public static String enemyMissileThreat = "";
    public static String myMainPushStrength = "";
    public static String myMainPushDensity = "";

    public static int lightEnemyThreats = 0;
    public static int mediumEnemyThreats = 0;
    public static int heavyEnemyThreats = 0;
    public static int assaultEnemyThreats = 0;

    public static int enemyAntiMissiles = 0;

    public static String teamStrategy = "";
    public static String teamAlert = "";
    public static String mainPushState = "";
    public static String attackState = "";

    public float mainPushRallyX = 0;
    public float mainPushRallyY = 0;
    public static float pestRallyX = 0;
    public static float pestRallyY = 0;
    public static float avgMainPushX = 0;
    public static float avgMainPushY = 0;

    public static int relativeEnemyThreatStrength = 0;
    public static int relativeAttackerStrength = 0;

    public static float furthestTankX = 0;
    public static float furthestTankY = 0;
    public static Unit furthestTank = null;

    public static float mainPushAvgDist = 0;

    protected Unit currentTarget = null;


    public void action() {
        if (Game.getTime() % 10 == 0) {
            calculations();
            setState();
            setRallyPoint();
        }
        act();
    }

    public void calculations(){
        calculateMyAttackers();
        calculateMyWorkers();
        calculateEnemyAttackType();
        calculateEnemyWorkerStrength();
        calculateNearestEnemyThreat();
        calculateEnemyMissiles();
        calculateMainPushStrength();
        calculateNearestEnemyWorker();
        calculateWorkersInDanger();
        calculateMainPushDensity();
        calculateMainPushAvgDist();
        calculateRelativePushStrength();
        calculateFurthestTankX();
        calculateAntiMissiles();
        calculateAvgMainPushXY();
    }

    public void setState(){
        setTeamStrategy();
        setTeamAlert();
        setMainPushState();
        setAttackState();
    }

    public void setRallyPoint(){
        if (attackState.equals("Growth")){
            if (getPlayer().isLeftPlayer()) mainPushRallyX = getHomeBase().getCenterX() + 600;
            else if (getPlayer().isRightPlayer()) mainPushRallyX = getHomeBase().getCenterX() - 600;
            mainPushRallyY = getHomeBase().getCenterY();
        }

        if (attackState.equals("Defense")){
            if (getPlayer().isLeftPlayer()) mainPushRallyX = getHomeBase().getCenterX() + 600;
            else if (getPlayer().isRightPlayer()) mainPushRallyX = getHomeBase().getCenterX() - 600;
            mainPushRallyY = getHomeBase().getCenterY();
        }

        if (attackState.equals("Defend Workers")){
            mainPushRallyX = nearestEnemyThreat.getCenterX();
            mainPushRallyY = nearestEnemyThreat.getCenterY();
        }

        if (attackState.equals("Attack")){
            if (mainPushState.equals("Retreat") || mainPushState.equals("Heavy Retreat")){
                if (getPlayer().isLeftPlayer()) mainPushRallyX = getHomeBase().getCenterX() + getHomeBase().getDistance(getEnemyBase()) / 2;
                else if (getPlayer().isRightPlayer()) mainPushRallyX = getHomeBase().getCenterX() - getHomeBase().getDistance(getEnemyBase()) / 2;
                mainPushRallyY = getHomeBase().getCenterY();
            }
            else {
                if (getBiggestThreatInRadius(getMaxRange() * 2) != null) {
                    mainPushRallyX = getBiggestThreatInRadius(getMaxRange() * 2).getCenterX();
                    mainPushRallyY = getBiggestThreatInRadius(getMaxRange() * 2).getCenterY();
                }
                else{
                    mainPushRallyX = nearestEnemyThreat.getCenterX();
                    mainPushRallyY = nearestEnemyThreat.getCenterY();
//                    mainPushRallyX = getEnemyBase().getCenterX();
//                    mainPushRallyY = getEnemyBase().getCenterY();
                }
            }
        }

        if (attackState.equals("Kill")){ // Best enemy to attack in lane?
            if (getBiggestThreatInRadius(getMaxRange() * 2) != null) {
                mainPushRallyX = getBiggestThreatInRadius(getMaxRange() * 2).getCenterX();
                mainPushRallyY = getBiggestThreatInRadius(getMaxRange() * 2).getCenterY();
//                mainPushRallyX = nearestEnemyThreat.getCenterX();
//                mainPushRallyY = nearestEnemyThreat.getCenterY();
            }
            else{
                    mainPushRallyX = nearestEnemyThreat.getCenterX();
                    mainPushRallyY = nearestEnemyThreat.getCenterY();
//                mainPushRallyX = getEnemyBase().getCenterX();
//                mainPushRallyY = getEnemyBase().getCenterY();
            }
        }

        if (nearestEnemyWorker != null) {
            pestRallyX = nearestEnemyWorker.getCenterX();
            pestRallyY = nearestEnemyWorker.getCenterY();
        }
        else{
            pestRallyX = getHomeBase().getCenterX();
            pestRallyY = getHomeBase().getCenterY();
        }

    }

    public void act(){
        currentTarget = getBiggestThreatInRadius(getMaxRange() * 1.25f);

        attack(getWeaponOne());
        attack(getWeaponTwo());
        movement();
    }

    public void attack(Weapon w) {
        if (w != null){
            if (currentTarget != null) w.use(currentTarget);
            else w.use(getNearestEnemy());
        }
    }

    public void movement() {
//        if (getNearestAllyAttacker() != null && getDistance(getNearestAllyAttacker()) > 75) {
            boolean behindTanks = (getPlayer().isLeftPlayer() && getX() < (furthestTankX - (getMaxRange() * 0.65f))) || (getPlayer().isRightPlayer() && getX() > (furthestTankX + (getMaxRange() * 0.65f)));
            if (!tanks.isEmpty()) {
                if ((!antiMissileTanks.isEmpty() && antiMissileTanks.contains(this)) || (antiMissileTanks.isEmpty() && tanks.contains(this))) {
                    if (getAlliesInRadius(1500).size() > myAttackers.size() * 0.75f || myMainPushStrength.equals("Very High")) { // ensures that tanks don't rush in and that tanks don't camp near base for no reason
                        if (currentTarget != null) {
                            if (getDistance(currentTarget) < getWeaponOne().getMaxRange() && getWeaponOne().getCooldownProgress() > getWeaponOne().getCooldownTimer() * 0.25f && !getWeaponOne().canUse()) {
                                turnTo(currentTarget);
                                turnAround();
                                move();
                            } else {
                                if (getDistance(avgMainPushX, avgMainPushY) > 750 && avgMainPushY != 0 && avgMainPushX != 0) {
                                    moveTo(avgMainPushX, avgMainPushY);
                                } else {
                                    moveTo(mainPushRallyX, mainPushRallyY);
                                }
                            }
                        } else {
                            if (getDistance(getNearestEnemy()) < getWeaponOne().getMaxRange() && getWeaponOne().getCooldownProgress() > getWeaponOne().getCooldownTimer() * 0.25f && !getWeaponOne().canUse()) {
                                turnTo(getNearestEnemy());
                                turnAround();
                                move();
                            } else {
                                if (getDistance(avgMainPushX, avgMainPushY) > 750 && avgMainPushY != 0 && avgMainPushX != 0) {
                                    moveTo(avgMainPushX, avgMainPushY);
                                } else {
                                    moveTo(mainPushRallyX, mainPushRallyY);
                                }
                            }
                        }
                    } else {
                        moveTo(getHomeBase());
                    }
                } else {
                    if (myMainPushStrength.equals("Very High") && getDistance(getEnemyBase()) < 1000) {
                        if (currentTarget != null) {
                            if (getDistance(currentTarget) < getWeaponOne().getMaxRange() && getWeaponOne().getCooldownProgress() > getWeaponOne().getCooldownTimer() * 0.25f && !getWeaponOne().canUse()) {
                                turnTo(currentTarget);
                                turnAround();
                                move();
                            } else {
                                moveTo(mainPushRallyX, mainPushRallyY);
                            }
                        } else {
                            if (getDistance(getNearestEnemy()) < getWeaponOne().getMaxRange() && getWeaponOne().getCooldownProgress() > getWeaponOne().getCooldownTimer() * 0.25f && !getWeaponOne().canUse()) {
                                turnTo(getNearestEnemy());
                                turnAround();
                                move();
                            } else {
                                moveTo(mainPushRallyX, mainPushRallyY);
                            }
                        }
                    } else {
                        if (behindTanks && getDistance(x, furthestTankY) < 1500) {
                            if (currentTarget != null) {
                                if (getDistance(currentTarget) < getWeaponOne().getMaxRange() && getWeaponOne().getCooldownProgress() > getWeaponOne().getCooldownTimer() * 0.25f && !getWeaponOne().canUse()) {
                                    turnTo(currentTarget);
                                    turnAround();
                                    move();
                                } else {
                                    moveTo(mainPushRallyX, mainPushRallyY);
                                }
                            } else {
                                if (getDistance(getNearestEnemy()) < getWeaponOne().getMaxRange() && getWeaponOne().getCooldownProgress() > getWeaponOne().getCooldownTimer() * 0.25f && !getWeaponOne().canUse()) {
                                    turnTo(getNearestEnemy());
                                    turnAround();
                                    move();
                                } else {
                                    moveTo(mainPushRallyX, mainPushRallyY);
                                }
                            }
                        } else {
                            if (getDistance(x, furthestTankY) > 1500) {
                                moveTo(x, furthestTankY);
                            } else {
                                moveTo(getHomeBase());
                                move();
                            }
                        }
                    }
                }
            } else {
                if (currentTarget != null) {
                    if (getDistance(currentTarget) < getWeaponOne().getMaxRange() && getWeaponOne().getCooldownProgress() > getWeaponOne().getCooldownTimer() * 0.25f && !getWeaponOne().canUse()) {
                        turnTo(currentTarget);
                        turnAround();
                        move();
                    } else {
                        moveTo(mainPushRallyX, mainPushRallyY);
                    }
                } else {
                    if (getDistance(getNearestEnemy()) < getWeaponOne().getMaxRange() && getWeaponOne().getCooldownProgress() > getWeaponOne().getCooldownTimer() * 0.25f && !getWeaponOne().canUse()) {
                        turnTo(getNearestEnemy());
                        turnAround();
                        move();
                    } else {
                        moveTo(mainPushRallyX, mainPushRallyY);
                    }
                }
            }
//        }
//        else{
//            turnTo(getNearestAllyAttacker());
//            turnAround();
//            move();
//        }
    }

    public void calculateFurthestTankX(){
        tanks.clear();
        antiMissileTanks.clear();

        for (Unit u : myAttackers){
            if (u instanceof Tank) tanks.add(u);
            if (u instanceof Tank && u.hasWeapon(AntiMissileSystem.class)) antiMissileTanks.add(u);
        }

        tanks.sort((w1, w2) -> Float.compare(w1.getDistance(getEnemyBase()), w2.getDistance(getEnemyBase())));
        antiMissileTanks.sort((w1, w2) -> Float.compare(w1.getDistance(getEnemyBase()), w2.getDistance(getEnemyBase())));

        if (!tanks.isEmpty()){
            if (antiMissileTanks.isEmpty()) {
                furthestTankX = tanks.getFirst().getCenterX();
                furthestTankY = tanks.getFirst().getCenterY();
                furthestTank = tanks.getFirst();
            }
            else{
                furthestTankX = antiMissileTanks.getFirst().getCenterX();
                furthestTankY = antiMissileTanks.getFirst().getCenterY();
                furthestTank = antiMissileTanks.getFirst();
            }
        }
    }

    // Calculations
    public void calculateEnemyAttackType(){
        enemyThreats.clear();
        enemyRush.clear();

        for (Unit u : getEnemies()){
            if (!u.hasComponent(Drillbeam.class) && !u.hasComponent(Collector.class)){
                if ((u.getFrame() == Frame.LIGHT && !(u.hasWeapon(Collector.class)) && !(u.hasWeapon(Drillbeam.class)))){
                    if (u.hasComponent(ExplosiveWeapon.class) || u.hasComponent(KineticWeapon.class) || u.hasComponent(EnergyWeapon.class)) {
                        enemyRush.add(u);
                    }
                }
                enemyThreats.add(u);
            }
        }

        calculateLightEnemyThreats(enemyThreats);
        calculateMediumEnemyThreats(enemyThreats);
        calculateHeavyEnemyThreats(enemyThreats);
        calculateAssaultEnemyThreats(enemyThreats);

        if (((float) lightEnemyThreats / enemyThreats.size()) > 0.5f){ // if more than 50% of all attacking units are Light
            enemyAttackScheme = "Very Light";
        }
        else if (((float) mediumEnemyThreats / enemyThreats.size()) > 0.5f){
            enemyAttackScheme = "Very Medium";
        }
        else if (((float) heavyEnemyThreats / enemyThreats.size()) > 0.5f){
            enemyAttackScheme = "Very Heavy";
        }
        else if (((float) assaultEnemyThreats / enemyThreats.size()) > 0.25f){
            enemyAttackScheme = "Very Assault";
        }
        else {
            enemyAttackScheme = "Undecided";
        }

        if (((float) lightEnemyThreats / enemyThreats.size()) > 0.25f && ((float) mediumEnemyThreats / enemyThreats.size()) > 0.25f){
            enemyAttackScheme = "Light-Medium Rush";
        }
        else if (((float) mediumEnemyThreats / enemyThreats.size()) > 0.25f && ((float) heavyEnemyThreats / enemyThreats.size()) > 0.25f){
            enemyAttackScheme = "Medium-Heavy Tank";
        }
    }

    public Unit getIsolatedEnemyWorker(float radius) {

        ArrayList<Unit> enemies = new ArrayList<>(getEnemiesInRadius(radius));

        Unit best = null;
        float bestScore = 0;

        for (Unit u : enemies) {

            if (!u.hasComponent(Collector.class) || !u.hasComponent(Drillbeam.class))
                continue;

            float distFromBase = u.getDistance(getEnemyBase());
            float distFromYou = u.getDistance(getPosition());
            float health = u.getPercentEffectiveHealth();

            float score = distFromBase * 1.0f - distFromYou  * 1.5f - health * 2.0f;

            if (score > bestScore) {
                bestScore = score;
                best = u;
            }
        }

        return best;
    }


    public void calculateWorkersInDanger(){
        myWorkersInDanger.clear();
        if (myWorkers.contains(this)){
            if (getNearestEnemyThreat() != null && getDistance(getNearestEnemyThreat()) > getNearestEnemyThreat().getMaxRange() * 1.5f){
                myWorkersInDanger.add(this);
            }
        }

        myWorkersInDanger.sort((w1, w2) -> Float.compare(w2.getDistance(getHomeBase()), w1.getDistance(getHomeBase())));

        if (!myWorkersInDanger.isEmpty()){
            primaryWorkerInDanger = myWorkersInDanger.getFirst();
        }

    }

    public void calculateEnemyWorkerStrength() {
        enemyWorkers.clear();

        for (Unit u : getEnemiesExcludeBaseShip()) {
            if (u.hasComponent(Drillbeam.class) || u.hasComponent(Collector.class)) {
                enemyWorkers.add(u);
            }
        }

        if ((float) enemyWorkers.size() / getEnemiesExcludeBaseShip().size() > 0.75f) {
            enemyWorkerStrength = "High";
        }
        else if ((float) enemyWorkers.size() / getEnemiesExcludeBaseShip().size() > 0.5f) {
            enemyWorkerStrength = "Medium";
        }
        else if ((float) enemyWorkers.size() / getEnemiesExcludeBaseShip().size() > 0.25f) {
            enemyWorkerStrength = "Low";
        }
        else {
            enemyWorkerStrength = "Very Low";
        }
    }

    public void calculateAntiMissiles(){
        enemyAntiMissiles = 0;
        for (Unit u : enemyThreats){
            if (u.hasWeapon(AntiMissileSystem.class)) enemyAntiMissiles++;
        }
    }

    public void calculateEnemyMissiles(){
        enemyMissiles.clear();

        for (Unit u : getEnemiesExcludeBaseShip()) {
            if (u.hasWeapon(ExplosiveWeapon.class)) {
                enemyMissiles.add(u);
            }
        }

        if ((float) enemyMissiles.size() / enemyThreats.size() > 0.35f) {
            enemyMissileThreat = "High";
        }
        else if ((float) enemyMissiles.size() / enemyThreats.size() > 0.2f) {
            enemyMissileThreat = "Medium";
        }
        else if ((float) enemyMissiles.size() / enemyThreats.size() > 0.05f) {
            enemyMissileThreat = "Low";
        }
        else {
            enemyMissileThreat = "Very Low";
        }
    }

    public void calculateNearestEnemyThreat(){
        nearestEnemyThreatNumDist = 0;
        enemyThreats.sort((e1, e2) -> Float.compare(e1.getDistance(getHomeBase()), e2.getDistance(getHomeBase())));
        if (!enemyThreats.isEmpty()){
            nearestEnemyThreat = enemyThreats.getFirst();
            for (int i = 0; i < enemyThreats.size(); i++) {
                if ((getPlayer().isLeftPlayer() && enemyThreats.get(i).getX() > getHomeBase().getCenterX()) || (getPlayer().isRightPlayer() && enemyThreats.get(i).getX() < getHomeBase().getCenterX())){
                    nearestEnemyThreat = enemyThreats.get(i);
                    break;
                }
            }

            if (attackState.equals("Attack") && mainPushAvgDist > getHomeBase().getDistance(getEnemyBase()) / 3){
                for (int i = 0; i < enemyThreats.size(); i++) {
                    if (enemyThreats.get(i).getAlliesInRadius(1000).size() + 1 > enemyThreats.size() * 0.25f) {
                        if ((getPlayer().isLeftPlayer() && enemyThreats.get(i).getX() > getHomeBase().getCenterX()) || (getPlayer().isRightPlayer() && enemyThreats.get(i).getX() < getHomeBase().getCenterX())){
                            nearestEnemyThreat = enemyThreats.get(i);
                            break;
                        }
                    }
                }
            }

//            if (attackState.equals("Kill")){
//                if (!getEnemyBase().getAlliesInRadius(1000).isEmpty()) nearestEnemyThreat = getEnemyBase().getNearestAlly();
//                else nearestEnemyThreat = getEnemyBase();
//            }

            nearestEnemyThreatNumDist = nearestEnemyThreat.getDistance(getHomeBase());
        }

//        if (nearestEnemyThreat.getAlliesInRadius(600).size() > enemyThreats.size() * 0.25f) {
        if (nearestEnemyThreatNumDist > 10000) {
            nearestEnemyThreatDist = "Very Far";
        } else if (nearestEnemyThreatNumDist > 7000) {
            nearestEnemyThreatDist = "Far";
        } else if (nearestEnemyThreatNumDist > 4500) {
            nearestEnemyThreatDist = "Moderate";
        }else if (nearestEnemyThreatNumDist > 1500) {
            nearestEnemyThreatDist = "Close";
        } else if (nearestEnemyThreatNumDist > 750) {
            nearestEnemyThreatDist = "Very Close";
        } else {
            nearestEnemyThreatDist = "Extremely Close";
        }
    }

    public void calculateNearestEnemyWorker(){
        nearestEnemyWorker = getNearestEnemyWorker();
    }

    public void calculateMyAttackers(){
        myAttackers.clear();
        for (Unit u : getAlliesExcludeBaseShip()){
            if (!u.hasComponent(Drillbeam.class) && !u.hasComponent(Collector.class) && !(u instanceof Pest) && !(u instanceof Destroyer)){
                // Should we apply the rush aspect here as well?
                myAttackers.add(u);
            }
        }
    }

    public void calculateRelativePushStrength(){
        int attackerSum = 0;
        int threatSum = 0;

        for (Unit u : myAttackers) {
            if (u.getFrame().equals(Frame.LIGHT)) attackerSum += 2;
            else if (u.getFrame().equals(Frame.MEDIUM)) attackerSum += 3;
            else if (u.getFrame().equals(Frame.HEAVY)) attackerSum += 4;
            else if (u.getFrame().equals(Frame.ASSAULT)) attackerSum += 5;
        }

        for (Unit u : enemyThreats){
            if (u.getFrame().equals(Frame.LIGHT)) threatSum += 2;
            else if (u.getFrame().equals(Frame.MEDIUM)) threatSum += 3;
            else if (u.getFrame().equals(Frame.HEAVY)) threatSum += 4;
            else if (u.getFrame().equals(Frame.ASSAULT)) threatSum += 5;
        }

        relativeAttackerStrength = attackerSum;
        relativeEnemyThreatStrength = threatSum;
    }

    public void calculateMainPushAvgDist(){
        float sum = 0;
        if (!myAttackers.isEmpty()) {
            for (Unit u : myAttackers) {
                sum += getHomeBase().getDistance(u);
            }
            sum = sum / myAttackers.size();
        }

        mainPushAvgDist = sum;
    }

    public void calculateMyWorkers(){
        myWorkers.clear();
        for (Unit u : getAlliesExcludeBaseShip()){
            if (u.hasComponent(Drillbeam.class) || u.hasComponent(Collector.class)){
                myWorkers.add(u);
            }
        }
    }

    public void calculateMainPushStrength(){
        if (relativeAttackerStrength > relativeEnemyThreatStrength * 1.35){
            myMainPushStrength = "Very High";
        }
        else if (relativeAttackerStrength > relativeEnemyThreatStrength * 1.15f){
            myMainPushStrength = "High";
        }
        else if (relativeAttackerStrength > relativeEnemyThreatStrength){
            myMainPushStrength = "Moderately High";
        }
        else if (relativeAttackerStrength * 2 < relativeEnemyThreatStrength){
            myMainPushStrength = "Very Low";
        }
        else if (relativeAttackerStrength * 1.5f < relativeEnemyThreatStrength){
            myMainPushStrength = "Low";
        }
        else if (relativeAttackerStrength < relativeEnemyThreatStrength){
            myMainPushStrength = "Moderately Low";
        }
        else {
            myMainPushStrength = "Moderate";
        }
    }


    public void calculateAvgMainPushXY(){
        float sumX = 0;
        float sumY = 0;
        float count = 0;

        for (Unit u : myAttackers){
            if (u.getDistance(getEnemyBase()) < getEnemyBase().getDistance(0,0)){
                sumX += u.getX();
                sumY += u.getY();
                count++;
            }
        }

        sumX = sumX / count;
        sumY = sumY / count;

        avgMainPushX = sumX;
        avgMainPushY = sumY;
    }

    public void calculateMainPushDensity(){
        // ALREADY RECALCULATED - just put this here to explain how I did in case in the future in needs to change again

        // Redo this method - take the average x and y values of all attacking units on the attacking side of the map (past 0,0)
        // Then, get the number of attacking units with a distance of 1000 or less towards that average
        // If that value is less than 0.5f of all attacking units on the attacking side, retreat
        // Assign strings based on this
        // Basically compare the close-to-average units with only the units actually on the attacking side of the map...
        // Not all attacking units in general

        ArrayList<Unit> mainPushAttackers = new ArrayList<>();
        float count = 0;

        for (Unit u : myAttackers){
            if (u.getDistance(getEnemyBase()) < getEnemyBase().getDistance(0,0)){
                mainPushAttackers.add(u);
            }
        }

        for (Unit u : mainPushAttackers){
            if (u.getDistance(avgMainPushX, avgMainPushY) < 750){
                count++;
            }
        }

        if (count == mainPushAttackers.size()) {
            myMainPushDensity = "Perfect";
        } else if (count < mainPushAttackers.size() * 0.1f) {
            myMainPushDensity = "Terrible";
        } else if (count < mainPushAttackers.size() * 0.25f) {
            myMainPushDensity = "Bad";
        } else if (count < mainPushAttackers.size() * 0.55f) {
            myMainPushDensity = "Moderate";
        } else if (count < mainPushAttackers.size() * 0.7f) {
            myMainPushDensity = "Decent";
        }
    }


    public void calculateLightEnemyThreats(ArrayList<Unit> enemies){
        lightEnemyThreats = 0;
        for (Unit u : enemies){
            if (u.getFrame().equals(Frame.LIGHT)) lightEnemyThreats++;
        }
    }
    public void calculateMediumEnemyThreats(ArrayList<Unit> enemies){
        mediumEnemyThreats = 0;
        for (Unit u : enemies){
            if (u.getFrame().equals(Frame.MEDIUM)) mediumEnemyThreats++;
        }
    }
    public void calculateHeavyEnemyThreats(ArrayList<Unit> enemies){
        heavyEnemyThreats = 0;
        for (Unit u : enemies){
            if (u.getFrame().equals(Frame.HEAVY)) heavyEnemyThreats++;
        }
    }
    public void calculateAssaultEnemyThreats(ArrayList<Unit> enemies){
        assaultEnemyThreats = 0;
        for (Unit u : enemies){
            if (u.getFrame().equals(Frame.ASSAULT)) assaultEnemyThreats++;
        }
    }


    // Set State Methods
    public void setTeamStrategy(){
        switch (enemyAttackScheme) {
            case "Very Light" -> teamStrategy = "Anti-Rush";
            // Eos for accuracy, Poseidon for knockback, Achilles to kill, EMP repeater to stun

            case "Very Medium" -> teamStrategy = "Buildup";
            // Achilles to kill, Cerberus if no antimissile, Kratos to slow, EMP repeater to stun, Poseidon for knockback

            case "Very Heavy" -> teamStrategy = "Range";
            // Zeus for range, Cerberus/Nyx if no antimissile, Ares for black

            case "Very Assault" -> teamStrategy = "Rush";
            // Ares for block, Cerberus if no antimissile

            case "Light-Medium Rush" -> teamStrategy = "DPS Buildup";

            case "Medium-Heavy Tank" -> teamStrategy = "Range Buildup";

            default -> teamStrategy = "Undecided";
        }
    }

    public void setTeamAlert(){
        switch (nearestEnemyThreatDist) {
            case "Very Far" -> teamAlert = "Very Low";
            case "Far" -> teamAlert = "Low";
            case "Moderate" -> teamAlert = "Moderate";
            case "Close" -> teamAlert = "High";
            case "Very Close" -> teamAlert = "Very High";
            case "Extremely Close" -> teamAlert = "Extreme";
            case "Small Close" -> teamAlert = "Small High";
            case "Small Very Close" -> teamAlert = "Small Very High";
            case "Small Extremely Close" -> teamAlert = "Small Extreme";


            default -> teamAlert = "Undecided";
        }
    }

    public void setMainPushState(){
        switch (myMainPushDensity) {
            case "Perfect" -> mainPushState = "Perfect";
            case "Decent" -> mainPushState = "Decent";
            case "Moderate" -> mainPushState = "Moderate";
            case "Bad" -> mainPushState = "Retreat";
            case "Terrible" -> mainPushState = "Heavy Retreat";

            default -> mainPushState = "Undecided";
        }
    }

    public void setAttackState(){
        if (teamAlert.equals("High") || teamAlert.equals("Very High") || teamAlert.equals("Extreme")){
            if (enemyAttackScheme.equals("Very Light")){
                attackState = "Defend Workers";
            }
            else {
//                if (myMainPushStrength.equals("High") || myMainPushStrength.equals("Moderately High") || getAllies().size() > 77){
                attackState = "Attack";
//                }
                if (myMainPushStrength.equals("Very High")){ // else
                    attackState = "Kill";
                }
            }
        }
        if (teamAlert.equals("Low") || teamAlert.equals("Moderate")){
            if (myMainPushStrength.equals("Very High") || myMainPushStrength.equals("High") || myMainPushStrength.equals("Moderately High") || getAllies().size() > 75){
                attackState = "Attack";
            }
            else{
                attackState = "Growth";
            }

            if (teamAlert.equals("Moderate") && myMainPushStrength.equals("Very High")){
                attackState = "Kill";
            }
        }
        if (teamAlert.equals("Very Low") && !myMainPushStrength.equals("Very High") && !myMainPushStrength.equals("High")){ // Last part is a gamble ig
            if (getAllies().size() > 75) attackState = "Attack";
            else attackState = "Growth";
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.white);
        g.drawLine(getCenterX(), getCenterY(), mainPushRallyX, mainPushRallyY);
        g.setColor(Color.green);
        if (currentTarget != null) g.drawLine(getCenterX(), getCenterY(), currentTarget.getCenterX(), currentTarget.getCenterY());
        g.setColor(new Color(255,0,255));
        g.drawLine(getCenterX(), getCenterY(), avgMainPushX, avgMainPushY);
    }

    public ArrayList<Unit> getEnemyThreatsInRange(int range){
        ArrayList<Unit> threats = new ArrayList<>();

        for (Unit u : getEnemies()) {
            if (!u.hasWeapon(Collector.class) && getDistance(u) < range) threats.add(u);
        }

        return threats;
    }

    public Unit lowestTarget(int range) {
        ArrayList<Unit> e = getEnemyThreatsInRange(range);

        e.sort((e1, e2) -> Float.compare(e1.getPercentEffectiveHealth(), e2.getPercentEffectiveHealth()));

        if (!e.isEmpty()) {
            return e.getFirst();
        }

        else return null;
    }

    //**********************************************************************************
    // ENEMY TARGETING *****************************************************************
    //**********************************************************************************

    public Unit getBiggestThreatInRadius(float radius) {
        Unit bestEnemy = null;
        ArrayList<Unit> enemies = getEnemiesInRadius(radius);
        if (!enemies.isEmpty()) {
            int bestEnemyScore = 0;
            for (Unit enemy : enemies) {
                int totalPoints = 0;
                totalPoints += healthScore(enemy);
                totalPoints += damageScore(enemy);
                totalPoints += speedScore(enemy);
                totalPoints += typeShipScore(enemy);
                totalPoints += statusAndRangeScore(enemy);
                totalPoints += distanceScore(enemy);
                if (bestEnemy == null) {
                    bestEnemyScore = totalPoints;
                    bestEnemy = enemy;
                } else {
                    if (bestEnemyScore < totalPoints) {
                        bestEnemyScore = totalPoints;
                        bestEnemy = enemy;
                    }
                }
            }
        }
        if (bestEnemy != null && (bestEnemy.hasComponent(Collector.class))) return null;
        if (bestEnemy != null) return bestEnemy;
        return null;
    }

    protected int distanceScore(Unit u) {
        // Should be the most important method (health is nearly as big)
        if (getDistance(u) < (float) getMaxRange() / 3) {
            return 500;
        }
        else if (getDistance(u) < (float) (getMaxRange() * 2) / 3) {
            return 300;
        }
        else if (getDistance(u) < getMaxRange()) {
            return 200;
        }
        else {
            return 150;
        }
    }

    protected int statusAndRangeScore(Unit u) {
        if (u.hasComponent(CommandRelay.class)) { // Inherently increases their DPS, so value more
            return 150;
        }
        else if (u.hasComponent(Pullbeam.class)) {
            return 75;
        }
        else if (u.hasComponent(SpeedBoost.class)) {
            return 50;
        }
        else if (u.hasComponent(ElectromagneticPulse.class)) { // Inherently reduces our DPS, so value more
            return 200;
        }
        else if (u.hasComponent(GravitationalRift.class)) { // Inherently reduces our DPS, so value more
            return 300;
        }
        else if (u.hasComponent(HeavyRepairBeam.class)) { // Inherently increases their DPS, so value more
            return 175;
        }
        else if (u.hasWeapon(RepairBeam.class)) { // Inherently increases their DPS, so value more
            return 150;
        }
        if (u.hasWeapon(Collector.class)) { // We need to be able to attack wandering gatherers that are in our way if there isn't anything else
            return 50;
        }
        if (u.hasComponent(Drillbeam.class)) { // lol
            return 75;
        }
        return 0;
    }

    protected int typeShipScore(Unit u) {
        // This shouldn't really matter much, but I'll keep it just in case
        if (u.getModel().equals(Model.STRIKER)) {
            return 20;
        }
        else if (u.getModel().equals(Model.ARTILLERY)) {
            return 15;
        }
        else if (u.getModel().equals(Model.BASTION)) {
            return 15;
        }
        else if (u.getModel().equals(Model.DESTROYER)) {
            return 20;
        }
        else if (u.getModel().equals(Model.TRANSPORT)) {
            return 5;
        }
        return 15;
    }

    protected int speedScore(Unit u) {
        // This also shouldn't matter too much, chances are if they are light they don't do much DPS and we can dispatch of them easily anyways
        if (u.getFrame().equals(Frame.LIGHT)) {
            return 25;
        }
        else if (u.getFrame().equals(Frame.MEDIUM)) {
            return 50;
        }
        else if (u.getFrame().equals(Frame.HEAVY)) {
            return 75;
        }
        else if (u.getFrame().equals(Frame.ASSAULT)) {
            return 100;
        }
        return 20;
    }

    protected int damageScore(Unit u) {
        // Simplified this a lot, it shouldn't really matter what type the weapon is other than if it is heavy
        if (u.hasWeapon(HeavyMissile.class)) {
            return 300;
        }
        else if (u.hasWeapon(Missile.class)) {
            return 200;
        }
        else if (u.hasWeapon(HeavyLaser.class)) {
            return 300;
        }
        else if (u.hasWeapon(Laser.class)) {
            return 200;
        }
        else if (u.hasWeapon(HeavyAutocannon.class)) {
            return 300;
        }
        else if (u.hasWeapon(Autocannon.class)) {
            return 200;
        }
        return 5;
    }

    protected int healthScore(Unit u) {
        // Should be the most important method because more units dead = less enemy DPS
        if (u.getPercentEffectiveHealth() < 0.25) {
            return 550;
        }
        else if (u.getPercentEffectiveHealth() < 0.5) {
            return 400;
        }
        else if (u.getPercentEffectiveHealth() < 0.75) {
            return 250;
        }
        return 100;
    }

    //**********************************************************************************
    // RESOURCES ***********************************************************************
    //**********************************************************************************

    public ArrayList<Resource> getResourcesInRadius(float rad, NeverendingKnightsUnit u){
        ArrayList<Resource> res = new ArrayList<>();
        for (Resource r : ResourceManager.getResources()){
            if (r.getDistance(u) < rad){
                res.add(r);
            }
        }

        return res;
    }

    public boolean isResourceSafe(Resource r){
        float myDist = getDistance(r);
        float enemyDist = Float.MAX_VALUE;
        Unit enemy = getNearestEnemy();

        for (Unit u : enemyThreats){
            if (enemyDist > u.getDistance(r)) {
                enemyDist = u.getDistance(r);
                enemy = u;
            }
        }

        if (enemyDist > enemy.getMaxRange() * 1.5f) return true;
        return false;
    }

    public boolean isNodeSafe(Node n){
        float myDist = getDistance(n);
        float enemyDist = Float.MAX_VALUE;
        Unit enemy = getNearestEnemy();

        for (Unit u : enemyThreats){
            if (enemyDist > u.getDistance(n)) {
                enemyDist = u.getDistance(n);
                enemy = u;
            }
        }

        if (enemyDist > enemy.getMaxRange() * 1.5f) return true;
        return false;
    }

    public ArrayList<Resource> getClosestResourcesToResource(Resource r){
        ArrayList<Resource> threeClosest = new ArrayList<>();
        ArrayList<Resource> res = new ArrayList<>();

        for (Resource resource : ResourceManager.getResources()){
            if (!NeverendingKnights.resourceAssigner.assignedResources.contains(resource) && !Gatherer.allDumpedResources.contains(resource) && isResourceSafe(resource)){
//                if (getNearestRealEnemy().getDistance(r) > getDistance(r)) {
                res.add(resource);
//                }
            }
        }

        res.sort((r1, r2) -> Float.compare(r1.getDistance(r), r2.getDistance(r)));

        if (res.size() > 3){
            if (res.get(1).getDistance(r.getCenterX(), r.getCenterY()) < 1000) threeClosest.add(res.get(1));
            if (res.get(2).getDistance(r.getCenterX(), r.getCenterY()) < 1000) threeClosest.add(res.get(2));
            if (res.get(3).getDistance(r.getCenterX(), r.getCenterY()) < 1000) threeClosest.add(res.get(3));
        }

        return threeClosest;
    }

    public ArrayList<Node> findBestChainOfNodes(){
        ArrayList<Node> nearNodes = new ArrayList<>();
        ArrayList<Node> listOfNodes = new ArrayList<>();

        for (Node n : NodeManager.getNodes()) {
            if ((n.getX() < 0 && getPlayer().isLeftPlayer()) || (n.getX() > 0 && getPlayer().isRightPlayer())){
                nearNodes.add(n);
            }
        }

        nearNodes.sort((n1, n2) -> Float.compare(n1.getDistance(getHomeBase()), n2.getDistance(getHomeBase())));
        nearNodes.sort((n1,n2) -> Float.compare(n1.getDistance(nearNodes.getFirst()), n2.getDistance(nearNodes.getFirst())));

        if (!nearNodes.isEmpty() && nearNodes.size() > 1) {
            listOfNodes.add(nearNodes.getFirst());
            listOfNodes.add(nearNodes.get(1));
            nearNodes.removeFirst();
        }

        if (!nearNodes.isEmpty()) {
            for (int i = 0; i < nearNodes.size(); i++) {
                if (nearNodes.size() > 2) {
//                    nearNodes.sort((n1, n2) -> Float.compare(n1.getDistance(nearNodes.getFirst()), n2.getDistance(nearNodes.getFirst())));
                    nearNodes.sort((n1, n2) -> Float.compare(determineNodeScore(n1), determineNodeScore(n2)));
                    listOfNodes.add(nearNodes.get(1));
                    nearNodes.removeFirst();
                    i--;
                }
            }
        }

        if (!listOfNodes.isEmpty()){
            return listOfNodes;
        }

        return null;
    }

    public float determineNodeScore(Node n){ // No idea if it is better
        float minerBufferScore = getDistance(n) * 1.75f;
        float homeBaseScore = getHomeBase().getDistance(n) * 0.75f;

        float mapBackEdgeX = Math.abs((float) Game.getMapLeftEdge());
        float nodeX = Math.abs(n.getCenterX());
        float mapXEdgeScore = (mapBackEdgeX - nodeX);

        float clusterScore = 0;
        for (Node node : NodeManager.getNodes()){
            if (node != n) {
                clusterScore -= n.getDistance(node);
            }
        }
        clusterScore = clusterScore / (NodeManager.getNodes().size() * 2);

        return minerBufferScore + homeBaseScore + mapXEdgeScore + clusterScore;
    }

    public ArrayList<Node> updateNodeChain(ArrayList<Node> nodeChain){
        ArrayList<Node> newNodeChain = new ArrayList<>();

        if (nodeChain != null) {
            for (Node n : nodeChain) {
                if (n.isAlive() && n.isInBounds()){ // && isNodeSafe(n) // && getDistance(n) < getNearestEnemyThreat().getDistance(n)
                    newNodeChain.add(n);
                }
            }
            newNodeChain.sort((n1, n2) -> Float.compare(determineNodeScore(n1), determineNodeScore(n2)));
        }

        return newNodeChain;
    }

    //**********************************************************************************
    // MISC ****************************************************************************
    //**********************************************************************************

    public int getAverageEnemyMaxSpeed()
    {
        ArrayList<Unit> enemies = getEnemies();
        float totalSpeed = 0;
        float numEnemies = enemies.size();

        for(Unit e : enemies) {
            if(e instanceof BaseShip) continue;
            float curSpeed = e.getMaxSpeed() / Values.SPEED;
            totalSpeed += curSpeed;
        }

        return (int) (totalSpeed / numEnemies);
    }

    public Unit getLowestSafeEnemyWorker(int range) {
        ArrayList<Unit> e = new ArrayList<>();

        for (Unit u : getEnemiesInRadiusWithComponent(range,Collector.class)){
            if (u.getDistance(getEnemyBase()) > 500 && u.getDistance(getNearestEnemyThreat()) > 500) e.add(u);
        }

        for (Unit u : getEnemiesInRadiusWithComponent(range,Drillbeam.class)){
            if (u.getDistance(getEnemyBase()) > 500 && u.getDistance(getNearestEnemyThreat()) > 500) e.add(u);
        }

//        e.sort((e1, e2) -> Float.compare(e1.getDistance(this), e2.getDistance(this)));
        e.sort((e1, e2) -> Float.compare(e1.getPercentEffectiveHealth(), e2.getPercentEffectiveHealth()));

        if (!e.isEmpty()) {
            return e.getFirst();
        }

        else return null;
    }


    public Unit getLowestAttackingEnemy(int range) {
        ArrayList<Unit> e = new ArrayList<>();

        for (Unit u : getEnemiesInRadiusWithComponent(range,Missile.class)){
            if (u.getDistance(getEnemyBase()) > 500) e.add(u);
        }
        for (Unit u : getEnemiesInRadiusWithComponent(range,Laser.class)){
            if (u.getDistance(getEnemyBase()) > 500) e.add(u);
        }
        for (Unit u : getEnemiesInRadiusWithComponent(range,Autocannon.class)){
            if (u.getDistance(getEnemyBase()) > 500) e.add(u);
        }

        e.sort(Comparator.comparingDouble(Unit::getPercentEffectiveHealth));

        if (!e.isEmpty()) {
            return e.getFirst();
        }

        else return null;
    }

    public boolean hasAntiMissiles() {
        int antiMissiles = 0;
        for (Unit l : getEnemies()) {
            if (l.hasComponent(AntiMissileSystem.class)) {
                antiMissiles++;
            }
        }

        if (antiMissiles >= 3) return true;
        return false;
    }

    public boolean hasManyMissiles(){
        int totalMissiles = 0;

        for (Unit l: getEnemies()){
            if (l.hasComponent(Missile.class) || l.hasComponent(HeavyMissile.class)) {
                totalMissiles++;
            }
        }

        if (totalMissiles > 5) return true;
        return false;
    }

    public boolean hasManyRushUnits(){
        int totalRush = 0;

        for (Unit l : getEnemies()){
            if (l.getFrame() == Frame.LIGHT && !l.hasComponent(Collector.class) && !l.hasComponent(Drillbeam.class)){
                totalRush++;
            }
        }

        if (totalRush >= 5){
            return true;
        }
        return false;
    }

    public ArrayList<Unit> getRealEnemiesInRadius(int r) {
        ArrayList<Unit> enemies = getEnemiesInRadius(r);
        ArrayList<Unit> realEnemies = new ArrayList<>();

        for (Unit u : enemies){
            if (!u.hasWeapon(Collector.class) && !u.hasWeapon(Drillbeam.class)) {
                realEnemies.add(u);
            }
        }

        return realEnemies;
    }
    public ArrayList<Unit> getSafeEnemiesInRadius(int r) {
        ArrayList<Unit> enemies = getEnemiesInRadius(r);
        ArrayList<Unit> safeEnemies = new ArrayList<>();

        for (Unit u : enemies){
            if (u.hasWeapon(Collector.class) || u.hasWeapon(Drillbeam.class)) {
                safeEnemies.add(u);
            }
        }

        return safeEnemies;
    }

    public Unit getNearestEnemyThreat() {
        // found this method that lowkey reduces lag by a ton
        // basically sets the distance to the highest possible value, and then decreasing it by the lower ones every time
        // that way we don't need to sort every frame which is pretty laggy
        Unit nearest = null;
        float bestDist = Float.MAX_VALUE;
        for (Unit u : getEnemies()) {
            float d = getDistance(u);
            if (d < bestDist) {
                bestDist = d;
                nearest = u;
            }
        }
        return nearest;
    }


    public Unit getNearestEnemyWorker() {
        Unit nearest = null;
        float bestDist = Float.MAX_VALUE;
        for (Unit u : enemyWorkers) {
            float d = getDistance(u);
            if (d < bestDist) {
                bestDist = d;
                nearest = u;
            }
        }
        return nearest;
    }

    public Unit getNearestSafeEnemyWorker() {
        Unit nearest = null;
        float bestDist = Float.MAX_VALUE;
        for (Unit u : enemyWorkers) {
            if (u.getDistance(u.getHomeBase()) < 1000) continue;
            float d = getDistance(u);
            if (d < bestDist) {
                bestDist = d;
                nearest = u;
            }
        }
        return nearest;
    }

    public Unit getNearestAllyAttacker(){
        ArrayList<Unit> allies = new ArrayList<>();

        for (Unit u : getAllies()) {
            if (!u.hasComponent(Collector.class) && !u.hasComponent(Drillbeam.class)
                && !(u instanceof Pest) && !(u instanceof Destroyer) && !u.hasWeapon(RepairBeam.class)){
                allies.add(u);
            }
        }

        allies.sort((a1, a2) -> Float.compare(getDistance(a1), getDistance(a2)));

        if (!allies.isEmpty()) return allies.getFirst();
        return null;
    }

    public boolean suicideCheck(float radius, Unit type)
    {
        if(type instanceof Pest && getRealEnemiesInRadius((int) radius).size() >= 4 & getSafeEnemiesInRadius((int) radius).size() <= 4)
        {
            return true;
        }
        if(type instanceof Creak && getRealEnemiesInRadius((int) radius).size() >= 5 && getAlliesInRadius((int) radius).size() <= 2)
        {
            return true;
        }

        return false;
    }

    public Unit getClosestEnemyRushUnit(){
        Unit nearest = null;
        float bestDist = Float.MAX_VALUE;
        for (Unit u : enemyRush) {
            if ((getPlayer().isLeftPlayer() && u.getX() > -500) || (getPlayer().isRightPlayer() && u.getX() < 500)) continue;
            float d = getDistance(u);
            if (d < bestDist) {
                bestDist = d;
                nearest = u;
            }
        }
        return nearest;
    }

    public boolean creakActive(boolean on)
    {
        if(on)
        {
            return true;
        }
        else {
            return false;
        }
    }

    public Unit getNearestRaider(){
        return null;
    }

    public Unit getMostDangerousRaider(){
        ArrayList<Unit> e = enemyRush;

        float rushDist = Float.MAX_VALUE;
        Unit dangerousRaider = null;

        for (Unit u : myWorkers){
            if (enemyRush.contains(u.getNearestEnemy()) && u.getDistance(u.getNearestEnemy()) < rushDist){
                rushDist = u.getDistance(getNearestEnemy());
                dangerousRaider = u.getNearestEnemy();
            }
        }

        return dangerousRaider;
    }

    public Unit getFarthestWorker(){
        ArrayList<Unit> workers = myWorkers;

        workers.sort((a1, a2) -> Float.compare(a1.getDistance(getEnemyBase()), a2.getDistance(getEnemyBase())));

        if (workers.isEmpty()) return null;

        return workers.getFirst();
    }

    protected float getAverageUnitMaxSpeed() {
        float totalSpeedToBeAveraged = 0;
        ArrayList<Unit> allies = getAllies();
        for (int i = 0; i<allies.size(); i++) {
            if (!(allies.get(i).hasComponent(Collector.class) && !(allies.get(i).hasComponent(Drillbeam.class)))) {
                totalSpeedToBeAveraged += allies.get(i).getMaxSpeed();
            }
            else {
                allies.remove(i);
                i--;
            }
        }
        return totalSpeedToBeAveraged / allies.size();
    }

    protected boolean opponentHasLongRangeWeapons() {
        ArrayList<Unit> enemies = getEnemies();
        float totalDistanceToBeAveraged = 0;
        for (int i = 0; i<enemies.size(); i++) {
            if (!(enemies.get(i).hasComponent(Collector.class) && !(enemies.get(i).hasComponent(Drillbeam.class)))) {
                totalDistanceToBeAveraged += enemies.get(i).getMaxRange();
            }
            else {
                enemies.remove(i);
                i--;
            }
        }
        float averageEnemyDistance = totalDistanceToBeAveraged / enemies.size();

        ArrayList<Unit> allies = getAllies();
        float totalDistanceToBeAveragedAllies = 0;
        for (int i = 0; i<allies.size(); i++) {
            if (!(allies.get(i).hasComponent(Collector.class) && !(allies.get(i).hasComponent(Drillbeam.class)))) {
                totalDistanceToBeAveragedAllies += allies.get(i).getMaxRange();
            }
        }
        float averageEnemyDistanceAllies = totalDistanceToBeAveragedAllies / allies.size();

        return averageEnemyDistance > averageEnemyDistanceAllies;
    }

    public NeverendingKnights getPlayer() {
        return (NeverendingKnights) super.getPlayer();
    }

}