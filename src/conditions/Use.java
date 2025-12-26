package conditions;

public class Use extends Condition
{
	public Use(float movementPenalty)
	{
		super(Integer.MAX_VALUE, 0);
		mobility = 1 - movementPenalty;
	}

}
