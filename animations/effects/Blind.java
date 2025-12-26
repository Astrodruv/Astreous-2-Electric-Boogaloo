package animations.effects;

import animations.Animation;
import engine.Utility;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class Blind extends Animation {
	Image image;

	float w;
	float h;
	int sides;
	Color outer;
	Color inner;
	float displaySize;
	float displayX;
	float displayY;

	public Blind(float x, float y, float size)
	{
		super(x, y, 25);

		this.w = size * 1.5f;
		this.h = size * 1.5f;
		this.x = x - size * .25f;
		this.y = y - size * .25f;
	}
	
	public void update()
	{
		super.update();
		
		int c = Utility.random(20, 80);
		float percentComplete = ((float) ticks) / ((float) duration);
		int alpha = (int) (percentComplete * 100) + 155;
		
		
		outer = new Color(c, c, c, alpha);
		
		displaySize = (int) (w * (1-percentComplete) * Utility.random(.5, 1.5));
		displayX = x + Utility.random(-displaySize/2, displaySize/2) - displaySize / 2;
		displayY = y + Utility.random(-displaySize/2, displaySize/2) - displaySize / 2;
	
	}

	public void render(Graphics g) 
	{	
		g.setColor(outer);
		g.fillOval(displayX, displayY, displaySize, displaySize);
	}
}