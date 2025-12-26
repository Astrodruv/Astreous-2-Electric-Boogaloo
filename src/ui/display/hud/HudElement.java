package ui.display.hud;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class HudElement 
{
	protected float x;
	protected float y;
	protected float w;
	protected float h;
	
	protected final Color COLOR_BORDER = new Color(0, 0, 0);
	protected final Color COLOR_BACKGROUND = new Color(15, 15, 15);
	
	public HudElement(float x, float y, float w, float h)
	{
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public void render(Graphics g)
	{
		int edge = 3;
		
		g.setLineWidth(edge);
		
		g.setColor(COLOR_BACKGROUND);
		g.fillRect(x,  y, w, h);




		
		g.resetLineWidth();
	}

}
