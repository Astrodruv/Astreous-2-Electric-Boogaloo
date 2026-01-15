package teams.student.neverendingKnights.units;

import components.upgrade.Munitions;
import components.upgrade.Shield;
import components.weapon.energy.HeavyLaser;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;

public class Commander extends Sniper{
    public void design()
    {
        setFrame(Frame.HEAVY);
        setModel(Model.ARTILLERY);
        setStyle(Style.WEDGE);

        add(HeavyLaser.class);
        add(Munitions.class);
        add(Shield.class);
//        add(PoseidonMod.class);


    }
}
