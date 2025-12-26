package territory.trials;

import engine.Utility;
import objects.resource.Scrap;
import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Point;
import territory.Territory;
import ui.display.Images;

public class MountOlympus extends Territory
{
	public MountOlympus() 
	{
		super();
		background = Images.getBackgroundGod(8);
//		derelictColor = new Color(new Color(120, 30, 30));
//		asteroidColor = new Color(new Color(200, 100, 70));
//		mineralColor = new Color(new Color(225, 200, 75));
//		scrapColor = new Color(new Color(200, 200, 200));
	}

//	public Color getDerelictColor()
//	{
//		int green = Utility.random(30, 100);
//		return new Color(100, green, 30);
//	}
	
	float stretch = .65f;
	
	

	public Color getAsteroidColor()
	{
		int b = Utility.random(200, 255);
		return new Color(120, 150, b);
	}
	
	public Color getMineralColor()
	{
		return new Color(255, 255, 255);
	}
	
	public Color getScrapColor()
	{
		return new Color(255, 255, 255);
	}
	
	
	public void spawnNodes()
	{		
		int i = 0;
		while(i < 70)
		{
			Point p = new Point(getRandomX(), getRandomY());
			Point p2 = new Point(0, 0);
			float distance = Utility.distance(p.getX() * stretch, p.getY(), p2.getX(), p2.getY());
			
			if((distance > 5150 && distance < 5300) || (distance > 3150 && distance < 3300) || (distance > 1150 && distance < 1300))
			{
				spawnAsteroid(p.getX() + getRandomX(.05f), p.getY() + getRandomY(.05f), 1);
				i++;
			}
		}
//		for(int i = 0; i < 10; i++)
//		{
//			float x = getRandomX(.25f, .60f);
//			float y = getRandomY(.25f, .60f);
//
//			for(int j = 0; j < 3; j++)
//			{
//				spawnAsteroid(x + getRandomX(.1f), y + getRandomY(.1f));
//			}
//		}
	}

	public void spawnResources()
	{
		int i = 0;
		
		while(i < 60)
		{
			Point p = new Point(getRandomX(), getRandomY());
			Point p2 = new Point(0, 0);
			float distance = Utility.distance(p.getX() * stretch, p.getY(), p2.getX(), p2.getY());
			
			if((distance < 4850 && distance > 3150) || (distance < 3000 && distance > 1150)  || (distance < 1000))
			{
				spawnResourceCluster(Scrap.class, p.getX(), p.getY(), Utility.random(5), 150);
				i++;
			}
		}
		
	
	}
}
