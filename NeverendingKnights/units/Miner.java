package teams.student.NeverendingKnights.units;


import components.weapon.Weapon;
import components.weapon.economy.Drillbeam;
import objects.entity.node.Node;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import teams.student.NeverendingKnights.NeverendingKnightsUnit;

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
        dbgMessage(getWeaponOne().getDamage());
        if (MinerBuffer.closestNode != null) {
            harvest(MinerBuffer.closestNode, getWeaponOne());
        }
        else harvest(getNearestNode(), getWeaponOne());
	}

	public void harvest(Node n, Weapon w)
	{
        moveTo(n);
		w.use(n);
	}
	
}
