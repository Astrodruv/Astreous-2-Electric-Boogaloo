package teams.foes.farmer;

import org.newdawn.slick.Graphics;
import player.Player;
import teams.foes.farmer.units.Gatherer;
import teams.foes.farmer.units.Potato;
import teams.foes.farmer.units.Corn;
import teams.foes.farmer.units.Miner;

public class TheFarmer extends Player
{	
	
	public void setup()
	{		
		setName("The Farmer");
		setTeamImage("src/teams/foes/farmer/farmer.png");
		setTitle("Grow and Harvest");

		setColorPrimary(50, 220, 70);
		setColorSecondary(20, 130, 220);
		setColorAccent(255, 255, 255);
	
	}
		
	public void strategy() 
	{		
		if(getFleetValueUnitPercentage(Gatherer.class) < .225f)
		{
			buildUnit(new Gatherer(this));
		}
		else if(getFleetValueUnitPercentage(Miner.class) < .225f)
		{
			buildUnit(new Miner(this));
		}
		else if(getFleetValueUnitPercentage(Potato.class) < .3f)
		{
			buildUnit(new Potato(this));
		}	
		else
		{
			buildUnit(new Corn(this));
		}
	}
			
	public void draw(Graphics g) 
	{
		
	}
	
}
