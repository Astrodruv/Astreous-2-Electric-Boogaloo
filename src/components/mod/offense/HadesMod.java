package components.mod.offense;

import components.mod.Mod;

public class HadesMod extends Mod {
	public static int SIZE = 1;
	public static int MASS = 2;

	public static final int DECAY_TIME = 24*60;
	public static String NAME_KINETIC = "Stygian Shot";
	public static String NAME_ENERGY = "Stygian Beam";

	public HadesMod()
	{
		super();
		mass = MASS;
		name = NAME_KINETIC;
	}

}
