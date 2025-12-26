package ui.display.message;

import engine.Settings;
import engine.Utility;
import objects.GameObject;
import objects.entity.node.Node;
import objects.entity.unit.Unit;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import ui.display.Fonts;

public class FloatText extends Message
{
	public final static int DEFAULT_DURATION = 100;

	private final GameObject owner;
	private float x;
	private float y;
	private Font myFont;
	private final float ySpeed;
	private final float xSpeed;

	private int value;

	public FloatText(GameObject u, String message)
	{
		this(u, message, DEFAULT_DURATION);
	}

	public FloatText(GameObject u, String message, int duration)
	{
		this(u, message, duration, Color.white);
	}

	public FloatText(GameObject u, String message, Color color)
	{
		this(u, message, DEFAULT_DURATION, color);
	}

	public FloatText(GameObject u, String message, int duration , Color color)
	{
		super(message, duration, color);
		owner = u;
		myFont = Fonts.tinyFont;
		x = owner.getX() + Utility.random(owner.getWidth());
		float lowestY = owner.getY() - id * myFont.getHeight("a") - myFont.getHeight("a") * 2;
		y = lowestY - Utility.random(20);
		ySpeed = Utility.random(-1.4, -2);
		xSpeed = Utility.random(-.2, .2);
	}

	public FloatText(GameObject u, int message)
	{
		this(u, message, Color.white);
	}

	public FloatText(GameObject u, int message, Color color)
	{
		this(u, ""+message, color);
		value = message;
		setFontBasedOnValue();
	}

	private void setFontBasedOnValue()
	{
		if(value < 10)
		{
			myFont = Fonts.tinyFont;
		}
		else if(value <= 25)
		{
			myFont = Fonts.smallFont;
		}
		else
		{
			myFont = Fonts.mediumFont;
		}
	}

	public void update()
	{
		super.update();

		x = x + xSpeed;
		y = y + ySpeed;
	}
	
	public void render(Graphics g)
	{
		// Do not show messages if they are hidden
		if(!Settings.showFloatTextUnit && owner instanceof Unit)
		{
			return;
		}
		if(!Settings.showFloatTextNode && owner instanceof Node)
		{
			return;
		}

		g.setFont(myFont);
		g.setColor(Utility.modifyAlpha(color,percentComplete()));
		Utility.drawStringCenterTop(g, myFont, message, x, y);
	}
	

}
