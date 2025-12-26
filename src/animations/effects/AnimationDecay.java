package animations.effects;

import animations.Animation;
import engine.Utility;
import objects.entity.Entity;
import objects.entity.unit.Unit;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class AnimationDecay extends Animation {
	Image image;

	float w;
	float h;
	int sides;
	Color outer;
	Color inner;
	float displaySize;
	float displayX;
	float displayY;
	Entity owner;

	int r;
	int gr;
	int b;
	int c;

	public AnimationDecay(float x, float y, float size, Entity owner)
	{
		super(x, y, 50);

		this.w = size ;
		this.h = size ;
		this.x = x ;
		this.y = y ;
		this.owner = owner;

		c = Utility.random(30, 50);
		float percentGray = Utility.random(.2f, 1f);
		float percentColor = 1 - percentGray;

		r = (int) (c * percentGray + ((Unit) owner).getColorPrimary().getRedByte() * percentColor);
		gr = (int) (c * percentGray + ((Unit) owner).getColorPrimary().getGreenByte() * percentColor);
		b = (int) (c * percentGray + ((Unit) owner).getColorPrimary().getBlueByte() * percentColor);
	}
	
	public void update()
	{
		super.update();
		
//		int d = c + 50;
		float percentComplete = ((float) ticks) / ((float) duration);
		int alpha = (int) (percentComplete * 50) + 155;
		inner = new Color(c, c, c, alpha);

		if(owner instanceof Unit)
		{
			inner = new Color(r, gr, b, alpha);
		}

		// Inner color
//		inner = new Color(c, c, c, alpha);
//		if(owner instanceof Unit)
//		{
//			inner = Utility.blend(new Color(c, c, c, alpha), ((Unit)owner).getColorPrimary());
//		}

//		outer = new Color(d, d, d, alpha);
		
		displaySize = (int) (w * (1-percentComplete) * Utility.random(.75, 1.25));
		displayX = x + Utility.random(-displaySize, displaySize) - displaySize / 2;
		displayY = y + Utility.random(-displaySize, displaySize) - displaySize / 2;
	
	}

	public void render(Graphics g) 
	{	
		g.setColor(inner);
		g.fillRoundRect(displayX, displayY, displaySize, displaySize, 3);
//		g.setColor(outer);
//		g.drawRoundRect(displayX, displayY, displaySize, displaySize, 3);

	}
}