package components.mod.offense;

import components.mod.Mod;

public class AresMod extends Mod {
	public static int SIZE = 1;
	public static int MASS = 2;

	public static final float BLOCK_PENETRATION = .75f;
	public static final float RANGE_BONUS = 100;

	public static String NAME = "Warcannon";

	public AresMod()
	{
		super();
		mass = MASS;
		name = NAME;
	}

}
