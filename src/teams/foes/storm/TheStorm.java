package teams.foes.storm;

import org.newdawn.slick.Graphics;
import player.Player;
import teams.foes.storm.units.Procrastination;
import teams.foes.storm.units.Gatherer;
import teams.foes.storm.units.Arrogance;
import teams.foes.storm.units.Miner;

public class TheStorm extends Player
{	
	
	public void setup()
	{
		setName("Storm");
		setTeamImage("src/teams/foes/storm/storm.png");
		setTitle("Shockingly Bold");

		setColorPrimary(200, 45, 50);
		setColorSecondary(255, 255, 0);
		setColorAccent(255, 255, 255);
		
	}

	public void strategy() 
	{		
		if(getFleetValueUnitPercentage(Gatherer.class) < .175f)
		{
			buildUnit(new Gatherer(this));
		}
		else if(getFleetValueUnitPercentage(Miner.class) < .175f)
		{
			buildUnit(new Miner(this));
		}
		else if(getFleetValueUnitPercentage(Arrogance.class) < .4f)
		{
			buildUnit(new Arrogance(this));
		}		
		else
		{
			buildUnit(new Procrastination(this));
		}
	}
			
	public void draw(Graphics g) 
	{
		
	}
	
}
