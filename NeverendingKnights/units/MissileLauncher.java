package teams.student.NeverendingKnights.units;

import components.mod.offense.*;
import components.upgrade.HeavyMunitions;
import components.upgrade.Munitions;
import components.upgrade.Shield;
import components.weapon.energy.HeavyLaser;
import components.weapon.energy.Laser;
import components.weapon.explosive.HeavyMissile;
import components.weapon.explosive.Missile;
import engine.states.Game;
import objects.entity.unit.Frame;
import objects.entity.unit.Model;
import objects.entity.unit.Style;
import teams.student.NeverendingKnights.NeverendingKnightsUnit;

public class MissileLauncher extends NeverendingKnightsUnit {
    private String stage;
    private float spreadY;

    // For best enemy, should prioritize highly clustered swarms rather than typical bestTarget

    public void design() {


        // max missile launcher equipment
        if (getPlayer().getStoredResources() >= 6 && Game.getTime() > 300 * 60) {
            if (!hasAntiMissiles()) {
                setFrame(Frame.ASSAULT);
                setModel(Model.ARTILLERY);
                setStyle(Style.DAGGER);
                add(HeavyMissile.class);
                add(Missile.class);
                add(Munitions.class);
                double chance = Math.random();
                if (chance < 0.33) {
                    add(CerberusMod.class);
                } else if (chance < 0.66){
                    add(NyxMod.class);
                }
                else{
                    add(ArtemisMod.class);
                }
            }
            else{
                setFrame(Frame.ASSAULT);
                setModel(Model.ARTILLERY);
                setStyle(Style.DAGGER);
                add(HeavyLaser.class); // Possibly switch to 2 lasers
                add(HeavyMunitions.class);
                add(PoseidonMod.class);
            }
        }
        else{
            if (!hasAntiMissiles()) {
                setFrame(Frame.HEAVY);
                setModel(Model.ARTILLERY);
                setStyle(Style.DAGGER);
                add(Missile.class);
                add(Missile.class);
                add(Munitions.class);
                double chance = Math.random();
                if (chance < 0.33) {
                    add(CerberusMod.class);
                } else if (chance < 0.66){
                    add(NyxMod.class);
                }
                else{
                    add(ArtemisMod.class);
                }
            }
            else{
                setFrame(Frame.MEDIUM);
                setModel(Model.ARTILLERY);
                setStyle(Style.DAGGER);
                add(Laser.class);
                add(Munitions.class);
                add(PoseidonMod.class);
            }
        }
    }


}