package teams.student.NeverendingKnights;

import objects.entity.unit.BaseShip;
import objects.resource.Resource;
import objects.resource.ResourceManager;
import player.Player;
import teams.student.NeverendingKnights.units.Gatherer;

import java.util.ArrayList;

public class ResourceAssigner {

    public ArrayList<Resource> resourcesByDistToHomeBase;
    public ArrayList<Resource> resourcesByDensity;
    public ArrayList<Resource> assignedResources;
    public BaseShip homeBase;
    public Player player;

    public ResourceAssigner(Player p){
        player = p;
        homeBase = player.getMyBase();
        resourcesByDistToHomeBase = new ArrayList<>();
        assignedResources = new ArrayList<>();
        resourcesByDensity = new ArrayList<>();
    }

    public void updateResources(){
        if (homeBase == null) homeBase = player.getMyBase();
        resourcesByDistToHomeBase.clear();
        for (Resource r : ResourceManager.getResources()) {
            if (!assignedResources.contains(r) && !Gatherer.allDumpedResources.contains(r)) resourcesByDistToHomeBase.add(r);
        }
        resourcesByDistToHomeBase.sort((r1, r2) -> Float.compare(r1.getDistance(homeBase), r2.getDistance(homeBase)));
    }

    public void assignResources(Gatherer g) {
        if (resourcesByDistToHomeBase != null && assignedResources != null) {

            g.assignedResources.clear();
            updateResources();

            if (!resourcesByDistToHomeBase.isEmpty()) {
                Resource r = resourcesByDistToHomeBase.getFirst();

                if (g.getClosestResourcesToResource(r) != null && !g.getClosestResourcesToResource(r).isEmpty()) {
                    ArrayList<Resource> closestResources = g.getClosestResourcesToResource(r);
                    assignedResources.addAll(closestResources);
                    g.assignedResources.addAll(closestResources);
                    resourcesByDistToHomeBase.removeAll(closestResources);
                }

                if (!resourcesByDistToHomeBase.isEmpty()) {
                    assignedResources.add(r);
                    g.assignedResources.add(r);
                    resourcesByDistToHomeBase.removeFirst();
                }
            }

        }
    }
}
