package teams.student.NeverendingKnights.units;

import components.mod.utility.AthenaMod;
import components.weapon.energy.Laser;
import components.weapon.utility.CommandRelay;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import org.newdawn.slick.Graphics;
import teams.student.NeverendingKnights.NeverendingKnightsUnit;

public class Commander extends NeverendingKnightsUnit {
    public void design()
    {
        setFrame(Frame.ASSAULT);
        setModel(Model.ARTILLERY);
        setStyle(Style.BUBBLE);


//        add(Laser.class);
        add(Laser.class);
        add(CommandRelay.class);
        add(AthenaMod.class);


    }


    public void draw(Graphics g) {

    }
}
