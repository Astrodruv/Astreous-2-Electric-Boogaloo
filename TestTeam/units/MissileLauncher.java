package teams.student.TestTeam.units;

import components.mod.offense.CerberusMod;
import components.upgrade.Shield;
import components.weapon.Weapon;
import components.weapon.explosive.Missile;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import org.newdawn.slick.Graphics;
import teams.student.TestTeam.TestTeamUnit;

public class MissileLauncher extends TestTeamUnit {
    private String stage;
    private float spreadY;

    // For best enemy, should prioritize highly clustered swarms rather than typical bestTarget

    public void design() {
        setFrame(Frame.MEDIUM);
        setModel(Model.ARTILLERY);
        setStyle(Style.DAGGER);

        add(Missile.class);
        add(CerberusMod.class);
        add(Shield.class);

    }


}