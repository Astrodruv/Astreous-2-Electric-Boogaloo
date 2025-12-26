package teams.foes.dummy;

import org.newdawn.slick.Graphics;
import player.Player;


public class Dummy extends Player
{	
	
	public void setup()
	{		
		setName("Dummy");
		setTeamImage("src/teams/foes/dummy/dummy.png");

		setColorPrimary(165, 90, 70);
		setColorSecondary(255, 255, 255);
		setColorAccent(200, 200, 200);
	}
	

	public void strategy() 
	{		

	}
			
	public void draw(Graphics g) 
	{
		
	}
	
}
