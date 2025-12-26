package objects.entity.missile;

import animations.AnimationManager;
import animations.effects.BoomInferno;
import components.DamageType;
import conditions.debuffs.Burning;
import engine.Utility;
import objects.entity.Entity;
import org.newdawn.slick.Color;

public class MissileInferno extends MissileEntity
{
	int burnDuration;
	float burnDamageOverDuration;
	
	public MissileInferno(objects.entity.unit.Unit owner, Entity target, boolean locked, int range, float damage, DamageType damageType, int radius, int burnDuration, float burnDamageOverDuration)
	{
		super(owner, target, locked, range, damage, damageType, radius, 1);
		this.burnDuration = burnDuration;
		this.burnDamageOverDuration = burnDamageOverDuration;
	}

	public void addExplosionEffect()
	{
		AnimationManager.add(new BoomInferno(getCenterX(), getCenterY(), radius * EXPLOSION_IMAGE_SCALING * getScale()));
	}
	
	public void directHit(objects.entity.unit.Unit u, float damage, DamageType damageType)
	{
		super.directHit(u,  damage,  damageType);
		u.addCondition(new Burning(burnDuration, burnDamageOverDuration));
	}

	public void splashDamage(objects.entity.unit.Unit u, float damage, DamageType damageType)
	{
		super.splashDamage(u, damage, damageType);

  		float distanceAway = Utility.distance(this,  u);

		if(distanceAway > radius)
		{
			return;
		}

		float percentAway = distanceAway / radius;

		if(Math.random() > u.getDodgeChance())
		{
			u.addCondition(new Burning((int) (burnDuration * percentAway), (int) (burnDamageOverDuration * percentAway)));
		}
	}

	public Color getColorSecondary()
	{
		 return Color.red;
	}

}
