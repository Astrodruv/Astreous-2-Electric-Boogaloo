package teams.student.neverendingKnights.units.resource;


import components.weapon.economy.Collector;
import engine.states.Game;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import objects.resource.Resource;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import teams.student.neverendingKnights.NeverendingKnights;
import teams.student.neverendingKnights.NeverendingKnightsUnit;

import java.util.ArrayList;

public class Gatherer extends NeverendingKnightsUnit
{
    public ArrayList<Resource> assignedResources;
    private int i;
    private int timeToGainSpeed;

    public ArrayList<Resource> dumpedResources;
    public static ArrayList<Resource> allDumpedResources;

    public void design()
	{
		setFrame(Frame.LIGHT);
		setModel(Model.TRANSPORT);
		setStyle(Style.BUBBLE);
		add(Collector.class);

        assignedResources = new ArrayList<>();
        dumpedResources = new ArrayList<>();
        if (allDumpedResources == null) allDumpedResources = new ArrayList<>();

        i = 0;
        timeToGainSpeed = 0;
	}

	public void action() 
	{
        if (myAttackers.isEmpty()){
            if (Game.getTime() % 10 == 0) {
                calculations();
                setState();
                setRallyPoint();
            }
        }
        i++;
        if (!NeverendingKnights.resourceAssigner.allResourcesFinished) {
            returnResources();
            if (assignedResources != null && assignedResources.isEmpty() && isEmpty()) NeverendingKnights.resourceAssigner.assignResources(this);
            gatherResources();

            if (i % 10 == 0) NeverendingKnights.resourceAssigner.updateResources(this);
        }
        else{
            if (assignedResources != null && assignedResources.isEmpty() && isEmpty()) {
                turnTo(getHomeBase());
                turnAround();
                move();
            }
            else{
                returnResources();
                if (assignedResources != null && assignedResources.isEmpty() && isEmpty()) NeverendingKnights.resourceAssigner.assignResources(this);
                gatherResources();
            }
        }
    }

	public void returnResources()
	{
        if (NeverendingKnights.resourceGrabberCount > 0) {
            if (assignedResources.isEmpty()) {
                    if (timeToGainSpeed < 600 && getDistance(getHomeBase()) > 250) {
                        float distance = getDistance(getHomeBase());
                        if (getHomeBase().getSpeedX() != 0) {
                            if (getPlayer().isLeftPlayer()) {
                                moveTo(getHomeBase().getCenterX() + (distance / 25), getHomeBase().getCenterY());
                            }
                            if (getPlayer().isRightPlayer()) {
                                moveTo(getHomeBase().getCenterX() - (distance / 25), getHomeBase().getCenterY());
                            }
                        } else {
                            moveTo(getHomeBase());
                        }
                        timeToGainSpeed++;
                    } else {
                        timeToGainSpeed = 0;
                        dump();
                        for (Resource r : getResourcesInRadius(50, this)) {
                            if (!allDumpedResources.contains(r)) allDumpedResources.add(r);
                        }
                    }
            }
        }
        else{
            moveTo(getHomeBase());
            deposit();
        }
	}

	public void gatherResources()
	{
        if(hasCapacity()) {
            if (assignedResources != null && !assignedResources.isEmpty()) {
                Resource r = assignedResources.getFirst();
                Unit threat = getNearestEnemyThreat();
                if (r != null && !dumpedResources.contains(r)) {
                    if (threat != null && getDistance(threat) > threat.getMaxRange() * 1.1f) {
                        moveTo(r);
                        ((Collector) getWeaponOne()).use(r);
                        if (r.isPickedUp()) {
                            assignedResources.remove(r);
                            NeverendingKnights.resourceAssigner.assignedResources.remove(r);
                        }
                    }
                    else{
                        if (isInBounds()) {
                            turnTo(threat);
                            turnAround();
                            move();
                        }
                        else{
                            moveTo(getHomeBase());
                        }
                    }
                }
                else moveTo(getHomeBase());
            }
		}
        else{
            moveTo(getHomeBase());
            assignedResources.clear();
        }
	}

    public void draw(Graphics g){
        g.setColor(new Color(0,0,255));
        if (!assignedResources.isEmpty() && assignedResources.getFirst() != null) {
            g.drawLine(getCenterX(), getCenterY(), assignedResources.getFirst().getCenterX(), assignedResources.getFirst().getCenterY());
        }
        g.setColor(new Color(0,0,225));
        if (!assignedResources.isEmpty() && assignedResources.size() > 1 && assignedResources.get(1) != null){
            g.drawLine(assignedResources.getFirst().getCenterX(), assignedResources.getFirst().getCenterY(), assignedResources.get(1).getCenterX(), assignedResources.get(1).getCenterY());
        }
        g.setColor(new Color(0,0,200));
        if (!assignedResources.isEmpty() && assignedResources.size() > 2 && assignedResources.get(2) != null){
            g.drawLine(assignedResources.get(1).getCenterX(), assignedResources.get(1).getCenterY(), assignedResources.get(2).getCenterX(), assignedResources.get(2).getCenterY());
        }
        g.setColor(new Color(0,0,175));
        if (!assignedResources.isEmpty() && assignedResources.size() > 3 && assignedResources.get(3) != null){
            g.drawLine(assignedResources.get(2).getCenterX(), assignedResources.get(2).getCenterY(), assignedResources.get(3).getCenterX(), assignedResources.get(3).getCenterY());
        }
    }

}
