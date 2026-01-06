package teams.student.NeverendingKnights.units.combat.strategies;

import objects.entity.unit.Unit;
import teams.student.NeverendingKnights.NeverendingKnightsUnit;
import teams.student.NeverendingKnights.units.Gatherer;
import teams.student.NeverendingKnights.units.Miner;
import teams.student.NeverendingKnights.units.ResourceGrabber;
import teams.student.NeverendingKnights.units.combat.CombatManager;
import teams.student.NeverendingKnights.units.combat.Fighter;

import java.util.ArrayList;

public class ProtectGathererStrategy extends CombatStrategy{
    ArrayList<Unit> gatherers;
//    ArrayList<Miner> miners;
    ArrayList<ResourceGrabber> resourceGrabbers;
    ArrayList<Unit> assignedGatherers;
    ArrayList<Unit> assignedFighters;

    public ProtectGathererStrategy(CombatManager combatManager) {
        super(combatManager);
        assignedGatherers = new ArrayList<>();
        assignedFighters = new ArrayList<>();
    }

    public void update() {
        super.update();
        gatherers = player.getMyUnits(Gatherer.class);
        resetListsInCaseAWorkerDied();
        coordinateWhichFighterProtectsWhichWorker();
    }

    public void resetListsInCaseAWorkerDied() {
        for (int i= 0; i<assignedFighters.size(); i++) {
            if (assignedFighters.get(i) instanceof Fighter) {
                if (((Fighter) assignedFighters.get(i)).getWorkerToProtect() == null || ((Fighter) assignedFighters.get(i)).getWorkerToProtect().isDead()) {
                    assignedFighters.remove(i);
                    i--;
                }
            }
        }
        for (int i= 0; i<assignedGatherers.size(); i++) {
            if (assignedGatherers.get(i) instanceof Gatherer) {
                if (assignedGatherers.get(i).isDead()) {
                    assignedGatherers.remove(i);
                    i--;
                }
            }
        }
    }

    public void coordinateWhichFighterProtectsWhichWorker() {
        if (gatherers != null && combatUnits != null) {
            for (Unit g : gatherers) {
                if (assignedGatherers != null) {
                    if (g != null) {
                        if (!assignedGatherers.contains(g)) {
                            for (Unit f : combatUnits) {
                                if (!assignedFighters.contains(f) && f instanceof NeverendingKnightsUnit) {
                                    ((NeverendingKnightsUnit) f).setWorkerToProtect(g);
                                    assignedFighters.add(f);
                                    assignedGatherers.add(g);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    //send at least 1 Unit to each gatherer and miner
}
