package teams.student.TestTeam.units.resource;

import components.mod.utility.AthenaMod;
import components.weapon.utility.CommandRelay;
import engine.states.Game;
import objects.entity.node.Node;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import teams.student.TestTeam.TestTeamUnit;

import java.util.ArrayList;

public class MinerBuffer extends TestTeamUnit {

    public static Node bestNode;
    public static ArrayList<Node> bestNodes;


    public void design()
    {
        setFrame(Frame.MEDIUM);
        setModel(Model.ARTILLERY);
        setStyle(Style.CANNON);
        add(CommandRelay.class);
        add(AthenaMod.class);

        bestNode = null;
        bestNodes = findBestChainOfNodes();
    }

    public void action()
    {
        if (Game.getTime() % 60 == 0){
            bestNodes = updateNodeChain(bestNodes);
        }

        if (bestNodes != null && !bestNodes.isEmpty()) bestNode = bestNodes.getFirst();
        else bestNode = getNearestNode();

        if (getNearestEnemyThreat() != null) {
            Unit threat = getNearestEnemyThreat();
            if (getDistance(threat) > threat.getMaxRange() * 2f) {
                moveTo(bestNode);
                if (getDistance(bestNode) < 150) getWeaponOne().use();
            } else {
                if (isInBounds()) {
                    turnTo(threat);
                    turnAround();
                    move();
                } else {
                    moveTo(getHomeBase());
                }
            }
        }

        if (bestNode != null && bestNode.isDead()) bestNodes.remove(bestNode);

        if (getNearestNode() == null){
            moveTo(getEnemyBase());
            getWeaponOne().use(getEnemyBase());
        }
    }

    public void draw(Graphics g){
        g.setColor(Color.yellow);
        if (bestNodes != null) {
            if (bestNodes.size() >= 2)
                g.drawLine(bestNodes.get(0).getCenterX(), bestNodes.get(0).getCenterY(), bestNodes.get(1).getCenterX(), bestNodes.get(1).getCenterY());
            if (bestNodes.size() >= 3)
                g.drawLine(bestNodes.get(1).getCenterX(), bestNodes.get(1).getCenterY(), bestNodes.get(2).getCenterX(), bestNodes.get(2).getCenterY());
            if (bestNodes.size() >= 4)
                g.drawLine(bestNodes.get(2).getCenterX(), bestNodes.get(2).getCenterY(), bestNodes.get(3).getCenterX(), bestNodes.get(3).getCenterY());
            if (bestNodes.size() >= 5)
                g.drawLine(bestNodes.get(3).getCenterX(), bestNodes.get(3).getCenterY(), bestNodes.get(4).getCenterX(), bestNodes.get(4).getCenterY());
            if (bestNodes.size() >= 6)
                g.drawLine(bestNodes.get(4).getCenterX(), bestNodes.get(4).getCenterY(), bestNodes.get(5).getCenterX(), bestNodes.get(5).getCenterY());
            if (bestNodes.size() >= 7)
                g.drawLine(bestNodes.get(5).getCenterX(), bestNodes.get(5).getCenterY(), bestNodes.get(6).getCenterX(), bestNodes.get(6).getCenterY());
        }
    }

}
