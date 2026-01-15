package teams.student.neverendingKnights.units.resource;


import components.weapon.Weapon;
import components.weapon.economy.Drillbeam;
import engine.states.Game;
import objects.entity.node.Node;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import org.newdawn.slick.Graphics;
import teams.student.neverendingKnights.NeverendingKnightsUnit;

public class Miner extends NeverendingKnightsUnit
{

	public void design()
	{
		setFrame(Frame.LIGHT);
		setModel(Model.DESTROYER);
		setStyle(Style.BOXY);
		add(Drillbeam.class);
	}


    public void action() {
        if (myAttackers.isEmpty()){
            if (Game.getTime() % 10 == 0) {
                calculations();
                setState();
                setRallyPoint();
            }
        }
        if (MinerBuffer.bestNode == null && getNearestNode() == null) {
            moveTo(getEnemyBase());
            getWeaponOne().use(getEnemyBase());
        }
        else {
            if (getNearestEnemyThreat() != null) {
                Unit threat = getNearestEnemyThreat();
                if (getDistance(threat) > threat.getMaxRange() * 1.25f || threat.equals(getEnemyBase())) {
                    if (MinerBuffer.bestNode != null) {
                        harvest(MinerBuffer.bestNode, getWeaponOne()); // Must mine in clusters
                    } else harvest(getNearestNode(), getWeaponOne());
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
        }


	}

	public void harvest(Node n, Weapon w)
	{
        moveTo(n);
		w.use(n);
	}

    public void draw(Graphics g) {

    }
	
}
