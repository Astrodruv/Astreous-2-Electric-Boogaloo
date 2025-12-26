package components.upgrade;

public class Plating extends Upgrade
{
	public static final int SIZE = 1;
	public static final int PLATING = 350;
	public static final float DEFENSE = .08f;
	public static final int MASS = 8;

	public Plating()
	{
		super();
		mass = MASS;
	}
	
	public void onAddition()
	{
		super.onAddition();		
		getOwner().increaseMaxPlating(PLATING);		
		getOwner().addBlock(DEFENSE);
	}


}
