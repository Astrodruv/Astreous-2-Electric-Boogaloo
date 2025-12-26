package animations.beams;

import engine.Utility;
import objects.GameObject;
import objects.entity.Entity;
import org.newdawn.slick.Color;

public class AnimBeamConstantTransparent extends AnimBeamConstant 
{
	
	public AnimBeamConstantTransparent(Entity origin, GameObject target, int width, int duration)
	{
		super(origin, target, width, duration, 0, 0);
		
		int r = ((objects.entity.unit.Unit) origin).getColorPrimary().getRed();
		int gr = ((objects.entity.unit.Unit) origin).getColorPrimary().getGreen();
		int b = ((objects.entity.unit.Unit) origin).getColorPrimary().getBlue();
		color = new Color(r, gr, b, Utility.random(100, 150));

	}
	
	public AnimBeamConstantTransparent(Entity origin, GameObject target, int width, int duration, int minAlpha, int maxAlpha)
	{
		super(origin, target, width, duration, 0, 0);
		
		int r = ((objects.entity.unit.Unit) origin).getColorPrimary().getRed();
		int gr = ((objects.entity.unit.Unit) origin).getColorPrimary().getGreen();
		int b = ((objects.entity.unit.Unit) origin).getColorPrimary().getBlue();
		color = new Color(r, gr, b, Utility.random(minAlpha, maxAlpha));

	}
		
	public int getWidth()
	{
		return width;		
	}
	
	



}
