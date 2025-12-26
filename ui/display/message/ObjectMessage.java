package ui.display.message;

import engine.Settings;
import engine.Utility;
import engine.states.Game;
import objects.GameObject;
import objects.entity.unit.Unit;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import ui.display.Fonts;

public class ObjectMessage extends Message
{
	private final GameObject owner;
	private final Font myFont;

	public ObjectMessage(GameObject u, String message)
	{
		this(u, message, 1);
	}
	
	public ObjectMessage(GameObject u, String message, int duration)
	{
		this(u, message, duration, Color.gray);
	}
	
	public ObjectMessage(GameObject u, String message, int duration , Color color)
	{
		super(message, duration, color);
		owner = u;
		myFont = Fonts.mediumFont;

	}

	public void render(Graphics g)
	{
		// Do not show messages if they are hidden
		if(owner instanceof Unit)
		{
			if(!Settings.showPlayerOneInfo && ((Unit)owner).getPlayer() == Game.getPlayerOne())
			{
				return;
			}
			if(!Settings.showPlayerTwoInfo && ((Unit)owner).getPlayer() == Game.getPlayerTwo())
			{
				return;
			}
		}

		g.setFont(myFont);
		
		if(color != null)
		{
			g.setColor(color);
		}
		else
		{
			g.setColor(Color.gray);
		}

		float x = owner.getCenterX();
		float y = owner.getY() - id * myFont.getHeight("a") - myFont.getHeight("a") * 2;
		Utility.drawStringCenterTop(g, myFont, message, x, y);
	}
	

}
