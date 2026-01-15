package teams.student.neverendingKnights.units;

import components.upgrade.Shield;
import components.weapon.explosive.HeavyMissile;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import teams.student.neverendingKnights.NeverendingKnightsUnit;

public class MissileLauncher extends NeverendingKnightsUnit {
    private String stage;
    private float spreadY;

    // For best enemy, should prioritize highly clustered swarms rather than typical bestTarget

    public void design() {
        setFrame(Frame.MEDIUM);
        setModel(Model.ARTILLERY);
        setStyle(Style.DAGGER);

        add(HeavyMissile.class);
        add(Shield.class);

    }


}