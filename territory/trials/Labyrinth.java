package territory.trials;

import engine.Utility;
import objects.resource.Minerals;
import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Point;
import territory.Territory;
import ui.display.Images;

import java.util.ArrayList;

public class Labyrinth extends Territory
{
	ArrayList<Point> points = new ArrayList<>();


	public Labyrinth()
	{
		super();
		background = Images.getBackgroundGod(0);
	}

//	public Color getDerelictColor()
//	{
//		int b = Utility.random(150, 200);
//		return new Color(150, 150, b);
//	}
	
	public Color getAsteroidColor()
	{
		return new Color(150, 120, 50);
	}
	
	public Color getMineralColor()
	{
		return new Color(255, 0, 0);
	}	
	
	public void spawnNodes()
	{
		int rX = 0;
		int rY = 0;

		for(int i = 0; i < 25; i++)
		{
			points.add(new Point(rX, rY));

			while(inPointList(rX, rY))
			{
//				System.out.println("labyr" + rX + " " + rY);

				rX = rX + Utility.random(-1, 1);
				rY = rY + Utility.random(-1, 1);
			}

		}

		rX = 0;
		rY = 0;

		for(int i = 0; i < 25; i++)
		{
			points.add(new Point(rX, rY));

			while(inPointList(rX, rY))
			{
//				System.out.println("labyr" + rX + " " + rY);

				rX = rX + Utility.random(-1, 1);
				rY = rY + Utility.random(-1, 1);
			}

		}


		float scalar = 500;


		for(Point p : points)
		{
			spawnAsteroid(p.getX() * scalar * 1.5f, p.getY() * scalar, 1);
			spawnResourceCluster(Minerals.class,p.getX() * scalar, p.getY() * scalar, Utility.random(9), 300);
		}
		

	}

	public boolean inPointList(int x, int y)
	{
		for(Point p : points)
		{
			System.out.println("labyr" + x + " " + Math.round(p.getX()));


			if(x == Math.round(p.getX()) && y == Math.round(p.getY()))
			{
				return true;
			}
		}
		return false;
	}
	

	public void spawnResources()
	{

	}
}
