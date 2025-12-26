package teams.foes.librarian;

import org.newdawn.slick.Graphics;
import player.Player;
import teams.foes.librarian.units.*;

public class TheLibrarian extends Player
{
	public void setup()
	{		
		setName("The Librarian");
		setTeamImage("src/teams/foes/librarian/librarian.png");
		setTitle("Return Your Books");

		setColorPrimary(83, 197, 255);
		setColorSecondary(180, 140, 95);
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
		else if(getFleetValueUnitPercentage(Shelver.class) < .2f)
		{
			buildUnit(Shelver.class);
		}
		else
		{
			buildUnit(new Stacks());
		}
	}
			
	public void draw(Graphics g) 
	{
		
	}
	
}
