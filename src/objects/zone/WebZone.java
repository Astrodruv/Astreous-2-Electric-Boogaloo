package objects.zone;

import animations.AnimationManager;
import animations.circles.AnimCircle;
import animations.effects.EnergyWebParticle;
import animations.effects.Sparks;
import components.DamageType;
import conditions.buffs.Fast;
import conditions.debuffs.Stop;
import conditions.debuffs.slow.WebSlow;
import conditions.instant.Damage;
import engine.Utility;
import objects.entity.Entity;
import objects.entity.unit.BaseShip;
import objects.entity.unit.Frame;
import objects.entity.unit.Unit;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Point;
import player.Player;

public class WebZone extends Zone
{

float damage;
float slow;


	public WebZone(float x, float y, int duration, float slow, float damage, Player player, float radius)
	{
		super(x, y, duration, player);
		this.radius = radius;
		this.damage = damage;
		this.slow = slow;

		targetsEnemies = true;
		
	//	AnimationManager.add(new AnimCircle(this, radius*2, duration, player.getColorPrimary().brighter()));

	}
	
	public void applyEffect(Entity u)
	{
		
		if(!(u instanceof BaseShip) && !u.hasCondition(Fast.class))
		{
			//u.addCondition(new Stop(1, 1));
			u.addCondition(new WebSlow(1, slow));

		}


	//	u.addCondition(new Damage(damage, DamageType.SHIELDS_ONLY));

	}
	
	
	public void render(Graphics g)
	{
		g.setColor(Utility.modifyAlpha(getPlayer().getColorPrimary(), 12));
			
		g.fillOval(x - radius, y - radius, 	radius*2, radius*2);


	}
	
	public void update() 
	{
		super.update();

		for(int i = 0; i < 10; i++)
		{
			Point p1 = getRandomPointInWeb();
			Point p2 = getRandomPointInWeb();
			AnimationManager.add(new EnergyWebParticle(p1, p2, 2, getPlayer().getColorPrimary()));
		}

	}

	public Point getRandomPointInWeb()
	{
		float x = Utility.random(this.getX()-radius, this.getX() + radius);
		float y = Utility.random(this.getY()-radius, this.getY() + radius);

		// Retry if it's not above radius
		if(Utility.distance(x, y, this.getX(), this.getY()) > radius)
		{
			return getRandomPointInWeb();
		}

		return new Point(x, y);

	}
	
	
}
