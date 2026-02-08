package teams.student.Destiny;

import components.weapon.Weapon;
import components.weapon.economy.Collector;
import objects.entity.node.Node;
import objects.entity.node.NodeManager;
import objects.entity.unit.Unit;
import objects.resource.Resource;
import objects.resource.ResourceManager;
import org.newdawn.slick.Graphics;
import teams.student.Destiny.units.Juggernaut;
import teams.student.Destiny.units.Prince;
import teams.student.Destiny.units.resource.Gatherer;

import java.util.ArrayList;

public abstract class DestinyUnit extends Unit
{
	public Destiny getPlayer()
	{
		return (Destiny) super.getPlayer();
	}

    public String attackStage = "Wait";

	public void action() 
	{
		attack(getWeaponOne());
		attack(getWeaponTwo());
		movement();
	}
		
	public void attack(Weapon w)
	{
        Unit enemy;

		if (this instanceof Juggernaut){
            enemy = getNearestEnemyThreatInLane();
        }
        else{
            enemy = lowestTarget(getMaxRange());
        }

		if(enemy != null && w != null) {
			w.use(enemy);	
		}
	}
		
	public void movement() {
        Unit enemy = getNearestEnemyThreatInLane();
        Unit furthestJuggernaut = getFurthestJuggernaut();

        if (enemy != null) {
            if (this instanceof Juggernaut) {
                    if (enemy.getDistance(getHomeBase()) > 2500 && (getAlliesInRadius(1500, Juggernaut.class).size() < 4 || getAlliesInRadius(1500, Prince.class).size() < 8) && getAllies().size() < 70) {
                        if (getPlayer().isLeftPlayer())
                            moveTo(getHomeBase().getCenterX() + 500, getHomeBase().getCenterY());
                        else if (getPlayer().isRightPlayer())
                            moveTo(getHomeBase().getCenterX() - 500, getHomeBase().getCenterY());
                    } else {
                        attackStage = "Attack";
                        if (getDistance(enemy) > getMaxRange() * 0.25f) {
                            moveTo(enemy);
                        } else {
                            turnTo(enemy);
                            turnAround();
                            move();
                        }
                    }
            } else {
                if (enemy.getDistance(getHomeBase()) > 2500 && (getAlliesInRadius(1500, Juggernaut.class).size() < 4 || getAlliesInRadius(1500, Prince.class).size() < 8) && getAllies().size() < 70) {
                    if (getPlayer().isLeftPlayer())
                        moveTo(getHomeBase().getCenterX() + 500, getHomeBase().getCenterY());
                    else if (getPlayer().isRightPlayer())
                        moveTo(getHomeBase().getCenterX() - 500, getHomeBase().getCenterY());
                }
                else {
                    attackStage = "Attack";
                    if (furthestJuggernaut != null) {
                        if ((getPlayer().isLeftPlayer() && getX() < (furthestJuggernaut.getX() - getMaxRange() * 0.5f)) || (getPlayer().isRightPlayer() && getX() > (furthestJuggernaut.getX() + getMaxRange() * 0.5f))) {
                            if (getDistance(furthestJuggernaut) > 1500){
                                moveTo(furthestJuggernaut);
                            }
                            else {
                                if (getDistance(enemy) > getMaxRange() && !getWeaponOne().onCooldown()) { // can make weapon 2 as well
                                    moveTo(enemy);
                                } else {
                                    turnTo(enemy);
                                    turnAround();
                                    move();
                                }
                            }
                        } else {
                            moveTo(getHomeBase());
                        }
                    }
                    else{
                        if (getDistance(enemy) > getMaxRange() && !getWeaponOne().onCooldown()) { // can make weapon 2 as well
                            moveTo(enemy);
                        } else {
                            turnTo(enemy);
                            turnAround();
                            move();
                        }
                    }
                }
            }
        }
        else{
            if (getDistance(getEnemyBase()) > getMaxRange() && !getWeaponOne().onCooldown()) {
                moveTo(getEnemyBase());
            } else {
                turnTo(getEnemyBase());
                turnAround();
                move();
            }
        }

	}

    public Unit lowestTarget(int range) {
        ArrayList<Unit> e = getEnemyThreatsInRange(range);

        e.sort((e1, e2) -> Float.compare(e1.getPercentEffectiveHealth(), e2.getPercentEffectiveHealth()));

        if (!e.isEmpty()) {
            return e.getFirst();
        }

        else return null;
    }

    public ArrayList<Resource> getResourcesInRadius(float rad, DestinyUnit u){
        ArrayList<Resource> res = new ArrayList<>();
        for (Resource r : ResourceManager.getResources()){
            if (r.getDistance(u) < rad){
                res.add(r);
            }
        }

        return res;
    }

    public ArrayList<Unit> getEnemyThreats(){
        ArrayList<Unit> threats = new ArrayList<>();

        for (Unit u : getEnemies()) {
            if (!u.hasWeapon(Collector.class)) threats.add(u);
        }

        return threats;
    }

    public ArrayList<Unit> getEnemyThreatsInRange(int range){
        ArrayList<Unit> threats = new ArrayList<>();

        for (Unit u : getEnemies()) {
            if (!u.hasWeapon(Collector.class) && getDistance(u) < range) threats.add(u);
        }

        return threats;
    }

    public Unit getNearestEnemyThreat() {
        // found this method that lowkey reduces lag by a ton
        // basically sets the distance to the highest possible value, and then decreasing it by the lower ones every time
        // that way we don't need to sort every frame which is pretty laggy
        Unit nearest = null;
        float bestDist = Float.MAX_VALUE;
        for (Unit u : getEnemies()) {
            if (u.hasWeapon(Collector.class)) continue;
            float d = getDistance(u);
            if (d < bestDist) {
                bestDist = d;
                nearest = u;
            }
        }
        return nearest;
    }

    public Unit getNearestEnemyThreatInLane(){
        Unit nearest = null;
        float bestDist = Float.MAX_VALUE;
        for (Unit u : getEnemies()) {
            if (u.hasWeapon(Collector.class) || (u.getY() < -2500 || u.getY() > 2500)) continue; // Range for y value
            float d = getDistance(u);
            if (d < bestDist) {
                bestDist = d;
                nearest = u;
            }
        }
        return nearest;
    }

    public Unit getBestAllyToHealInRadius(int radius, Class<? extends Unit> u){
        ArrayList<Unit> alliesInRadius = getAlliesInRadius(radius, u);

        alliesInRadius.sort((a1, a2) -> Float.compare(a1.getPercentEffectiveHealth(), a2.getPercentEffectiveHealth()));

        if (!alliesInRadius.isEmpty()) return alliesInRadius.getFirst();
        return null;
    }

    public Unit getFurthestJuggernaut(){
        ArrayList<Unit> juggernauts = getAlliesInRadius(100000, Juggernaut.class);

        juggernauts.sort((j1, j2) -> Float.compare(j1.getDistance(getEnemyBase()), j2.getDistance(getEnemyBase())));

        if (!juggernauts.isEmpty()) return juggernauts.getFirst();
        return null;
    }


    public boolean isResourceSafe(Resource r){
        float enemyDist = Float.MAX_VALUE;
        Unit enemy = getNearestEnemy();

        for (Unit u : getEnemyThreats()){
            if (enemyDist > u.getDistance(r)) {
                enemyDist = u.getDistance(r);
                enemy = u;
            }
        }

        if (enemyDist > enemy.getMaxRange() * 1.5f) return true;
        return false;
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

    public ArrayList<Resource> getClosestResourcesToResource(Resource r){
        ArrayList<Resource> threeClosest = new ArrayList<>();
        ArrayList<Resource> res = new ArrayList<>();

        for (Resource resource : ResourceManager.getResources()){
            if (!Destiny.resourceAssigner.assignedResources.contains(resource) && !Gatherer.allDumpedResources.contains(resource) && isResourceSafe(resource)){
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

    public ArrayList<Node> updateNodeChain(ArrayList<Node> nodeChain){
        ArrayList<Node> newNodeChain = new ArrayList<>();

        if (nodeChain != null) {
            for (Node n : nodeChain) {
                if (n.isAlive() && n.isInBounds() && n.getDistance(getNearestEnemyThreat()) > 500){ // && isNodeSafe(n) // && getDistance(n) < getNearestEnemyThreat().getDistance(n)
                    newNodeChain.add(n);
                }
            }
        }

        return newNodeChain;
    }
	
	public void draw(Graphics g) 
	{

	}
}
