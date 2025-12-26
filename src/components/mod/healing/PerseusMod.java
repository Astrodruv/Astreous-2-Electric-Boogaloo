package components.mod.healing;

import components.mod.Mod;

public class PerseusMod extends Mod {
	public static int SIZE = 1;
	public static int MASS = 2;
	public static int DURATION = 1 * 60;

	public static String NAME = "Aegis Beam";

	public PerseusMod()
	{
		super();
		mass = MASS;
		name = NAME;
	}

}
