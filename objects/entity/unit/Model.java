package objects.entity.unit;

public enum Model
{	
	CRUISER, BASTION, ARTILLERY, DESTROYER, STRIKER, TRANSPORT, PROTOTYPE;

	// Cruiser

	public final static int CRUISER_MASS = 4;
	public final static int CRUISER_STRUCTURE = 100;
	public final static float CRUISER_POWER = .10f;
	public final static float CRUISER_ACCURACY = .10f;

	// Bastion
	public final static int BASTION_MASS = 4;
	public final static int BASTION_STRUCTURE = 400;

	// Destroyer
	public final static int DESTROYER_MASS = 4;
	public final static float DESTROYER_POWER = .30f;

	// Destroyer
	public final static int ARTILLERY_MASS = 4;
	public final static int ARTILLERY_RANGE = 100;

	// Transport
	public final static int TRANSPORT_MASS = 4;
	public final static int TRANSPORT_CAPACITY = 2;
	public final static int TRANSPORT_STRUCTURE = 100;

	// Striker
	public final static int STRIKER_MASS = 0;
	public final static float STRIKER_ACCURACY = .25f;

	// Striker
	public final static int PROTOTYPE_MASS = 8;
	public final static int PROTOTYPE_COMPONENTS = 1;


	public int getMass()
	{
		switch (this)
		{
			case CRUISER: 	return CRUISER_MASS;
			case BASTION: 	return BASTION_MASS;
			case ARTILLERY: return ARTILLERY_MASS;
			case STRIKER: 	return STRIKER_MASS;
			case TRANSPORT: return TRANSPORT_MASS;
			case PROTOTYPE: return PROTOTYPE_MASS;
			case DESTROYER: return DESTROYER_MASS;

			default: 		return Integer.MAX_VALUE;
		}
	}
	
	public String toString()
	{
		switch (this)
        {
            case CRUISER: 	return "Cruiser";
            case BASTION: 	return "Bastion";
            case ARTILLERY: return "Artillery";
            case STRIKER: 	return "Striker";
            case TRANSPORT: return "Transport";
			case PROTOTYPE: return "Prototype";
			case DESTROYER: return "Destroyer";
			default: 		return "?";
        }
	}

	public int getStructure()
	{
		switch(this)
		{
			case CRUISER:	return CRUISER_STRUCTURE;
			case BASTION:	return BASTION_STRUCTURE;
			case TRANSPORT:	return TRANSPORT_STRUCTURE;
			default:		return 0;
		}
	}

	public float getAccuracy()
	{
		switch(this)
		{
			case CRUISER:	return CRUISER_ACCURACY;
			case STRIKER:	return STRIKER_ACCURACY;
			default:		return 0;
		}
	}

	public float getPower()
	{
		switch(this)
		{
			case CRUISER:	return CRUISER_POWER;
			case DESTROYER:	return DESTROYER_POWER;
			default:		return 0;
		}
	}

	public int getRange()
	{
		switch(this)
		{
			case ARTILLERY:	return ARTILLERY_RANGE;
			default:		return 0;
		}
	}

	public int getComponents()
	{
		switch(this)
		{
			case PROTOTYPE: return PROTOTYPE_COMPONENTS;
			default:		return 0;
		}
	}

	public int getCapacity()
	{
		switch(this)
		{
			case TRANSPORT: return TRANSPORT_CAPACITY;
			default:		return 0;
		}
	}
	
	

}
