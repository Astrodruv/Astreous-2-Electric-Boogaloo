package objects.entity.missile;


import animations.AnimationManager;
import animations.effects.Boom;
import animations.effects.Smoke;
import components.DamageType;
import engine.Utility;
import engine.Values;
import engine.states.Game;
import objects.entity.Entity;
import objects.entity.unit.Unit;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Point;
import player.Player;
import ui.display.Images;
import ui.sound.Sounds;

import java.util.ArrayList;

public class MissileEntity extends Entity
{
	public static float TRIGGER_DISTANCE = 15;
	protected static float EXPLOSION_IMAGE_SCALING = .25f;
	
	protected int smokeSize = 12;
	private final objects.entity.unit.Unit owner;
	private final Entity target;
	private Point missPoint;
	private final boolean locked;
	private final float damage;
	private final DamageType damageType;
	private final float zAxisMissAcc;

	protected Image imageSecondary;
	protected Image imageMove;
	protected int radius;
	private final int duration;
	private int time;

	public MissileEntity(Unit owner, Entity target, boolean locked, int range, float damage, DamageType damageType, int radius, float speedScalar)
	{
		super(owner.getCenterX(), owner.getCenterY(), owner.getTeam());
		this.owner = owner;
		this.target = target;
		this.locked = locked;
		image = Images.missile.getSprite(0,0);
		imageSecondary = Images.missile.getSprite(0,1);
		imageMove = Images.missile.getSprite(0, 2);
		
		setSpeed(65 * Values.SPEED * speedScalar);
		setAcceleration(320 * Values.ACC * speedScalar);
		zAxisMissAcc = Utility.random(.001, .003);
		if(locked)
		{
			this.damage = damage;
		}
		else
		{
			this.damage = damage / 2;		// only deals half damage on a miss
		}

		this.damageType = damageType;
		this.radius = radius;

//		if(wasHit())
//		{
			this.duration = (int) (range / getMaxSpeed() * 1.40);
//		}
//		else
//		{
//			this.duration = (int) (range / getMaxSpeed() * 1.10);
//		}




		this.setStructure((int)(damage/10f));


		float missAmount = owner.getDistance(target) ;

		while(missPoint == null || target.getDistance(missPoint) > missAmount)
		{
			float x = target.getCenterX() + Utility.random(-missAmount, missAmount);
			float y = target.getCenterY() + Utility.random(-missAmount, missAmount);
				
			missPoint = new Point(x, y);
		}
			
		turnTo(missPoint);


	}

	public int getTimeLeft()
	{
		return duration - time;
	}

	public void update()
	{
		super.update();
		time++;

		if(!locked)
		{
			scale = scale - zAxisMissAcc;

			if(scale <= .35)
			{
				die();
			}
		}

		if(locked && time > 40 && target != null && target.isAlive())
		{
			moveTo(target);	
		}
		else 
		{
			move();
		}
		
		Unit u = getNearestEnemy();
			
		// Explode on trigger of being near a unit
		if(u != null && getDistance(u) < TRIGGER_DISTANCE * getScale())
		{
			dealAreaDamage();
			die();
			return;
		}

		// Explode after too much time has passed
		if(time == duration)
		{
			dealAreaDamage();
			die();
			return;
		}
		
		
		if(Game.getTime() % 4 == 0)
		{
			makeSmoke();
		}
		
	}
	
	public void makeSmoke()
	{
		float x = getCenterX();
		float y = getCenterY();
		AnimationManager.add(new Smoke(x, y, 10 * getScale()));
	}
	
	public void dealAreaDamage()
	{
		ArrayList<objects.entity.unit.Unit> radiusEnemies = getEnemiesInRadius(radius);
		
		for(objects.entity.unit.Unit u : radiusEnemies)
		{
			// Always deal full damage to the target
			if(u == target && locked)
			{
//				System.out.println("Direct");
//				System.out.println(damage);
				directHit(u, damage, damageType);
			}
			
			else
			{
//				System.out.println("Splash");
				splashDamage(u, damage , damageType);
			}
		}
	}
	
	public void directHit(objects.entity.unit.Unit u, float damage, DamageType damageType)
	{
		//System.out.println("Hit the target!  Dealing " + damage + "damage");
		u.takeDamage(damage, damageType);
	}
	
	public void splashDamage(objects.entity.unit.Unit u, float damage, DamageType damageType)
	{
		// Splash damage is up to 100% damage, based on distance from the explosion and target's dodge chance (evasion)

		float distanceAway = Utility.distance(this,  u);

		if(distanceAway > radius)
		{
			return;
		}

		float percentAway = distanceAway / radius;
		float actualDamage = damage * (1-percentAway) * getScale();

		if(Math.random() > u.getDodgeChance())
		{
			u.takeDamage(actualDamage, damageType);
		}

	}
	
	public void die()
	{
		super.die();		
		addExplosionEffect();
		Sounds.boom.play(getPosition(), .8f, .5f);
	}
		
	public void addExplosionEffect()
	{
		AnimationManager.add(new Boom(getCenterX(), getCenterY(), radius * EXPLOSION_IMAGE_SCALING * getScale()));
	}
	
	public objects.entity.unit.Unit getOwner()
	{
		return owner;
	}
	
	public Player getPlayer()
	{
		return getOwner().getPlayer();
	}
	
	public void render(Graphics g)
	{
		super.render(g);
		renderPrimary(g);
		renderSecondary(g);
		renderMove(g);
	}
	
	public Color getColorPrimary()
	{
		return getOwner().getColorPrimary();
	}
	
	public Color getColorSecondary()
	{
		 return getOwner().getColorSecondary();
	}
	

	
	public void renderPrimary(Graphics g)
	{
		if (image != null) 
		{
			Image tmp = image.getScaledCopy(getScale());
			tmp.setCenterOfRotation(tmp.getWidth() / 2 * getScale(), tmp.getHeight() / 2 * getScale());
			tmp.setRotation(getTheta());
			tmp.draw(x, y, getColorPrimary());
		}
	}
	
	public void renderSecondary(Graphics g)
	{
		if (imageSecondary != null) 
		{
			Image tmp = imageSecondary.getScaledCopy(getScale());
			tmp.setCenterOfRotation(tmp.getWidth() / 2 * getScale(), tmp.getHeight() / 2 * getScale());
			tmp.setRotation(getTheta());
			tmp.draw(x, y, getColorSecondary());
		}
	}
	
	public void renderMove(Graphics g)
	{
		if (imageMove != null) 
		{
			Image tmp = imageMove.getScaledCopy(getScale());
			tmp.setCenterOfRotation(tmp.getWidth() / 2 * getScale(), tmp.getHeight() / 2 * getScale());
			tmp.setRotation(getTheta());
			tmp.draw(x, y);
		}
	}

	public objects.entity.unit.Unit getNearestEnemy()
	{
		float nearestDistance = Float.MAX_VALUE;
		objects.entity.unit.Unit nearestUnit = null;
		ArrayList<objects.entity.unit.Unit> units =  Game.getUnits();

		for(objects.entity.unit.Unit u : units)
		{
			if(u.getPlayer() != getPlayer() && getDistance(u) < nearestDistance)
			{
				nearestUnit = u;
				nearestDistance = getDistance(u);
			}
		}

		return nearestUnit;
	}
	
	
	public ArrayList<objects.entity.unit.Unit> getEnemiesInRadius(float radius)
	{
		ArrayList<objects.entity.unit.Unit> units = Game.getUnits();
		ArrayList<objects.entity.unit.Unit> radiusEnemies = new ArrayList<objects.entity.unit.Unit>();

		if (units != null) 
		{
			for (objects.entity.unit.Unit e : units)
			{
				if (e.getPlayer() != getPlayer() && getDistance(e) <= radius) 
				{
					radiusEnemies.add(e);
				}
			}
		}

		return radiusEnemies;
	}

	


}
