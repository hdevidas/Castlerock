package castleGame.gameObjects;

import java.util.ArrayList;

import castleGame.infoObjects.Owner;
import castleGame.infoObjects.Settings;
import castleGame.infoObjects.TroopType;

// this class contains all the methods to manage troops and their combined health as int arrays
// It is used in the following classes : Castle, Ost
public abstract class TroopsManager
{
	
	ArrayList<ArrayList<Troop>> troops = new ArrayList<ArrayList<Troop>>(Settings.NB_TROOP_TYPES);
	protected Owner owner;
	

	public TroopsManager(int troops[])
	{
		for (TroopType troopType : TroopType.values())
		{
			this.troops.add(troopType.ordinal(), new ArrayList<Troop>());
		}
		setTroops(troops);
	}
	
	public TroopsManager()
	{
		this(new int [Settings.NB_TROOP_TYPES]);
	}

	void setTroops(int troops[])
	{
		if (troops.length == Settings.NB_TROOP_TYPES)
		{
			for (TroopType troopType : TroopType.values())
			{
				addTroops(troopType, troops[troopType.ordinal()]);
			}
		}
	}
	
	void addTroop(TroopType troopType)
	{
		this.troops.get(troopType.ordinal()).add(new Troop(troopType));
	}
	
	void addTroops(TroopType troopType, int nbTroop)
	{
		for (int i = 0; i < nbTroop; i++)
		{
			addTroop(troopType);
		}
	}
	
	void removeTroop()
	{
		
	}
	
	void attack(TroopsManager foe)
	{
		
	}
	
	void takeDamage(int damage)
	{
		
	}
	
	void heal (int healing)
	{
		
	}
	
	public int getNbTroop(TroopType troopType)
	{
		return troops.get(troopType.ordinal()).size();
	}
	
	int[] getNbTroops()
	{
		int table[] = new int [Settings.NB_TROOP_TYPES];
		for (TroopType troopType : TroopType.values())
		{
			table[troopType.ordinal()] = getNbTroop(troopType);
		}
		return table;
	}
	
	
	//TODO : add missing methods...
}
