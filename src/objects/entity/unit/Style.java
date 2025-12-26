package objects.entity.unit;

public enum Style 
{	
	ARROW, BUBBLE, DAGGER, ROCKET, BOXY, ORB, WEDGE, PINCER, DRAGON, SHARK, CANNON, CRESCENT;

	public int getID()
	{
		switch(this)
		{
			case ARROW: 	return 0;
			case BUBBLE: 	return 1;
			case DAGGER:	return 2;
			case ROCKET:	return 3;
			case BOXY:		return 4;
			case ORB:		return 5;		
			case WEDGE:		return 6;
			case PINCER:	return 7;
			case DRAGON: 	return 8;
			case SHARK: 	return 9;
			case CANNON:	return 10;
			case CRESCENT:	return 11;

			default:		return 0;
		}
	}
}
