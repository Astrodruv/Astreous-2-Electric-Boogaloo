package components.weapon.energy;

public class Laser extends EnergyWeapon
{
	public static final int SIZE = 1;
	public static final int MAX_RANGE = 600;
	public static final int DAMAGE = 35;
	public static final int USE_TIME = 90;
	public static final int COOLDOWN = 120;
	public static final float ACCURACY = .50f;
	public static final int MASS = 5;

	public Laser()
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
		adjective = "";
		animationWidth = 5;
		animationDuration = 15;
		soundPitch = 1.50f;
		useSlow = USE_SLOW_LIGHT_AVERAGE;
	}
	

}
