package teams.student.NeverendingKnights.units.combat.strategies;

import objects.entity.unit.Unit;
import org.newdawn.slick.Graphics;
import player.Player;
import teams.student.NeverendingKnights.units.combat.CombatManager;
import teams.student.NeverendingKnights.units.combat.Fighter;

import java.util.ArrayList;

public abstract class CombatStrategy{
    protected CombatManager combatManager;
    protected Player player;
    protected ArrayList<Unit> combatUnits;
    protected ArrayList<Unit> enemyUnits;
    public CombatStrategy(CombatManager combatManager) {
        this.combatManager = combatManager;
        this.player = combatManager.getPlayer();
    }

    public void update() {
        combatUnits = player.getMyUnits(Fighter.class);
        enemyUnits = player.getEnemyUnits();
    }

    public void draw(Graphics g) {

    }


}
