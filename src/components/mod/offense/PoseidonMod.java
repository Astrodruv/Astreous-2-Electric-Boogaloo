package components.mod.offense;

import components.mod.Mod;

public class PoseidonMod extends Mod {
	public static int SIZE = 1;
	public static int MASS = 2;

	public static final float LASER_PUSH_EFFECT = 25f;
	public static final int LASER_PUSH_DURATION = 15;
	public static final float DAMAGE_FROM_SHIELD_SCALAR = .04f;

	public static String NAME = "Tidal Blast";

	public PoseidonMod()
	{
		super();
		mass = MASS;
		name = NAME;
	}

}
