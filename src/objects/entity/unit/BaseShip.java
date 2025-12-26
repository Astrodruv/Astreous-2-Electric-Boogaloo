package objects.entity.unit;

import components.upgrade.TitanCore;
import components.weapon.energy.HeavyLaser;
import components.weapon.explosive.HeavyMissile;
import components.weapon.kinetic.HeavyAutocannon;
import conditions.Condition;
import conditions.Instant;
import engine.Values;
import org.newdawn.slick.Graphics;
import player.Player;
import ui.display.Images;

public final class BaseShip extends Unit 
{
	public final static float TURN_RATE_START = .012f;
	public final static float BASE_SHIP_X_POSITION = 8000;
	public final static float BASE_SHIP_Y_POSITION = 150;

	private boolean skirmishing;
	public float turnRate;
	
	public BaseShip(Player p)
	{
		super(p);

		sheet = Images.imageBase;
		setImage();
		
		if (p.getTeam() == Values.TEAM_ONE_ID) 
		{
			this.x = -BASE_SHIP_X_POSITION - w/2;
			this.y = -BASE_SHIP_Y_POSITION - h/2;			
			theta = 0;
		} 
		else if (p.getTeam() == Values.TEAM_TWO_ID) 
		{
			this.x = BASE_SHIP_X_POSITION - w/2;
			this.y = BASE_SHIP_Y_POSITION - h/2;
			theta = 180;
		}
	
		setFrame(Frame.COLOSSAL);
		add(HeavyMissile.class);
		add(HeavyLaser.class);
		add(TitanCore.class);

		
	//	addUpgrade
		
		//this.setHull(500000);
		
		turnRate = TURN_RATE_START;

	}

	public void repairHull(float amount, boolean restoresShields)
	{

	}

	public void update() 
	{
		super.update();



		
		Unit u = getNearestEnemy();
		
		float trueTheta = getTheta();
		
		getWeaponOne().use(u);
		getWeaponTwo().use(u);

		setBaseShipSpeed();
		changeSpeed(getAcceleration(), getTheta(), false);

		if(team == 0 && getCenterX() > 0)
		{
			xSpeed = 0;
		}
		if(team == 1 && getCenterX() < -0)
		{
			xSpeed = 0;
		}
		



//		xSpeed = 5;
//		System.out.println("----");
//		System.out.println(getMaxSpeed() + " " + getTheta());
//		System.out.println(xSpeed + " " + ySpeed);



//		System.out.println(getMaxSpeed() + " " + getTheta());
//		System.out.println(xSpeed + " " + ySpeed);
	}
	
	public void addCondition(Condition c)
	{		
		// Only adds direct damage!
		if(c instanceof Instant)
		{	
			super.addCondition(c);
		}
	}


	
	public void action() 
	{

	}

	public void draw(Graphics g) 
	{

	}

	public void design() 
	{
		
	}



}
