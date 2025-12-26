package ui.sound.music;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;

public class Song 
{
	private final String file;
	private final String artist;
	private final String title;
	private final boolean isSpecial;
	private Music music;
	
	public Song(String artist, String title, String file)
	{
		this.artist = artist;
		this.title = title;
		this.file = file;		
		isSpecial = false;
	}
	
	Song(String artist, String title, String file, boolean isSpecial)
	{
		this.artist = artist;
		this.title = title;
		this.file = file;		
		this.isSpecial = isSpecial;
	}
	
	public boolean isLoaded()
	{
		return music != null;
	}
	
	public void loadMusic() throws SlickException
	{		
		music = new Music(file);
	}
	public String getTitle()
	{
		return title;
	}
	
	public String getArtist()
	{
		return artist;
	}
	
	public boolean isSpecial()
	{
		return isSpecial;
	}
	
	public Music getMusic() throws SlickException
	{
		return music;
	}

}
