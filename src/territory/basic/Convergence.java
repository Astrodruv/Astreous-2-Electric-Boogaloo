package territory.basic;

import engine.states.Game;
import objects.resource.Minerals;
import org.newdawn.slick.Color;
import territory.Territory;
import ui.display.Images;

public class Convergence extends Territory
{
	public Convergence() 
	{
		super();
		background = Images.getBackground(0);
	}

	public void spawnNodes()
	{
		for(float i = 0; i < 1; i += .05f)
		{
			spawnAsteroid(i * Game.getMapWidth(), i * Game.getMapHeight(), 2);
			spawnAsteroid(i * Game.getMapWidth(), -i * Game.getMapHeight(), 2);
		}
			
		for(float i = 0; i < 1; i += .05f)
		{
			spawnAsteroid(i * .35f * Game.getMapWidth(), i * .65f * Game.getMapHeight(), 1);
			spawnAsteroid(i * .35f * Game.getMapWidth(), i * -.65f * Game.getMapHeight(), 1);
			spawnAsteroid(i * .15f * Game.getMapWidth(), i * .85f * Game.getMapHeight(), 1);
			spawnAsteroid(i * .15f * Game.getMapWidth(), i * -.85f * Game.getMapHeight(), 1);
		}
	}
	
	public void spawnResources()
	{
		spawnResourceCluster(Minerals.class, 0, 0, 50, 1000);
	}
	
	public Color getAsteroidColor()
	{
		return new Color(130, 130, 130);
	}
		
	public Color getMineralColor()
	{
		return new Color(100, 100, 220);
	}
}
