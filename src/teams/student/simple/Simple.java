package teams.student.simple;

import org.newdawn.slick.Graphics;
import player.Player;
import teams.student.simple.units.Fighter;
import teams.student.simple.units.Gatherer;
import teams.student.simple.units.Miner;

public class Simple extends Player
{	
	public void setup()
	{		
		setName("Simple");
		setTeamImage("src/teams/student/simple/teamLogo.png");
		setTitle("A Copy Of My Team");

		setColorPrimary(255, 100, 50);
		setColorSecondary(255, 255, 50);
		setColorAccent(255, 255, 255);

	}
	
	public void strategy() 
	{
		if(getFleetValueUnitPercentage(Gatherer.class) < .2f)
		{
			buildUnit(Gatherer.class);
		}
		else if(getFleetValueUnitPercentage(Miner.class) < .2f)
		{
			buildUnit(Miner.class);
		}
		else
		{
			buildUnit(Fighter.class);
		}
	}
			
	public void draw(Graphics g) 
	{
		
	}
	
}
