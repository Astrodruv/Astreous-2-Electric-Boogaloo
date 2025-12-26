package components;

import components.mod.Mod;
import components.weapon.Weapon;
import components.weapon.WeaponType;
import objects.entity.unit.Unit;
import org.newdawn.slick.Graphics;
import ui.display.DisplayManager;

import java.util.ArrayList;

public class ComponentSystem 
{
	private Unit owner;
	private ArrayList<Component> components;
	private Mod mod;
	private int componentSlotsUsed;
	private Weapon weaponOne;
	private Weapon weaponTwo;
	
	public ComponentSystem(Unit owner)
	{
		this.owner = owner;
		components = new ArrayList<>();
	}

	public Weapon getWeaponOne()			{		return weaponOne;	}
	public Weapon getWeaponTwo()			{		return weaponTwo;	}

	public boolean hasWeaponOne()			{ 	return weaponOne != null;	}
	public boolean hasWeaponTwo()			{ 	return weaponTwo != null;	}

	public boolean hasWeaponSlotOpen()		{   return !hasWeaponOne() || !hasWeaponTwo();	}
	public boolean hasMod()					{		return mod  != null;	}
	public Mod getMod()						{		return mod;	}

	public int getMass() {
		int mass = 0;

		for (Component c : components) {
			mass += c.getMass();
		}

		return mass;
	}

	public int getComponentSlotsUsed()
	{
		return componentSlotsUsed;
	}
	
	public int getComponentSlotsOpen()
	{
		return getTotalComponentSlots() - componentSlotsUsed;
	}

	public int getTotalComponentSlots()
	{
		return owner.getFrame().getComponentSlots() + owner.getModel().getComponents();
	}


	private Component buildComponent(Class <? extends Component> clazz)
	{
		Component c = null;
		try
		{
			c = clazz.getDeclaredConstructor().newInstance();
			c.setOwner(owner);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return c;
	}

	public Component get(int i)
	{
		return components.get(i);
	}
	
	public ArrayList<Component> getAll()
	{
		return components;
	}

	public boolean canAdd(Component c)
	{
//		System.out.println(c);
//		System.out.println(owner);
//		System.out.println(owner.getFrame());
//		System.out.println("what");

		if(c instanceof Weapon && !hasWeaponSlotOpen())
		{
			return false;
		}

		if(c instanceof Mod && hasMod())
		{
			return false;
		}
		
		return componentSlotsUsed + c.getSize() <= getTotalComponentSlots();
	}

	public boolean add(Class <? extends Component> clazz)
	{
		return add(buildComponent(clazz));
	}

	public boolean add(Component c)
	{
		if(c == null)
		{
			System.out.println("Error: " + owner.getName()  + " has missing or invalid component type");
			DisplayManager.addMessage("Error: " + owner.getName()  + " has missing or invalid component type");
			return false;
		}

		if(!canAdd(c))
		{
			System.out.println("Error: " + owner.getName()  + " does not have enough component slots open.");
			DisplayManager.addMessage("Error: " + owner.getName() + "  does not have enough component slots open.");
			return false;
		}

		componentSlotsUsed += c.getSize();
		components.add(c);

		if(c instanceof Mod)
		{
			mod = (Mod) c;
		}

		if(c instanceof Weapon w)
		{

            if(weaponOne == null)
			{
				weaponOne = w;
			}
			else
			{
				weaponTwo = w;
			}
		}



		c.onAddition();
		
		return true;
	
	}
	
	public boolean has(Component comp)
	{
		for(Component c : components)
		{
			if(comp.getClass().equals(c.getClass()))
			{
				return true;
			}
		}
		return false;
	}
	
	public boolean has(WeaponType type)
	{
		for(Component c : components)
		{
			if(c instanceof Weapon && ((Weapon) c).getWeaponType() == type)
			{
				return true;
			}
		}
		return false;
	}
	
	public boolean has(Class<? extends Component> clazz)
	{
		
		for(Component c : components)
		{
			if(clazz.isInstance(c))
			{
				//System.out.println(clazz + " is a " + c);

				return true;
			}
		}

		return false;
	}

	public Component get(Class<? extends Component> clazz)
	{
		for(Component c : components)
		{
			if(clazz.isInstance(c))
			{
				return c;
			}
		}

		return null;
	}
	
	public void update()
	{
		for(Component c : components)
		{
			c.update();
		}
	}
	
	public void render(Graphics g)
	{
		for(Component c : components)
		{
			c.render(g);
		}
	}
	
	
}
