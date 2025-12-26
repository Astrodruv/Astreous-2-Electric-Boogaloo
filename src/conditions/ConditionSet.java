package conditions;

import engine.Settings;
import objects.entity.Entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ConditionSet implements Iterable<Condition>
{
	List<Condition> conditions;
	Entity owner;
	
	public ConditionSet(Entity owner)
	{
		this.owner = owner;
		conditions = new ArrayList<Condition>();
	}
		
	public int countDebuffs()
	{
		int count = 0;
		for(Condition c : conditions)
		{
			if(c instanceof Debuff)
			{
				count++;
			}
		}
		return count;
	}
	
	public int countBuffs()
	{
		int count = 0;
		for(Condition c : conditions)
		{
			if(c instanceof Buff)
			{
				count++;
			}
		}
		return count;
	}
	
	
	public int countType(Class<? extends Condition> clazz)
	{
		int count = 0;
		for(Condition c : conditions)
		{
			if(clazz.isInstance(c))
			{
				count++;
			}
		}
		return count;
	}
	
	
	public void add(Condition condition)
	{
		conditions.add(condition);
		condition.setOwner(owner);

	}
	
	public void remove(Condition condition)
	{
		conditions.remove(condition);
		condition.end();
	}
	
	public void removeAll(Class<? extends Condition> clazz)
	{
		for(int i = 0; i < conditions.size(); i++)
		{
			Condition c = conditions.get(i);
			if(clazz.isInstance(c))
			{
				remove(c);
				i--;
			}
		}
	}
	
	public void update()
	{
		// Update count on all conditions
		for(int i = 0; i < conditions.size(); i++)
		{
			Condition c = conditions.get(i);
			
			if(c.isPreparing())
			{
				c.prepare();
			}
			else
			{
				c.update();
			}
		}
		
		// Remove inactive conditions
		for(int i = 0; i < conditions.size(); i++)
		{
			Condition c = conditions.get(i);
			
			if(!c.isActive())
			{
				remove(c);
				i--;
			}
		}
		
		// Display debug message
		if(Settings.dbgShowUnitConditions && owner instanceof objects.entity.unit.Unit)
		{
			for(Condition c : conditions)
			{
				owner.dbgMessage(c.toString());
			}
		}

	}
	
	public Condition get(int i)
	{
		return conditions.get(i);
	}
	
	public Condition get(Class<? extends Condition> clazz)
	{
		for(Condition c : conditions)
		{
			if(clazz.isInstance(c))
			{
				return c;
			}
		}
		
		return null;
	}
	
	public boolean contains(Condition c)
	{
		return conditions.contains(c);
	}
	
	public boolean containsType(Class<? extends Condition> clazz)
	{
		for(Condition c : conditions)
		{
			if(clazz.isInstance(c))
			{
				return true;
			}
		}
		return false;
	}
	
	public int size()
	{
		return conditions.size();
	}

	public Iterator<Condition> iterator() 
	{
		return conditions.iterator();
	}
	
	public float getSpeedScaling()
	{
		float totalScalars = 1;
		ArrayList<Class<? extends Condition>> types = new ArrayList<>();

		for(Condition c : conditions)
		{
			if(!types.contains(c.getClass())) {
				totalScalars *= c.getSpeedModifier();
				types.add(c.getClass());

			}
		}
		
		return totalScalars;
	}
	
	public boolean increasesSpeed()
	{
		return getSpeedScaling() > 1;
	}
	
	public boolean modifiesSpeed()
	{	
		return getSpeedScaling() != 1;
	}
	
	public boolean decreasesSpeed()
	{
		return getSpeedScaling() < 1;
	}
	
	public float getModifiedAcceleration(float baseAcceleration)
	{
		return baseAcceleration * getSpeedScaling();
	}
		
	
	public float getModifiedSpeed(float baseSpeed)
	{
		return baseSpeed * getSpeedScaling();
	}

	public float getBlock()
	{
		// Adds up all the block modifiers

		float totalScalar = 0;
		ArrayList<Class<? extends Condition>> types = new ArrayList<>();

		for(Condition c : conditions)
		{
			if(!types.contains(c.getClass()))
			{
				totalScalar += c.getBlock();
				types.add(c.getClass());
			}
		}

		return totalScalar;

	}


	public float getAccuracy()
	{
		// Adds up all the block modifiers

		float totalScalar = 0;
		ArrayList<Class<? extends Condition>> types = new ArrayList<>();

		for(Condition c : conditions)
		{
			if(!types.contains(c.getClass()))
			{
				totalScalar += c.getAccuracyModifier();
				types.add(c.getClass());
			}
		}

		return totalScalar;

	}

	public float getRange()
	{
		float totalBonus = 0;
		ArrayList<Class<? extends Condition>> types = new ArrayList<>();

		for(Condition c : conditions)
		{
			if(!types.contains(c.getClass()))
			{
				totalBonus += c.getRangeModifier();
				types.add(c.getClass());
			}
		}

		return totalBonus;

	}



	public float getPower()
	{
		float totalScalar = 1;
		ArrayList<Class<? extends Condition>> types = new ArrayList<>();

		for(Condition c : conditions)
		{
			if(!types.contains(c.getClass()))
			{
				totalScalar += c.getPower();
				types.add(c.getClass());
			}
		}
//		System.out.println("CONDITION SYSTEM " + totalScalar);
		
		return totalScalar;
	}

	public float getRepair()
	{
		float totalScalar = 1;
		ArrayList<Class<? extends Condition>> types = new ArrayList<>();

		for(Condition c : conditions)
		{
			if(!types.contains(c.getClass()))
			{
				totalScalar += c.getRepair();
				types.add(c.getClass());
			}
		}
//		System.out.println("CONDITION SYSTEM " + totalScalar);

		return totalScalar;
	}
		
		
	public boolean stopsMovement()
	{
		for(Condition c : conditions)
		{
			if(c.stopsMovement())
			{
				return true;
			}
		}
		return false;
	}
	
	public boolean preventsShieldRecovery()
	{
		for(Condition c : conditions)
		{
			if(c.preventsShieldRecovery())
			{
				return true;
			}
		}
		return false;
	}
	
	public boolean preventsRepair()
	{
		for(Condition c : conditions)
		{
			if(c.preventsRepair())
			{
				return true;
			}
		}
		return false;
	}
	
	public boolean stopsAction()
	{
		for(Condition c : conditions)
		{
			if(c.stopsAction())
			{
				return true;
			}
		}
		return false;
	}
	
	public boolean locksPosition()
	{
		for(Condition c : conditions)
		{
			if(c.locksPosition())
			{
				return true;
			}
		}
		return false;
	}
	
	public boolean preventsDamage()
	{
		for(Condition c : conditions)
		{
			if(c.preventsDamage())
			{
				return true;
			}
		}
		return false;
	}

	public void removeDebuffs()
	{
		for(int i = 0; i < conditions.size(); i++)
		{
			Condition c = conditions.get(i);
			if(c instanceof Debuff)
			{
				conditions.remove(c);
				i--;
			}
		}
	}

	public void reduceDebuffs(int amount)
	{
		for(int i = 0; i < conditions.size(); i++)
		{
			Condition c = conditions.get(i);
			if(c instanceof Debuff)
			{
				c.reduceDuration(amount);
			}
		}
	}

	public void removeBuffs()
	{
		for(int i = 0; i < conditions.size(); i++)
		{
			Condition c = conditions.get(i);
			if(c instanceof Buff)
			{
				conditions.remove(c);
				i--;
			}
		}
	}

	public void reduceBuffs(int amount)
	{
		for(int i = 0; i < conditions.size(); i++)
		{
			Condition c = conditions.get(i);
			if(c instanceof Buff)
			{
				c.reduceDuration(amount);
			}
		}
	}
}
