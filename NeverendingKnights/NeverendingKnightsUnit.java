package teams.student.NeverendingKnights;

import components.weapon.Weapon;
import components.weapon.economy.Collector;
import components.weapon.economy.Drillbeam;
import objects.entity.node.Node;
import objects.entity.node.NodeManager;
import objects.entity.unit.Unit;
import objects.resource.Resource;
import objects.resource.ResourceManager;
import org.newdawn.slick.Graphics;
import teams.student.NeverendingKnights.units.Gatherer;

import java.util.ArrayList;
import java.util.Comparator;

public abstract class NeverendingKnightsUnit extends Unit
{	
	public NeverendingKnights getPlayer()
	{
		return (NeverendingKnights) super.getPlayer();
	}
	
	public void action() 
	{
		attack(getWeaponOne());
		attack(getWeaponTwo());
		movement();
	}
		
	public void attack(Weapon w)
	{
		Unit enemy = getNearestEnemy();

		if(enemy != null && w != null)
		{
			w.use(enemy);	
		}
	}
		
	public void movement()
	{
		Unit enemy = getNearestEnemy();

		if(enemy != null)
		{		
			if(getDistance(enemy) > getMaxRange())
			{
				moveTo(enemy);
			}
			else
			{
				turnTo(enemy);
				turnAround();
				move();
			}
		}
	}

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

    public ArrayList<Node> updateNodeChain(ArrayList<Node> nodeChain){
        ArrayList<Node> newNodeChain = new ArrayList<>();

        if (nodeChain != null) {
            for (Node n : nodeChain) {
                if (n.isAlive() && n.isInBounds()) newNodeChain.add(n);
            }
        }

        return newNodeChain;
    }
	
	public void draw(Graphics g) 
	{

	}
}
