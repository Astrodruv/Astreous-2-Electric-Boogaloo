package components.mod.offense;

import components.mod.Mod;

public class ArtemisMod extends Mod {
	public static int SIZE = 1;
	public static int MASS = 2;

	public static final int MISSLE_RANGE_BONUS = 200;
	public static final float MISSILE_ACCURACY_BONUS = .25f;
	public static String NAME = "Artemissile";

	public ArtemisMod()
	{
		super();
		mass = MASS;
		name = NAME;

	}

}
