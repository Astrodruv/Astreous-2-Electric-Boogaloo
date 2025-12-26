package components.mod.offense;

import components.mod.Mod;

public class EosMod extends Mod {
	public static int SIZE = 1;
	public static int MASS = 2;

	public static final float LASER_ACCURACY_BONUS = .50f;
	public static String NAME = "Dawnlance";

	public EosMod()
	{
		super();
		mass = MASS;
		name = NAME;
	}

}
