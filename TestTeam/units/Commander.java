package teams.student.TestTeam.units;

import components.mod.utility.AthenaMod;
import components.weapon.energy.Laser;
import components.weapon.kinetic.Autocannon;
import components.weapon.utility.CommandRelay;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import teams.student.TestTeam.TestTeamUnit;

public class Commander extends TestTeamUnit {
    @Override
    public void design() {
        setFrame(Frame.HEAVY);
        setModel(Model.ARTILLERY);
        setStyle(Style.BUBBLE);

        add(AthenaMod.class);
//        add(Laser.class);
        add(CommandRelay.class);
        add(Autocannon.class);
    }

    public void act(){
//        currentTarget = nearestEnemyThreat;
        currentTarget = getBiggestThreatInRadius(getMaxRange());
        getWeapon(CommandRelay.class).use();
        attack(getWeaponOne());
        attack(getWeaponTwo());
        movement();
    }

}
