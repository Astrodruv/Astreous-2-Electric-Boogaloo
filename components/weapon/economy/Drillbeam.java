package components.weapon.economy;

import animations.AnimationManager;
import animations.beams.AnimBeamConstant;
import animations.effects.Smoke;
import animations.effects.Sparks;
import components.DamageType;
import components.weapon.WeaponTargetEntity;
import components.weapon.WeaponType;
import conditions.instant.Damage;
import engine.Settings;
import engine.Utility;
import engine.states.Game;
import objects.entity.Entity;
import objects.entity.node.Asteroid;
import objects.entity.node.Derelict;
import objects.entity.node.Node;
import ui.display.DisplayManager;
import ui.sound.Sounds;

public class Drillbeam extends WeaponTargetEntity
{
	public static final int SIZE = 2;
	public static final int MAX_RANGE = 250;

	public static final float DAMAGE = 1f;
	public static final int USE_TIME = 1;
	public static final int COOLDOWN = 3;
	public static final WeaponType WEAPON_TYPE = WeaponType.RESOURCE;
	public static final DamageType DAMAGE_TYPE = DamageType.TRUE;
	public static final int MASS = 12;
	public static final float ACCURACY = 1.00f;

	public static final int ANIM_BEAM_WIDTH = 4;
	public static final int ANIM_BEAM_DURATION = USE_TIME+COOLDOWN;
	
	private float animTargetXOffset;
	private float animTargetYOffset;
	private boolean resetOffset;
	
	public Drillbeam()
	{
		super();
		size = SIZE;
		damage = DAMAGE;
		maxRange = MAX_RANGE;
		cooldown = COOLDOWN;
		useTime = USE_TIME;
		accuracy = ACCURACY;
		mass = MASS;
		weaponType = WEAPON_TYPE;
		damageType = DAMAGE_TYPE;
		name = "Drillbeam";
		useSlow = USE_SLOW_LIGHT_MAJOR;

	}

	@Override
	public void applyMod() {

	}

	public void update()
	{
		super.update();

		resetOffset = Game.getTime() % Utility.random(80, 120) == 0;
		
		animTargetXOffset += Utility.random(-1f, 1f);
		animTargetYOffset += Utility.random(-1f, 1f);
	}
	
	protected void playAudio()
	{
		if(Math.random() < .01)
		{
			Sounds.mining.play(getOwner().getPosition(), .6f, .8f);

		}
	}
	
	protected void animation(Entity target)
	{
		if(resetOffset)
		{
			setOffset(target);
		}
		
		if(Settings.showAnimations && Math.random() < .1)
		{
			if(target instanceof Asteroid)
			{
				AnimationManager.add(new Smoke(target.getCenterX() + animTargetXOffset, target.getCenterY() + animTargetYOffset, 24));
			}
			else if(target instanceof Derelict)
			{
				for(int i = 0; i < 3; i++)
				{
					AnimationManager.add(new Sparks(target.getCenterX() + animTargetXOffset, target.getCenterY() + animTargetYOffset, 12, 25));
				}

			}
		}
		
		if(target instanceof Node)
		{
			AnimationManager.add(new AnimBeamConstant(getOwner(), target, ANIM_BEAM_WIDTH, ANIM_BEAM_DURATION, animTargetXOffset, animTargetYOffset));		
		}
		else if(target instanceof objects.entity.unit.Unit)
		{
			AnimationManager.add(new AnimBeamConstant(getOwner(), target, ANIM_BEAM_WIDTH, ANIM_BEAM_DURATION, animTargetXOffset/4, animTargetYOffset/4));		
		}
		
	}
	
	protected void activation(Entity target)
	{
		if(target.getGameNumber() != Game.getGameNumber())
		{
			DisplayManager.addMessage("Error: " + this + " is trying to target an entity from a previous game.", 800);
			DisplayManager.addMessage("If you have copied the nodes array, make sure to clear it every game.", 800);
		}
		if(target instanceof objects.entity.unit.Unit)
		{
			if(((objects.entity.unit.Unit) target).rollToHit(this))
			{
				target.addCondition(new Damage(getDamage(), getDamageType()));
			}
		}
		else
		{
			target.addCondition(new Damage(getDamage(), getDamageType()));
		}
	}
	
	
	protected void setOffset(Entity target)
	{
		float variance = target.getSize()/4;		
		animTargetXOffset = Utility.random(-variance,  variance);
		animTargetYOffset = Utility.random(-variance,  variance);
	}

	

	


}
