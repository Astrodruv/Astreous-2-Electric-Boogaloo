package teams.foes.farmer;

import components.weapon.Weapon;
import objects.entity.unit.Unit;
import org.newdawn.slick.Graphics;
import player.Player;

public abstract class FarmerUnit extends Unit
{	
	public FarmerUnit(Player p)
	{
		super(p);
	}
	
	public void action() 
	{
		attack(getWeaponOne());
		attack(getWeaponTwo());
		movement();
	}
		
	public void attack(Weapon w)
	{
		Unit enemy = getNearestEnemy();

		if(enemy != null && w != null)
		{
			w.use(enemy);	
		}
	}
		
	public void movement()
	{
		// Run away and regen shield
		if(getPercentShield() <= .2f)
		{
			moveTo(getHomeBase());
		}

		Unit enemy = getNearestEnemy();

		// Only attack if I have enough units and the enemy is out of reach, otherwise go home
		if(getPlayer().countMyUnits() > 20 && enemy != null && getDistance(enemy) > getMaxRange())
		{		
			moveTo(enemy);
		}

		// Defend if the enemy is nearby
		else if(getDistance(enemy) < getMaxRange() * 3 && getDistance(enemy) > getMaxRange())
		{
			moveTo(enemy);
		}

		// Go home
		else
		{
			turnTo(getHomeBase());
			move();
		}
	}
	
	public void draw(Graphics g) 
	{

	}
}
