package teams.foes.arsonist;

import org.newdawn.slick.Graphics;
import player.Player;

import teams.foes.arsonist.units.*;


public class TheArsonist extends Player
{	
	int nascars = 0;

	public void setup()
	{		
		setName("The Arsonist");
		setTeamImage("src/teams/foes/arsonist/arsonist.png");
		setTitle("Smokescreen and Slow Burn");

		setColorPrimary(214, 45, 28);
		setColorSecondary(255, 255, 255);
		setColorAccent(255, 255, 255);
	}
		
	public void strategy() 
	{
		if(countMyUnits(Nascar.class) < 3 && nascars < 10)
		{
			buildUnit(Nascar.class);
			nascars++;
		}
		else if(getFleetValueUnitPercentage(Gatherer.class) < .2f || countMyUnits(Gatherer.class) < 6)
		{
			buildUnit(Gatherer.class);
		}
		else if(getFleetValueUnitPercentage(Miner.class) < .2f || countMyUnits(Miner.class) < 6)
		{
			buildUnit(Miner.class);
		}
		else if(countMyUnits(Nascar.class) < 4 && nascars < 10)
		{
			buildUnit(Nascar.class);
			nascars++;
		}
		else if(getFleetValueUnitPercentage(Titan.class) < .35f)
		{
			buildUnit(Titan.class);
		}
		else
		{
			buildUnit(Sizzler.class);
		}
	}
			
	public void draw(Graphics g) 
	{
		
	}
	
}
