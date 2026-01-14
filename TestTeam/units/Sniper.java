package teams.student.TestTeam.units;

import components.upgrade.Munitions;
import components.upgrade.Shield;
import components.weapon.Weapon;
import components.weapon.energy.HeavyLaser;
import components.weapon.energy.Laser;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import org.newdawn.slick.Graphics;
import teams.student.NeverendingKnights.NeverendingKnights;
import teams.student.TestTeam.TestTeamUnit;

public class Sniper extends TestTeamUnit {
    private String stage;
    private float spreadY;

    // Attack at same x (in a line) behind tanks

    public void design()
    {
        setFrame(Frame.HEAVY);
        setModel(Model.ARTILLERY);
        setStyle(Style.WEDGE);

        add(HeavyLaser.class);
        add(Munitions.class);
        add(Shield.class);
//        add(PoseidonMod.class);

        stage = "Waiting";

    }

}