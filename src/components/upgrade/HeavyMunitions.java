package components.upgrade;

import components.Component;

public class HeavyMunitions extends Upgrade
{
	public static final int SIZE = 2;
	public static final float DAMAGE_SCALAR = Munitions.DAMAGE_SCALAR * Component.HEAVY_POWER_SCALAR;
	public static final int MAX_RANGE_BONUS = (int) (Munitions.MAX_RANGE_BONUS * Component.HEAVY_POWER_SCALAR);
	public static final int MASS = (int) (Munitions.MASS * Component.HEAVY_MASS_SCALAR);

	public HeavyMunitions()
	{
		super();
		mass = MASS;
		size = SIZE;
		name = "Heavy Munitions";
	}
	
	public void onAddition()
	{
		super.onAddition();
		getOwner().increasePower(DAMAGE_SCALAR);
		getOwner().applyMaxRangeBonus(MAX_RANGE_BONUS);
	}

}
