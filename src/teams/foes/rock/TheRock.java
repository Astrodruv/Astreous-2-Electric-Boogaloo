package teams.foes.rock;

import org.newdawn.slick.Graphics;
import player.Player;
import teams.foes.rock.units.*;

public class TheRock extends Player
{	
	
	public void setup()
	{
		setName("The Rock");

		if(Math.random() < .8f)
		{
			setTeamImage("src/teams/foes/rock/rock.png");
			setTitle("Stunning Durability");
		}
		else
		{
			setTeamImage("src/teams/foes/rock/rock2.png");
			setTitle("The Toned Stone Cyclone");
		}

		setColorPrimary(246, 226, 154);
		setColorSecondary(128, 128, 112);
		setColorAccent(255, 255, 255);
	}

	public void strategy() 
	{		
		if(getFleetValueUnitPercentage(Gatherer.class) < .25f)
		{
			buildUnit(Gatherer.class);
		}
		else if(getFleetValueUnitPercentage(Miner.class) < .2f)
		{
			buildUnit(Miner.class);
		}
		else if(getFleetValueUnitPercentage(Power.class) < .1f)
		{
			buildUnit(Power.class);
		}
		else if(getFleetValueUnitPercentage(Drive.class) < .075f)
		{
			buildUnit(Drive.class);
		}
		else if(getFleetValueUnitPercentage(Hungry.class) < .075f)
		{
			buildUnit(Hungry.class);
		}
		else
		{
			buildUnit(Devour.class);
		}
	}
			
	public void draw(Graphics g) 
	{
		
	}
	
}
