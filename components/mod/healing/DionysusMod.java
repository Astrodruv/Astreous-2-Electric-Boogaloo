package components.mod.healing;

import components.mod.Mod;

public class DionysusMod extends Mod
{
	public static int SIZE = 1;
	public static int MASS = 2;
	public static int DURATION = 6 * 60;

	public static String NAME = "Revelry Beam";

	public DionysusMod()
	{
		super();
		mass = MASS;
		name = NAME;
	}

}
