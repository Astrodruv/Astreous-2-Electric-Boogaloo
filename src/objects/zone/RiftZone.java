package objects.zone;

import animations.AnimationManager;
import animations.circles.AnimCircle;
import components.mod.offense.PoseidonMod;
import conditions.buffs.Fast;
import conditions.debuffs.Pull;
import conditions.debuffs.Push;
import engine.Utility;
import objects.entity.Entity;
import objects.entity.unit.BaseShip;
import objects.entity.unit.Frame;
import objects.entity.unit.Unit;
import org.newdawn.slick.Graphics;
import player.Player;

public class RiftZone extends Zone 
{
	final int MAX_ALPHA = 15;
	
	float pullAmount;
	float[] percents;
	
	int alpha;

	public RiftZone(float x, float y, int duration, Player player, float radius, float pullAmount) 
	{
		super(x, y, duration, player);
		this.radius = radius;
		this.pullAmount = pullAmount;
		
		targetsEnemies = true;
		
		AnimationManager.add(new AnimCircle(this, radius*2, duration, player.getColorPrimary().brighter()));
	
		percents = new float[12];

		for(int i = 0; i < percents.length; i++)
		{
			percents[i] = i * 1.0f / percents.length;
		//	System.out.println(percents[i]);
		}
		
	}
	
	public void applyEffect(Entity u)
	{
		
		if(!(u instanceof BaseShip) && !u.hasCondition(Fast.class))
		{
			float distanceScalar = 1 - getPercentDistance(u);
//			float weightScalar = 1 - (u.getMass() / 100f);
//			u.dbgMessage(pullAmount + " / " + distanceScalar + " / " + weightScalar);
//			u.changeSpeedForcedMovement(pullAmount * distanceScalar * weightScalar, u.getAngleToward(getCenterX(), getCenterY()));

			u.addCondition(new Pull(1, 0, pullAmount * distanceScalar,this));

		}
	}
	
	
	public void render(Graphics g)
	{
		alpha = MAX_ALPHA;
		

		//	g.setLineWidth(7);
		//	g.drawOval(x - radius, y - radius, radius*2, radius*2);
		
			g.setColor(Utility.modifyAlpha(getPlayer().getColorPrimary(), alpha));
			
			for(int i = 0; i < percents.length; i++)
			{
				g.fillOval(x - radius * percents[i], y - radius * percents[i], 
						radius*2*percents[i], radius*2*percents[i]);
			}
		

	}
	
	public void update() 
	{
		super.update();
		
		for(int i = 0; i < percents.length; i++)
		{
			percents[i] -= .006;
			
			if(percents[i] < 0)
			{
				percents[i] = 1;
			}
		}
	}
	
	
}
