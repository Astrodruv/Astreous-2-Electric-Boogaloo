package territory.trials;

import engine.Utility;
import engine.states.Game;
import objects.entity.unit.BaseShip;
import objects.resource.Minerals;
import objects.resource.Scrap;
import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Point;
import territory.Territory;
import ui.display.Images;

public class ThracianPeaks extends Territory
{
	public ThracianPeaks()
	{
		super();
		background = Images.getBackgroundGod(5);

	}

	public Color getAsteroidColor()
	{
		int g = Utility.random(120, 160);
		return new Color(120, g, 200);
	}

	public Color getMineralColor()
	{
		return new Color(100, 100, 255);
	}

	public void spawnNodes()
	{
		int i = 0;

		while(i < 50)
		{
			Point p = new Point(getRandomX(), getRandomY());
			Point p2 = new Point(BaseShip.BASE_SHIP_X_POSITION-1000, BaseShip.BASE_SHIP_Y_POSITION);
			float distance = Utility.distance(p, p2);

			if(distance > 1000 && distance < 6000 && Math.abs(p.getY()) > 2000)
			{
				spawnAsteroid(p.getX(), p.getY(), 1);
				i++;
			}
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

		while(i < 40)
		{
			Point p = new Point(getRandomX(), getRandomY());
			Point p2 = new Point(BaseShip.BASE_SHIP_X_POSITION-1000, BaseShip.BASE_SHIP_Y_POSITION);
			float distance = Utility.distance(p, p2);

			if(distance > 2000 && distance < 6000 && Math.abs(p.getY()) > 2000)
			{
				spawnResourceCluster(Minerals.class, p.getX(), p.getY(), Utility.random(5), 350);
				i++;
			}
		}



	}

//	public void spawnResources()
//	{
//		for(int i = 0; i < 5; i++)
//		{
//			spawnResourceCluster(Scrap.class, getRandomX(a), getRandomY(a), Utility.random(5), 150);
//		}
//		for(int i = 0; i < 5; i++)
//		{
//			spawnResourceCluster(Scrap.class, getRandomX(b), getRandomY(b), Utility.random(5), 150);
//		}
//		for(int i = 0; i < 5; i++)
//		{
//			spawnResourceCluster(Scrap.class, getRandomX(c), getRandomY(c), Utility.random(5), 150);
//		}
//		for(int i = 0; i < 5; i++)
//		{
//			spawnResourceCluster(Scrap.class, getRandomX(d), getRandomY(d), Utility.random(5), 150);
//		}
//	}
}
