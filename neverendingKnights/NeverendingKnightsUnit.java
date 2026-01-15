package teams.student.neverendingKnights;

import components.weapon.Weapon;
import components.weapon.economy.Collector;
import components.weapon.economy.Drillbeam;
import components.weapon.energy.HeavyLaser;
import components.weapon.energy.Laser;
import components.weapon.explosive.ExplosiveWeapon;
import components.weapon.explosive.HeavyMissile;
import components.weapon.explosive.Missile;
import components.weapon.kinetic.Autocannon;
import components.weapon.kinetic.HeavyAutocannon;
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
import teams.student.neverendingKnights.units.Pest;
import teams.student.neverendingKnights.units.Tank;
import teams.student.neverendingKnights.units.resource.Gatherer;

import java.util.ArrayList;
import java.util.Comparator;

public abstract class NeverendingKnightsUnit extends Unit
{
    // Side note: Always add the shortest range weapon first

    // Calculations
    public static ArrayList<Unit> enemyThreats = new ArrayList<>();
    public static ArrayList<Unit> enemyWorkers = new ArrayList<>();
    public static ArrayList<Unit> enemyMissiles = new ArrayList<>();
    public static ArrayList<Unit> myAttackers = new ArrayList<>();
    public static ArrayList<Unit> myWorkers = new ArrayList<>();
    public static ArrayList<Unit> myWorkersInDanger = new ArrayList<>();

    public static ArrayList<Unit> tanks = new ArrayList<>();

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

    public static int relativeEnemyThreatStrength = 0;
    public static int relativeAttackerStrength = 0;

    public static float furthestTankX = 0;

    public static float mainPushAvgDist = 0;

    protected Unit currentTarget;


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
            if (mainPushState.equals("Rally") || mainPushState.equals("Retreat") || mainPushState.equals("Heavy Retreat")){
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
                }
            }
        }

        if (attackState.equals("Kill")){ // Best enemy to attack in lane?
            if (getBiggestThreatInRadius(getMaxRange() * 2) != null) {
                mainPushRallyX = getBiggestThreatInRadius(getMaxRange() * 2).getCenterX();
                mainPushRallyY = getBiggestThreatInRadius(getMaxRange() * 2).getCenterY();
            }
            else{
                mainPushRallyX = nearestEnemyThreat.getCenterX();
                mainPushRallyY = nearestEnemyThreat.getCenterY();
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
//        currentTarget = nearestEnemyThreat;
        currentTarget = getBiggestThreatInRadius(getMaxRange());

        attack(getWeaponOne());
        attack(getWeaponTwo());
        movement();
    }

    public void attack(Weapon w) {
        if(currentTarget != null && w != null){
            w.use(currentTarget);
        }
    }

    public void movement() {
        if (getDistance(getNearestAlly()) > 50) {
            if (!tanks.isEmpty()) {
                if (((getPlayer().isLeftPlayer() && getX() < furthestTankX) || (getPlayer().isRightPlayer() && getX() > furthestTankX)) && !myMainPushStrength.equals("Very High") && !(this instanceof Tank)) {
                    if (currentTarget != null) {
                        if (getDistance(currentTarget) < getWeaponOne().getMaxRange()) {
                            turnTo(currentTarget);
                            turnAround();
                            move();
                        } else {
                            moveTo(mainPushRallyX, mainPushRallyY);
                        }
                    } else {
                        moveTo(mainPushRallyX, mainPushRallyY);
                    }
                } else {
                    if (this instanceof Tank || (myMainPushStrength.equals("Very High") && getDistance(getEnemyBase()) < 1000)){
                        if (currentTarget != null) {
                            if (getDistance(currentTarget) < getWeaponOne().getMaxRange()) {
                                turnTo(currentTarget);
                                turnAround();
                                move();
                            } else {
                                moveTo(mainPushRallyX, mainPushRallyY);
                            }
                        } else {
                            moveTo(mainPushRallyX, mainPushRallyY);
                        }
                    }
                    else {
                        turnTo(getNearestAlly(Tank.class));
                        move();
                    }
                }
            }
            else{
                if (currentTarget != null) {
                    if (getDistance(currentTarget) < getWeaponOne().getMaxRange()) {
                        turnTo(currentTarget);
                        turnAround();
                        move();
                    } else {
                        moveTo(mainPushRallyX, mainPushRallyY);
                    }
                } else {
                    moveTo(mainPushRallyX, mainPushRallyY);
                }
            }
        }
        else{
            turnTo(getNearestAlly());
            turnAround();
            move();
        }
    }

    public void calculateFurthestTankX(){
        tanks.clear();

        for (Unit u : myAttackers){
            if (u instanceof Tank) tanks.add(u);
        }

        tanks.sort((w1, w2) -> Float.compare(w1.getDistance(getEnemyBase()), w2.getDistance(getEnemyBase())));

        if (!tanks.isEmpty()) furthestTankX = tanks.getFirst().getCenterX();
    }

    // Calculations
    public void calculateEnemyAttackType(){
        enemyThreats.clear();

        for (Unit u : getEnemies()){
            if (!u.hasComponent(Drillbeam.class) && !u.hasComponent(Collector.class)){
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

            if (attackState.equals("Kill")){
                if (!getEnemyBase().getAlliesInRadius(1000).isEmpty()) nearestEnemyThreat = getEnemyBase().getNearestAlly();
                else nearestEnemyThreat = getEnemyBase();
            }

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
//        }
//        else{
//            if (nearestEnemyThreatNumDist > 1500) {
//                nearestEnemyThreatDist = "Small Close";
//            } else if (nearestEnemyThreatNumDist > 750) {
//                nearestEnemyThreatDist = "Small Very Close";
//            } else {
//                nearestEnemyThreatDist = "Small Extremely Close";
//            }
//        }

    }

    public void calculateNearestEnemyWorker(){
        nearestEnemyWorker = getNearestEnemyWorker();
    }

    public void calculateMyAttackers(){
        myAttackers.clear();
        for (Unit u : getAlliesExcludeBaseShip()){
            if (!u.hasComponent(Drillbeam.class) && !u.hasComponent(Collector.class) && !(u instanceof Pest)){
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
        if (relativeAttackerStrength > relativeEnemyThreatStrength * 1.5){
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
//        if (myAttackers.size() > enemyThreats.size() * 1.5){
//            myMainPushStrength = "Very High";
//        }
//        else if (myAttackers.size() > enemyThreats.size() * 1.15f){
//            myMainPushStrength = "High";
//        }
//        else if (myAttackers.size() > enemyThreats.size()){
//            myMainPushStrength = "Moderately High";
//        }
//        else if (myAttackers.size() * 2 < enemyThreats.size()){
//            myMainPushStrength = "Very Low";
//        }
//        else if (myAttackers.size() * 1.5f < enemyThreats.size()){
//            myMainPushStrength = "Low";
//        }
//        else if (myAttackers.size() < enemyThreats.size()){
//            myMainPushStrength = "Moderately Low";
//        }
//        else {
//            myMainPushStrength = "Moderate";
//        }
    }

    public void calculateMainPushDensity(){
        if (myAttackers.contains(this)){
            int alliesInRadius = getAlliesInRadius(1250).size(); // can make excluding pests, etc
            if (alliesInRadius == myAttackers.size()){
                myMainPushDensity = "Perfect";
            }
            else if (alliesInRadius < myAttackers.size() * 0.2f){
                myMainPushDensity = "Terrible";
            }
            else if (alliesInRadius < myAttackers.size() * 0.35f){
                myMainPushDensity = "Bad";
            }
            else if (alliesInRadius < myAttackers.size() * 0.6f){
                myMainPushDensity = "Moderate";
            }
            else if (alliesInRadius < myAttackers.size() * 0.85f){
                myMainPushDensity = "Decent";
            }


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
            case "Very Far":
                teamAlert = "Very Low";
                break;
            case "Far":
                teamAlert = "Low";
                break;
            case "Moderate":
                teamAlert = "Moderate";
                break;
            case "Close":
                if (getHomeBase().getEnemiesInRadius(400).size() > 10) {
                    teamAlert = "High";
                }
                else {
                    teamAlert = "Moderate";
                }
                break;
            case "Very Close":
                if (getHomeBase().getEnemiesInRadius(400).size() > 7) {
                    teamAlert = "Very High";
                }
                else {
                    teamAlert = "Moderate";
                }
                break;
            case "Extremely Close":
                if (getHomeBase().getEnemiesInRadius(400).size() > 4) {
                    teamAlert = "Extreme";
                }
                else {
                    teamAlert = "Moderate";
                }
                break;

            default:
                teamAlert = "Undecided";
                break;
        }
//        switch (nearestEnemyThreatDist) {
//            case "Very Far" -> teamAlert = "Very Low";
//            case "Far" -> teamAlert = "Low";
//            case "Moderate" -> teamAlert = "Moderate";
//            case "Close" -> teamAlert = "High";
//            case "Very Close" -> teamAlert = "Very High";
//            case "Extremely Close" -> teamAlert = "Extreme";
//            case "Small Close" -> teamAlert = "Small High";
//            case "Small Very Close" -> teamAlert = "Small Very High";
//            case "Small Extremely Close" -> teamAlert = "Small Extreme";
//
//
//            default -> teamAlert = "Undecided";
//        }
    }

    public void setMainPushState(){
        switch (myMainPushDensity) {
            case "Perfect" -> mainPushState = "Perfect";
            case "Decent" -> mainPushState = "Decent";
            case "Moderate" -> mainPushState = "Rally";
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
            attackState = "Growth";
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.white);
        g.drawLine(getCenterX(), getCenterY(), mainPushRallyX, mainPushRallyY);
        g.setColor(Color.green);
        if (currentTarget != null) g.drawLine(getCenterX(), getCenterY(), currentTarget.getCenterX(), currentTarget.getCenterY());
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
                    int totalPoints = 10000;
                    totalPoints -= healthScore(enemy);
                    totalPoints -= damageScore(enemy);
                    totalPoints -= speedScore(enemy);
                    totalPoints -= typeShipScore(enemy);
                    totalPoints -= statusAndRangeScore(enemy);
                    totalPoints -= distanceScore(enemy);
                    if (bestEnemy == null) {
                        bestEnemyScore = totalPoints;
                        bestEnemy = enemy;
                    } else {
                        if (bestEnemyScore > totalPoints) {
                            bestEnemyScore = totalPoints;
                            bestEnemy = enemy;
                        }
                    }
            }
        }
        if (bestEnemy != null && (bestEnemy.hasComponent(Collector.class) || bestEnemy.hasComponent(Drillbeam.class))) return null; // Can change to only gatherers if need be
        return bestEnemy;
        //health
        //weapon/damage
        //speed
        //type
        //range

        //if they have a status, kill first
    }

    protected int distanceScore(Unit u) {
        if (getDistance(u) < getMaxRange()/3) {
            return 6000;
        }
        else if (getDistance(u) < getMaxRange()*2/3) {
            return 4000;
        }
        else if (getDistance(u) < getMaxRange()) {
            return 2500;
        }
        else {
            return 1000;
        }
    }

    protected int statusAndRangeScore(Unit u) {
        if (u.hasComponent(CommandRelay.class)) {
            return 4000;
        }
        else if (u.hasComponent(Pullbeam.class)) {
            return 150;
        }
        else if (u.hasComponent(SpeedBoost.class)) {
            return 150;
        }
        else if (u.hasComponent(ElectromagneticPulse.class)) {
            return 450;
        }
        else if (u.hasComponent(GravitationalRift.class)) {
            return 500;
        }
        else if (u.hasComponent(HeavyRepairBeam.class)) {
            return 550;
        }
        if (u.hasComponent(Collector.class)) {
            return -10000;
        }
        else if (u.getWeaponOne() instanceof RepairBeam && u.getWeaponTwo() instanceof RepairBeam) {
            return 2300;
        }
        else if (u.getWeaponOne() instanceof RepairBeam || u.getWeaponTwo() instanceof RepairBeam) {
            return 1000;
        }
        return 0;
    }

    protected int typeShipScore(Unit u) {
        if (u.getModel().equals(Model.STRIKER)) {
            return 2500;
        }
        else if (u.getModel().equals(Model.ARTILLERY)) {
            return 1900;
        }
        else if (u.getModel().equals(Model.BASTION)) {
            return 1700;
        }
        else if (u.getModel().equals(Model.DESTROYER)) {
            return 1500;
        }
        else if (u.getModel().equals(Model.TRANSPORT)) {
            return 1100;
        }
        return 1000;
    }

    protected int speedScore(Unit u) {
        if (u.getFrame().getMass()  <= 12) {
            return 3000;
        }
        else if (u.getFrame().getMass() <= 25) {
            return 2700;
        }
        else if (u.getFrame().getMass() <= 35) {
            return 2200;
        }
        else if (u.getFrame().getMass() <= 45) {
            return 1900;
        }
        return 1500;
    }

    protected int damageScore(Unit u) {
        if (u.getWeaponOne() instanceof HeavyMissile || u.getWeaponTwo() instanceof HeavyMissile ) {
            return 2000;
        }
        else if (u.getWeaponOne() instanceof Missile && u.getWeaponTwo() instanceof Missile) {
            return 1900;
        }
        else if (u.getWeaponOne() instanceof HeavyLaser || u.getWeaponTwo() instanceof HeavyLaser) {
            return 1800;
        }
        else if (u.getWeaponOne() instanceof Laser && u.getWeaponTwo() instanceof Laser) {
            return 1700;
        }
        else if (u.getWeaponOne() instanceof Missile || u.getWeaponTwo() instanceof Missile) {
            return 1500;
        }
        else if (u.getWeaponOne() instanceof Laser || u.getWeaponTwo() instanceof Laser) {
            return 1000;
        }
        else if (u.getWeaponOne() instanceof HeavyAutocannon || u.getWeaponTwo() instanceof HeavyAutocannon) {
            return 800;
        }
        else if (u.getWeaponOne() instanceof Autocannon && u.getWeaponTwo() instanceof Autocannon) {
            return 250;
        }
        return 5;
    }

    protected int healthScore(Unit u) {
        //gets a score to deduct based on health of the enemy
        if (u.getPercentEffectiveHealth() < 0.25) {
            return 2500;
        }
        else if (u.getPercentEffectiveHealth() < 0.5) {
            return 1500;
        }
        else if (u.getPercentEffectiveHealth() < 0.7) {
            return 1000;
        }
        return 200;
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
                    nearNodes.sort((n1, n2) -> Float.compare(n1.getDistance(nearNodes.getFirst()), n2.getDistance(nearNodes.getFirst())));
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

    public ArrayList<Node> updateNodeChain(ArrayList<Node> nodeChain){
        ArrayList<Node> newNodeChain = new ArrayList<>();

        if (nodeChain != null) {
            for (Node n : nodeChain) {
                if (n.isAlive() && n.isInBounds() && isNodeSafe(n)){ // && getDistance(n) < getNearestEnemyThreat().getDistance(n)
                    newNodeChain.add(n);
                }
            }
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
            if (u.getDistance(getEnemyBase()) > 500) e.add(u);
        }

        for (Unit u : getEnemiesInRadiusWithComponent(range,Drillbeam.class)){
            if (u.getDistance(getEnemyBase()) > 500) e.add(u);
        }

        e.sort(Comparator.comparingDouble(Unit::getPercentEffectiveHealth));

        if (!e.isEmpty()) {
            return e.getFirst();
        }

        else return null;
    }

    public boolean hasAntiMissiles() {
        for (Unit l : getEnemies()) {
            if (l.hasComponent(AntiMissileSystem.class)) {
                return true;
            }
        }
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

    public Unit getNearestEnemyThreat() {
        // found this method that lowkey reduces lag by a ton
        // basically sets the distance to the highest possible value, and then decreasing it by the lower ones every time
        // that way we don't need to sort every frame which is pretty laggy
        Unit nearest = null;
        float bestDist = Float.MAX_VALUE;
        for (Unit u : getEnemies()) {
            if (u.hasComponent(Collector.class) || u.hasComponent(Drillbeam.class)) continue;
            float d = getDistance(u);
            if (d < bestDist) {
                bestDist = d;
                nearest = u;
            }
        }
        return nearest;
    }


    public Unit getNearestEnemyWorker() {
        ArrayList<Unit> enemies = new ArrayList<>(getEnemies());
        enemies.sort((e1, e2) -> Float.compare(e1.getDistance(getHomeBase()), e2.getDistance(getHomeBase())));

        for (Unit u : enemies) {
            if (u.hasComponent(Collector.class) || u.hasComponent(Drillbeam.class)){
                return u;
            }
        }

        return null;

    }

    public Unit getNearestAllyAttacker(){
        ArrayList<Unit> allies = new ArrayList<>(getAllies());
        allies.sort((a1, a2) -> Float.compare(a1.getDistance(this), a2.getDistance(this)));

        for (Unit u : allies) {
            if (!u.hasComponent(Collector.class) && !u.hasComponent(Drillbeam.class)){
                return u;
            }
        }

        return null;
    }

    public NeverendingKnights getPlayer() {
        return (NeverendingKnights) super.getPlayer();
    }

}
