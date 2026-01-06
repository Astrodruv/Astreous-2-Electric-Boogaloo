package teams.student.NeverendingKnights.units.combat;

import objects.entity.unit.Unit;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import player.Player;
import teams.student.NeverendingKnights.NeverendingKnightsUnit;
import teams.student.NeverendingKnights.units.combat.strategies.ProtectGathererStrategy;
import teams.student.mrfeastsfleet.units.Fighter;
import teams.student.NeverendingKnights.units.combat.strategies.CombatStrategy;
import teams.student.NeverendingKnights.units.combat.strategies.RallyStrategy;

import java.util.ArrayList;

public class CombatManager {
    private ArrayList<Unit> combatUnits;
    private ArrayList<Unit> enemyUnits;
    private Player player;
    private CombatStrategy combatStrategy;
    public CombatManager(Player player) {
        this.player = player;
        combatUnits = player.getMyUnits(Fighter.class);
        enemyUnits = player.getEnemyUnits();
    }

    public void update() {
        combatUnits = player.getMyUnits(Fighter.class);
        enemyUnits = player.getEnemyUnits();
        for (Unit u: player.getMyUnits()) {
            if (u instanceof NeverendingKnightsUnit) {
                ((NeverendingKnightsUnit) u).setCombatManager(this);
            }
        }
        if (combatStrategy == null) {
            chooseCombatStrategy();
        }
        combatStrategy.update();
    }

    public void chooseCombatStrategy() {
        combatStrategy = new RallyStrategy(this);
    }

    public CombatStrategy getStrategy() {
        return combatStrategy;
    }



    //accessors

    public Player getPlayer() {
        return player;
    }

    public void draw(Graphics g) {
        g.setColor(Color.yellow);
        if (combatStrategy != null) {
            combatStrategy.draw(g);
        }
    }
}
