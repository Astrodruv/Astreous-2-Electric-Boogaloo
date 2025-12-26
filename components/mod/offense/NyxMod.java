package components.mod.offense;

import components.mod.Mod;

public class NyxMod extends Mod
{
	public static int SIZE = 1;
	public static int MASS = 2;

	public static final int DURATION = 12 * 60;
	public static String NAME = "Dark Missile";

	public NyxMod()
	{
		super();
		mass = MASS;
		name = NAME;
	}

}
