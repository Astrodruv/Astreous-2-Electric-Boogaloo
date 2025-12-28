package teams.student.NeverendingKnights;

import components.weapon.Weapon;
import objects.entity.unit.Unit;
import objects.resource.Resource;
import objects.resource.ResourceManager;
import org.newdawn.slick.Graphics;
import teams.student.NeverendingKnights.units.Gatherer;

import java.util.ArrayList;

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

    public ArrayList<Resource> getThreeClosestResourcesToResource(Resource r){
        ArrayList<Resource> threeClosest = new ArrayList<>();
        ArrayList<Resource> res = new ArrayList<>();

        for (Resource resource : ResourceManager.getResources()){
            if (!NeverendingKnights.resourceAssigner.assignedResources.contains(resource) && !Gatherer.allDumpedResources.contains(resource)){
                res.add(resource);
            }
        }

        res.sort((r1, r2) -> Float.compare(r1.getDistance(r), r2.getDistance(r)));

        if (res.size() > 3){
            threeClosest.add(res.get(1));
            threeClosest.add(res.get(2));
            threeClosest.add(res.get(3));
        }

        System.out.println(r + " " + threeClosest);
        return threeClosest;
    }
	
	public void draw(Graphics g) 
	{

	}
}
