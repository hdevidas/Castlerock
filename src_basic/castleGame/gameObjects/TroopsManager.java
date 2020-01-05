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
	public TroopsManager(Owner owner, int troops[])
	{
		this.owner = owner;
		for (TroopType troopType : TroopType.values())
		{
			this.troops.add(troopType.ordinal(), new ArrayList<Troop>());
		}
		setTroops(troops);
	}
	
	public TroopsManager(Owner owner)
	{
		this(owner, new int [Settings.NB_TROOP_TYPES]);
	}
	
	
	
	// GETTERS AND SETTERS
	void setTroops(int troops[])
	{
		if (troops.length == Settings.NB_TROOP_TYPES)
		{
			for (TroopType troopType : TroopType.values())
			{
				addNewTroops(troopType, troops[troopType.ordinal()]);
			}
		}
	}
	
	Troop getTroop(TroopType troopType)
	{
		return this.troops.get(troopType.ordinal()).get(0);
	}
	
	public void setOwner(Owner owner) 
	{
		this.owner = owner;
	}
	
	
	
	// INHERITED METHODS
	
	
	
	// METHODS	
	void addNewTroop(TroopType troopType)
	{
		this.troops.get(troopType.ordinal()).add(new Troop(this, troopType));
	}
	
	void addNewTroops(TroopType troopType, int nbTroop)
	{
		for (int i = 0; i < nbTroop; i++)
		{
			addNewTroop(troopType);
		}
	}
	
	void addTroop(Troop troop)
	{
		this.troops.get(troop.getType().ordinal()).add(troop); 
	}
	
	void addTroops(Troop troops[])
	{
		for (Troop troop : troops)
		{
			addTroop(troop);
		}
	}
	
	//Fonction pour supprimer une unité d'un type précisé
	// TODO a generaliser
	//supprimer toutes les troupes d'une armée
	void removeAllTroops(){
			for (TroopType troopType : TroopType.values()) {
				while (has_got_troops(troopType)) {
					removeTroop(troopType);
				}
			}

	}
	
	//supprime une SEULE troupe d'un type d'armée
	void removeTroop(TroopType troopType)
	{
		if ( this.has_got_troops(troopType) ) {
			this.troops.get(troopType.ordinal()).remove(0);
		}
	}
	
	void removeTroop(Troop troop)
	{
		troops.get(troop.getType().ordinal()).remove(troop);
	}
	
	void giveTroopTo(TroopsManager troopsManager, Troop troopToGive)
	{
		troopsManager.addTroop(troopToGive);
		troopToGive.setTroopsManager(troopsManager);
		this.removeTroop(troopToGive);
	}
	
	boolean giveTroopTo(TroopsManager troopsManager, TroopType troopType)
	{
		if (this.has_got_troops(troopType))
		{
			Troop troopToGive = this.getTroop(troopType);
			this.giveTroopTo(troopsManager, troopToGive);
			return true;
		}
		else
		{
			return false;
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
		
	}
	
	boolean has_got_troops(TroopType troopType) {
		return (getNbTroop(troopType) > 0 );
	}
	
	boolean has_got_troops() {
		boolean hasGotTroops = false;
		for (TroopType troopType : TroopType.values())
		{
			hasGotTroops = hasGotTroops || has_got_troops(troopType);
		}
		return hasGotTroops;
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
}
