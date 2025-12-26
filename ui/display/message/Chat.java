package ui.display.message;

import engine.Main;
import engine.Utility;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import player.Player;
import ui.display.Fonts;
import ui.display.hud.Hud;

public class Chat extends GlobalMessage
{
    Player owner;
    String chatMessage;

    public Chat(Player owner, String message, int duration)
    {
        super(message, duration);
        this.owner = owner;
        chatMessage = message;
    }

    public void render(Graphics g)
    {
        Font f = Fonts.bigFont;

        int alpha = (int) Math.pow((255 * this.percentComplete()), 1.3);

        int red = owner.getColorPrimary().getRedByte();
        int green = owner.getColorPrimary().getGreenByte();
        int blue = owner.getColorPrimary().getBlueByte();
//
//        int redTwo = owner.getColorSecondary().getRedByte() / 2;
//        int greenTwo = owner.getColorSecondary().getGreenByte() / 2;
//        int blueTwo = owner.getColorSecondary().getBlueByte() / 2;

        g.setFont(f);

//        final int PADDING = 8;

//        g.setColor(new Color(redTwo/4, greenTwo/4, blueTwo/4, alpha));
//        g.fillRect(x - f.getWidth(message) * .5f - PADDING * .5f, y- PADDING * .5f, f.getWidth(message) + PADDING, f.getHeight(message) + PADDING);
//        g.setColor(new Color(redTwo, greenTwo, blueTwo, alpha));
//        g.drawRect(x - f.getWidth(message) * .5f - PADDING * .5f , y- PADDING * .5f, f.getWidth(message) + PADDING, f.getHeight(message) + PADDING);

//        g.setColor(new Color(red, green, blue, alpha));

        String nameMessage = owner.getName().toUpperCase() + ": ";
        int nameLength = f.getWidth(nameMessage);
        int chatLength = f.getWidth(chatMessage);
        int totalLength = nameLength + chatLength;

        float x = Main.getScreenWidth() / 2 - totalLength/2;
        float y = Main.getScreenHeight() - (id + 2) * f.getHeight("A") * 1.2f - (Main.getScreenHeight() * Hud.BAR_HEIGHT_PERCENT);

        g.setColor(new Color(red, green, blue, alpha));
        g.drawString(nameMessage, x, y);

        g.setColor(new Color(255, 255, 255, alpha));
        g.drawString(chatMessage, x + nameLength, y);

//
//        Utility.drawStringCenterTop(g, f, nameMessage, x, y);
//
//        Utility.drawStringCenterTop(g, f, message, x, y);
    }
}
