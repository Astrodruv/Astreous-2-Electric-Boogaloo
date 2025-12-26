package objects.entity.missile;

import animations.AnimationManager;
import animations.effects.BoomInferno;
import components.DamageType;
import conditions.debuffs.Blind;
import conditions.debuffs.Burning;
import engine.Utility;
import objects.entity.Entity;
import org.newdawn.slick.Color;

public class MissileDark extends MissileEntity
{
	int duration;

	public MissileDark(objects.entity.unit.Unit owner, Entity target, boolean locked, int range, float damage, DamageType damageType, int radius, int duration)
	{
		super(owner, target, locked, range, damage, damageType, radius, 1);
		this.duration = duration;
	}

	public void addExplosionEffect()
	{
		AnimationManager.add(new BoomInferno(getCenterX(), getCenterY(), radius * EXPLOSION_IMAGE_SCALING * getScale()));
	}

	public void directHit(objects.entity.unit.Unit u, float damage, DamageType damageType)
	{
		super.directHit(u,  damage,  damageType);
		u.addCondition(new Blind(duration));
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
			u.addCondition(new Blind((int) (duration * percentAway)));
		}
	}

	public Color getColorSecondary()
	{
		return new Color(150,50, 200);
	}

}
