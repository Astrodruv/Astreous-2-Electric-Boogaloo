package objects.entity.missile;

import animations.AnimationManager;
import animations.effects.Boom;
import components.DamageType;
import objects.entity.Entity;

public class BlastCharge extends MissileEntity {

	public BlastCharge(objects.entity.unit.Unit owner, Entity target, boolean locked, int range, float damage, DamageType damageType,
					   int radius) {
		super(owner, target, locked, range, damage, damageType, radius, 1);
		// TODO Auto-generated constructor stub
	}
	
	public void addExplosionEffect()
	{
		AnimationManager.add(new Boom(getCenterX(), getCenterY(), 50 * getScale()));
	}

}
