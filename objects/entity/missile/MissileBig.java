package objects.entity.missile;

import components.DamageType;
import objects.entity.Entity;

public class MissileBig extends MissileEntity
{

	public MissileBig(objects.entity.unit.Unit owner, Entity target, boolean locked, int range, float damage, DamageType damageType, int radius)
	{
		super(owner, target, locked, range, damage, damageType, radius, 1);
		image = image.getScaledCopy(1.5f);
		imageSecondary = imageSecondary.getScaledCopy(1.5f);
		imageMove = imageMove.getScaledCopy(1.5f);
		smokeSize = 16;

	}
		
}
