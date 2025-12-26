package animations.effects;

import animations.Animation;
import objects.entity.Entity;
import org.newdawn.slick.Graphics;
import ui.display.Images;

public class AnimationOmen extends Animation
{
	//Image image;

	public AnimationOmen(Entity e)
	{
		super(e.getX() + e.getSize() / 2, e.getY() - e.getSize(), 1);
	}
	
	public void update()
	{
		super.update();
	}

	public void render(Graphics g) 
	{
		g.drawImage(Images.omen, x - 8, y + 4 );


//		g.setColor(new Color(50, 50, 50));
//		g.fillOval(x - 11, y - 10, 24, 24);
//
//		g.setColor(new Color(150, 150, 150));
//		Utility.drawStringCenterCenter(g, Fonts.bigFont, "O", x, y);

//		g.setColor(outer);
//		g.fillOval(displayX, displayY, displaySize, displaySize);
	}
}