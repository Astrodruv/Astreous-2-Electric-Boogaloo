package teams.student.NeverendingKnights.units;

import components.mod.utility.AthenaMod;
import components.weapon.utility.CommandRelay;
import objects.entity.node.Node;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import teams.student.NeverendingKnights.NeverendingKnightsUnit;

public class MinerBuffer extends NeverendingKnightsUnit {

    public static Node closestNode;

    public void design()
    {
        setFrame(Frame.MEDIUM);
        setModel(Model.ARTILLERY);
        setStyle(Style.CANNON);
        add(CommandRelay.class);
        add(AthenaMod.class);

        closestNode = null;
    }

    public void action()
    {
        closestNode = getNearestNode();

        moveTo(closestNode);
        if (getDistance(closestNode) < 150) getWeaponOne().use();
    }

}
