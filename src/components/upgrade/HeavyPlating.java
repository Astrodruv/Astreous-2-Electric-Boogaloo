package components.upgrade;

import components.Component;

public class HeavyPlating extends Upgrade
{
	public static final int SIZE = 2;

	public static final int PLATING = (int) (Plating.PLATING * Component.HEAVY_POWER_SCALAR);
	public static final float DEFENSE = Plating.DEFENSE * Component.HEAVY_POWER_SCALAR;
	public static final int MASS = (int) (Plating.MASS * Component.HEAVY_MASS_SCALAR);

	public HeavyPlating()
	{
		super();
		size = SIZE;
		mass = MASS;
	}
	
	public void onAddition()
	{
		super.onAddition();		
		getOwner().increaseMaxPlating(PLATING);		
		getOwner().addBlock(DEFENSE);
	}


}
