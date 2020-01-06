package castleGame.gameObjects;

import castleGame.infoObjects.TroopType;

public class Order
{
	// VARIABLES
	OrderManager myManager;
	boolean isBuildTroop;
	TroopType troopToBuild;
	int turnLeftToComplete;
	int cost;
	boolean isBeingBuild;
	
	
	// CONSTRUCTORS
	public Order (OrderManager manager, TroopType troopToBuild)
	{
		this.myManager = manager;
		this.isBuildTroop = (troopToBuild != null);
		this.troopToBuild = troopToBuild;
		
		this.turnLeftToComplete = this.computeTotalTurnsToComplete();
		this.cost = this.computeCost();
		
		this.isBeingBuild = false;
	}
	
	public Order (OrderManager manager)
	{
		this(manager, null);
	}
	
	
	// GETTERS AND SETTERS
	
	
	
	// INHERITED METHODS

	
	
	// METHODS	
	int computeTotalTurnsToComplete()
	{
		if (isBuildTroop)
		{
			return troopToBuild.getProductionTime();
		}
		else
		{
			return myManager.castle.getLevelUpTime();
		}
	}
	
	int computeCost()
	{
		if (isBuildTroop)
		{
			return troopToBuild.getProductionCost();
		}
		else
		{
			return myManager.castle.getLevelUpCost();
		}
	}

	public boolean isComplete() 
	{
		return (this.turnLeftToComplete <= 0);
	}
	
	void build()
	{
		turnLeftToComplete--;
	}
	
	boolean startNewOrder(Castle castle)
	{
		if (myManager.castle.getMoney() >= this.cost && !this.isBeingBuild)
		{
			myManager.castle.setMoney( myManager.castle.getMoney() - this.cost );
			isBeingBuild = true;
			return true;
		}
		else
		{
			return false;
		}
	}
	
	boolean finishOrder(Castle castle)
	{
		if (isBuildTroop)
		{
			myManager.castle.addNewTroop(troopToBuild);
		}
		else
		{
			myManager.castle.level_up();
		}
		isBeingBuild = false;
		return true;
	}
}
