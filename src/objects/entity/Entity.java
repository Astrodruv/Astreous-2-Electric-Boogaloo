package objects.entity;

import components.DamageType;
import conditions.Condition;
import conditions.ConditionSet;
import engine.Settings;
import engine.Values;
import engine.states.Game;
import objects.Attribute;
import objects.GameObject;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Point;
import player.Player;

import java.util.ArrayList;

public class Entity extends GameObject
{
	public static final int SHIELD_RECOVERY_TIME_BASE = 480;

	protected int team = 0;
	// Protected Data
	private final Attribute structure;
	private final Attribute plating;
	private final Attribute shield;
	private float hullRepairEfficiency = 1;
	private int shieldRecoveryTimer = 0;
	private int framesSinceLastHit = Integer.MAX_VALUE;
	private boolean alive = true;
	private final boolean wasHit = false;
	private Player player;
	
//	private boolean hasNotActed = true;

	private boolean selected = false;
	
	private float block = 0f;
	private float power = 1f;

	private final ConditionSet conditions;

	public Entity(float x, float y)
	{
		this(x, y, Values.NEUTRAL_ID);
	}
	
	public Entity(float x, float y, int team)
	{
		super(x, y);
		this.team = team;
		shield = new Attribute();
		structure = new Attribute();
		plating = new Attribute();
		conditions = new ConditionSet(this);
	}
	
	/**************** Accessors ******************/

	public boolean canMove() 				{	return super.canMove() && !conditions.stopsMovement() && !conditions.locksPosition();	}
	public final boolean canAct() 				{	return !conditions.stopsAction();						}
//	public float getAverageSize()			{	return image != null ? (image.getWidth()  + image.getHeight()) / 2 : -1;}

	public final boolean isSelected()				{	return selected;		}
	
	public final ConditionSet getConditions()		{	return conditions;	}
	public final float getCurEffectiveHealth()		{	return getCurStructure() + getCurShield() + getCurPlating(); }
	public final float getMaxEffectiveHealth()		{	return getMaxStructure() + getMaxShield() + getMaxPlating(); }
	public final float getPercentEffectiveHealth()	{	return getCurEffectiveHealth() / getMaxEffectiveHealth(); }

	public final boolean isInvulnerable()			{	return conditions.preventsDamage();	}

	// Plating
	public final boolean hasPlating() 				{	return plating.getMaximum() > 0;	}
	public final float getCurPlating()				{	return plating.getCurrent();		}
	public final float getMaxPlating() 				{	return plating.getMaximum();		}
	public final boolean hasMaxPlating()			{	return plating.isMaximum();			}
	public final float getPercentPlating() 			{	return plating.getPercent();		}

	// Shield	
	public final boolean hasShield() 				{	return shield.getMaximum() > 0;		}
	public final float getCurShield() 				{	return shield.getCurrent();			}
	public final float getMaxShield() 				{	return shield.getMaximum();			}
	public final boolean hasMaxShield()				{	return shield.isMaximum();			}
	public final int getShieldRecoveryTimeLeft()	{	return shieldRecoveryTimer;			}
	public final float getPercentShield() 			{	return shield.getPercent();			}
	public final float getShieldRegen() 			{	return shield.getRegeneration();	}
	public final boolean isShieldDamaged()			{	return shield.getPercent() < 1;		}
	public final boolean isShieldRegenerating()		{	return shieldRecoveryTimer == 0;	}
	public final boolean canShieldRecover()			{	return !conditions.preventsShieldRecovery();	}
	public final boolean canBeRepaired()			{	return !conditions.preventsRepair();	}
	
	// Structure
	public final float getCurStructure() 			{	return structure.getCurrent();			}
	public final float getMaxStructure() 			{	return structure.getMaximum();			}
	public final boolean hasMaxStructure()			{	return structure.isMaximum();			}
	public final float getPercentStructure() 		{	return structure.getPercent();			}
	public final float getHullRepair()				{	return structure.getRegeneration();		}
	public final float getHullRepairEfficiency()	{	return hullRepairEfficiency + conditions.getRepair() - 1;			}

	public final int getTeam() 						{	return team;						}
	public Player getPlayer() 						{	return player;						}

	public final Player getOpponent()				{	return Game.getOpponent(this);		}
	public final int getHitTimer() 					{	return framesSinceLastHit;			}
	public final int getTimeAlive()					{	return timer;						}
	public final boolean wasHit() 					{	return wasHit;						}

	// Life and Damage
	public final boolean isDead() 					{	return !isAlive();												}
	public final boolean isAlive() 					{	return alive;													}
	public final boolean isHullDamaged() 			{	return !isHullUndamaged();										}
	public final boolean isHullUndamaged() 			{	return hasMaxStructure() && hasMaxPlating();					}
	public final boolean isDamaged() 				{	return !isUndamaged();											}
	public final boolean isUndamaged()				{	return hasMaxStructure() && hasMaxShield() && hasMaxPlating();	}
	
	public final float getBlock()					{ return block + conditions.getBlock();			}
	public final float getPower()					{ return power + conditions.getPower() - 1;			}



	public final boolean hasCondition(Class<? extends Condition> clazz)
	{
		return conditions.containsType(clazz);
	}
	
	
	public final Condition getCondition(Class<? extends Condition> clazz)
	{
		return hasCondition(clazz) ? conditions.get(clazz) : null;
		
	}
	
	
	public final boolean isShieldRecovered()		
	{ 
		return shieldRecoveryTimer == 0;	
	} 
	
	/**************** Mutators ******************/
	
//	public void actionComplete() 			{	hasNotActed = false;					}
	
	public void setPlayer(Player p)
	{	player = p;
		this.team = p.getTeam();
	}
	public void addCondition(Condition c)
	{
		conditions.add(c);
	}
	
	public final void removeCondition(Condition c)
	{
		conditions.remove(c);
	}
	
	public final void removeAllConditions(Class<? extends Condition> clazz)
	{
		conditions.removeAll(clazz);
	}
	
	public void update()
	{
		super.update();
		
		if(framesSinceLastHit < Integer.MAX_VALUE)
		{
			framesSinceLastHit++;
		}
	
		// If my shields aren't blocked, begin to recover
		if(shieldRecoveryTimer > 0 && canShieldRecover())
		{
			shieldRecoveryTimer--;
		}
		
		conditions.update();
		
		if(timer % 60 == 0)
		{
			if(isShieldRecovered())
			{
				shield.update();
			}
			if(canBeRepaired())
			{
				repairHull(structure.getRegeneration(), false);
			}
		}


		if(conditions.locksPosition())
		{
			xSpeed = 0;
			ySpeed = 0;
		}

		if(conditions.stopsMovement())
		{
			moveComplete();
		}
		else
		{
			updateSpeed();
		}
	
	}
	
	protected void updateSpeed()
	{

		if(getConditions().modifiesSpeed())
		{
			setSpeedCurrent(getConditions().getModifiedSpeed(getMaxSpeedBase()));
			setAccelerationCurrent(getConditions().getModifiedAcceleration(getAccelerationBase()));
		}
	}
	
	public void render(Graphics g)
	{	
		super.render(g);	
	}
	
	public final void select()
	{
		selected = true;
	}

	public final void unselect()
	{
		selected = false;
	}
	
	public final void setStructure(int amount)
	{
		structure.set(amount);
	}
	
	public final void increaseMaxStructure(int amount)
	{
		structure.increaseMaximum(amount);
		structure.increaseCurrent(amount);
	}

	public void increaseHullRepair(float amount)
	{
		structure.increaseRegeneration(amount);
	}

	public void increaseHullRepairEfficiency(float amount)
	{
		hullRepairEfficiency += amount;
	}

	public final void setShield(int amount)
	{
		shield.set(amount);
	}
	
	public final void increaseMaxShield(int amount)
	{
		shield.increaseMaximum(amount);
		shield.increaseCurrent(amount);
	}

	public final void increaseShieldRegen(float amount)
	{
		shield.increaseRegeneration(amount);
	}

	public final void setPlating(int amount)
	{
		plating.set(amount);
	}
	
	public final void increaseMaxPlating(int amount)
	{
		plating.increaseMaximum(amount);
		plating.increaseCurrent(amount);
	}
	
	public final void addBlock(float amount)
	{
		block += amount;
	}
	
	public final void increasePower(float amount)
	{
		power += amount;
	}
	
	public void repairHull(float amount, boolean restoresShields)
	{
		if(amount == 0)
		{
			return;
		}
		
		// Cannot repair, abort (typically from a condition)
		if(!this.canBeRepaired())
		{
			return;
		}

		amount *= getHullRepairEfficiency();

		if(Settings.showFloatTextUnit)
		{
			floatText(Math.round(amount), Color.green);
		}

		float starting = getCurStructure() + getCurPlating() + getCurShield();
		
		float overflow = structure.increaseCurrent(amount);
		overflow = plating.increaseCurrent(overflow);

		if(restoresShields)
		{
			this.regainShield(overflow);
		}

		float ending = getCurStructure() + getCurPlating() + getCurShield();
		float totalRecovery = starting - ending;
		
		if(this instanceof objects.entity.unit.Unit)
		{
			this.getPlayer().addRepairRecieved(totalRecovery);
			this.getOpponent().addRepairRecieved(totalRecovery);
			Game.addTotalRepairRecieved(totalRecovery);
			Game.addTotalRepairRecieved(totalRecovery);
		}
		
	}

	public final void regainShield(float amount) 
	{
		// Cannot regain shield if recovery is disabled
		if(!canShieldRecover())
		{
			return;
		}
		float starting = getCurShield();

		shield.increaseCurrent(amount);
		float ending = getCurShield();
		float totalRecovery = starting - ending;
		
		if(this instanceof objects.entity.unit.Unit)
		{
			this.getPlayer().addShieldRecieved(totalRecovery);
			this.getOpponent().addShieldRecieved(totalRecovery);
			Game.addTotalShieldRecieved(totalRecovery);
			Game.addTotalShieldRecieved(totalRecovery);
		}
		
	}
	
	
	
	public void onDamageTaken(float amount, DamageType type)
	{
		
	}

	public void takeDamage(float amount, DamageType type)
	{
		takeDamage(amount, type, 0);
	}

	public void takeDamage(float amount, DamageType type, float blockPenetration)
	{
		// Apply base damage resistance *and* effects from conditions
		// Pierce ignores beneficial damage reduction
		
		// Pierce ignores half of the target's damage reduction
//		if(type.isType(DamageType.PIERCE) && getDamageTakenMultiplier() < 1)
//		{
//			float newDamageReduction = (1 - getDamageTakenMultiplier()) / 2;
//			amount *= 1 - newDamageReduction;
//		}
//		else
//		{


			float actualAmountBlocked = (1-blockPenetration) * getBlock();
			float damageBlocked = amount * actualAmountBlocked;
			amount -= damageBlocked;



			if(getPlayer() != null) {
				getPlayer().addDamageMitigated(damageBlocked);
			}
		//}
	
//		
//		if(getDamageTakenMultiplier() > 1)
//		{
//			amount *= getDamageTakenMultiplier();
//		}
		
		// Alert the player to the damage taken after block effects
		onDamageTaken(amount, type);

		if(Math.round(amount) >= 1)
		{
			floatText(Math.round(amount), type.getColor());
		}


		framesSinceLastHit = 0;	
		
		float starting = getCurEffectiveHealth();
		shieldRecoveryTimer = SHIELD_RECOVERY_TIME_BASE;
		
		// Start with Shield Layer
		amount = loseShield(amount, type);

		if(amount > 0)
		{
			// Leftover to Plating Layer
			amount = losePlating(amount, type);

			if(amount > 0)
			{
				// Last to Structure Layer
				loseStructure(amount, type);
			}
		}
		
		float ending = getCurEffectiveHealth();
		float totalDamage = starting - ending;
		
		if(this instanceof objects.entity.unit.Unit)
		{
			this.getPlayer().addDamageTaken(totalDamage);
			this.getOpponent().addDamageDealt(totalDamage);
			Game.addDamageTaken(totalDamage);
			Game.addDamageDealt(totalDamage);
		}

	}

	// Damage Structure
	public final void loseStructure(float amount, DamageType type) 
	{	
		// If it can't damage structure, end.
		if(type.cannotDamageStructure())
		{
			return;
		}

		structure.decreaseCurrent(amount);

		if (getCurStructure() == 0)
		{
			die();
		}		 
	}

	// Damage Shield and return the amount of extra damage
	public final float loseShield(float amount, DamageType type) 
	{
		// If it can't damage shields, end with all damage leftover
		if(type.cannotDamageShields())
		{
			return amount;
		}

		float breakthroughDamage = shield.decreaseCurrent(amount);

		// If we have enough shield
		if(breakthroughDamage == 0)
		{
			return 0;
		}

		// Breakthrough damage
		else
		{
			shield.setCurrent(0);

			return breakthroughDamage;
		}
	}


	// Damage Plating and return the amount of extra damage
	// Plating takes FULL damage now (0.9.2)
	public final float losePlating(float amount, DamageType type) 
	{
		// If it can't damage plating, end with all damage leftover
		if(type.cannotDamagePlating())
		{
			return amount;
		}

		float breakthroughDamage = plating.decreaseCurrent(amount);

		// If we have enough plating
		if(breakthroughDamage <= 0)
		{
			return 0;
		}

		// Breakthrough damage
		else
		{
			return breakthroughDamage;
		}
	}
	
	public void die()
	{
		image = null;
		alive = false;
		structure.setCurrent(0);
		plating.setCurrent(0);
		shield.setCurrent(0);
	}
	
	public static final Entity getNearestEntity(Point point, Class<? extends Entity> clazz)
	{
		float nearestDistance = Float.MAX_VALUE;
		Entity nearestEntity = null;
		ArrayList<Entity> entities = Game.getEntities();
		
	


		for(Entity e : entities)
		{
			if(clazz.isInstance(e) && e.getDistance(point) < nearestDistance)
			{
				nearestEntity = e;
				nearestDistance = e.getDistance(point);
			}
		}

		return nearestEntity;
	}
	
	
	
}
