package territory;

import engine.Settings;
import engine.states.Game;
import objects.entity.node.NodeManager;
import objects.resource.ResourceManager;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import territory.basic.*;
import territory.trials.*;

import java.util.ArrayList;

public class TerritoryManager 
{
	private static ArrayList<Territory> types;
	protected static Territory territory;
	
	public static void setup()
	{
		NodeManager.setup();
		ResourceManager.setup();
		
		types = new ArrayList<Territory>();
		if(Settings.includeBasicMaps)
		{
			addBasicMaps();
		}
		if(Settings.includeTrialMaps)
		{
			addTrialMaps();
		}
		if(Settings.includeTournamentMaps)
		{
			addTournamentMaps();
		}
		
		territory = getRandomTerritory();
		
		if(Settings.forceTrialMaps)
		{
			loadTrialMap();
		}
//			
		if(Settings.lockedMap != null)
		{
			Territory temp = findMap(Settings.lockedMap);
			if(temp != null)
			{
				territory = temp;
			}
			else
			{
				System.out.println("Territory Manager - " + Settings.lockedMap.getSimpleName() + " Not Found");
				System.out.println("Loading Random Map Instead");
			}
		}
		
		territory.spawn();
	
	}
	
	public static void loadTrialMap()
	{
		if(Game.getPlayerTwo().getName().equals("Minotaur"))
		{
			territory = new Labyrinth();
		}
		else if(Game.getPlayerTwo().getName().equals("Hydra"))
		{
			territory = new LernaSwamp();
		}
		else if(Game.getPlayerTwo().getName().equals("Harpy"))
		{
			territory = new ThracianPeaks();
		}
		else if(Game.getPlayerTwo().getName().equals("Ares"))
		{
			territory = new ArenaOfGlory();
		}
		else if(Game.getPlayerTwo().getName().equals("Poseidon"))
		{
			territory = new RockyShoals();
		}
		else if(Game.getPlayerTwo().getName().equals("Artemis"))
		{
			territory = new HuntingGrounds();
		}		
		else if(Game.getPlayerTwo().getName().equals("Hermes"))
		{
			territory = new MessengersPath();
		}
		else if(Game.getPlayerTwo().getName().equals("Dionysus"))
		{
			territory = new HiddenReserve();
		}
		else if(Game.getPlayerTwo().getName().equals("Apollo"))
		{
			territory = new DelphiTemple();
		}
		else if(Game.getPlayerTwo().getName().equals("Hades"))
		{
			territory = new RiverStyx();
		}
		else if(Game.getPlayerTwo().getName().equals("Athena"))
		{
			territory = new RingsOfWar();
		}
		else if(Game.getPlayerTwo().getName().equals("Zeus"))
		{
			territory = new MountOlympus();
		}
		
	}
	
	public static Territory getTerritory()
	{
		return territory;
	}
	
	public static Image getBackground()
	{
		return territory.getBackground();
	}
	
	public static void leave()
	{
		types.clear();
		territory = null;
	}
	
	
	public static Color getAsteroidColor()
	{
		return territory.getAsteroidColor();
	}
	
	public static Color getDerelictColor()
	{
		return territory.getDerelictColor();
	}
	
	public static Color getMineralColor()
	{
		return territory.getMineralColor();
	}
	
	public static Color getSalvageColor()
	{
		return territory.getScrapColor();
	}
	
	public static void update()
	{
//		if(Game.getTime() == 5)
//		{
//			DisplayManager.addMessage(territory.getName());
//		}
		
		NodeManager.update();
		ResourceManager.update();
	}
	
	public static void cleanUp()
	{
		NodeManager.cleanUp();
		ResourceManager.cleanUp();
	}
	
	public static void render(Graphics g)
	{
		NodeManager.render(g);
		ResourceManager.render(g);
	}
	
	private static void addBasicMaps()
	{
		addMap(new ShatteredSpace());
		addMap(new AsteroidBelt());
		addMap(new ThePassage());
		addMap(new Scrapyard());
		addMap(new Battlefield());
		addMap(new Convergence());

	}
	
	private static void addTrialMaps()
	{
		addMap(new ThracianPeaks());

		addMap(new ArenaOfGlory());
		addMap(new RockyShoals());
		addMap(new HuntingGrounds());
		addMap(new MessengersPath());
		addMap(new HiddenReserve());
		addMap(new DelphiTemple());
		addMap(new RiverStyx());
		addMap(new RingsOfWar());
		addMap(new MountOlympus());
	}
	
	private static void addTournamentMaps()
	{
		addMap(new RingsOfWar());
	}
	
	public static void addMap(Territory t)
	{
		if(!types.contains(t))
		{
			types.add(t);
		}
	}
	
	public static Territory findMap(Class <? extends Territory> clazz)
	{
		for(Territory t : types)
		{
			if(clazz.isInstance(t))
			{
				return t;
			}
		}
		
		return null;
	}
	
	public static Territory getRandomTerritory()
	{
		return types.get((int) (Math.random() * types.size()));
	}
	
}
