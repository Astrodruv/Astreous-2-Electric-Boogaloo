package ui.display;

import engine.Main;
import objects.entity.unit.Frame;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Images 
{
	public static final int STYLE_COUNT = 12;
	public static final int BG_COUNT = 6;
	public static final int BG_COUNT_GODS = 9;

	// Menu Backgrounds
	public static Image endBackground;
	public static Image bgPurpleBattle;
	public static Image bgDesolateShip;
	public static Image bgGreyMatte;
	public static Image bgMetal;
	public static Image defaultLogo;
	public static Image rock;
	public static Image news;

	public static Image omen;
	public static Image finisher;

	public static ArrayList<Image> screenshots;

	// Units
	public static SpriteSheet imageBase;
	public static SpriteSheet[] light;
	public static SpriteSheet[] medium;
	public static SpriteSheet[] heavy;
	public static SpriteSheet[] assault;

	// Other Objects
	public static SpriteSheet asteroids;
	public static SpriteSheet derelicts;
	public static SpriteSheet minerals;
	public static SpriteSheet scrap;
	public static SpriteSheet tech;
	public static SpriteSheet missile;
	
	// Backgrounds
	public static Image[] backgrounds;
	public static Image[] backgroundGods;

	public static Image logo;
	
	
	// Misc
	public static SpriteSheet booms;
	public static SpriteSheet boomsDark;

	public static SpriteSheet cursors;

	public static void loadScreenshots() throws SlickException
	{
		screenshots = new ArrayList<>();
		screenshots.add(new Image("res/menus/screenshots/ares_vs_poseidon.png"));
		screenshots.add(new Image("res/menus/screenshots/greatness_vs_librarian.png"));
		screenshots.add(new Image("res/menus/screenshots/poseidon_vs_arsonist.png"));
		screenshots.add(new Image("res/menus/screenshots/builder_vs_builder.png"));
		screenshots.add(new Image("res/menus/screenshots/poseidon_vs_artemis.png"));
		screenshots.add(new Image("res/menus/screenshots/space_trees_vs_ares.png"));
		screenshots.add(new Image("res/menus/screenshots/kronos_vs_poseidon.png"));
		screenshots.add(new Image("res/menus/screenshots/ares_vs_dionysus.png"));
		screenshots.add(new Image("res/menus/screenshots/nightshade_vs_hades.png"));
		screenshots.add(new Image("res/menus/screenshots/athena_vs_zeus.png"));
	}

	public static void loadShipImages() throws SlickException 
	{
		light = new SpriteSheet[STYLE_COUNT];
		medium = new SpriteSheet[STYLE_COUNT];
		heavy = new SpriteSheet[STYLE_COUNT];
		assault = new SpriteSheet[STYLE_COUNT];

		for(int i = 0; i < STYLE_COUNT; i++)
		{
			light[i] = new SpriteSheet("res/objects/units/light" + i + ".png", Frame.LIGHT_IMAGE_SIZE, Frame.LIGHT_IMAGE_SIZE, 0);
			medium[i] = new SpriteSheet("res/objects/units/medium" + i + ".png", Frame.MEDIUM_IMAGE_SIZE, Frame.MEDIUM_IMAGE_SIZE, 0);
			heavy[i] = new SpriteSheet("res/objects/units/heavy" + i + ".png", Frame.HEAVY_IMAGE_SIZE, Frame.HEAVY_IMAGE_SIZE, 0);
			assault[i] = new SpriteSheet("res/objects/units/assault" + i + ".png", Frame.ASSAULT_IMAGE_SIZE, Frame.ASSAULT_IMAGE_SIZE, 0);
		}
	}
	
	public static Image getBackground(int number)
	{
		return backgrounds[number];
	}
	
	public static Image getBackgroundGod(int number)
	{
		return backgroundGods[number];
	}

	public static Image getRandomScreenshot()
	{
		return screenshots.get((int) (Math.random() * screenshots.size()));
	}

	public static Image getRandomBackground()
	{
		int r = (int) (Math.random() * BG_COUNT);
		return backgrounds[r];
	}
	
	public static SpriteSheet getRandomAssaultShip()
	{
		int r = (int) (Math.random() * STYLE_COUNT);
		return assault[r];
	}
	
	public static void loadImages() throws SlickException 
	{
		loadShipImages();

		imageBase = new SpriteSheet("res/special/base2.png", 256, 256, 0);
		asteroids = new SpriteSheet("res/objects/nodes/asteroids.png", 150, 150, 0);
		derelicts = new SpriteSheet("res/objects/nodes/derelicts.png", 75, 75, 0);

		minerals = new SpriteSheet("res/objects/resources/minerals.png", 24, 24, 0);
		tech = new SpriteSheet("res/objects/resources/cores.png", 24, 24, 0);
		scrap = new SpriteSheet("res/objects/resources/scrap.png", 24, 24, 0);

		missile = new SpriteSheet("res/objects/missiles/missile.png", 20, 8, 0);

	//	nebula = new SpriteSheet("res/objects/ambient/nebwallpaper.jpg", 900, 700, 0);

		booms = new SpriteSheet("res/objects/ambient/boom.png", 16, 16, 0);
		boomsDark = new SpriteSheet("res/objects/ambient/boom_dark.png", 16, 16, 0);

		cursors = new SpriteSheet("res/menus/cursors.png", 64, 64, 0);
		logo = new Image("res/menus/logo.png");
		
		loadBackgrounds();
		loadScreenshots();
		loadConditions();


		finisher = new Image("res/animations/finisher.png");

	}
	
	public static void loadBackgrounds() throws SlickException 
	{
		backgrounds = new Image[BG_COUNT];
		backgroundGods = new Image[BG_COUNT_GODS];
		
		for(int i = 0; i < BG_COUNT; i++)
		{
			backgrounds[i] = new Image("res/backgrounds/bg" + i + ".png");
		}
		
		for(int i = 0; i < BG_COUNT_GODS; i++)
		{
			backgroundGods[i] = new Image("res/backgrounds/god" + i + ".png");
		}
	}

	public static void loadSelectImages() throws SlickException 
	{
		bgGreyMatte = new Image("res/menus/grey_matte.png");
		bgMetal = new Image("res/menus/metal.png");
		bgPurpleBattle = new Image("res/menus/purple_battle_3.png");
		defaultLogo = new Image("res/menus/default_logo.png");
//		rock = new Image("res/menus/rock2.png");
		news = new Image("res/menus/news.png");

	}
	
	public static void loadEndImages() throws SlickException 
	{
		endBackground = bgMetal.getScaledCopy(Main.getScreenWidth(), Main.getScreenHeight());
	}


	public static void loadConditions() throws SlickException
	{
		omen = new Image("res/conditions/omen.png");
	}



}
