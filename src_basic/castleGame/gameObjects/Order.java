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
	
	
	
	// GETTERS AND SETTERS
	
	
	
	// INHERITED METHODS

	
	
	// METHODS
	int computeTotalTurnsToComplete()
	{
		return 0;
	}

	public boolean isComplete() 
	{
		return false;
	}
	
	void build()
	{
		turnLeftToComplete--;
	}
	
	boolean startNewOrder(Castle castle)
	{
		// TODO deduct the cost from the castle money
		isBeingBuild = true;
		return false;
	}
	
	boolean finishOrder(Castle castle)
	{
		//TODO execute this order (add troop or level)
		return false;
	}
	
	
	
}
