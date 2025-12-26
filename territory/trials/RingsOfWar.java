package territory.trials;

import engine.Utility;
import objects.entity.unit.BaseShip;
import objects.resource.Minerals;
import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Point;
import territory.Territory;
import ui.display.Images;

public class RingsOfWar extends Territory
{
	public RingsOfWar() 
	{
		super();
		background = Images.getBackgroundGod(7);
	}

	public Color getDerelictColor()
	{
		int b = Utility.random(110, 130);
		return new Color(b, 50, 50);
	}
	
	public Color getAsteroidColor()
	{
		int b = Utility.random(90, 140);
		return new Color(b, b, 50);
	}
	
	public Color getMineralColor()
	{
		return new Color(220, 220, 0);
	}	
	
	public void spawnNodes()
	{	
		int i = 0;
	
		while(i < 40)
		{
			Point p = new Point(getRandomX(), getRandomY());
			Point p2 = new Point(BaseShip.BASE_SHIP_X_POSITION-1000, BaseShip.BASE_SHIP_Y_POSITION);
			float distance = Utility.distance(p, p2);
			
			if(distance > 2500 && distance < 3000)
			{
				spawnAsteroid(p.getX(), p.getY(), 1);
				i++;
			}
		}
		
		for(int j = 0; j < 16; j++)
		{
			spawnDerelict(getRandomX(.20f), getRandomY());
		}
		
//		for(int i = 0; i < 30; i++)
//		{
//
//			//spawnAsteroid(getRandomX(.4f, .95f), getRandomY(.95f));
//		}
	}
	

	public void spawnResources()
	{
		int i = 0;
		
		while(i < 20)
		{
			Point p = new Point(getRandomX(), getRandomY());
			Point p2 = new Point(BaseShip.BASE_SHIP_X_POSITION-1000, BaseShip.BASE_SHIP_Y_POSITION);
			float distance = Utility.distance(p, p2);
			
			if(distance < 3000)
			{
				spawnResourceCluster(Minerals.class, p.getX(), p.getY(), Utility.random(15), 350);
				i++;
			}
		}
		
		

	}
}
