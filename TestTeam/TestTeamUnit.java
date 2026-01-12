package teams.student.TestTeam;

import components.weapon.Weapon;
import components.weapon.economy.Collector;
import components.weapon.economy.Drillbeam;
import components.weapon.explosive.ExplosiveWeapon;
import components.weapon.explosive.HeavyMissile;
import components.weapon.explosive.Missile;
import components.weapon.utility.*;
import engine.Values;
import engine.states.Game;
import objects.entity.node.Node;
import objects.entity.node.NodeManager;
import objects.entity.unit.BaseShip;
import objects.entity.unit.Frame;
import objects.entity.unit.Unit;
import objects.resource.Resource;
import objects.resource.ResourceManager;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import teams.student.TestTeam.units.Pest;
import teams.student.TestTeam.units.resource.Gatherer;
import teams.student.TestTeam.units.resource.MinerBuffer;
import teams.student.TestTeam.units.resource.ResourceGrabber;

import java.util.ArrayList;
import java.util.Comparator;

public abstract class TestTeamUnit extends Unit
{
    // Side note: Always add the shortest range weapon first

    // Calculations
    public static ArrayList<Unit> enemyThreats = new ArrayList<>();
    public static ArrayList<Unit> enemyWorkers = new ArrayList<>();
    public static ArrayList<Unit> enemyMissiles = new ArrayList<>();
    public static ArrayList<Unit> myAttackers = new ArrayList<>();
    public static ArrayList<Unit> myWorkers = new ArrayList<>();
    public static ArrayList<Unit> myWorkersInDanger = new ArrayList<>();

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

    public static String teamStrategy = "";
    public static String teamAlert = "";
    public static String mainPushState = "";
    public static String attackState = "";

    public static float mainPushRallyX = 0;
    public static float mainPushRallyY = 0;
    public static float pestRallyX = 0;
    public static float pestRallyY = 0;

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
    }

    public void setState(){
        setTeamStrategy();
        setTeamAlert();
        setMainPushState();
        setAttackState();
    }

    public void setRallyPoint(){
        if (attackState.equals("Growth")){
            if (getPlayer().isLeftPlayer()) mainPushRallyX = getHomeBase().getCenterX() + 100;
            else if (getPlayer().isRightPlayer()) mainPushRallyX = getHomeBase().getCenterX() - 100;
            mainPushRallyY = getHomeBase().getCenterY();
        }

        if (attackState.equals("Defense")){
            mainPushRallyX = getHomeBase().getNearestEnemy().getCenterX();
            mainPushRallyY = getHomeBase().getNearestEnemy().getCenterY();
        }

        if (attackState.equals("Defend Workers")){
            mainPushRallyX = nearestEnemyThreat.getCenterX();
            mainPushRallyY = nearestEnemyThreat.getCenterY();
        }

        if (attackState.equals("Attack")){
            if (mainPushState.equals("Rally") || mainPushState.equals("Retreat") || mainPushState.equals("Heavy Retreat")){
                if (getPlayer().isLeftPlayer()) mainPushRallyX = getHomeBase().getCenterX() - 1500;
                else if (getPlayer().isRightPlayer()) mainPushRallyX = getHomeBase().getCenterX() - 1500;
                mainPushRallyY = getHomeBase().getCenterY();
            }
            else {
                mainPushRallyX = nearestEnemyThreat.getCenterX();
                mainPushRallyY = nearestEnemyThreat.getCenterY();
            }
        }

        if (attackState.equals("Kill")){ // Best enemy to attack in lane?
            mainPushRallyX = nearestEnemyThreat.getCenterX();
            mainPushRallyY = nearestEnemyThreat.getCenterY();
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
        currentTarget = nearestEnemyThreat;

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
        if (currentTarget != null) {
            if (getDistance(currentTarget) < getWeaponOne().getMaxRange()) {
                turnTo(currentTarget);
                turnAround();
                move();
            }
            else {
                moveTo(mainPushRallyX, mainPushRallyY);
            }
        }
        else{
            moveTo(mainPushRallyX, mainPushRallyY);
        }
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
        else if (((float) assaultEnemyThreats / enemyThreats.size()) > 0.5f){
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
            if (attackState.equals("Kill")){
                if (!getEnemyBase().getAlliesInRadius(1000).isEmpty()) nearestEnemyThreat = getEnemyBase().getNearestAlly();
                else nearestEnemyThreat = getEnemyBase();
            }
            nearestEnemyThreat = enemyThreats.getFirst();
            nearestEnemyThreatNumDist = nearestEnemyThreat.getDistance(getHomeBase());
        }


        if (nearestEnemyThreatNumDist > 10000){
            nearestEnemyThreatDist = "Very Far";
        }
        else if (nearestEnemyThreatNumDist > 7000){
            nearestEnemyThreatDist = "Far";
        }
        else if (nearestEnemyThreatNumDist > 4500){
            nearestEnemyThreatDist = "Moderate";
        }
        else if (nearestEnemyThreatNumDist > 1500){
            nearestEnemyThreatDist = "Close";
        }
        else if (nearestEnemyThreatNumDist > 750){
            nearestEnemyThreatDist = "Very Close";
        }
        else{
            nearestEnemyThreatDist = "Extremely Close";
        }
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

    public void calculateMyWorkers(){
        myWorkers.clear();
        for (Unit u : getAlliesExcludeBaseShip()){
            if (u.hasComponent(Drillbeam.class) || u.hasComponent(Collector.class)){
                myWorkers.add(u);
            }
        }
    }

    public void calculateMainPushStrength(){
        if (myAttackers.size() > enemyThreats.size() * 2){
            myMainPushStrength = "Very High";
        }
        else if (myAttackers.size() > enemyThreats.size() * 1.5f){
            myMainPushStrength = "High";
        }
        else if (myAttackers.size() > enemyThreats.size()){
            myMainPushStrength = "Moderately High";
        }
        else if (myAttackers.size() < enemyThreats.size()){
            myMainPushStrength = "Moderately Low";
        }
        else if (myAttackers.size() * 1.5f < enemyThreats.size()){
            myMainPushStrength = "Low";
        }
        else if (myAttackers.size() * 2 < enemyThreats.size()){
            myMainPushStrength = "Very Low";
        }
        else {
            myMainPushStrength = "Moderate";
        }
    }

    public void calculateMainPushDensity(){
        if (myAttackers.contains(this)){
            int alliesInRadius = getAlliesInRadius(2500).size(); // can make excluding pests, etc
            if (alliesInRadius == myAttackers.size()){
                myMainPushDensity = "Perfect";
            }
            else if (alliesInRadius < myAttackers.size() * 0.85f){
                myMainPushDensity = "Decent";
            }
            else if (alliesInRadius < myAttackers.size() * 0.6f){
                myMainPushDensity = "Moderate";
            }
            else if (alliesInRadius < myAttackers.size() * 0.35f){
                myMainPushDensity = "Bad";
            }
            else if (alliesInRadius < myAttackers.size() * 0.2f){
                myMainPushDensity = "Terrible";
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
            case "Very Far" -> teamAlert = "Very Low";
            case "Far" -> teamAlert = "Low";
            case "Moderate" -> teamAlert = "Moderate";
            case "Close" -> teamAlert = "High";
            case "Very Close" -> teamAlert = "Very High";
            case "Extremely Close" -> teamAlert = "Extreme";

            default -> teamAlert = "Undecided";
        }
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
                if (myMainPushStrength.equals("Very Low") || myMainPushStrength.equals("Low") || myMainPushStrength.equals("Moderately Low") || myMainPushStrength.equals("Moderate") || myMainPushStrength.equals("Moderately High")) {
                    attackState = "Defense";
                }
                else if (myMainPushStrength.equals("High")){
                    attackState = "Attack";
                }
                else if (myMainPushStrength.equals("Very High")){
                    attackState = "Kill";
                }
                else{
                    attackState = "Defense";
                }
            }
        }
        if (teamAlert.equals("Low") || teamAlert.equals("Moderate")){
            if (myMainPushStrength.equals("Very High")){
                attackState = "Attack";
            }
            else{
                attackState = "Growth";
            }
        }
        if (teamAlert.equals("Very Low")){
            attackState = "Growth";
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.white);
        g.drawLine(getCenterX(), getCenterY(), mainPushRallyX, mainPushRallyY);
    }

    //**********************************************************************************
    // ENEMY TARGETING *****************************************************************
    //**********************************************************************************


    //**********************************************************************************
    // RESOURCES ***********************************************************************
    //**********************************************************************************

    public ArrayList<Resource> getResourcesInRadius(float rad, TestTeamUnit u){
        ArrayList<Resource> res = new ArrayList<>();
        for (Resource r : ResourceManager.getResources()){
            if (r.getDistance(u) < rad){
                res.add(r);
            }
        }

        return res;
    }

    public ArrayList<Resource> getClosestResourcesToResource(Resource r){
        ArrayList<Resource> threeClosest = new ArrayList<>();
        ArrayList<Resource> res = new ArrayList<>();

        for (Resource resource : ResourceManager.getResources()){
            if (!TestTeam.resourceAssigner.assignedResources.contains(resource) && !Gatherer.allDumpedResources.contains(resource)){
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
                if (n.isAlive() && n.isInBounds()){ // && getDistance(n) < getNearestEnemyThreat().getDistance(n)
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

    public TestTeam getPlayer() {
        return (TestTeam) super.getPlayer();
    }

}
