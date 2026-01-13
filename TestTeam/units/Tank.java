package teams.student.TestTeam.units;

import components.mod.offense.AchillesMod;
import components.mod.offense.CerberusMod;
import components.mod.offense.HadesMod;
import components.upgrade.HeavyPlating;
import components.weapon.Weapon;
import components.weapon.energy.Laser;
import components.weapon.explosive.Missile;
import components.weapon.kinetic.Autocannon;
import components.weapon.utility.AntiMissileSystem;
import engine.states.Game;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import org.newdawn.slick.Graphics;
import teams.student.NeverendingKnights.NeverendingKnights;
import teams.student.TestTeam.TestTeamUnit;

public class Tank extends TestTeamUnit {

    public void design() {

        setFrame(Frame.HEAVY);
        setModel(Model.BASTION);
        setStyle(Style.CRESCENT);

        if (enemyMissileThreat.equals("High") || enemyMissileThreat.equals("Medium")){
            add(Autocannon.class);
            add(AntiMissileSystem.class);
        }
        else {
            add(Autocannon.class);
            add(AchillesMod.class);
        }
        add(HeavyPlating.class);
    }

}