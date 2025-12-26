package ui.display.hud;

import engine.Main;
import engine.Settings;
import engine.Utility;
import objects.entity.unit.BaseShip;
import objects.entity.unit.Unit;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import player.Player;
import ui.display.Fonts;

import java.util.*;

public class UnitList
{	
	Player owner;
	protected float x;
	protected float y;

	private int spacing = 28;

	public UnitList(Player owner, float x, float y)
	{
		this.x = x;
		this.y = y;
		this.owner = owner;
	}

	public void render(Graphics g)
	{
		if(Settings.showUnitList)
		{
			ArrayList<Unit> units = owner.getMyUnits();
			Map<Class<? extends Unit>, Integer> unitCounts = new HashMap<>();
			Map<Class<? extends Unit>, Float> unitPercents = new HashMap<>();

			for(Unit u : units)
			{	
				// Skip baseship
				if(u instanceof BaseShip)
				{
					continue;
				}

				// Fill up names + counts;
				if(unitCounts.containsKey(u.getClass()))
				{
					unitCounts.replace(u.getClass(), unitCounts.get(u.getClass()) + 1);
				}

				// Add a new entry to counts and percents
				else
				{
					unitCounts.put(u.getClass(),  1);
					unitPercents.put(u.getClass(), u.getPlayer().getFleetValueUnitPercentage(u.getClass()));
				}
			}	

			// Move the compacted list into an easy to sort ArrayList of custom data objects

			List<Data> unitData = new ArrayList<Data>();

			for(Map.Entry<Class<? extends Unit>, Float> mapElement : unitPercents.entrySet())
			{
				Class<? extends Unit> type = mapElement.getKey();
				int percentage = (int) (mapElement.getValue() * 100);
				int count = unitCounts.get(type);

				unitData.add(new Data(type, percentage, count));
			}

			Collections.sort(unitData);
//			Collections.reverse(unitData);

			// Output the sorted elements

			int longestName = 0;

			for(Data d : unitData)
			{
				if (d.name.length() > longestName)
				{
					longestName = d.name.length();
				}
			}

			for(int i = 0; i < unitData.size(); i++)
			{
				Data d = unitData.get(i);

				int made = owner.getMyComposition().getTotalBuilt(d.type);
				int lost = owner.getMyComposition().getTotalLost(d.type);
				int current = made - lost;

//				int r = (int) (owner.getColorPrimary().getRed() * (d.percentage/100.0)) + 127;
//				int gr = (int) (owner.getColorPrimary().getGreen() * (d.percentage/100.0)) + 127;
//				int b = (int) (owner.getColorPrimary().getBlue() * (d.percentage/100.0)) + 127;
//
//				g.setColor(new Color(r, gr, b));
				setFont();
				//g.drawString(d.percentage + "% " + d.name + " (x" + d.count + ")", x + 20, y - i * spacing + 40);

				String simpleName = "";

				for(int j = 0; j < longestName; j++)
				{
					simpleName += 'a';
				}

				int nameWidth = Fonts.bigFont.getWidth(simpleName);


				if(owner.isLeftPlayer())
				{


//					// Lost
//					g.setColor(new Color(0, 0, 0));
//					Utility.drawStringLeftTop(g, Fonts.bigFont, ""+lost, Main.getScreenWidth() * .03f+2, y - i * spacing + 40+2);
//
//					g.setColor(new Color(150, 90, 90));
//					Utility.drawStringLeftTop(g, Fonts.bigFont, ""+lost, Main.getScreenWidth() * .03f, y - i * spacing + 40);

					// Percentage
					g.setColor(new Color(0, 0, 0));
					Utility.drawStringLeftTop(g, Fonts.bigFont, d.percentage + "%", Main.getScreenWidth() * .025f+2 + nameWidth, y - i * spacing + 40+2);

					g.setColor(new Color(120, 120, 140));
					Utility.drawStringLeftTop(g, Fonts.bigFont, d.percentage + "%", Main.getScreenWidth() * .025f + nameWidth, y - i * spacing + 40);

					// Made
					g.setColor(new Color(0, 0, 0));
					Utility.drawStringCenterTop(g, Fonts.mediumFont, current+"/"+made, Main.getScreenWidth() * .065f+2+nameWidth, y - i * spacing + 45+2);

					float percentage = ((float) current) / ((float) made);

					g.setColor(new Color((int) (90 + ((1-percentage) * 80)), (int) (90 + (percentage * 80)), 90));
					Utility.drawStringCenterTop(g, Fonts.mediumFont, current+"/"+made, Main.getScreenWidth() * .065f+nameWidth, y - i * spacing + 45);

					// Count and Name
					g.setColor(new Color(0, 0, 0));
					Utility.drawStringLeftTop(g, Fonts.bigFont, d.name, Main.getScreenWidth() * .015f+2, y - i * spacing + 40+2);

					g.setColor(Utility.blend(owner.getColorPrimary(), Color.gray));
					Utility.drawStringLeftTop(g, Fonts.bigFont, d.name, Main.getScreenWidth() * .015f, y - i * spacing + 40);

				}
				else
				{
					// Percentage
					g.setColor(new Color(0, 0, 0));
					Utility.drawStringCenterTop(g, Fonts.bigFont, d.percentage + "%", Main.getScreenWidth() * .945f+2, y - i * spacing + 40+2);

					g.setColor(new Color(120, 120, 140));
					Utility.drawStringCenterTop(g, Fonts.bigFont, d.percentage + "%", Main.getScreenWidth() * .945f, y - i * spacing + 40);

					// Made
					g.setColor(new Color(0, 0, 0));
					Utility.drawStringCenterTop(g, Fonts.mediumFont, current+"/"+made, Main.getScreenWidth() * .975f, y - i * spacing + 45+2);

					float percentage = ((float) current) / ((float) made);

					g.setColor(new Color((int) (90 + ((1-percentage) * 80)), (int) (90 + (percentage * 80)), 90));
					Utility.drawStringCenterTop(g, Fonts.mediumFont, current+"/"+made, Main.getScreenWidth() * .975f, y - i * spacing + 45);

					// Count and Name



					g.setColor(new Color(0, 0, 0));
					Utility.drawStringLeftTop(g, Fonts.bigFont, d.name, Main.getScreenWidth() * .925f+2 - nameWidth, y - i * spacing + 40+2);



					g.setColor(Utility.blend(owner.getColorPrimary(), Color.gray));
					Utility.drawStringLeftTop(g, Fonts.bigFont, d.name, Main.getScreenWidth() * .925f - nameWidth, y - i * spacing + 40);
				}


			}
		}
	}



	public void setFont()
	{
		if(Main.getScreenWidth() > 1920)
		{
			spacing = 30;
		}
		else if(Main.getScreenWidth() == 1920)
		{
			spacing = 24;
		}
		else
		{
			spacing = 20;
		}
	}

	class Data implements Comparable<Data>
	{
		public String name;
		public Class<? extends Unit> type;
		public int percentage;
		public int count;

		Data(Class<? extends Unit> type, int percentage, int count)
		{
			this.type = type;
			this.percentage = percentage;
			this.count = count;
			name = type.getSimpleName();
		}

//		public int compareTo(Data other)
//		{
//			if(name.compareTo(other.name) < 0)
//			{
//				return -1;
//			}
//			else if(name.compareTo(other.name) > 0)
//			{
//				return 1;
//			}
//			else
//			{
//				return 0;
//			}
//		}

		public int compareTo(Data other)
		{
			if(percentage > other.percentage)
			{
				return -1;
			}
			else if(percentage < other.percentage)
			{
				return 1;
			}
			else
			{
				return 0;
			}
		}

	}


}
