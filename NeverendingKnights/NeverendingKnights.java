package teams.student.NeverendingKnights;

import objects.resource.ResourceManager;
import org.newdawn.slick.Graphics;
import player.Player;
import teams.student.NeverendingKnights.units.*;

public class NeverendingKnights extends Player
{

    public static ResourceAssigner resourceAssigner;

	public void setup()
	{		
		setName("Neverending Knights");
		setTeamImage("src/teams/student/NeverendingKnights/teamLogo.png");
		setTitle("Newbie Team");

		setColorPrimary(50, 125, 255);
		setColorSecondary(255, 255, 255);
		setColorAccent(255, 255, 255);

        resourceAssigner = new ResourceAssigner(this);
	}
	
	public void strategy() 
	{
        if (countUnit(this, MinerBuffer.class) < 1){
            buildUnit(MinerBuffer.class);
        }
        if (countUnit(this, ResourceGrabber.class) < 2){
            buildUnit(ResourceGrabber.class);
        }

        if (countUnit(this, Miner.class) < 8){
            buildUnit(Miner.class);
        }

		if(countUnit(this, Gatherer.class) < 12)
		{
			buildUnit(Gatherer.class);
		}

        if (countUnit(this, Pest.class) < 20){
            buildUnit(Pest.class);
        }
//		else if(getFleetValueUnitPercentage(Miner.class) < .2f)
//		{
//			buildUnit(Miner.class);
//		}
//		else
//		{
////			buildUnit(Gatherer.class);
//		}

	}
			
	public void draw(Graphics g) 
	{
        addMessage("Assigned: " + resourceAssigner.assignedResources.size());
        addMessage("Total Counted: " + resourceAssigner.resourcesByDistToHomeBase.size());
        addMessage("Total Counted + Assigned: " + (resourceAssigner.resourcesByDistToHomeBase.size() + resourceAssigner.assignedResources.size()));
        addMessage("Actual Total: " + ResourceManager.getResources().size());
        addMessage("Total Dumped: " + Gatherer.allDumpedResources.size());

    }
	
}
