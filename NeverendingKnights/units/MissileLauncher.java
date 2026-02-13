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
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import teams.student.NeverendingKnights.NeverendingKnightsUnit;

import java.awt.*;

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
                if (enemyHealers.size() > getEnemies().size() * 0.065f) { // If there are at least 6.5% healers in the fleet, equip more cerberus
                    if (chance < 0.75f) {
                        add(CerberusMod.class);
                    } else{
                        add(NyxMod.class);
                    }
                }
                else{
                    if (chance < 0.5f) {
                        add(CerberusMod.class);
                    } else{
                        add(NyxMod.class);
                    }
                }
            }
            else{
                setFrame(Frame.ASSAULT);
                setModel(Model.ARTILLERY);
                setStyle(Style.DAGGER);
                add(Laser.class);
                add(Laser.class);// Possibly switch to 2 lasers
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
                if (enemyHealers.size() > getEnemies().size() * 0.065f) {
                    if (chance < 0.75f) {
                        add(CerberusMod.class);
                    } else{
                        add(NyxMod.class);
                    }
                }
                else{
                    if (chance < 0.5) {
                        add(CerberusMod.class);
                    } else{
                        add(NyxMod.class);
                    }
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

    public void draw(Graphics g) {
        super.draw(g);
        g.setColor(Color.pink);
        if (currentTarget != null) g.drawOval(currentTarget.getCenterX() - 75, currentTarget.getCenterY() - 75, 125, 125);
    }
}