package teams.student.NeverendingKnights.units.buffs;

import components.mod.utility.AthenaMod;
import components.weapon.utility.CommandRelay;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import teams.student.NeverendingKnights.NeverendingKnightsUnit;

public class Commander extends NeverendingKnightsUnit {
    public void design()
    {
        setFrame(Frame.MEDIUM);
        setModel(Model.ARTILLERY);
        setStyle(Style.WEDGE);

        add(CommandRelay.class);
        add(AthenaMod.class);
//        add(Shield.class);
    }

    public void act() {
        if (getPlayer().isLeftPlayer()) moveTo(furthestTankX - 450, furthestTankY);
        else if (getPlayer().isRightPlayer()) moveTo(furthestTankX + 450, furthestTankY);
//        if (getDistance(furthestTankX, furthestTankY) < 500) {
//            getWeapon(CommandRelay.class).use();
//        }
        if (!getEnemiesInRadius(getMaxRange() * 4).isEmpty()){
            getWeapon(CommandRelay.class).use();
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.pink);
        g.drawLine(getCenterX(), getCenterY(), furthestTankX, furthestTankY);
    }
}
