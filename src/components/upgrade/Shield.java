package components.upgrade;

public class Shield extends Upgrade
{
	public static final int SIZE = 1;
	public static final int SHIELD = 300;
	public static final float SHIELD_REGEN_PER_SECOND = 6f;
	public static final float DEFENSE = .0f;
	public static final int MASS = 4;

	public Shield()
	{
		super();
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
