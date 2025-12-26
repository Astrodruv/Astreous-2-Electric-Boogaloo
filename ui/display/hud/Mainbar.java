package ui.display.hud;

import engine.Main;
import engine.Utility;
import engine.states.Game;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import ui.display.Fonts;

public class Mainbar extends HudElement
{
	public Relativebar relativebar;
	private final PlayerInfo playerOne;
	private final PlayerInfo playerTwo;
	
	public final float CENTER_START = .47f;
	public final float CENTER_WIDTH = .06f;
	
	public Mainbar(float x, float y, float w, float h)
	{
		super(x, y, w, h);
		
		relativebar = new Relativebar(x , y-2, w, 14);
		playerOne = new PlayerInfo(Game.getPlayerOne(), x, y + 20, w * CENTER_START, h);
		playerTwo = new PlayerInfo(Game.getPlayerTwo(), x + w * (1-CENTER_START) + CENTER_WIDTH/2, y + 20,  w * CENTER_START, h);
	}
	
	public void render(Graphics g)
	{		
		super.render(g);


		g.setColor(new Color(15, 15, 15));
		g.fillRect(x, y, w , h);

		g.setColor(new Color(30, 30, 30));
		g.fillRect(x + w * CENTER_START, y , w * CENTER_WIDTH, h);
		



		//Utility.drawStringCenterCenter(g, Hud.smallFont, "Time", x + w / 2, y + h *.2f );


//		g.drawRect(x + w / 2, 0, 1, Main.getScreenHeight());
//		g.drawRect(x, 0, 1, Main.getScreenHeight());
//		g.drawRect(x + w , 0, 1, Main.getScreenHeight());

		playerOne.render(g);
		playerTwo.render(g);
		relativebar.render(g);

		g.setColor(Color.white);
		Utility.drawStringCenterCenter(g, Fonts.bigFont, ""+ (Game.getTime()/60), x + w / 2, y + h *.4f );
		Utility.drawStringCenterCenter(g, Fonts.smallFont, Math.min(60, Main.getFPS()) + " FPS", x + w / 2, y + h * .75f);
	}
	
	public void update()
	{
		relativebar.update();
	}
	
}
