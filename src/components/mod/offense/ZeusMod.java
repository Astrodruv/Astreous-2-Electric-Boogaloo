package components.mod.offense;

import components.mod.Mod;

public class ZeusMod extends Mod {
	public static int SIZE = 1;
	public static int MASS = 2;

	public static final float ACC_SCALAR = .50f;

	public static final int RANGE_BONUS = 300;
//	public static final int STUN_DURATION = 2 * 60;

	public static String NAME = "Lightning Strike";

	public ZeusMod()
	{
		super();
		mass = MASS;
		name = NAME;
	}

}
