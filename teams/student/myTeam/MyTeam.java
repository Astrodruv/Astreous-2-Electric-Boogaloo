package teams.student.myTeam;

import org.newdawn.slick.Graphics;
import player.Player;
import teams.student.myTeam.units.Fighter;
import teams.student.myTeam.units.Gatherer;
import teams.student.myTeam.units.Miner;

public class MyTeam extends Player
{	
	public void setup()
	{		
		setName("My Team");
		setTeamImage("src/teams/student/myTeam/teamLogo.png");
		setTitle("Newbie Team");

		setColorPrimary(50, 125, 255);
		setColorSecondary(255, 255, 255);
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
