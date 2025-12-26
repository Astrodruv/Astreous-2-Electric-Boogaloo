package animations.effects;

import animations.Animation;
import objects.entity.Entity;
import org.newdawn.slick.Graphics;
import ui.display.Images;

public class AnimationInstantDeath extends Animation
{

	public AnimationInstantDeath(Entity e)
	{
		super(e.getX() + e.getSize() / 2, e.getY() + e.getSize() / 2, 60);
	}
	
	public void update()
	{
		super.update();
	}

	public void render(Graphics g) 
	{
		g.drawImage(Images.finisher, x - 16, y - 16);
	}
}