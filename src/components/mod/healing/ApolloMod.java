package components.mod.healing;

import components.mod.Mod;

public class ApolloMod extends Mod {
	public static int SIZE = 1;
	public static int MASS = 2;

	public static int DURATION = 6 * 60;

	public static String NAME = "Solar Beam";

	public ApolloMod()
	{
		super();
		mass = MASS;
		name = NAME;
	}

}
