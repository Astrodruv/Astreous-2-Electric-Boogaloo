package components.weapon.energy;

import components.weapon.Weapon;

public class HeavyLaser extends EnergyWeapon
{
	public static final int SIZE = 2;
	public static final int MAX_RANGE = Laser.MAX_RANGE;
	public static final float DAMAGE = Laser.DAMAGE * Weapon.HEAVY_POWER_SCALAR;
	public static final int MASS = (int) (Laser.MASS * Weapon.HEAVY_MASS_SCALAR);

	public static final int USE_TIME = Laser.USE_TIME;
	public static final int COOLDOWN = Laser.COOLDOWN;
	public static final float ACCURACY = Laser.ACCURACY;

	public HeavyLaser()
	{
		super();
		size = SIZE;
		damage = DAMAGE;
		maxRange = MAX_RANGE;
		cooldown = COOLDOWN;
		useTime = USE_TIME;
		accuracy = ACCURACY;
		damageType = DAMAGE_TYPE;
		weaponType = WEAPON_TYPE;
		mass = MASS;
		adjective = "Heavy";
		animationWidth = 9;
		animationDuration = 15;
		soundPitch = .75f;
		useSlow = USE_SLOW_LIGHT_AVERAGE  * HEAVY_MASS_SCALAR;
	}

}
