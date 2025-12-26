package teams.foes.librarian;

import components.weapon.Weapon;
import objects.entity.unit.Unit;
import org.newdawn.slick.Graphics;

public abstract class LibrarianUnit extends Unit
{
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
		Unit enemy = getNearestEnemy();

		// Only attack if the enemy is out of reach, otherwise go home
		if(enemy != null && getDistance(enemy) > getMaxRange())
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
