package objects.zone;

import engine.states.Game;
import objects.GameObject;
import objects.entity.Entity;
import player.Player;

import java.util.ArrayList;

abstract public class Zone extends GameObject
{
	private Player player;
	protected float duration;
	protected float radius;
	protected int time;
	
	protected boolean targetsEnemies;
	protected boolean targetsAllies;
	protected boolean targetsAll;
	
	private ArrayList<Entity> allEntities;
	private ArrayList<Entity> alliedEntities;
	private ArrayList<Entity> enemyEntities;

	
	public Zone(float x, float y) 
	{
		super(x, y);
		duration = Integer.MAX_VALUE;
		init();
	}
	
	public Zone(float x, float y, int duration) 
	{
		super(x, y);
		this.duration = duration;
		init();
	}
	
	public Zone(float x, float y, int duration, Player player) 
	{
		super(x, y);
		this.duration = duration;
		this.player = player;
		init();
	}
	
	private void init()
	{
		allEntities = new ArrayList<Entity>();
		alliedEntities = new ArrayList<Entity>();
		enemyEntities = new ArrayList<Entity>();
	}
	
	public boolean isDone()
	{
		return time == duration;
	}

	public void update()
	{
		super.update();
		updateUnits();
		applyEffects();
		time++;
	}

	public float getRadius()
	{
		return radius;
	}

	public float getPercentDistance(Entity u)
	{
		return getDistance(u) / radius;
	}
	
	public Player getPlayer()
	{
		return player;
	}
	
	private void updateUnits()
	{

		allEntities.clear();

		allEntities.addAll(Game.getUnits());
		allEntities.addAll(Game.getMissiles());

		alliedEntities.clear();
		enemyEntities.clear();
		
		if(player != null)
			
		for(Entity u : allEntities)
		{
			if(u.getPlayer() == getPlayer())
			{
				alliedEntities.add(u);
			}
			else
			{
				enemyEntities.add(u);
			}
		}
	}
	
	private void applyEffects()
	{
		if(targetsAll)
		{
			applyEffectAll();
		}
		else if(targetsEnemies)
		{
			applyEffectEnemies();
		}
		else if(targetsAllies)
		{
			applyEffectAllies();
		}
	}
	
	private void applyEffectAll()
	{
		for(Entity u : allEntities)
		{
			if(getDistance(u) <= radius)
			{
				applyEffect(u);
			}
		}
	}
	
	
	private void applyEffectEnemies()
	{
		for(Entity u : enemyEntities)
		{
			if(getDistance(u) <= radius)
			{
				applyEffect(u);
			}
		}
	}
	
	
	private void applyEffectAllies()
	{
		for(Entity u : alliedEntities)
		{
			if(getDistance(u) <= radius)
			{
				applyEffect(u);
			}
		}
	}
	
	protected void applyEffect(Entity u)
	{
		
	}
	
}
