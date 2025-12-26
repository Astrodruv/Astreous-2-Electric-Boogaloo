package ui.sound;

import engine.Settings;
import org.newdawn.slick.SlickException;
import ui.sound.music.Song;
import ui.sound.sfx.SoundTime;

import java.util.ArrayList;

public class AudioManager 
{
	public final static float MUSIC_SPECIAL_CHANCE = .01f;
	public final static float MUSIC_HIDDEN_CHANCE = .002f;
	
	private static Song gameMusic;
	private static ArrayList<SoundTime> delayedSounds;
	
	
	/************** Setup **************/
	
	public static void loadSFX() throws SlickException 
	{
		Sounds.loadSFX();
		delayedSounds = new ArrayList<SoundTime>();
	}
	
	public static void loadMusic() throws SlickException 
	{
		Sounds.loadSongList();
		playGameMusic();
	}
	
	public static void leave()
	{
		delayedSounds.clear();
		gameMusic = null;
	}
	
	/************** Music **************/

	public static Song getGameMusic()
	{
		return gameMusic;
	}
	
	public static void setRandomGameMusic()
	{
		try
		{
			gameMusic = Sounds.getRandomSong();
			playGameMusic();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}

	public static void setMusic(String title)
	{
		Song song = Sounds.getSong(title);
		if(song != null)
		{
			setMusic(song);
		}
	}

	public static void setMusic(Song song)
	{
		try
		{
			gameMusic = song;
			playGameMusic();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}


	private static void playGameMusic() throws SlickException
	{
		if(Settings.musicOn) 
		{
			if(gameMusic == null)
			{
				return;
			}

			if(!gameMusic.isLoaded())
			{
				gameMusic.loadMusic();
			}

			gameMusic.getMusic().loop();
		}
	}
	
	public static void setMusicVolume(float volume)
	{
		try 
		{
			if(getGameMusic() != null && getGameMusic().getMusic() != null)
			{
				getGameMusic().getMusic().setVolume(volume);
			}
		} 
		catch (SlickException e) 
		{
			e.printStackTrace();
		}
	}
	
	
	public static void update()
	{
		for(int i = 0; i < delayedSounds.size(); i++)
		{
			delayedSounds.get(i).update();
			
			if(delayedSounds.get(i).isDone())
			{
				delayedSounds.remove(i);
				i--;
			}
		}
		
		
	}
	/************** SFX **************/
	
	public static void addDelayedSound(SoundTime st)
	{
		delayedSounds.add(st);
	}

}
