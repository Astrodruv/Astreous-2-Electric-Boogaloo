package teams.student.NeverendingKnights.units;


import components.weapon.economy.Collector;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import objects.resource.Resource;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Point;
import teams.student.NeverendingKnights.NeverendingKnights;
import teams.student.NeverendingKnights.NeverendingKnightsUnit;

import java.util.ArrayList;

public class Gatherer extends NeverendingKnightsUnit
{
    private Point interceptionPoint;
    private float time1;
    private float time2;
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

        interceptionPoint = null;
        time1 = 0;
        time2 = 0;

        i = 0;
        timeToGainSpeed = 0;
	}

	public void action() 
	{
        returnResources();
        if (assignedResources != null && assignedResources.isEmpty() && isEmpty()) NeverendingKnights.resourceAssigner.assignResources(this);
        gatherResources();

        i++;
        if (i % 10 == 0) NeverendingKnights.resourceAssigner.updateResources();
    }

	public void returnResources()
	{
		if(assignedResources.isEmpty()) {

//            if (time1 == 0) time1 = (float) (((getSpeedX() - getHomeBase().getCurSpeed()) + Math.sqrt(Math.pow(getSpeedX() - getHomeBase().getCurSpeed(), 2) + (2 * getHomeBase().getAcceleration() * (getCenterX() - getHomeBase().getCenterX())))) / getHomeBase().getAcceleration());
//            if (time2 == 0) time2 = (float) (((getSpeedX() - getHomeBase().getCurSpeed()) - Math.sqrt(Math.pow(getSpeedX() - getHomeBase().getCurSpeed(), 2) + (2 * getHomeBase().getAcceleration() * (getCenterX() - getHomeBase().getCenterX())))) / getHomeBase().getAcceleration());
//            if (time2 > 0 && time2 < time1) time1 = time2;
//            if (interceptionPoint == null) interceptionPoint = new Point((float) (getHomeBase().getCenterX() + (getSpeedX() * time1) + (0.5f * getHomeBase().getAcceleration() * Math.pow(time1, 2))), getHomeBase().getCenterY());

            if (timeToGainSpeed < 600 && getDistance(getHomeBase()) > 250){
                moveTo(getHomeBase()); //interceptionPoint - make it throw to interceptionPoint, but also group around clusters and the home base as well
                timeToGainSpeed++;
            }
            else {
                timeToGainSpeed = 0;
                dump();
                for (Resource r : getResourcesInRadius(50, this)) {
                    if (!allDumpedResources.contains(r)) allDumpedResources.add(r);
                }
            }

//
//
//            if (getAngleToward(interceptionPoint.getX(), interceptionPoint.getY()) != 0) moveTo(interceptionPoint);
//            else {
//                dump();
//                for (Resource r : getResourcesInRadius(50, this)) {
//                    if (!allDumpedResources.contains(r)) allDumpedResources.add(r);
//                }
//            }
        }
        else{
            interceptionPoint = null;
            time1 = 0;
            time2 = 0;
        }
	}

	public void gatherResources()
	{
        if(hasCapacity())
		{
            if (assignedResources != null && !assignedResources.isEmpty()) {
                Resource r = assignedResources.getFirst();

                if (r != null && !dumpedResources.contains(r)) {
                    moveTo(r);
                    ((Collector) getWeaponOne()).use(r);
                    if (r.isPickedUp()){
                        assignedResources.remove(r);
                        NeverendingKnights.resourceAssigner.assignedResources.remove(r);
                    }
                } else moveTo(getHomeBase());
            }
		}
        else{
            moveTo(getHomeBase());
            assignedResources.clear();
        }
	}

    public void draw(Graphics g){
        dbgMessage(assignedResources.size());
        g.setColor(Color.white);
        g.drawOval(getCenterX() - 25, getCenterY() - 25, 50, 50);
        g.setColor(Color.green);
        if (interceptionPoint != null) {
            g.drawLine(getCenterX(), getCenterY(), interceptionPoint.getX(), interceptionPoint.getY());
        }
        g.setColor(Color.blue); // Make random to easily tell colors apart
        if (!assignedResources.isEmpty() && assignedResources.getFirst() != null) {
            g.drawLine(getCenterX(), getCenterY(), assignedResources.getFirst().getCenterX(), assignedResources.getFirst().getCenterY());
        }
        g.setColor(Color.blue.brighter());
        if (!assignedResources.isEmpty() && assignedResources.size() > 1 && assignedResources.get(1) != null){
            g.drawLine(assignedResources.getFirst().getCenterX(), assignedResources.getFirst().getCenterY(), assignedResources.get(1).getCenterX(), assignedResources.get(1).getCenterY());
        }
        g.setColor(Color.blue.brighter().brighter());
        if (!assignedResources.isEmpty() && assignedResources.size() > 2 && assignedResources.get(2) != null){
            g.drawLine(assignedResources.get(1).getCenterX(), assignedResources.get(1).getCenterY(), assignedResources.get(2).getCenterX(), assignedResources.get(2).getCenterY());
        }
        g.setColor(Color.blue.brighter().brighter().brighter());
        if (!assignedResources.isEmpty() && assignedResources.size() > 3 && assignedResources.get(3) != null){
            g.drawLine(assignedResources.get(2).getCenterX(), assignedResources.get(2).getCenterY(), assignedResources.get(3).getCenterX(), assignedResources.get(3).getCenterY());
        }
    }

}
