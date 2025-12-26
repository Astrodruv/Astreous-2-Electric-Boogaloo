package components.mod.offense;

import components.mod.Mod;

public class AchillesMod extends Mod {
	public static int SIZE = 1;
	public static int MASS = 2;

	public static final float HEALTH_THRESHOLD = 150;

	public static String NAME = "Final Shot";

	public AchillesMod()
	{
		super();
		mass = MASS;
		name = NAME;
	}

}
