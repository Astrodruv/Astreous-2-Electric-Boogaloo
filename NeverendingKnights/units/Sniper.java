package teams.student.NeverendingKnights.units;

import components.upgrade.Munitions;
import components.weapon.energy.HeavyLaser;
import components.weapon.energy.Laser;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import teams.student.NeverendingKnights.NeverendingKnightsUnit;

public class Sniper extends NeverendingKnightsUnit {
    private String stage;
    private float spreadY;


    public void design()
    {
        setFrame(Frame.MEDIUM);
        setModel(Model.ARTILLERY);
        setStyle(Style.WEDGE);

        add(Laser.class);
        add(Laser.class);
//        add(HeavyLaser.class);
//        add(Munitions.class);
//        add(Shield.class);
        add(Munitions.class);

        stage = "Waiting";

    }

}