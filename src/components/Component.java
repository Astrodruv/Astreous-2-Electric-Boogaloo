package components;

import objects.entity.unit.Unit;
import org.newdawn.slick.Graphics;


public abstract class Component 
{
	public static float HEAVY_POWER_SCALAR = 2.5f;
	public static float HEAVY_MASS_SCALAR = 3.5f;

	private Unit owner;
	protected String name;
	protected int size;
	protected int mass;

	public Component()
	{
		name = getClass().getSimpleName();
		size = 1;
	}

	public int getMass()							{	return mass;	}

	public Unit getOwner()
	{
		return owner;
	}

	// package level for security
	void setOwner(Unit u)
	{
		this.owner = u;
	}

	// Called when the component is created
	public void onAddition()
	{

	}
	
	// Called every frame
	public void update() 
	{
		
	}
	
	public void render(Graphics g)
	{
		
	}

	public int getSize()
	{
		return size;
	}

	//public abstract int getSize();
	
	public String getName()
	{
		return name;
	}

	public String toString()
	{
		return getName();
	}

}
