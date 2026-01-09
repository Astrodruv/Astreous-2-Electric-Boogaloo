package teams.student.NeverendingKnights.units;

import components.mod.healing.ApolloMod;
import components.mod.healing.DionysusMod;
import components.mod.healing.PerseusMod;
import components.mod.healing.PythiaMod;
import components.weapon.Weapon;
import components.weapon.utility.HeavyRepairBeam;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import org.newdawn.slick.Graphics;
import teams.student.NeverendingKnights.NeverendingKnights;
import teams.student.NeverendingKnights.NeverendingKnightsUnit;

public class Healer extends NeverendingKnightsUnit {
    private String stage;
    private Unit unitToHeal;

    public void design() {
        setFrame(Frame.MEDIUM);
        setModel(Model.CRUISER);
        setStyle(Style.BOXY);

        // 4 slots

        add(HeavyRepairBeam.class);

        if (opponentHasPullers()) {
            add(PerseusMod.class);
        }
        else if (opponentHasLongRangeWeapons()) {
            add(PythiaMod.class);
        }
        else if (getAverageEnemyMaxSpeed() > getAverageMyUnitsMaxSpeed() * 1.3) {
            add(DionysusMod.class);
        }
        else {
            add(ApolloMod.class);
        }

        //check for if the opponent has pullers -> fortify (perseus mod)
        //check for if the opponent has long range weapons -> Omen (pythia mod)
        //check for if the opponent has fast moving units -> revelry (dionysus mod)
        //else ->glory (apollo mod)
        stage = "Waiting";

    }

    public void movement() {
        if (unitToHeal != null) {
            moveTo(unitToHeal);
        }
        else {
            float midX = getHomeBase().getCenterX();
            if (NeverendingKnights.furthestTank != null) {
                midX = (NeverendingKnights.furthestTank.getCenterX() + getHomeBase().getCenterX()) / 2;
            }
            else {
                midX = (getEnemyBase().getCenterX() + getHomeBase().getCenterX()) / 2;
            }
            moveTo(midX,y);
        }

    }

    public void attack(Weapon w) {
        if (unitToHeal != null && w != null) {
            if (getDistance(unitToHeal) < getMaxRange()) {
                w.use(unitToHeal);
            }
        }
    }

    public void action() {
        setStage();
        movement();
        attack(getWeaponOne());
    }

    public void setStage() {
        boolean shouldStartHealing = false;
        for (Unit u: getUnits(getPlayer())) {
            if (u instanceof Sniper) {
                if (((Sniper) u).getStage().equals("Healing")) {
                    shouldStartHealing = true;
                    unitToHeal = u;
                }
            }
        }
        if (shouldStartHealing) {
            stage = "Healing";
        }
        else {
            stage = "Waiting";
            unitToHeal = null;
        }
    }


    public String getStage() {
        return stage;
    }



    @Override
    public void draw(Graphics g) {

    }
}

