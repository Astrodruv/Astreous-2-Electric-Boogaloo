package teams.student.TestTeam.units.resource;


import components.weapon.Weapon;
import components.weapon.economy.Drillbeam;
import objects.entity.node.Node;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import org.newdawn.slick.Graphics;
import teams.student.TestTeam.TestTeamUnit;

public class Miner extends TestTeamUnit
{


    // Need to:
    // - Move away from the borders
    // - Move away from dangerous units
    // - Update the nodes so that the ones already gone don't affect the others
	public void design()
	{
		setFrame(Frame.LIGHT);
		setModel(Model.DESTROYER);
		setStyle(Style.BOXY);
		add(Drillbeam.class);
	}


    public void action() {
        if (MinerBuffer.bestNode != null) {
            harvest(MinerBuffer.bestNode, getWeaponOne()); // Must mine in clusters
        }
        else harvest(getNearestNode(), getWeaponOne());

        if (getNearestNode() == null){
            moveTo(getEnemyBase());
            getWeaponOne().use(getEnemyBase());
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
