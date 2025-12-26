package ui.display.healthbar;

import engine.Settings;
import objects.entity.Entity;
import org.newdawn.slick.Graphics;

public class NodeHealthbar extends Healthbar
{
	public NodeHealthbar(Entity e)
	{
		super(e);
	}

	public boolean showingHealthbar()
	{
		if(!Settings.showNodeHealthbars || owner.isDead())
		{
			return false;
		}
		else return !Settings.smartNodeHealthbars || !owner.isUndamaged();
	}	
	
	public void render(Graphics g)
	{
		super.render(g);
	}
	
	public float getHeight()
	{
		return owner.getHeight() / 18 + 5;
	}
	
	public float getY()
	{
		return owner.getY() - h;
	}
}
