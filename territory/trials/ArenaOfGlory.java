package territory.trials;

import engine.Utility;
import engine.states.Game;
import objects.resource.Scrap;
import org.newdawn.slick.Color;
import territory.Territory;
import ui.display.Images;

public class ArenaOfGlory extends Territory
{
	public ArenaOfGlory() 
	{
		super();
		background = Images.getBackgroundGod(0);

	}
	
	float a = .50f;
	float b = .65f;
	float c = .80f;
	float d = .95f;

	public Color getDerelictColor()
	{
		int a = 70;
		int b = 40;
		Color c1 = (Game.getPlayerOne().getColorPrimary().multiply(new Color(a, a, a))).addToCopy(new Color(b, b, b));
		Color c2 = (Game.getPlayerTwo().getColorPrimary().multiply(new Color(a, a, a))).addToCopy(new Color(b, b, b));	
		
		if(Math.random() < .5)
		{
			return c1;
		}
		else
		{
			return c2;
		}
	}
	
	public void spawnNodes()
	{		
		for(int i = 0; i < 14; i++)
		{
			spawnDerelict(getRandomX(a), getRandomY(a));
		}
		
		for(int i = 0; i < 12; i++)
		{
			spawnDerelict(getRandomX(b), getRandomY(b));
		}
		
		for(int i = 0; i < 10; i++)
		{
			spawnDerelict(getRandomX(c), getRandomY(c));
		}
		
		for(int i = 0; i < 8; i++)
		{
			spawnDerelict(getRandomX(d), getRandomY(d));
		}
	}
	
	public void spawnResources()
	{
		for(int i = 0; i < 5; i++)
		{
			spawnResourceCluster(Scrap.class, getRandomX(a), getRandomY(a), Utility.random(5), 150);
		}
		for(int i = 0; i < 5; i++)
		{
			spawnResourceCluster(Scrap.class, getRandomX(b), getRandomY(b), Utility.random(5), 150);
		}
		for(int i = 0; i < 5; i++)
		{
			spawnResourceCluster(Scrap.class, getRandomX(c), getRandomY(c), Utility.random(5), 150);
		}
		for(int i = 0; i < 5; i++)
		{
			spawnResourceCluster(Scrap.class, getRandomX(d), getRandomY(d), Utility.random(5), 150);
		}
	}
}
