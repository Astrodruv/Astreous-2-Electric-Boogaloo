package ui.display.opener;

import engine.Main;
import engine.Utility;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import player.Player;
import ui.display.Fonts;

public class PlayerProfile
{	
	
	private final float x;
	private final float y;
	private final float w;
	private final float h;
	
	Player owner;
	Font bigFont;
	Font smallFont;
	Color black;
	Color primary ;
	Color secondary;
	
	public PlayerProfile(Player owner, float x, float y)
	{
		this.x = x;
		this.y = y;
		
		w = Main.getScreenWidth() / 5;
		h = Main.getScreenHeight() / 2;
		
		this.owner = owner;
		
	
		
//		if(Main.getScreenWidth() >= 1920)
//		{
			bigFont = Fonts.gargantuanFont;
			smallFont = Fonts.hugeFont;
//		}

	}
	
	public void render(Graphics g)
	{	
//		g.setColor(new Color(50, 50, 50, 120));
//		g.fillRect(x,  y + h * .13f,  w,  160);
		

		
		primary = Utility.modifyAlpha(owner.getColorPrimary(),  Opener.getAlpha());
		secondary = Utility.modifyAlpha(Color.white,  Opener.getAlpha());
		black = Utility.modifyAlpha(Color.black,  Opener.getAlpha());

		g.setColor(new Color(40, 40, 40,  Opener.getAlpha()));
		Utility.drawStringCenterCenter(g,  bigFont, owner.getName(), x+w/2+2, y+h *.16f+2);
		
		g.setColor(primary);
		Utility.drawStringCenterCenter(g,  bigFont, owner.getName(), x+w/2, y+h *.16f);
		
		if(owner.getTitle() != null)
		{
		g.setColor(new Color(60, 60, 60,  Opener.getAlpha()));
		Utility.drawStringCenterCenter(g,  smallFont, owner.getTitle(), x+w/2+2, y+h*.25f+2);
		
		g.setColor(secondary);
		Utility.drawStringCenterCenter(g,  smallFont, owner.getTitle(), x+w/2, y+h*.25f);
		}
		
		renderTeamImage(g);
	}

	public void renderTeamImage(Graphics g)
	{
		float iW = h * .5f;
		float iH = h * .5f;
		float iX = x + w/2 - iW/2;
		float iY = y + h * .35f;

		float border = 7;
		g.setLineWidth(border);
		g.setColor(black);
		g.drawRect(iX - border, iY - border, iW + border * 2-2, iH + border * 2-2);

		border = 3;
		g.setLineWidth(border);
		g.setColor(primary);
		g.drawRect(iX - border, iY - border, iW + border * 2-2, iH + border * 2-2);


		Color imgColor = new Color(255, 255, 255,  Opener.getAlpha());
		owner.getTeamImage().draw(iX, iY, iW, iH, imgColor);

		g.setColor(new Color(0, 0, 0, (int) ( Opener.getAlpha()*.20f)));
		g.fillRect(iX - border, iY - border, iW + border * 2, iH + border * 2);
		
	}
	
	public void update()
	{
		
	}
	
	

	

	
	
}
