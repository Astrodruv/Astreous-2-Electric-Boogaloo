package animations.effects;

import animations.Animation;
import engine.Utility;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Point;

public class EnergyWebParticle extends Animation {

	Color color;

	Point one;
	Point two;

	public EnergyWebParticle(Point a, Point b, int duration, Color c)
	{
		super(a.getX(), a.getY(), duration);

		one = a;
		two = b;
		color = c.brighter();
		color = new Color(color.getRedByte(), color.getGreenByte(), color.getBlueByte(), 60);
	}
	
//	public int getAlpha()
//	{
//		return (int) (255 * percentLeft());
//	}

	
	public void update()
	{
		super.update();
//
//		//System.out.println(this.ticks);
//
//		//float percentComplete = ((float) ticks) / ((float) duration);
//		displaySize = w;//(int) (w * percentComplete * Utility.random(.5, 1.5));
//		displayX = x + Utility.random(-displaySize/2, displaySize/2) - displaySize / 2;
//		displayY = y + Utility.random(-displaySize/2, displaySize/2) - displaySize / 2;

	}

	public void render(Graphics g) 
	{
	//	Utility.modifyAlpha(color, getAlpha());
		g.setColor(color);
		g.resetLineWidth();
		g.drawLine(one.getX(), one.getY(), two.getX(), two.getY());
	}
}