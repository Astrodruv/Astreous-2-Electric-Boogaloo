package components.upgrade;

public class Munitions extends Upgrade
{
	public static final int SIZE = 1;
	public static final float DAMAGE_SCALAR = .25f;
	public static final int MAX_RANGE_BONUS = 80;
	public static final int MASS = 6;

	public Munitions()
	{
		super();
		mass = MASS;
	}
	
	public void onAddition()
	{
		super.onAddition();
		getOwner().increasePower(DAMAGE_SCALAR);
		getOwner().applyMaxRangeBonus(MAX_RANGE_BONUS);
	}

}
