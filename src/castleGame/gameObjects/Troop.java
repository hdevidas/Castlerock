package castleGame.gameObjects;

import castleGame.infoObjects.TroopType;

public class Troop
{
	private String name;
	private int productionCost;
	private int productionTime;
	private int speed;
	private int healthPoint;
	private int attack;
	
	
	public Troop(String name, int productionCost, int productionTime, int speed, int healthPoint, int attack)
	{
		this.name = name;
		this.productionCost = productionCost;
		this.productionTime = productionTime;
		this.speed = speed;
		this.healthPoint = healthPoint;
		this.attack = attack;
	}
	
	public Troop(TroopType troopType)
	{
		this(troopType.getName(), troopType.getProductionCost(),troopType.getProductionTime(),
			 troopType.getSpeed(), troopType.getHealthPoint(), troopType.getAttack());
	}
	
	
}
