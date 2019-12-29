package castleGame.gameObjects;

import java.util.ArrayList;

import castleGame.base.GameObject;
import castleGame.infoObjects.Owner;
import castleGame.infoObjects.Settings;
import castleGame.infoObjects.TroopType;

// this class contains all the methods to manage troops and their combined health as int arrays
// It is used in the following classes : Castle, Ost
public abstract class TroopsManager extends GameObject
{
	// VARIABLES
	ArrayList<ArrayList<Troop>> troops = new ArrayList<ArrayList<Troop>>(Settings.NB_TROOP_TYPES);
	protected Owner owner;
	
	
	
	// CONSTRUCTORS	
	public TroopsManager(int troops[],double x, double y)
	{
		for (TroopType troopType : TroopType.values())
		{
			this.troops.add(troopType.ordinal(), new ArrayList<Troop>());
		}
		setTroops(troops, x, y);
	}
	
	public TroopsManager()
	{
		this(new int [Settings.NB_TROOP_TYPES], 1, 1);
	}
	
	
	
	// GETTERS AND SETTERS
	void setTroops(int troops[], double x, double y)
	{
		if (troops.length == Settings.NB_TROOP_TYPES)
		{
			for (TroopType troopType : TroopType.values())
			{
				addTroops(troopType, troops[troopType.ordinal()],x,y);
			}
		}
	}
	
	// INHERITED METHODS
	
	
	
	// METHODS	
	void addTroop(TroopType troopType, double x, double y)
	{
		this.troops.get(troopType.ordinal()).add(new Troop(troopType, x , y));
	}
	
	void addTroops(TroopType troopType, int nbTroop, double x, double y)
	{
		for (int i = 0; i < nbTroop; i++)
		{
			addTroop(troopType,x,y);
			y=y+10;
		}
	}
	
	//Fonction pour supprimer une unité d'un type précisé
	// TODO a generaliser
	void removeTroop(TroopType troopType)
	{
		if ( this.has_got_troops(troopType) ) {
			this.troops.get(troopType.ordinal()).remove(0);
		}
	}

	protected void updateChilds()
	{
		this.troops.forEach(troopTypeList -> troopTypeList.forEach(troop -> troop.update()));
	}
	
	
	// Fonction pour résoudre une attaque
	//	TODO Fonction a retravailler
	//	TODO a generaliser avec tous les types de troupes
	void attack(TroopsManager foe)
	{
		while ( this.has_got_troops() && foe.has_got_troops()) {
			this.removeTroop(TroopType.Piquier);
			foe.removeTroop(TroopType.Piquier);
		}
	}
	

	
	boolean has_got_troops(TroopType troopType) {
		if (getNbTroop(troopType) > 0 ) {
			return true;
		}
		return false;
	}
	
	boolean has_got_troops() {
		if (has_got_troops(TroopType.Piquier) == false && has_got_troops(TroopType.Onager) == false && has_got_troops(TroopType.Knight) == false) {
			return false;
		}
		return true;
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
