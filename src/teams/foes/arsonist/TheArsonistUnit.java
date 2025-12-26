package teams.foes.arsonist;

import components.weapon.Weapon;
import objects.entity.unit.Unit;
import org.newdawn.slick.Graphics;

public abstract class TheArsonistUnit extends Unit
{
	abstract public void movement();

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

	public void draw(Graphics g)
	{

	}
}
