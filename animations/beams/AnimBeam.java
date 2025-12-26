package animations.beams;

import animations.Animation;
import engine.Utility;
import objects.GameObject;
import objects.entity.Entity;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import ui.display.Camera;

public abstract class AnimBeam extends Animation 
{
	protected Entity origin;
	protected GameObject target;
	protected int team;
	protected int width;
	protected Color color;
	
	private final float variance;
	protected float xOffset;
	protected float yOffset;
	
	public AnimBeam(Entity origin, GameObject target, int width, int duration)
	{
		super(origin.getX(), origin.getY(), duration);

		this.origin = origin;
		this.target = target;
		this.width = width;
		
		variance = target.getSize()/3;
		xOffset = Utility.random(-variance,  variance);
		yOffset = Utility.random(-variance,  variance);
	}
	
	public AnimBeam(Entity origin, Entity target, int width, int duration, Color color)
	{
		super(origin.getX(), origin.getY(), duration);
		this.origin = origin;
		this.target = target;
		this.width = width;
		this.color = color;
		
		variance = target.getSize()/3;
		xOffset = Utility.random(-variance,  variance);
		yOffset = Utility.random(-variance,  variance);
	}


	public Color getColor()
	{
		if(color != null)
		{
			return color;
		}
		else if(origin instanceof objects.entity.unit.Unit)
		{
			return ((objects.entity.unit.Unit) origin).getColorPrimary();
		}	
		else
		{
			return null;
		}
	}

	abstract int getWidth();

	public void render(Graphics g)
	{
		if (ticks > duration)
		{
			return;
		}
		
		// Draw background color
		
		g.setLineWidth(getWidth() * Camera.getZoom());
		g.setColor(getColor().darker());
		g.drawLine(origin.getCenterX(), origin.getCenterY(), target.getCenterX() + xOffset, target.getCenterY() + yOffset);

		// Draw brighter core
	
		g.setLineWidth(getWidth() * Camera.getZoom() / 2);
		g.setColor(getColor().brighter());
		g.drawLine(origin.getCenterX(), origin.getCenterY(), target.getCenterX() + xOffset, target.getCenterY() + yOffset);
				
		g.resetLineWidth();
	}

}
