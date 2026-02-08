package teams.student.NeverendingKnights.units;

import components.upgrade.Shield;
import components.weapon.utility.ElectromagneticPulse;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import teams.student.NeverendingKnights.NeverendingKnightsUnit;

public class Stunner extends NeverendingKnightsUnit {

    public void design() {
        setFrame(Frame.MEDIUM);
        setModel(Model.ARTILLERY);
        setStyle(Style.ROCKET);

        add(Shield.class);
        add(ElectromagneticPulse.class);
//        add(SisyphusMod.class);
    }

    public void act() {
        if (getWeaponOne().onCooldown() || getDistance(getNearestEnemyThreat()) > 500) {
            if (getPlayer().isLeftPlayer()) moveTo(furthestTankX - 300, furthestTankY);
            else if (getPlayer().isRightPlayer()) moveTo(furthestTankX + 300, furthestTankY);
            dbgMessage("The");
        }
        else{
            moveTo(getNearestEnemyThreat());
            if (getDistance(getNearestEnemyThreat()) < 300){
                attack(getWeaponOne());
            }
            dbgMessage("Yeh");
        }
    }

}
