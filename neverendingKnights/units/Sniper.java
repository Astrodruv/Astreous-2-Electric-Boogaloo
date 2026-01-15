package teams.student.neverendingKnights.units;

import components.upgrade.Munitions;
import components.upgrade.Shield;
import components.weapon.energy.HeavyLaser;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import teams.student.neverendingKnights.NeverendingKnightsUnit;

public class Sniper extends NeverendingKnightsUnit {
    private String stage;
    private float spreadY;

    // Attack at same x (in a line) behind tanks

    public void design()
    {
        setFrame(Frame.HEAVY);
        setModel(Model.ARTILLERY);
        setStyle(Style.WEDGE);

//        add(Laser.class);
//        add(HeavyMunitions.class);
//        add(Shield.class);
        //73.6% good

//        add(PoseidonMod.class);

        add(HeavyLaser.class);
        add(Munitions.class);
        add(Shield.class);
//        //76.6% good

        stage = "Waiting";

    }

}