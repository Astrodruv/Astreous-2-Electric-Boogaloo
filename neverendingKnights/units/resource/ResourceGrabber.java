package teams.student.neverendingKnights.units.resource;

import components.weapon.economy.Collector;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import objects.resource.Resource;
import org.newdawn.slick.Graphics;
import teams.student.neverendingKnights.NeverendingKnightsUnit;

public class ResourceGrabber extends NeverendingKnightsUnit {

    public void design()
    {
        setFrame(Frame.MEDIUM);
        setModel(Model.PROTOTYPE);
        setStyle(Style.ORB);
        add(Collector.class);
        add(Collector.class);
    }

    public void action()
    {
        moveTo(getHomeBase().getCenterX(), getHomeBase().getCenterY());

        Resource r = getNearestResource();
        Resource r2 = getNearestResource();

        ((Collector) getWeaponOne()).use(r);

        for (Resource res : getResourcesInRadius(getHomeBase().getWidth(), this)){
            if (res != r){
                r2 = res;
            }
        }
        ((Collector) getWeaponTwo()).use(r2);

        deposit();
    }

    public void draw(Graphics g) {

    }

}
