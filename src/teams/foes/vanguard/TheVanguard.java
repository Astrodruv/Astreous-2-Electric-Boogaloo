package teams.foes.vanguard;

import org.newdawn.slick.Graphics;
import player.Player;
import teams.foes.vanguard.units.Relentless;
import teams.foes.vanguard.units.Gatherer;
import teams.foes.vanguard.units.Miner;

public class TheVanguard extends Player
{	
	
	public void setup()
	{
		setName("The Vanguard");
		setTeamImage("src/teams/foes/vanguard/vanguard.png");
		setTitle("Endless Assault");

		setColorPrimary(160, 42, 160);
		setColorSecondary(25, 100, 150);
		setColorAccent(255, 255, 255);
		
	}

	public void strategy() 
	{		
		if(getFleetValueUnitPercentage(Gatherer.class) < .175f)
		{
			buildUnit(Gatherer.class);
		}
		else if(getFleetValueUnitPercentage(Miner.class) < .175f)
		{
			buildUnit(Miner.class);
		}
		else
		{
			buildUnit(Relentless.class);
		}
	}
			
	public void draw(Graphics g) 
	{
		
	}
	
}
