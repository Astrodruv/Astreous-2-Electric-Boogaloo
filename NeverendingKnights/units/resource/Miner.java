package teams.student.NeverendingKnights.units.resource;


import components.weapon.Weapon;
import components.weapon.economy.Drillbeam;
import engine.states.Game;
import objects.entity.node.Node;
import objects.entity.node.NodeManager;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import org.newdawn.slick.Graphics;
import teams.student.NeverendingKnights.NeverendingKnights;
import teams.student.NeverendingKnights.NeverendingKnightsUnit;

public class Miner extends NeverendingKnightsUnit
{
    Node n = null;

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

        if (NodeManager.getNodes().size() <= 5 || Game.getTime() >= 850 * 60) {
            moveTo(getNearestEnemyThreat());
            getWeaponOne().use(getNearestEnemyThreat());
            if (NeverendingKnights.spawnMiners) NeverendingKnights.spawnMiners = false;
        }
        else {
            if (MinerBuffer.bestNode == null || getNearestAlly(MinerBuffer.class) == null){
                harvest(getNearestNode(), getWeaponOne());
            }
            else {
                if (getNearestEnemyThreat() != null) {
                    Unit threat = getNearestEnemyThreat();
                    if (getDistance(threat) > threat.getMaxRange() * 1.25f || threat.equals(getEnemyBase())) {
                        harvest(MinerBuffer.bestNode, getWeaponOne());
                    } else {
                        moveTo(MinerBuffer.bestNode);
                        getWeaponOne().use(threat);
                    }
                }
            }
        }

        if (MinerBuffer.bestNode != null) dbgMessage("Not null");
        else dbgMessage("NULL");
	}

	public void harvest(Node n, Weapon w)
	{
        moveTo(n);
		w.use(n);
	}

    public void draw(Graphics g) {

    }
	
}
