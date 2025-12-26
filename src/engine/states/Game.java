package engine.states;

import animations.AnimationManager;
import engine.Settings;
import engine.Values;
import engine.kdtree.KDTree;
import engine.kdtree.OptimizationToggler;
import engine.states.menu.Menu;
import objects.ambient.AmbientManager;
import objects.entity.Entity;
import objects.entity.missile.MissileEntity;
import objects.entity.node.Node;
import objects.entity.node.NodeManager;
import objects.entity.unit.BaseShip;
import objects.resource.Resource;
import objects.resource.ResourceManager;
import objects.zone.Zone;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import player.Player;
import territory.TerritoryManager;
import ui.display.Camera;
import ui.display.DisplayManager;
import ui.input.InputManager;
import ui.sound.AudioManager;

import java.util.ArrayList;

public class Game extends BasicGameState 
{
	private final int id;
	
	private static int gameNumber;
	private static boolean gameOver;
	private static boolean paused;
	private static int timer;
	private static int gameOverTimer;
	
	private static Player playerOne;
	private static Player playerTwo;
	private static BaseShip alpha;
	private static BaseShip beta;
	private static ArrayList<objects.entity.unit.Unit> units;
	private static ArrayList<objects.entity.unit.Unit> teamOneUnits;
	private static ArrayList<objects.entity.unit.Unit> teamTwoUnits;
	private static ArrayList<MissileEntity> missiles;
	private static ArrayList<Zone> zones;

	private static float damageDealt;
	private static float damageTaken;
	private static int dodgeCount;
	private static float repairRecieved;
	private static float shieldRecieved;

	private static KDTree<objects.entity.unit.Unit> playerOneUnitKDTree;
	private static KDTree<objects.entity.unit.Unit> playerTwoUnitKDTree;
	private static KDTree<Resource> resourceKDTree;
	private static KDTree<Node> nodeKDTree;
	
	/****************** Constructor and Setup ******************/

	public Game(int id) 
	{
		this.id = id;
	}
	
	private void setPlayers() 
	{
		if(playerOne == null)
		{
			playerOne = Menu.getPlayers().get(0);
		}
		if(playerTwo == null)
		{
			playerTwo = Menu.getPlayers().get(1);
		}
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) 
	{		
		// Overall audio volume modifiers
		gc.setSoundVolume(Settings.soundVolume / 2);
		gc.setMusicVolume(Settings.musicVolume / 5);
		gc.setShowFPS(false);
	}
	
	/****************** Accessors  ******************/
	
	public int getID()									{	return id;							}
	public static boolean isGamePaused() 				{	return paused;						}
	public static int getTime()							{	return timer;						}
	public static boolean isGameOver()					{	return gameOver;					}
	public static ArrayList<objects.entity.unit.Unit> getUnits()			{	return units;						}
	public static ArrayList<objects.entity.unit.Unit> getTeamOneUnits()		{	return teamOneUnits;				}
	public static ArrayList<objects.entity.unit.Unit> getTeamTwoUnits()		{	return teamTwoUnits;				}
	public static ArrayList<MissileEntity> getMissiles()		{	return missiles;					}
	public static ArrayList<Zone> getZones()			{	return zones;						}	
	public final ArrayList<Node> getAllNodes()			{	return NodeManager.getNodes();		}
	public final ArrayList<Resource> getAllResources()	{	return ResourceManager.getResources();	}
	
	public static float getTotalDamageDealt()			{	return damageDealt;						}
	public static float getTotalDamageTaken()			{	return damageTaken;						}
	public static int getTotalDodgeCount()				{	return dodgeCount;						}
	public static float getTotalRepairRecieved()		{	return repairRecieved;					}
	public static float getTotalShieldRecieved()		{	return shieldRecieved;					}
	
	public static int getMapWidth()						{	return Values.PLAYFIELD_WIDTH;			}
	public static int getMapHeight()					{	return Values.PLAYFIELD_HEIGHT;			}
	public static int getMapLeftEdge()					{	return -Values.PLAYFIELD_WIDTH / 2;		}
	public static int getMapRightEdge()					{	return Values.PLAYFIELD_WIDTH / 2;		}
	public static int getMapTopEdge()					{	return -Values.PLAYFIELD_HEIGHT / 2;	}
	public static int getMapBottomEdge()				{	return Values.PLAYFIELD_HEIGHT / 2;		}
	
	public static BaseShip getBaseShip(Player p)		{	return p == playerOne ? alpha : p == playerTwo ? beta : null; }
	public static Player getOpponent(Entity e) 			{ 	return getOpponent(e.getTeam()); 	}
	public static Player getOpponent(Player p) 			{ 	return getOpponent(p.getTeam()); 	}
	public static Player getOpponent(int team) 			{ 	return team == Values.TEAM_ONE_ID ? playerTwo : team == Values.TEAM_TWO_ID ? playerOne : null; }	
	
	public static Player getPlayerOne()					{	return playerOne;		}
	public static Player getPlayerTwo()					{	return playerTwo;		}

	public static int getGameNumber()					{	return gameNumber;		}

	/****************** Mutators ******************/
	
	public static void addDamageDealt(float amount)				{ 	 damageDealt += amount;			}
	public static void addDamageTaken(float amount)				{ 	 damageTaken += amount;			}
	
	public static void addTotalRepairRecieved(float amount)		{ 	 repairRecieved += amount;		}
	public static void addTotalShieldRecieved(float amount)		{ 	 shieldRecieved += amount;		}
	
	public static void addDodge()								{ 	 dodgeCount++;					}	
	
	public static void togglePause()							{	paused = !paused; 				}
	public static void addMissile(MissileEntity m)					{	missiles.add(m);				}
	public static void addZone(Zone z)							{	zones.add(z);				}

	public static void addUnit(objects.entity.unit.Unit u)
	{		
		units.add(u);	
		if(u.getPlayer().getTeam() == Values.TEAM_ONE_ID)
		{
			teamOneUnits.add(u);
			playerOneUnitKDTree.insert(u);
		}
		else
		{
			teamTwoUnits.add(u);
			playerTwoUnitKDTree.insert(u);
		}
	}
	
	public static ArrayList<Entity> getEntities()
	{
		ArrayList<Entity> entities = new ArrayList<Entity>();
		
		entities.addAll(Game.getUnits());
		entities.addAll(Game.getMissiles());
		entities.addAll(NodeManager.getNodes());
		
		return entities;						
	}

	
	
	public void enter(GameContainer gc, StateBasedGame sbg) {
		Settings.loadPresets();


		//AudioManager.setMusic("First Light");


		setPlayers();
		playerOne.setTeam(Values.TEAM_ONE_ID);
		playerTwo.setTeam(Values.TEAM_TWO_ID);

		gameNumber++;
		damageDealt = 0;
		damageTaken = 0;
		dodgeCount = 0;
		repairRecieved = 0;
		shieldRecieved = 0;
		gameOver = false;
		gameOverTimer = 0;
		timer = 0;

		// Initialize Arrays
		units = new ArrayList<>();
		teamOneUnits = new ArrayList<>();
		teamTwoUnits = new ArrayList<>();
		missiles = new ArrayList<>();
		zones = new ArrayList<>();

		InputManager.setup(gc, sbg);
		Camera.setup();

		AmbientManager.setup(gc.getGraphics());
		AnimationManager.setup(gc.getGraphics());

		TerritoryManager.setup();
		DisplayManager.setup(gc.getGraphics());

		playerOne.initialize();
		playerTwo.initialize();


		if (playerTwo.hasSong())
		{
			AudioManager.setMusic(playerTwo.getSong());
		}
		else
		{
			AudioManager.setRandomGameMusic();
		}



		alpha = new BaseShip(playerOne);
		beta = new BaseShip(playerTwo);

		units.add(alpha);
		teamOneUnits.add(alpha);
		units.add(beta);
		teamTwoUnits.add(beta);

		resourceKDTree = new KDTree<>();
		nodeKDTree = new KDTree<>();
		playerOneUnitKDTree = new KDTree<>();
		playerOneUnitKDTree.insert(alpha);
		playerTwoUnitKDTree = new KDTree<>();
		playerTwoUnitKDTree.insert(beta);
	}

	public void leave(GameContainer gc, StateBasedGame sbg) 
	{
		units.clear();
		teamOneUnits.clear();
		teamTwoUnits.clear();
		missiles.clear();
		
		DisplayManager.leave();
		InputManager.leave();
		TerritoryManager.leave();
		AnimationManager.leave();
		AmbientManager.leave();
		AudioManager.leave();
		
		alpha = null;
		beta = null;
		playerOne = null;
		playerTwo = null;
		paused = false;

		gameOver = false;
		gameOverTimer = 0;
		timer = 0;
		Settings.gameSpeed = 2;
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
	{
		DisplayManager.render();
	}

	public void keyPressed(int key, char c) 
	{
		InputManager.keyPressed(key, c);
	}
	
	public void keyReleased(int key, char c) 
	{
		InputManager.keyReleased(key, c);
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta)
	{
		if (!paused) 
		{
			for (int j = 0; j < Settings.gameSpeed; j++) 
			{
				// PLAYER ONE TURN
				long startTime;
				long duration;

				startTime = System.nanoTime();
				playerOne.update();
				duration = (System.nanoTime() - startTime) / 1000;
				
				playerOne.updateMissiles();	
				
				if (timer > 1)
				{
					playerOne.addLatency((int) duration);
				}

				//UPDATE TEAM ONE POSITIONS IN K-D TREE
				regenerateTree(getPlayerOne());

				// PLAYER TWO TURN
				startTime = System.nanoTime();
				playerTwo.update();
				duration = (System.nanoTime() - startTime) / 1000;
				
				playerTwo.updateMissiles();	

				if (timer > 1)
				{
					playerTwo.addLatency((int) duration);
				}

				//UPDATE TEAM TWO POSITIONS IN K-D TREE
				regenerateTree(getPlayerTwo());
				
				timer++;
				cleanUp();
		
				updateZones();
				
				TerritoryManager.update();
				if (OptimizationToggler.isOptimized())
				{
					//UPDATE RESOURCE POSITIONS IN K-D TREE
					resourceKDTree = new KDTree<>();
					for (Resource r : getAllResources()) {
						if (r.isInBounds()) {
							resourceKDTree.insert(r);
						}
					}
					//UPDATE NODE POSITIONS IN K-D TREE
					nodeKDTree = new KDTree<>();
					for (Node n : getAllNodes()) {
						if (n.isInBounds()) {
							nodeKDTree.insert(n);
						}
					}
				}

				AmbientManager.update();
				AnimationManager.update();

				AudioManager.update();
				DisplayManager.updateCanPause();

			}
		
		}
	
	
		DisplayManager.update();
		Camera.update(gc);

	}
	
	public void updateZones()
	{
		for(Zone z : zones)
		{
			z.update();
		}
	}
	
	
	


	public void cleanUp() 
	{
		for (int i = 0; i < units.size(); i++) 
		{
			objects.entity.unit.Unit a = units.get(i);
			
			if (a.isDead()) 
			{
				units.remove(i);
				
				if(a.getPlayer().getTeam() == Values.TEAM_ONE_ID)
				{
					teamOneUnits.remove(a);
				}
				else
				{
					teamTwoUnits.remove(a);
				}
//				a.die();
				i--;
			}

		}
		
		for (int i = 0; i < missiles.size(); i++) 
		{
			MissileEntity m = missiles.get(i);
			
			if (m.isDead()) 
			{
				missiles.remove(i);
				//m.die();
				i--;
			}

		}
		
		for (int i = 0; i < zones.size(); i++) 
		{
			Zone z = zones.get(i);
			
			if (z.isDone()) 
			{
				zones.remove(i);
				i--;
			}

		}
		
	
		TerritoryManager.cleanUp();
		
		if (playerOne.isDefeated() && !gameOver) 
		{
			gameOver = true;
			gameOverTimer = 0;
			Settings.gameSpeed = 1;
			playerOne.removeAllUnits();
		} 
		else if (playerTwo.isDefeated() && !gameOver) 
		{
			gameOver = true;
			gameOverTimer = 0;
			Settings.gameSpeed = 1;
			playerTwo.removeAllUnits();
		}

		if (gameOverTimer == Values.FRAMES_PER_SECOND) 
		{
			paused = true;
			gameOverTimer = 0;
		} 
		else if (gameOver && !paused) 
		{
			gameOverTimer++;
		}
	}

	public void mouseWheelMoved(int change)
	{
		Camera.zoom(change);
	}
	
	
	public void mousePressed(int button, int x, int y)
	{
		InputManager.mousePressed(button, x, y);
 	}
		
	public void mouseMoved(int oldX, int oldY, int newX, int newY) 
	{
		Camera.panCamera(newX - oldX, newY - oldY);
	}

	public void mouseDragged(int oldX, int oldY, int newX, int newY) 
	{
		Camera.panCamera(newX - oldX, newY - oldY);
	}

	// UNIT K-D TREE ACCESS
	public static KDTree<objects.entity.unit.Unit> getUnitKDTree(Player p)
	{
		return p == getPlayerOne() ? playerOneUnitKDTree : playerTwoUnitKDTree;
	}

	// RESOURCE K-D TREE ACCESS
	public static KDTree<Resource> getResourceKDTree()
	{
		return resourceKDTree;
	}

	// NODE K-D TREE ACCESS
	public static KDTree<Node> getNodeKDTree()
	{
		return nodeKDTree;
	}

	public static void regenerateTree(Player p) {
		if (p == getPlayerOne()) {
			playerOneUnitKDTree = new KDTree<>();
			for (objects.entity.unit.Unit u : getTeamOneUnits()) {
				if (u.isAlive()) playerOneUnitKDTree.insert(u);
			}
		} else {
			playerTwoUnitKDTree = new KDTree<>();
			for (objects.entity.unit.Unit u : getTeamTwoUnits()) {
				if (u.isAlive()) playerTwoUnitKDTree.insert(u);
			}
		}
	}

}
