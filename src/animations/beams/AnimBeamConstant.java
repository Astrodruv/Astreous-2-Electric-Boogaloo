package animations.beams;

import objects.GameObject;
import objects.entity.Entity;
import org.newdawn.slick.Color;

public class AnimBeamConstant extends AnimBeam 
{
	
	public AnimBeamConstant(Entity origin, GameObject target, int width, int duration, float xOff, float yOff)
	{
		super(origin, target, width, duration);
		xOffset = xOff;
		yOffset = yOff;
	}
	
	
	public Color getColor(int r, int g, int b)
	{
		return new Color(r, g, b, 150);
	}
	
	public int getWidth()
	{
		return width;		
	}



}
