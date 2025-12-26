package components;

import engine.Values;
import org.newdawn.slick.Color;

public enum DamageType 
{
	ENERGY,
	KINETIC,
	EXPLOSIVE,
	TRUE,
	PULSE,		// Ignores other defenses and only damages shields
	FIRE,		// Ignores other defenses and only damages structure
	NONE;

	public boolean cannotDamageShields()
	{
		return this == FIRE;
	}
	
	public boolean cannotDamagePlating()
	{
		return this == FIRE || this == PULSE;
	}
	
	public boolean cannotDamageStructure()
	{
		return this == PULSE;
	}
	
	public Color getColor()
	{
		if(this == ENERGY)
		{
			return Color.white;
		}
		else if(this == FIRE)
		{
			return Values.COLOR_FIRE;
		}
		else if(this == PULSE)
		{
			return Values.COLOR_PULSE;
		}
		else if(this == KINETIC)
		{
			return Color.white;
		}
		else if(this == EXPLOSIVE)
		{
			return Color.white;
		}
		else
		{
			return Color.white;
		}
	}
}
