package components.upgrade;

import components.Component;

public class HeavyShield extends Upgrade
{
	public static final int SIZE = 2;
	public static final int SHIELD = (int) (Shield.SHIELD * Component.HEAVY_POWER_SCALAR);
	public static final float SHIELD_REGEN_PER_SECOND = Shield.SHIELD_REGEN_PER_SECOND * Component.HEAVY_POWER_SCALAR;
	public static final float DEFENSE = Shield.DEFENSE * Component.HEAVY_POWER_SCALAR;
	public static final int MASS = (int) (Shield.MASS * Component.HEAVY_MASS_SCALAR);

	public HeavyShield()
	{
		super();
		size = SIZE;
		mass = MASS;
	}
	
	public void onAddition()
	{
		super.onAddition();
		getOwner().increaseMaxShield((SHIELD));
		getOwner().increaseShieldRegen(SHIELD_REGEN_PER_SECOND);
		getOwner().addBlock(DEFENSE);

	}
	


}
