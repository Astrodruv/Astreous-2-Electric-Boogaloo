package teams.student.NeverendingKnights.units;

import components.mod.offense.CerberusMod;
import components.mod.offense.NyxMod;
import components.upgrade.Munitions;
import components.weapon.explosive.Missile;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import teams.student.NeverendingKnights.NeverendingKnightsUnit;

public class MissileLauncher extends NeverendingKnightsUnit {
    private String stage;
    private float spreadY;

    // For best enemy, should prioritize highly clustered swarms rather than typical bestTarget

    public void design() {
        setFrame(Frame.MEDIUM);
        setModel(Model.PROTOTYPE);
        setStyle(Style.DAGGER);

        add(Missile.class);
        add(Missile.class);
        add(Munitions.class);

        double chance = Math.random();
        if (chance > 0.5) {
            add(CerberusMod.class);
        } else {
            add(NyxMod.class);
        }
    }


}