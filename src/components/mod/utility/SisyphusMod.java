package components.mod.utility;

import components.mod.Mod;

public class SisyphusMod extends Mod
{
	public static int SIZE = 1;
	public static int MASS = 2;

	public static final float COOLDOWN_REDUCTION = .40f;

	public static String NAME_RIFT = "Rift Repeater";
	public static String NAME_EMP = "EMP Repeater";

	public SisyphusMod()
	{
		super();
		mass = MASS;
		name = "Repeater";
	}

}
