package components.mod.offense;

import components.mod.Mod;

public class CerberusMod extends Mod {
	public static int SIZE = 1;
	public static int MASS = 2;
	public static final float DAMAGE_TOTAL = 35;
	public static final int DURATION = 8 * 60;
	public static String NAME = "Inferno Launcher";

	public CerberusMod()
	{
		super();
		mass = MASS;
		name = NAME;
	}

}
