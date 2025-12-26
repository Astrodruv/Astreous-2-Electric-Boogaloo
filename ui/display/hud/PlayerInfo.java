package ui.display.hud;

import engine.Utility;
import engine.Values;
import engine.states.Game;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import player.Player;
import ui.display.Fonts;

public class PlayerInfo
{	
	Player player;
	protected float x;
	protected float y;
	protected float w;
	protected float h;

	float pairGap;
	float fleetX;
	float mineralX;
	float combatX;
	float lagX;
	
	public PlayerInfo(Player owner, float x, float y, float w, float h)
	{
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.player = owner;

		 pairGap = .10f;

		 fleetX = .32f;

		if(player.isRightPlayer())
		{
			fleetX = .12f;
		}

		mineralX = fleetX + .22f;
		combatX = mineralX + .22f;
		lagX = combatX + .16f;

	}

	public void render(Graphics g)
	{	
//		Utility.drawStringLeftTop(g, bigFont, "" + player.getName(), x, y);
		basicInfo(g);
		fleetInfo(g);
		mineralInfo(g);
		combatInfo(g);
		lagInfo(g);
		bonusInfo(g);
		lostMineralInfo(g);
//		Utility.drawStringCenterCenter(g, bigFont, "" + (int) player.getFleetValue(), x + w * .1f, y + h * .1f);
//		Utility.drawStringCenterCenter(g, smallFont, "Fleet Value", x + w * .1f, y + h * .3f);

		
		
//		g.setColor(new Color(200, 200, 50));
//		g.drawString("Mins:     " + (int) player.getMinerals(), x + 20, y + PADDING + SPACING);
//		g.drawString("Mined:    " + (int) player.getMineralsMined(), x + 220, y + PADDING + SPACING);
//			
//		g.setColor(new Color(200, 200, 200));
//		g.drawString("Units:    " + (int) player.countMyUnits(), x + 420, y + PADDING + SPACING*3);		
	}
	
	
	public void basicInfo(Graphics g)
	{
		
	




//		player.getTeamImage().draw(x+Main.getScreenWidth() * .02f, y + Main.getScreenHeight() * .02f, Main.getScreenWidth() * .03f, Main.getScreenWidth() * .0225f);

		g.setColor(Color.black);
		g.setLineWidth(3);

		player.getTeamImage().setFilter(Image.FILTER_LINEAR);


		if(player.isLeftPlayer()) {


			player.getTeamImage().draw(x, y - h * .05f, h, h);

			g.drawRect(x, y - h *.05f, h, h);
		}
		else
		{

			player.getTeamImage().draw(x + w - h, y - h * .05f, h, h);
			g.drawRect(x + w - h, y - h *.05f, h, h);
		}


		//String name = player.getName().substring(0, Math.min(10, player.getName().length()));
		String name = player.getName().substring(0, Math.min(30, player.getName().length()));


		if(player.isLeftPlayer())
		{
			g.setColor(Color.black);
			Utility.drawStringLeftCenter(g, Fonts.massiveFont, name, x + w * .02f+2 , y - Fonts.massiveFont.getHeight()+2);

			g.setColor(player.getColorPrimary());
			Utility.drawStringLeftCenter(g, Fonts.massiveFont, name, x + w * .02f , y - Fonts.massiveFont.getHeight());
		}
		else
		{
			g.setColor(Color.black);
			Utility.drawStringRightCenter(g, Fonts.massiveFont, name, x + w * .98f+2, y - Fonts.massiveFont.getHeight()+2);

			g.setColor(player.getColorPrimary());
			Utility.drawStringRightCenter(g, Fonts.massiveFont, name, x + w * .98f, y - Fonts.massiveFont.getHeight());
		}

//		g.setColor(player.getColorSecondary());


//		Utility.drawStringCenterCenter(g, Hud.smallFont, "" + player.getBoonOne().name(), x + w * basicX, y + h * .40f);
//		Utility.drawStringCenterCenter(g, Hud.smallFont, "" + player.getBoonTwo().name(), x + w * basicX, y + h * .60f);

	}
	
	
	public void fleetInfo(Graphics g)
	{

		
		g.setColor(new Color(70, 70, 70));
		
		Utility.drawStringCenterCenter(g, Fonts.mediumFont, "FLEET", x + w * fleetX, y + h * .12f);

		g.setColor(new Color(100, 180, 255));

		
		Utility.drawStringCenterCenter(g, Fonts.bigFont, "" + player.getFleetValueUnit(), x + w * (fleetX-pairGap/2), y + h * .42f);
		g.setColor(Color.white);
		Utility.drawStringCenterCenter(g, Fonts.smallFont, "Value", x + w * (fleetX-pairGap/2), y + h * .65f);

		if(player.countMyUnits() == player.getMaxFleetSize())
		{
			g.setColor(Color.red);
		}
		else if(player.countMyUnits() >= player.getMaxFleetSize() * .8f)
		{
			g.setColor(Color.yellow);
		}
		else
		{
			g.setColor(new Color(100, 180, 255));
		}
		
		Utility.drawStringCenterCenter(g, Fonts.bigFont, "" + player.countMyUnits(), x + w * (fleetX + pairGap/2), y + h * .42f);
		g.setColor(Color.white);
		Utility.drawStringCenterCenter(g, Fonts.smallFont, "Count", x + w * (fleetX + pairGap/2), y + h * .65f);
	}
	
	public void mineralInfo(Graphics g)
	{


		
		g.setColor(new Color(70, 70, 70));
		
		Utility.drawStringCenterCenter(g, Fonts.mediumFont, "RESOURCE", x + w * mineralX-pairGap, y + h * .12f);

		g.setColor(new Color(255, 200, 150));		
		Utility.drawStringCenterCenter(g, Fonts.bigFont, "" + (int) player.getMinerals(), x + w * (mineralX-pairGap/2), y + h * .42f);
		g.setColor(Color.white);
		Utility.drawStringCenterCenter(g, Fonts.smallFont, "Store", x + w * (mineralX-pairGap/2), y + h * .65f);

		g.setColor(new Color(255, 200, 150));		
		Utility.drawStringCenterCenter(g, Fonts.bigFont, "" + (int) player.getMineralsMined(), x + w * (mineralX + pairGap/2), y + h * .42f);
		g.setColor(Color.white);
		Utility.drawStringCenterCenter(g, Fonts.smallFont, "Total", x + w * (mineralX + pairGap/2), y + h * .65f);
	}
	
	public void combatInfo(Graphics g)
	{


		g.setColor(new Color(70, 70, 70));
		
		Utility.drawStringCenterCenter(g, Fonts.mediumFont, "COMBAT", x + w * (combatX), y + h * .12f);


		float dmgPlayer = player.getDamageDealt() - player.getRepairRecieved() - player.getShieldRecieved();
		float dmgTotal = Game.getTotalDamageDealt() - Game.getTotalRepairRecieved() - Game.getTotalShieldRecieved();
		int dmg = 0;
		
		if(dmgTotal > 1)
		{
			dmg = (int) ((dmgPlayer / dmgTotal) * 100 + .5f);
		}
		
		g.setColor(new Color(255, 150, 200));
		Utility.drawStringCenterCenter(g, Fonts.bigFont, dmg + "%", x + w * (combatX-pairGap/2), y + h * .42f);
		g.setColor(Color.white);
		Utility.drawStringCenterCenter(g, Fonts.smallFont, "Damage", x + w *(combatX-pairGap/2), y + h * .65f);
	
//		int dodge = 0;
//		if(player.getDodgeAttempts() > 0)
//		{
//			dodge =  Math.round ((float) player.getDodgeCount() / (float) player.getDodgeAttempts() * 100);
//		}
//		g.setColor(new Color(255, 150, 200));
//		Utility.drawStringCenterCenter(g, Fonts.bigFont, dodge + "%", x + w * (combatX+pairGap/2), y + h * .42f);
//		g.setColor(Color.white);
//		Utility.drawStringCenterCenter(g, Fonts.smallFont, "Dodge", x + w * (combatX+pairGap/2), y + h * .65f);


		int mitigation = 0;
		float totalDamage = player.getDamageTaken() + player.getDamageMitigated();
		if(player.getDamageMitigated() > 0)
		{
			mitigation =  Math.round (player.getDamageMitigated() / totalDamage * 100);
		}


		g.setColor(new Color(255, 150, 200));
		Utility.drawStringCenterCenter(g, Fonts.bigFont, mitigation + "%", x + w * (combatX+pairGap/2), y + h * .42f);
		g.setColor(Color.white);
		Utility.drawStringCenterCenter(g, Fonts.smallFont, "Guard", x + w * (combatX+pairGap/2), y + h * .65f);
	}
	
	public void lagInfo(Graphics g)
	{


		g.setColor(new Color(70, 70, 70));
		
		Utility.drawStringCenterCenter(g, Fonts.mediumFont, "ALGO", x + w * lagX, y + h * .12f);


		int latency = player.getWeightedAverageLatency(Values.LATENCY_SAMPLE_FREQUENCY);
		
		if(latency > Player.EXTREME_LATENCY)
		{
			g.setColor(new Color(255, 150, 150));
		}
		else if(latency > Player.HIGH_LATENCY)
		{
			g.setColor(new Color(255, 200, 150));
		}
		else if(latency > Player.MEDIUM_LATENCY)
		{
			g.setColor(new Color(255, 255, 150));
		}
		else
		{
			g.setColor(new Color(150, 255, 155));
		}

		Utility.drawStringCenterCenter(g, Fonts.bigFont, "" + latency, x + w * lagX, y + h * .42f);
		g.setColor(Color.white);
		Utility.drawStringCenterCenter(g, Fonts.smallFont, "Latency", x + w *lagX, y + h * .65f);
	
	
	}
	
	public void bonusInfo(Graphics g)
	{
		if(player.getDifficultyRating() > 1)
		{
			g.setColor(new Color(50, 255, 50));
		}
		else if(player.getDifficultyRating() < 1)
		{
			g.setColor(new Color(255, 50, 50));
		}
		else
		{
			return;
		}

		float spacingX = .06f;
		float spacingY = .62f;

		String message = player.getDifficultyRatingModifierString();
				
		if(player == Game.getPlayerOne())
		{
			Utility.drawStringCenterCenter(g, Fonts.bigFont, message, x + w * (spacingX), y - h * spacingY);
		}
		else
		{
			Utility.drawStringCenterCenter(g, Fonts.bigFont, message, x + w + w * (-spacingX), y - h * spacingY);
		}
		
		


		
		

	}
	
	public void lostMineralInfo(Graphics g)
	{
		int mineralsLost = Math.round(player.getMineralsLost());
		
		if(mineralsLost == 0)
		{
			return;
		}
		
		g.setColor(new Color(255, 50, 50));

		
		String message = "Lost " + mineralsLost + " minerals from latency";
		float spacingY = .36f;


		Utility.drawStringCenterCenter(g, Fonts.smallFont, message, x + w * .55f, y - h * spacingY);

	
	}
}
