package player;

import components.Component;
import components.mod.offense.CerberusMod;
import components.weapon.utility.ElectromagneticPulse;
import engine.Settings;
import engine.Values;
import engine.states.Game;
import objects.entity.missile.MissileEntity;
import objects.entity.node.Node;
import objects.entity.node.NodeManager;
import objects.entity.unit.BaseShip;
import objects.entity.unit.Model;
import objects.entity.unit.Unit;
import objects.resource.Resource;
import objects.resource.ResourceManager;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import ui.display.DisplayManager;
import ui.display.Images;
import ui.display.message.PlayerMessage;
import ui.sound.music.Song;

import java.util.ArrayList;

public abstract class Player 
{
	public final static int MAX_FLEET_SIZE = 80;
	public final static float MIN_COLOR_DIFF = .40f;		// Swap primary / secondary if colors are too similar
	
	public final static int EXTREME_LATENCY = 1500;
	public final static int HIGH_LATENCY = 1000;
	public final static int MEDIUM_LATENCY = 500;
//	public final static int CHAT_COOLDOWN = 1 * 60;

	/******* Data *******/
	private CompositionData myComposition;
	private ArrayList<String> pastChatMessages;

	protected int timer;
	private int chatCooldown;
	private float difficultyRating = 1.0f;
	private float minerals;
	private float mineralsMined;
	private float mineralsLost;
		
	private String name;
	private String title;
	private ArrayList<PlayerMessage> messages;
	private Color colorPrimary;
	private Color colorSecondary;
	private Color colorAccent;
	private Image teamImage;
	
	private int team;
	private boolean isDefeated;		
	private float damageDealt;
	private float damageMitigated;
	private float damageTaken;
	private float repairRecieved;
	private float shieldRecieved;
	private int dodgeCount;
	private int dodgeAttempts;
	private ArrayList<Integer> latencies;

	private Song song;

	public void opening()
	{
		
	}

	/******* Constructor *******/

	public Player() 
	{
		teamImage = Images.defaultLogo;
		name = getClass().getSimpleName();
	}
	
	protected void setTitle(String title)
	{
		this.title = title;
	}
		
	public void initialize()
	{
		latencies = new ArrayList<>();
		messages = new ArrayList<>();
		myComposition = new CompositionData();
		pastChatMessages = new ArrayList<>();

		setStartingValues();
		setup();
		minimumBrightness();

		// Always swap left player, but only after second initialized
		if(this == Game.getPlayerTwo())
		{
			Game.getPlayerOne().flipColorsIfNeeded();
			Game.getPlayerOne().minimumBrightness();
		}
	}
	
	abstract public void setup();
	
	public void setTeam(int team)
	{
		this.team = team;
		
		// Default colors
		if(team == 0 && colorPrimary == null) colorPrimary = new Color(6, 180, 224);
		if(team == 0 && colorSecondary == null) colorSecondary = Color.white;
		if(team == 0 && colorAccent == null) colorAccent = Color.white;
	
		if(team == 1 && colorPrimary == null) colorPrimary = new Color(235, 45, 50);
		if(team == 1 && colorSecondary == null) colorSecondary = Color.white;
		if(team == 1 && colorAccent == null) colorAccent = Color.white;
	}
	
	protected final void setTeamImage(String filename)
	{
		try 
		{
			teamImage = new Image(filename);
		} 
		catch (SlickException e) 
		{
			e.printStackTrace();
		}
	}

	public final void setStartingValues()
	{
		isDefeated = false;

		timer = 0;
		damageDealt = 0;
		damageTaken = 0;
		damageMitigated = 0;
		dodgeCount = 0;
		dodgeAttempts = 0;
		repairRecieved = 0;
		shieldRecieved = 0;
		minerals = 0;
		mineralsMined = 0;
		mineralsLost = 0;

		if(Settings.difficultyAffectsStartingResource) {
			addMinerals(Values.STARTING_MINERALS * getDifficultyRating());
		}
		else
		{
			addMinerals(Values.STARTING_MINERALS);
		}
	}

	public abstract void draw(Graphics g);

	public void chat(String message)
	{
		if(!pastChatMessages.contains(message))
		{
			DisplayManager.chat(this, message);
//			chatCooldown = CHAT_COOLDOWN;
			pastChatMessages.add(message);
		}
	}

	public void chat(String message, int time)
	{
		if(Game.getTime() == time)
		{
			chat(message);
		}
	}

	public abstract void strategy() throws SlickException;

	/******* Accessor Methods *******/

	public final String getName() 							{	return name;								}
	public final boolean isDefeated()						{	return isDefeated;							}
	public final ArrayList<PlayerMessage> getMessages()		{	return messages;							}
	public final PlayerMessage getMessage(int index)		{	return messages.get(index);					}
	public final float getDamageDealt()						{	return damageDealt;							}
	public final float getDamageTaken()						{	return damageTaken;							}
	public final float getDamageMitigated()					{	return damageMitigated;						}

	public final int getDodgeCount()						{	return dodgeCount;							}
	public final int getDodgeAttempts()						{	return dodgeAttempts;						}
	public final float getRepairRecieved()					{	return repairRecieved;						}
	public final float getShieldRecieved()					{	return shieldRecieved;						}
	
	public final float getMinerals() 						{	return minerals;							}
	public final float getMineralsMined() 					{	return mineralsMined;						}
	public final float getMineralsLost() 					{	return mineralsLost;						}

	public final int getMaxFleetSize()						{ 	return MAX_FLEET_SIZE;						}
	public final int getPercentFleetSize()					{	return countMyUnits() / getMaxFleetSize();	} 
	
	public final Color getColorPrimary()					{	return colorPrimary;						}
	public final Color getColorSecondary()					{	return colorSecondary;						}
	public final Color getColorAccent()						{	return colorAccent;							}
		
	public final Player getOpponent() 						{	return Game.getOpponent(this);				}
	public final float getDifficultyRating()				{	return difficultyRating;					}
	public final int getTeam() 								{	return team;								}
	public final Image getTeamImage()						{ 	return teamImage;							}
	public final boolean isLeftPlayer()						{	return Game.getPlayerOne() == this;			}
	public final boolean isRightPlayer()					{	return Game.getPlayerTwo() == this;			}

	public final String getTitle()							{	return title;								}
	public final Song getSong()								{	return song;								}
	public final boolean hasSong()							{	return song != null;						}
	public CompositionData getMyComposition()	{	return myComposition;			}

	// Counting

	public final int countAllUnits()									{	return Game.getUnits().size();				}
	public final int countUnit(Player p, Class<? extends Unit> clazz) 	{	return getUnits(p, clazz).size();			}
	public final int countMyUnits()										{	return getMyUnits().size();					}
	public final int countMyUnits(Class<? extends Unit> clazz) 			{	return countUnit(this, clazz);				}
	public final int countEnemyUnits() 									{	return getEnemyUnits().size();				}
	public final int countEnemyUnits(Class<? extends Unit> clazz) 		{	return countUnit(getOpponent(), clazz);		}

	public String getDifficultyRatingModifierString()
	{
		String message = "";
		int bonus = (Math.round((getDifficultyRating() - 1) * 100));
		
		if(bonus > 0)
		{
			message += "+";
		}
				
		return message + bonus + "%";
	}

	public int getWeightedAverageLatency() 				
	{
		return getWeightedAverageLatency(Values.LATENCY_SAMPLE_FREQUENCY);
	}
	
	public int getWeightedAverageLatency(int lastNumFrames) 				
	{	
		int total = 0;
		int start = Math.max(0,  latencies.size() - lastNumFrames);	
		int count = latencies.size() - start;
		int weightedCount = 0;
		float weight = 0;
		
		for(int i = start; i < latencies.size(); i++)
		{
			weight = (float) i / (float) count;
			weightedCount += weight;
			total += latencies.get(i) * weight;
		}
		
		if(weightedCount == 0)
		{
			return 0;						
		}
		else
		{
			return total / weightedCount;
		}
				
		
	}
	
	// Getting Units

	public final ArrayList<Unit> getAllUnits()							{	return Game.getUnits();	}
	public final ArrayList<MissileEntity> getAllMissiles()					{	return Game.getMissiles();	}
	public final ArrayList<Node> getAllNodes()							{	return NodeManager.getNodes();	}
	public final ArrayList<Resource> getAllResources()					{	return ResourceManager.getResources();	}

	public final ArrayList<Unit> getUnits(Player p, Class<? extends Unit> clazz) 
	{
		ArrayList<Unit> playerUnits = p.getMyUnits();
		ArrayList<Unit> units = new ArrayList<Unit>();
				
		for(Unit u : playerUnits)
		{
			if(clazz.isInstance(u))
			{
				units.add(u);
			}
		}

		return units;
	}
	
	public final ArrayList<MissileEntity> getMissiles(Player p)
	{
		ArrayList<MissileEntity> allMissiles = getAllMissiles();
		ArrayList<MissileEntity> missiles = new ArrayList<MissileEntity>();
				
		for(MissileEntity m : allMissiles)
		{
			if(m.getOwner().getPlayer() == p)
			{
				missiles.add(m);
			}
		}

		return missiles;
	}

	public final ArrayList<Unit> getMyUnits(Class<? extends Unit> clazz) 
	{	
		return getUnits(this, clazz);
	}

	public final ArrayList<Unit> getMyUnits() 
	{	
		if(team == Values.TEAM_ONE_ID)	return Game.getTeamOneUnits();
		else							return Game.getTeamTwoUnits();
	}
	
	public final ArrayList<MissileEntity> getMyMissiles()
	{
		return getMissiles(this);
	}
	
	public final ArrayList<MissileEntity> getEnemyMissiles()
	{
		return getMissiles(getOpponent());
	}

	//	public ArrayList<Unit> getEnemyUnits(Class<? extends Unit> clazz) 
	//	{	
	//		return getUnits(getOpponent(), clazz);
	//	}

	public final ArrayList<Unit> getEnemyUnits() 
	{	
		return getOpponent().getMyUnits();
	}

	public final int getEnemyTeam() 
	{
		if(getTeam() == Values.TEAM_ONE_ID)
		{
			return Values.TEAM_TWO_ID;
		}
		else if(getTeam() == Values.TEAM_TWO_ID)
		{
			return Values.TEAM_ONE_ID;
		}
		else
		{
			return -1;
		}
	}
	
	public final int getFleetValueUnit()
	{
		int total = 0;
		ArrayList<Unit> units = getMyUnits();
		
		for(Unit u : units)
		{
			if(!(u instanceof BaseShip))
			{
				total += u.getValue();
			}
		}
		
		return total;
	}
		
	
	public final int getFleetValueUnit(Class<? extends Unit> clazz)
	{
		int total = 0;
		ArrayList<Unit> units = getMyUnits();
		
		for(Unit u : units)
		{
			if(clazz.isInstance(u))
			{
				total += u.getValue();
			}
		}
		
		return total;
	}

	public final int getFleetValueDebuffer()
	{
		int total = 0;
		ArrayList<Unit> units = getMyUnits();

		ArrayList<Class<? extends Component>> debuffs = new ArrayList<>();
		debuffs.add(CerberusMod.class);
		debuffs.add(ElectromagneticPulse.class);

		for(Unit u : units)
		{
			for(Class<? extends Component> c : debuffs)
			{
				if(u.hasComponent(c))
				{
					total += u.getValue();
				}
			}
		}

		return total;
	}

	public final int getFleetValueComponent(Class<? extends Component> clazz)
	{
		int total = 0;
		ArrayList<Unit> units = getMyUnits();

		for(Unit u : units)
		{
			if(u.hasComponent(clazz))
			{
				total += u.getValue();
			}
		}

		return total;
	}

	
	public final float getFleetValueUnitPercentage(Class<? extends Unit> clazz)
	{
		if(getFleetValueUnit() == 0)
		{
			return 0;
		}
		else
		{
			return (float) getFleetValueUnit(clazz) / (float) getFleetValueUnit();
		}
	}

	public final float getFleetValueComponentPercentage(Class<? extends Component> clazz)
	{
		if(getFleetValueUnit() == 0)
		{
			return 0;
		}
		else
		{
			return (float) getFleetValueComponent(clazz) / (float) getFleetValueUnit();
		}
	}



	/************** MUTATORS *************/
	
	protected final void setName(String n) 						{	name = n;										}
	public final void loseGame()								{	isDefeated = true;								}	



	public final void addMessage(PlayerMessage pm)				{	if(pm != null ) messages.add(pm);				}
	public final void addMessage(String s)						{	if(s != null) messages.add(new PlayerMessage(s));				}
	public final void addMessage(String s, Color c)				{	if(s != null) messages.add(new PlayerMessage(s, c));			}

	public final void setSong(Song song)						{   this.song = song;}
	public final void setColorPrimary(int r, int g, int b)		{	setColorPrimary(new Color(r, g, b));			}
	public final void setColorSecondary(int r, int g, int b)	{	setColorSecondary(new Color(r, g, b));			}
	public final void setColorAccent(int r, int g, int b)		{	setColorAccent(new Color(r, g, b));				}
	public final void setColorPrimary(Color c)					{	colorPrimary = c;								}
	public final void setColorSecondary(Color c)				{	colorSecondary = c;								}
	public final void setColorAccent(Color c)					{	colorAccent = c;								}

	public final void addDamageDealt(float amount)				{ 	 damageDealt += amount;					}
	public final void addDamageTaken(float amount)				{ 	 damageTaken += amount;					}

	public final void addDamageMitigated(float amount)			{ 	 damageMitigated += amount;					}

	public final void addRepairRecieved(float amount)			{ 	 repairRecieved += amount;					}
	public final void addShieldRecieved(float amount)			{ 	 shieldRecieved += amount;					}
	public final void addDodge()								{ 	 dodgeCount++;							}
	public final void addDodgeAttempt()							{ 	 dodgeAttempts++;						}

	public void addLatency(int latency)							{ 	latencies.add(latency);			}
	
	public final void update()
	{
		if(Game.getTime() == 0)
		{
			opening();
		}		
		
//		if(this  == Game.getPlayerOne())
//		{
//			System.out.println("Weighted Latency " + getWeightedAverageLatency());
//
//		}
		
//		applyLatencyPenalty();


		// Update units
		ArrayList<Unit> units = getMyUnits();
		for (Unit u : units)
		{
			if (u.isAlive())
			{
				u.update();
			}
		}

		timer++;
		try
		{
			strategy();
		}
		catch(SlickException e)
		{
			e.printStackTrace();
		}
		


		// Activate units
		for (Unit u : units)
		{
			if (u.isAlive())
			{
				u.action();
			}
		}


		
		// Update missiles

//		System.out.println(Game.getTime() + " " + getName() + " " + getAverageLatency(30));
		
	}
	
	public final void updateMissiles()
	{
		ArrayList<MissileEntity> missiles = getMyMissiles();
		for (MissileEntity m : missiles)
		{
			if (m.isAlive()) 
			{
				m.update();
			}
		}

	}
	
	public final void removeAllUnits()
	{
		ArrayList<Unit> units = getMyUnits();
		for (Unit u : units) 
		{
			u.die();
		}
	}
	/** Upgrades **/

	public final void setDifficultyRating(int percentage)
	{
		float difficultyRating = ((float) percentage) * .01f;
		if(difficultyRating >= 0.0f)
		{
			this.difficultyRating = difficultyRating;
		}
	}

	public final BaseShip getMyBase() 
	{
		return Game.getBaseShip(this);
	}

	public final BaseShip getEnemyBase() 
	{
		return Game.getBaseShip(getOpponent());
	}	

	public final void addMinerals(float amount) 
	{
		minerals += amount;
		mineralsMined += amount;
	}

	public final void subtractMinerals(float amount) 
	{
		minerals -= amount;
		mineralsLost += amount;
	}	
	
	public final void render(Graphics g)
	{
		messages.clear();
		g.setColor(Color.red);

		if(Game.getTime() <= 1)
		{
			return;
		}

		if ((Settings.showPlayerOneInfo && this == Game.getPlayerOne()) ||
				(Settings.showPlayerTwoInfo && this == Game.getPlayerTwo()))
		{

			draw(g);
			
			for (Unit u : getMyUnits()) 
			{
				u.draw(g);
			}
			
	
		}

//		System.out.println(myComposition);


	}
	
	// Building Units


	private Unit unitFactory(Class <? extends Unit> clazz)
	{
		Unit u = null;
		try
		{
			u = clazz.getDeclaredConstructor().newInstance();
			u.setPlayer(this);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return u;
	}


	public final void buildUnit(Class <? extends Unit> clazz)
	{
		Unit u = unitFactory(clazz);
		buildUnit(u);
	}

	public final void buildUnit(Unit u)
	{
		u.setPlayer(this);
		u.design();

		// If the user does not assign a model, it is a cruiser by default
		u.setModel(Model.CRUISER);

		if(minerals >= u.getValue() && countMyUnits() < MAX_FLEET_SIZE)
		{

			Game.addUnit(u);
			minerals -= u.getValue();
			u.applyDifficultyToHealth();
			u.buildTriggers();

		}
	}


	
	// Colors

	public final void minimumBrightness()
	{		
		if(!Settings.minimumTeamBrightness)
		{
			return;
		} 
		
		final int MIN_BRIGHT = 150;
		
		int r = getColorPrimary().getRed();
		int g = getColorPrimary().getGreen();
		int b = getColorPrimary().getBlue();
		
		if(r > g && r > b)
		{
			if(r < MIN_BRIGHT)
			{
				r = MIN_BRIGHT;
			}
		}
		else if(g > b)
		{
			if(g < MIN_BRIGHT)
			{
				g = MIN_BRIGHT;
			}
		}
		else
		{
			if(b < MIN_BRIGHT)
			{
				b = MIN_BRIGHT;
			}
		}
		
		setColorPrimary(r, g, b);
	}
	
	
	public final void flipColorsIfNeeded()
	{		
		int r1 = getColorPrimary().getRed();
		int g1 = getColorPrimary().getGreen();
		int b1 = getColorPrimary().getBlue(); 

		int r2 = getOpponent().getColorPrimary().getRed();
		int g2 = getOpponent().getColorPrimary().getGreen();
		int b2 = getOpponent().getColorPrimary().getBlue();  

		float minDiff = MIN_COLOR_DIFF;

		float rDiff = (float) Math.abs(r1 - r2) / 255f;
		float gDiff = (float) Math.abs(g1 - g2) / 255f;
		float bDiff = (float) Math.abs(b1 - b2) / 255f;


		if(rDiff < minDiff && gDiff < minDiff && bDiff < minDiff)
		{
			this.addMessage("Colors too similar.  Changing to alternate.");
			flipColors();
		}
	}

	public final void flipColors()
	{
		Color tmp = getColorPrimary();
		setColorPrimary(getColorSecondary());
		setColorSecondary(tmp);
	}
	
//	public final void applyLatencyPenalty()
//	{
//		if(Settings.penalizeLatency)
//		{
//			if(Game.getTime() < Values.LATENCY_GRACE_PERIOD)
//			{
//				return;
//			}
//
//			if(getWeightedAverageLatency() > Player.EXTREME_LATENCY)
//			{
//				subtractMinerals(1.0f/60.0f);
//			}
//		}
//	}




}
