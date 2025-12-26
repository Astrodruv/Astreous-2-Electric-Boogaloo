package engine;

import org.newdawn.slick.Color;

public interface Values 
{ 
	String VERSION = "2.5.8";
	String RELEASE_DATE = "3-7-25";

	// Image Scaling
	
	// Light -- > 24 x 24
	// Medium --> 36 x 36
	// Heavy --> 48 x 48
	// Assault --> 72 x 72
	// Base Ship --> 256 x 150  
	
	int TEAM_ONE_ID = 0;		// left
	int TEAM_TWO_ID = 1;		// right
	int NEUTRAL_ID = 2;			// both sides can damage (asteroids)
	int AMBIENT_ID = 3;			// background can't interact with
		
	int LATENCY_SAMPLE_FREQUENCY = 240;
	int LATENCY_GRACE_PERIOD = 480;
	
	int TRANSITION_FADE_TIME = 20;
	
	int SELECT_MIN_DIFFICULTY_RED = 40;
	int SELECT_MAX_DIFFICULTY_GREEN = 300;
	int SELECT_DIFFICULTY_RED_MAX = 180;
	int SELECT_DIFFICULTY_GREEN_MAX = 130;
	
	// Public Constants

	
	Color COLOR_SHIELD = new Color(100, 150, 255);
	Color COLOR_PULSE = new Color(100, 220, 255);

	Color COLOR_FIRE = new Color(220, 140, 30);

	Color COLOR_PLATING = new Color(220, 200, 30);
	Color COLOR_CAPACITY = new Color(196, 164, 132);
	Color COLOR_SPEED = new Color(255, 255, 180);

	Color COLOR_STRUCTURE = new Color(255, 80, 80);
	Color COLOR_UTILITY = new Color(80, 255, 80);

	int PLAYFIELD_WIDTH = 20000;
	int PLAYFIELD_HEIGHT = PLAYFIELD_WIDTH / 16 * 9;

	int FRAMES_PER_SECOND = 60;
	
	int STARTING_MINERALS = 60;


	float ACC = .0005f; //.0004f before Josh's fix;
	float SPEED = .04f;
	float DODGE_FULL = 100;

	float OUT_OF_BOUNDS_DAMAGE_PER_FRAME = .5f;





}
