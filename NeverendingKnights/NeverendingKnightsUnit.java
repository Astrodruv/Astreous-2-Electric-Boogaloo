package teams.student.NeverendingKnights;

import components.weapon.Weapon;
import components.weapon.economy.Collector;
import components.weapon.economy.Drillbeam;
import components.weapon.energy.HeavyLaser;
import components.weapon.energy.Laser;
import components.weapon.explosive.HeavyMissile;
import components.weapon.explosive.Missile;
import components.weapon.kinetic.Autocannon;
import components.weapon.kinetic.HeavyAutocannon;
import components.weapon.utility.*;
import engine.Values;
import objects.entity.node.Node;
import objects.entity.node.NodeManager;
import objects.entity.unit.BaseShip;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Unit;
import objects.resource.Resource;
import objects.resource.ResourceManager;
import teams.student.NeverendingKnights.units.resource.Gatherer;

import java.util.ArrayList;
import java.util.Comparator;

public abstract class NeverendingKnightsUnit extends Unit
{
    protected Unit currentTarget;
    protected final float TOP_Y = 2500;
    protected final float BOTTOM_Y = -2500;
    protected float maxX;
    protected boolean stayInMiddle;
    protected boolean notConfinedToMiddle;
    protected boolean stayOutsideMiddle;


    public NeverendingKnights getPlayer() {
        return (NeverendingKnights) super.getPlayer();
    }

    public void action()
    {
        attack(getWeaponOne());
        attack(getWeaponTwo());
        movement();
    }

    public void attack(Weapon w) {
        if (currentTarget == null || currentTarget.isDead()) {
            currentTarget = getBestTargetEnemy(getMaxRange());
        }

        if(currentTarget != null && w != null && getDistance(currentTarget) < getMaxRange()){
            w.use(currentTarget);
        }
    }

    public void movement()
    {
        if (currentTarget == null || currentTarget.isDead()) {
            currentTarget = getBestTargetEnemy(getMaxRange()*2);
        }
        Unit enemy = currentTarget;

        if(enemy != null) {
            if(getDistance(enemy) > getMaxRange()) {
                moveTo(enemy);
            }
            else {
                turnTo(enemy);
                turnAround();
                move();
            }
        }
    }

    public void setMaxX(){
        if (NeverendingKnights.furthestTank != null) maxX = NeverendingKnights.furthestTank.getCenterX();
    }

    //**********************************************************************************
    // ENEMY TARGETING *****************************************************************
    //**********************************************************************************

    public Unit getBestTargetEnemy(float radius) {
        Unit bestEnemy = null;
        ArrayList<Unit> enemies = getEnemiesInRadius(radius);
        if (!enemies.isEmpty()) {
            int bestEnemyScore = 0;
            for (Unit enemy : enemies) {
                if (enemy.getY() > BOTTOM_Y && enemy.getY() < TOP_Y && !enemy.equals(getEnemyBase())) {
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
//        if (u.hasComponent(CommandRelay.class)) {
//            return 4000;
//        }
//        else if (u.hasComponent(Pullbeam.class)) {
//            return 3800;
//        }
//        else if (u.hasComponent(SpeedBoost.class)) {
//            return 3500;
//        }
//        else if (u.hasComponent(ElectromagneticPulse.class)) {
//            return 3000;
//        }
//        else if (u.hasComponent(GravitationalRift.class)) {
//            return 2500;
//        }
//        else if (u.hasComponent(HeavyRepairBeam.class)) {
//            return 2400;
//        }
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
            return 500;
        }
        else if (u.getWeaponOne() instanceof Autocannon && u.getWeaponTwo() instanceof Autocannon) {
            return 10;
        }
        return 5;
    }

    protected int healthScore(Unit u) {
        //gets a score to deduct based on health of the enemy
        if (u.getPercentEffectiveHealth() / u.getMaxEffectiveHealth() < 0.2) {
            return 2500;
        }
        else if (u.getPercentEffectiveHealth() / u.getMaxEffectiveHealth() < 0.5) {
            return 1500;
        }
        else if (u.getPercentEffectiveHealth() / u.getMaxEffectiveHealth() < 0.7) {
            return 1000;
        }
        return 200;
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

    public ArrayList<Resource> getClosestResourcesToResource(Resource r){
        ArrayList<Resource> threeClosest = new ArrayList<>();
        ArrayList<Resource> res = new ArrayList<>();

        for (Resource resource : ResourceManager.getResources()){
            if (!NeverendingKnights.resourceAssigner.assignedResources.contains(resource) && !Gatherer.allDumpedResources.contains(resource)){
                res.add(resource);
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
                if (n.isAlive() && n.isInBounds()) newNodeChain.add(n);
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

}
