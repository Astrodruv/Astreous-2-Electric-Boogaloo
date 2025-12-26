package objects.entity.unit;

import engine.Values;

public enum Frame 
{	
	LIGHT, MEDIUM, HEAVY, ASSAULT, COLOSSAL;

	public final static int LIGHT_MINERAL_COST = 3;
	public final static int LIGHT_MASS = 20;
	public final static int LIGHT_STRUCTURE = 300;
	public final static float LIGHT_BLOCK = .00f;
	public final static int LIGHT_IMAGE_SIZE = 24;		

	public final static int MEDIUM_MINERAL_COST = 4;
	public final static int MEDIUM_MASS = 26;
	public final static int MEDIUM_STRUCTURE = 450;
	public final static float MEDIUM_BLOCK = .10f;
	public final static int MEDIUM_IMAGE_SIZE = 36;		
	
	public final static int HEAVY_MINERAL_COST = 5;
	public final static int HEAVY_MASS = 32;
	public final static int HEAVY_STRUCTURE = 600;
	public final static float HEAVY_BLOCK = .20f;
	public final static int HEAVY_IMAGE_SIZE = 48;		
	
	public final static int ASSAULT_MINERAL_COST = 6;
	public final static int ASSAULT_MASS = 38;
	public final static int ASSAULT_STRUCTURE = 750;
	public final static float ASSAULT_BLOCK = .30f;
	public final static int ASSAULT_IMAGE_SIZE = 72;		

	public final static int COLOSSAL_MINERAL_COST = 32;
	public final static int COLOSSAL_MASS = 10;

	public final static float COLOSSAL_SPEED = 3.0f * Values.SPEED;
	public final static float COLOSSAL_ACCELERATION = .20f * Values.ACC;
	public final static int COLOSSAL_STRUCTURE = 20000;
	public final static float COLOSSAL_BLOCK = .20f;

	public final static int COLOSSAL_IMAGE_SIZE = 256;		
	
	public int getScrapAmountOnDeath()
	{
		switch (this)
		{
			case LIGHT: 	return (int) (Math.random() + .5);
			case MEDIUM: 	return (int) (Math.random() + 1);
			case HEAVY: 	return (int) (Math.random() + 1.5);
			case ASSAULT: 	return (int) (Math.random() + 2);
			default: 		return 0;
		}
	}
	
	public int getCost()
	{
		switch (this)
		{
			case LIGHT: 	return LIGHT_MINERAL_COST;
			case MEDIUM: 	return MEDIUM_MINERAL_COST;
			case HEAVY: 	return HEAVY_MINERAL_COST;
			case ASSAULT: 	return ASSAULT_MINERAL_COST;
			case COLOSSAL: 	return COLOSSAL_MINERAL_COST;
			default: 		return Integer.MAX_VALUE;
		}
	}

	public int getMass()
	{
		switch (this)
		{
			case LIGHT: 	return LIGHT_MASS;
			case MEDIUM: 	return MEDIUM_MASS;
			case HEAVY: 	return HEAVY_MASS;
			case ASSAULT: 	return ASSAULT_MASS;
			case COLOSSAL: 	return COLOSSAL_MASS;
			default: 		return Integer.MAX_VALUE;
		}
	}

	public float getBlock()
	{
		switch (this)
		{
			case LIGHT: 	return LIGHT_BLOCK;
			case MEDIUM: 	return MEDIUM_BLOCK;
			case HEAVY: 	return HEAVY_BLOCK;
			case ASSAULT: 	return ASSAULT_BLOCK;
			case COLOSSAL: 	return COLOSSAL_BLOCK;
			default: 		return Integer.MAX_VALUE;
		}
	}

	public int getComponentSlots()
	{
		switch (this)
        {
            case LIGHT: 	return 2;
            case MEDIUM: 	return 3;
            case HEAVY: 	return 4;
            case ASSAULT: 	return 5;
            case COLOSSAL: 	return 16;
            default: 		return 0;
        }
	}
	
	public int getStructure()
	{
		switch (this)
        {
            case LIGHT: 	return LIGHT_STRUCTURE;
            case MEDIUM: 	return MEDIUM_STRUCTURE;
            case HEAVY: 	return HEAVY_STRUCTURE;
            case ASSAULT: 	return ASSAULT_STRUCTURE;
            case COLOSSAL: 	return COLOSSAL_STRUCTURE;
            default: 		return Integer.MAX_VALUE;
        }
	}
	
	public int getImageSize()
	{
		switch (this)
        {
            case LIGHT: 	return LIGHT_IMAGE_SIZE;
            case MEDIUM: 	return MEDIUM_IMAGE_SIZE;
            case HEAVY: 	return HEAVY_IMAGE_SIZE;
            case ASSAULT: 	return ASSAULT_IMAGE_SIZE;
            case COLOSSAL: 	return COLOSSAL_IMAGE_SIZE;
            default: 		return 0;
        }
	}
	
	public String toString()
	{
		switch (this)
        {
            case LIGHT: 	return "Light";
            case MEDIUM: 	return "Medium";
            case HEAVY: 	return "Heavy";
            case ASSAULT: 	return "Assault";
            case COLOSSAL: 	return "Colossal";
            default: 		return "?";
        }
	}
	
	

}
