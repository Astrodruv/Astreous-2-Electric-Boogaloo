package teams.student.NeverendingKnights.units;

import components.upgrade.Munitions;
import components.weapon.explosive.HeavyMissile;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import org.newdawn.slick.Graphics;
import teams.student.NeverendingKnights.NeverendingKnightsUnit;

public class MissileLauncher extends NeverendingKnightsUnit {

    // For best enemy, should prioritize highly clustered swarms rather than typical bestTarget

    public void design() {
        setFrame(Frame.MEDIUM);
        setModel(Model.ARTILLERY);
        setStyle(Style.WEDGE);

        add(HeavyMissile.class);
        add(Munitions.class);

    }

    public void action() {
        super.action();
    }

    public void draw(Graphics g) {

    }
}