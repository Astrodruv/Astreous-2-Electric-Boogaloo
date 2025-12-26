package components.mod;

import components.Component;

abstract public class Mod extends Component
{
	public Mod()
	{
		super();
	}

	public void onAddition()
	{
		super.onAddition();

		if(getOwner().hasWeaponOne())
		{
			getOwner().getWeaponOne().applyMod();
		}
		if(getOwner().hasWeaponTwo())
		{
			getOwner().getWeaponTwo().applyMod();
		}

	}
	
}
