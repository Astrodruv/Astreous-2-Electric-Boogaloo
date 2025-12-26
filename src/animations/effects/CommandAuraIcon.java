package animations.effects;

import animations.Animation;
import engine.Utility;
import objects.entity.Entity;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import ui.display.Fonts;

public class CommandAuraIcon extends Animation
{
	//Image image;

	public CommandAuraIcon(Entity e)
	{
		super(e.getX() + e.getSize() / 2, e.getY() - e.getSize(), 1);
	}
	
	public void update()
	{
		super.update();
	}

	public void render(Graphics g) 
	{	
		g.setColor(new Color(20, 20, 20, 100));
		g.fillRoundRect(x - 11, y - 10, 24, 24, 5);

		g.setColor(new Color(150, 150, 0));
		Utility.drawStringCenterCenter(g, Fonts.bigFont, "^", x, y);

//		g.setColor(outer);
//		g.fillOval(displayX, displayY, displaySize, displaySize);
	}
}