package teams.student.Destiny.units;

import components.mod.offense.CerberusMod;
import components.mod.offense.NyxMod;
import components.upgrade.Munitions;
import components.upgrade.Shield;
import components.weapon.energy.Laser;
import components.weapon.explosive.Missile;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import teams.student.Destiny.DestinyUnit;

public class Bazooka extends DestinyUnit
{

    public void design()
    {
        setFrame(Frame.HEAVY);
        setModel(Model.ARTILLERY);
        setStyle(Style.CANNON);

        add(Missile.class);
        add(Missile.class);
        if (Math.random() < 0.5f){
            add(CerberusMod.class);
        }
        else{
            add(NyxMod.class);
        }
        add(Munitions.class);
    }


}
