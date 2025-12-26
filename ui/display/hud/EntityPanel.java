package ui.display.hud;

import components.Component;
import components.mod.Mod;
import components.upgrade.Plating;
import components.upgrade.Shield;
import components.upgrade.Structure;
import components.weapon.Weapon;
import conditions.debuffs.Immobilized;
import conditions.debuffs.Slow;
import conditions.debuffs.Stop;
import engine.Main;
import engine.Utility;
import engine.Values;
import objects.entity.node.Asteroid;
import objects.entity.node.Derelict;
import objects.entity.node.Node;
import objects.entity.unit.BaseShip;
import objects.entity.unit.Unit;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import territory.TerritoryManager;
import ui.display.Fonts;

public class EntityPanel extends HudElement
{
	Unit unit;
	Node node;
	int indent;
	int pos;
	int spacing;
	
	public EntityPanel(float x, float y, float w, float h)
	{
		super(x, y, w, h);
	}
	
	public boolean hasUnit()
	{
		return unit != null && unit.isAlive();
	}
	
	public void setUnit(Unit u)
	{
		this.unit = u;
		this.node = null;
	}
	
	public boolean hasNode()
	{
		return node != null && node.isAlive();
	}
	
	public void setNode(Node n)
	{
		this.unit = null;
		this.node = n;
	}
	
	public void render(Graphics g)
	{		
		super.render(g);

		if(hasUnit())
		{			
			setFontsAndSpacing();

			drawTitle(g);		
			drawAttribute(g);		
			drawWeapons(g);
			drawUpgrades(g);
			drawDefense(g);
		}
		
		if(hasNode()) 
		{
			setFontsAndSpacing();
			drawNodeTitle(g);
			drawNodeAttribute(g);
		}

		g.setLineWidth(3);
		g.setColor(COLOR_BORDER);
		g.drawRect(x+2,  y, w, h);
	}
	
	public void setFontsAndSpacing()
	{

		if(Main.getScreenWidth() > 1920)
		{
			indent = 20;
			spacing = 17;
		}
		else if(Main.getScreenWidth() == 1920)
		{
			indent = 14;
			spacing = 14;
		}
		else
		{
			indent = 12;
			spacing = 10;
		}
		
		pos = 3;

	}
	
	
	public void drawNodeTitle(Graphics g)
	{
		String message = "";

		if(node instanceof Asteroid)
		{
			g.setColor(TerritoryManager.getAsteroidColor().darker().darker());
			message = "Mine for Resources";
		}
		else if(node instanceof Derelict)
		{
			g.setColor(TerritoryManager.getDerelictColor().darker().darker());
			message = "Mine for Resources";
		}

		g.setColor(new Color(30, 30, 30 ));
		g.fillRect(x+2, y + indent - 4, w-4, (int) (spacing * 1.5 + 25));

		g.setFont(Fonts.mediumFont);
		g.setColor(Color.white);
		g.drawString(node.getName(), x + indent, y + indent);
		

		g.setFont(Fonts.smallFont);
		g.setColor(Color.gray);
		g.drawString(message, x + indent, y + indent + spacing + 9);
		pos++;
		
		
	}
	
	public void drawNodeAttribute(Graphics g)
	{
		if(node.getMaxShield() != 0)
		{
			g.setColor(Values.COLOR_SHIELD);
			g.drawString("Shields:   " + (int) node.getCurShield() + " / " + (int) node.getMaxShield(), x + indent, y + indent + spacing * pos++);
		} 
		if(node.getMaxPlating() != 0)
		{
			g.setColor(Values.COLOR_PLATING);
			g.drawString("Plating:   " + (int) node.getCurPlating() + " / " + (int)  node.getMaxPlating(), x + indent, y + indent + spacing * pos++);
		}
		
		g.setColor(Values.COLOR_STRUCTURE);
		g.drawString("Structure: " + (int) node.getCurStructure() + " / " + (int)  node.getMaxStructure(), x + indent, y + indent + spacing * pos++);
		
		pos++;
		
		if(node.getCurResources() != 0)
		{
			g.setColor(Values.COLOR_CAPACITY);
			if(node instanceof Asteroid)
			{
				g.drawString("Minerals:  " + node.getCurResources() + " / " + node.getMaxResources(), x + indent, y + indent + spacing * pos++);
			}
			else
			{
				g.drawString("Scrap:  " + node.getCurResources() + " / " + node.getMaxResources(), x + indent, y + indent + spacing * pos++);

			}
		}		
	}
	
	public void drawTitle(Graphics g)
	{
		g.setColor(new Color(30, 30, 30 ));
		g.fillRect(x+2, y + indent - 4, w-4, (int) (spacing * 1.5 + 25));
		
		g.setFont(Fonts.mediumFont);
		g.setColor(unit.getColorPrimary().brighter());
		g.drawString(unit.getPlayer().getName() + " " + unit.getName(), x + indent, y + indent);
		

		g.setFont(Fonts.smallFont);

		g.setColor(Color.gray);
		g.drawString(unit.getFrame().toString() + " " + unit.getModel().toString() + " (Mass: " + unit.getMass() + ")", x + indent, y + indent + spacing + 9);
		pos++;
		
	//	//g.setColor(Color.white);
	//	Utility.drawStringCenterCenter(g, Hud.bigFont, ""+unit.getValue(), x + w *.9f, y + h * .165f);

		g.setFont(Fonts.smallFont);
	}
	


	
	public void drawAttribute(Graphics g)
	{
		g.setFont(Fonts.tinyFont);

		if(unit.getMaxShield() != 0)
		{
			g.setColor(Values.COLOR_SHIELD);
			g.drawString("Shields:   " + (int) unit.getCurShield() + " / " + (int) unit.getMaxShield(), x + indent, y + indent + spacing * pos++);
		} 
		if(unit.getMaxPlating() != 0)
		{
			g.setColor(Values.COLOR_PLATING);
			g.drawString("Plating:   " + (int) unit.getCurPlating() + " / " + (int)  unit.getMaxPlating(), x + indent, y + indent + spacing * pos++);
		}
		
			g.setColor(Values.COLOR_STRUCTURE);
			g.drawString("Structure: " + (int) unit.getCurStructure() + " / " + (int)  unit.getMaxStructure(), x + indent, y + indent + spacing * pos++);
		
		if(unit.getCapacity() != 0)
		{
			g.setColor(Values.COLOR_CAPACITY);
			g.drawString("Capacity:  " + unit.getCargo() + " / " + unit.getCapacity(), x + indent, y + indent + spacing * pos++);
		}
		
		if(unit.getMaxSpeed() > 1 * Values.SPEED && !(unit instanceof BaseShip))
		{
			g.setColor(Values.COLOR_SPEED);
			g.drawString("Speed:     " + Math.round (unit.getCurSpeed() / Values.SPEED ) + " / " + Math.round (unit.getMaxSpeedBase() / Values.SPEED), x + indent, y + indent + spacing * pos++);
		}
		
	}
	
	public void drawWeapons(Graphics g)
	{
		g.setFont(Fonts.tinyFont);


		pos++;
		if(unit.hasWeaponOne()) {
			String weapon = unit.getWeaponOne().getAdjective();

			if (!weapon.equals(""))
			{
				weapon += " ";
			}

			int a = Math.round(100 * unit.getWeaponOne().getAccuracy());
			int r = unit.getWeaponOne().getMaxRange();

			weapon += unit.getWeaponOne().getName();

			weapon += " (" + r;

			if(a >= 0)
			{
				weapon += " / " + a + "%)";
			}
			else
			{
				weapon += ")";
			}

			Color c = unit.getWeaponOne().getWeaponType().getColor();

//			if(unit.getWeaponOne().inUse())
//			{
//				c = Utility.blend(Color.white,  c);
//				c = Utility.blend(c, unit.getWeaponOne().getDamageType().getColor());
//
//			}
//			else 
			if(unit.getWeaponOne().onCooldown())
			{
				c = c.darker();
			}

			g.setColor(c);
			g.drawString(weapon, x + indent, y + indent + spacing * pos++);
		}
		
		if(unit.hasWeaponTwo())
		{
			String weapon = unit.getWeaponTwo().getAdjective();

			if (!weapon.equals(""))
			{
				weapon += " ";
			}

			int a = Math.round(100 * unit.getWeaponTwo().getAccuracy());
			int r = unit.getWeaponTwo().getMaxRange();

			weapon += unit.getWeaponTwo().getName();

			weapon += " (" + r;

			if(a >= 0)
			{
				weapon += " / " + a + "%)";
			}
			else
			{
				weapon += ")";
			}
			
			Color c = unit.getWeaponTwo().getWeaponType().getColor();
//		
//			if(unit.getWeaponOne().inUse())
//			{
//				c = Utility.blend(Color.white,  c);
//				c = Utility.blend(c, unit.getWeaponOne().getDamageType().getColor());
//			}
//			else 
			if(unit.getWeaponTwo().onCooldown())
			{
				c = c.darker();
			}
			 
			
			g.setColor(c);
			g.drawString(weapon, x + indent, y + indent + spacing * pos++);
		}
		
	}
	
	public void drawDefense(Graphics g)
	{
		int block = Math.round(100 * (unit.getBlock()));
		int dodge = Math.round(100 * (unit.getDodgeChance()));
		int power =  Math.round(100 * (unit.getPower()));

		g.setColor(Color.white);
		Utility.drawStringCenterCenter(g, Fonts.mediumFont, power + "%", x + w *.8f, y + h * .40f);
		g.setColor(Color.gray);
		Utility.drawStringCenterCenter(g, Fonts.smallFont, "Power", x + w *.8f, y + h * .49f);

		g.setColor(Color.white);
		Utility.drawStringCenterCenter(g, Fonts.mediumFont, block + "%", x + w *.8f, y + h * .60f);
		g.setColor(Color.gray);
		Utility.drawStringCenterCenter(g, Fonts.smallFont, "Block", x + w *.8f, y + h * .69f);
		
		g.setColor(Color.white);
		Utility.drawStringCenterCenter(g, Fonts.mediumFont, dodge + "%", x + w *.8f, y + h * .80f);
		g.setColor(Color.gray);
		Utility.drawStringCenterCenter(g, Fonts.smallFont, "Dodge", x + w *.8f, y + h * .89f);
	}
	
	public void drawUpgrades(Graphics g)
	{
		for(Component c : unit.getComponents())
		{
			if(c instanceof Weapon || c instanceof Shield || c instanceof Plating || c instanceof Structure || c instanceof Mod)
			{
				continue;
			}
			else
			{
				g.setColor(new Color(200, 200, 200));
				g.drawString(c.getName() + " ", x + indent, y + indent + spacing * pos++);
			}
				
		}
	}
	
	
}
