package ui.sound;

import engine.Utility;
import org.newdawn.slick.SlickException;
import ui.sound.music.Song;
import ui.sound.sfx.SmartSound;
import org.newdawn.slick.Sound;

import java.util.ArrayList;

public class Sounds 
{
	public static SmartSound laser;
	public static SmartSound laserSmall;

	public static SmartSound mg;
	public static SmartSound blast;
	public static SmartSound heal;
	public static SmartSound boom;
	public static SmartSound mining;
	public static SmartSound boost;
	public static SmartSound missileFire;
	public static Sound message;

	// Abilities
	public static SmartSound aegis;
	public static SmartSound emp;
	
	static ArrayList<Song> songs;

	public static Song getRandomSong()
	{
		return songs.get(Utility.random(0, songs.size()-1));	
	}
	
	static void loadSFX() throws SlickException
    {
		Sounds.boom = new SmartSound("general/boom", 3);

		Sounds.laserSmall = new SmartSound("energy/laser_small", 4);
		Sounds.laser = new SmartSound("energy/laser", 3);
		
		Sounds.mining = new SmartSound("kinetic/mining", 5);
		Sounds.mg = new SmartSound("kinetic/mg");
		
		Sounds.aegis = new SmartSound("utility/aegis");
		Sounds.boost = new SmartSound("utility/boost");

		Sounds.missileFire = new SmartSound("explosive/missile_fire");

		Sounds.message = new Sound("res/sfx/wavs/chat.wav");

	}
	
	static void loadSongList()
	{		
		String path = "res/music/";
		
		songs = new ArrayList<>();
		songs.add(new Song("Lindsey Stirling", "Between Twilight", path + "between_twilight.ogg"));		
		songs.add(new Song("Lindsey Stirling", "The Arena", path + "the_arena.ogg"));		
		songs.add(new Song("Lindsey Stirling", "Heist", path + "heist.ogg"));
		songs.add(new Song("Lindsey Stirling", "The Pheonix", path + "pheonix.ogg"));
		songs.add(new Song("Lindsey Stirling", "First Light", path + "first_light.ogg"));
		songs.add(new Song("Lindsey Stirling", "Artemis", path + "artemis.ogg"));
		songs.add(new Song("Lindsey Stirling", "Guardian", path + "guardian.ogg"));
		songs.add(new Song("Lindsey Stirling", "Darkside", path + "darkside.ogg"));
		songs.add(new Song("Lindsey Stirling", "Till The Light Goes Out", path + "till_the_light_goes_out.ogg"));
		songs.add(new Song("Lindsey Stirling", "Underground", path + "underground.ogg"));
		songs.add(new Song("Lindsey Stirling", "Ascendance", path + "ascendance.ogg"));
		songs.add(new Song("Lindsey Stirling", "Beyond the Veil", path + "beyond_the_veil.ogg"));
		songs.add(new Song("Lindsey Stirling", "Master of Tides", path + "master_of_tides.ogg"));
		songs.add(new Song("Lindsey Stirling", "Take Flight", path + "take_flight.ogg"));
		//songs.add(new Song("Lindsey Stirling", "Spontaneous Me", path + "spontaneous_me.ogg"));
	}

	public static Song getSong(String title)
	{
		for(Song s : songs)
		{
			if(s.getTitle().equalsIgnoreCase(title))
			{
				return s;
			}
		}
		return null;
	}


	
}
