package teams.student.NeverendingKnights.units.combat;

import components.mod.utility.AthenaMod;
import components.upgrade.Plating;
import components.upgrade.Shield;
import components.weapon.Weapon;
import components.weapon.energy.Laser;
import components.weapon.utility.CommandRelay;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;

public class Commander extends Fighter{
    public void design()
    {
        setFrame(Frame.ASSAULT);
        setModel(Model.ARTILLERY);
        setStyle(Style.BUBBLE);

        add(AthenaMod.class);
//        add(Laser.class);
        add(CommandRelay.class);
        add(Laser.class);

    }

    @Override
    public void attack(Weapon w) {
        if (w != null) {
            w.use();
        }
    }
}
