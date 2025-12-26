package components.mod.offense;

import components.mod.Mod;

public class KratosMod extends Mod {
	public static int SIZE = 1;
	public static int MASS = 2;

	public static final int STOP_TIME = 1;
	public static final int SHOT_AMOUNT = 2;
//	public static final float DAMAGE_SCALAR = 3;

	public static final float ACCURACY_PENALTY = .25f;


	public static String NAME = "Impact Cannon";

	public KratosMod()
	{
		super();
		mass = MASS;
		name = NAME;
	}

}
