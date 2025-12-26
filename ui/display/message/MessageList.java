package ui.display.message;

import org.newdawn.slick.Graphics;

import java.util.ArrayList;

public class MessageList 
{
	protected ArrayList<Message> messages;
	
	public MessageList()
	{
		messages = new ArrayList<>();
	}
	
	public void render(Graphics g)
	{
		for(int i = 0; i < messages.size(); i++)
		{
			Message m = messages.get(i);
			m.updateID(i);
			m.render(g);
		}
	}
	
	public void addMessage(Message m)
	{
		messages.add(m);
	}
	
	public void update()
	{
		int countObjectMessages = 0;

		for(int i = 0; i < messages.size(); i++)
		{
			Message m = messages.get(i);

				if(m instanceof ObjectMessage)
				{
					m.updateID(countObjectMessages);
					countObjectMessages++;
				}

				m.update();

			if(m.isDone())
			{
				messages.remove(m);
				i--;
			}
			}

	}
	
	public void clear()
	{
		messages.clear();
	}
}
