package ui.display;

import engine.Main;
import org.newdawn.slick.TrueTypeFont;

import java.awt.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class Fonts
{
	public static TrueTypeFont gargantuanFont;
	public static TrueTypeFont massiveFont;
	public static TrueTypeFont hugeFont;
	
	public static TrueTypeFont bigFont;
	public static TrueTypeFont mediumFont;
	public static TrueTypeFont smallFont;
	public static TrueTypeFont tinyFont;

	private static TrueTypeFont gargantuanFont1440;
	private static TrueTypeFont massiveFont1440;
	private static TrueTypeFont hugeFont1440;
	private static TrueTypeFont bigFont1440;
	private static TrueTypeFont mediumFont1440;
	private static TrueTypeFont smallFont1440;
	private static TrueTypeFont tinyFont1440;

	private static TrueTypeFont gargantuanFont1080;
	private static TrueTypeFont massiveFont1080;
	private static TrueTypeFont hugeFont1080;
	private static TrueTypeFont bigFont1080;
	private static TrueTypeFont mediumFont1080;
	private static TrueTypeFont smallFont1080;
	private static TrueTypeFont tinyFont1080;

	private static TrueTypeFont gargantuanFont800;
	private static TrueTypeFont massiveFont800;
	private static TrueTypeFont hugeFont800;
	private static TrueTypeFont bigFont800;
	private static TrueTypeFont mediumFont800;
	private static TrueTypeFont smallFont800;
	private static TrueTypeFont tinyFont800;

	public static void setFonts()
	{
		if(Main.getScreenWidth() > 1920)
		{
			gargantuanFont = Fonts.gargantuanFont1440;
			massiveFont = Fonts.massiveFont1440;
			hugeFont = Fonts.hugeFont1440;
			bigFont = Fonts.bigFont1440;
			mediumFont = Fonts.mediumFont1440;
			smallFont = Fonts.smallFont1440;
			tinyFont = Fonts.tinyFont1440;
		}
		else if(Main.getScreenWidth() == 1920)
		{
			gargantuanFont = Fonts.gargantuanFont1080;
			massiveFont = Fonts.massiveFont1080;
			hugeFont = Fonts.hugeFont1080;
			bigFont = Fonts.bigFont1080;
			mediumFont = Fonts.mediumFont1080;
			smallFont = Fonts.smallFont1080;
			tinyFont = Fonts.tinyFont1080;

		}
		else
		{
			gargantuanFont = Fonts.gargantuanFont800;
			massiveFont = Fonts.massiveFont800;
			hugeFont = Fonts.hugeFont800;
			bigFont = Fonts.bigFont800;
			mediumFont = Fonts.mediumFont800;
			smallFont = Fonts.smallFont800;
			tinyFont = Fonts.tinyFont800;

		}
	}

	public static void loadFonts()
	{
		// Ye Olde Fonts

//		tinyFont1440 = new TrueTypeFont(new Font("OCR A Extended", Font.PLAIN,  14), true);
//		smallFont1440 = new TrueTypeFont(new Font("OCR A Extended", Font.PLAIN, 17), true);
//		mediumFont1440 = new TrueTypeFont(new Font("OCR A Extended", Font.PLAIN, 20), true);
//		bigFont1440 = new TrueTypeFont(new Font("OCR A Extended", Font.PLAIN, 28), true);
//		hugeFont1440 = new TrueTypeFont(new Font("OCR A Extended", Font.BOLD,  32), true);
//		massiveFont1440 = new TrueTypeFont(new Font("OCR A Extended", Font.BOLD, 38), true);
//		gargantuanFont1440 = new TrueTypeFont(new Font("OCR A Extended", Font.BOLD, 50), true);
//
//		tinyFont1080 = new TrueTypeFont(new Font("OCR A Extended", Font.PLAIN,  13), true);
//		smallFont1080 = new TrueTypeFont(new Font("OCR A Extended", Font.PLAIN,  15), true);
//		mediumFont1080 = new TrueTypeFont(new Font("OCR A Extended", Font.PLAIN, 18), true);
//		bigFont1080 = new TrueTypeFont(new Font("OCR A Extended", Font.PLAIN, 24), true);
//		hugeFont1080 = new TrueTypeFont(new Font("OCR A Extended", Font.BOLD,  28), true);
//		massiveFont1080 = new TrueTypeFont(new Font("OCR A Extended", Font.BOLD, 34), true);
//		gargantuanFont1080 = new TrueTypeFont(new Font("OCR A Extended", Font.BOLD, 44), true);
//
//		tinyFont800 = new TrueTypeFont(new Font("OCR A Extended", Font.PLAIN,  12), true);
//		smallFont800 = new TrueTypeFont(new Font("OCR A Extended", Font.PLAIN,  14), true);
//		mediumFont800 = new TrueTypeFont(new Font("OCR A Extended", Font.PLAIN, 16), true);
//		bigFont800 = new TrueTypeFont(new Font("OCR A Extended", Font.PLAIN, 20), true);
//		hugeFont800 = new TrueTypeFont(new Font("OCR A Extended", Font.BOLD,  24), true);
//		massiveFont800 = new TrueTypeFont(new Font("OCR A Extended", Font.BOLD, 30), true);
//		gargantuanFont800 = new TrueTypeFont(new Font("OCR A Extended", Font.BOLD, 40), true);

		// Uncomment Code Below For Fancy Fonts (works in Modern Java / IntelliJ Setup)

		tinyFont1440 = new TrueTypeFont(createFont("roboto", Font.PLAIN,  15), true);
		smallFont1440 = new TrueTypeFont(createFont("roboto", Font.PLAIN,  17), true);
		mediumFont1440 = new TrueTypeFont(createFont("roboto", Font.PLAIN, 19), true);
		bigFont1440 = new TrueTypeFont(createFont("voltaire", Font.PLAIN, 28), true);
		hugeFont1440 = new TrueTypeFont(createFont("orbitron", Font.BOLD,  32), true);
		massiveFont1440 = new TrueTypeFont(createFont("orbitron", Font.BOLD, 38), true);
		gargantuanFont1440 = new TrueTypeFont(createFont("orbitron", Font.BOLD, 50), true);

		tinyFont1080 = new TrueTypeFont(createFont("roboto", Font.PLAIN,  14), true);
		smallFont1080 = new TrueTypeFont(createFont("roboto", Font.PLAIN,  15), true);
		mediumFont1080 = new TrueTypeFont(createFont("roboto", Font.PLAIN, 17), true);
		bigFont1080 = new TrueTypeFont(createFont("voltaire", Font.PLAIN, 24), true);
		hugeFont1080 = new TrueTypeFont(createFont("orbitron", Font.BOLD,  28), true);
		massiveFont1080 = new TrueTypeFont(createFont("orbitron", Font.BOLD, 34), true);
		gargantuanFont1080 = new TrueTypeFont(createFont("orbitron", Font.BOLD, 44), true);

		tinyFont800 = new TrueTypeFont(createFont("roboto", Font.PLAIN,  12), true);
		smallFont800 = new TrueTypeFont(createFont("roboto", Font.PLAIN,  14), true);
		mediumFont800 = new TrueTypeFont(createFont("roboto", Font.PLAIN, 15), true);
		bigFont800 = new TrueTypeFont(createFont("voltaire", Font.PLAIN, 20), true);
		hugeFont800 = new TrueTypeFont(createFont("orbitron", Font.BOLD,  24), true);
		massiveFont800 = new TrueTypeFont(createFont("orbitron", Font.BOLD, 30), true);
		gargantuanFont800 = new TrueTypeFont(createFont("orbitron", Font.BOLD, 40), true);

		Fonts.setFonts();
	}

	public static Font createFont(String filename, int style, float size)
	{
		try
		{
			String path = "res/fonts/" + filename + ".ttf";
			InputStream myStream = new BufferedInputStream(new FileInputStream(path));
			Font customFont = Font.createFont(Font.TRUETYPE_FONT, myStream);
			customFont = customFont.deriveFont(style, size);

//			Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File(filename)).deriveFont(size);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			//register the font
			ge.registerFont(customFont);
			return customFont;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
}



