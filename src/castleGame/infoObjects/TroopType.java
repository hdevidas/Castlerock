package castleGame.infoObjects;

import castleGame.base.Sprite;

public enum TroopType
{
	Piquier ("Piquier", 100, 5, 2, 1, 1),
	Knight ("Chevalier", 500, 20, 6, 3, 5),
	Onager ("Onagre", 1000, 50, 1, 5, 10);
	

	private String name;
	private Sprite sprite;
	private int productionCost;
	private int productionTime;
	private int speed;
	private int healthPoint;
	private int attack;

	// constructor
	private TroopType(String name, int productionCost, int productionTime, int speed, int healthPoint, int attack)
	{
		this.name = name;
		this.productionCost = productionCost;
		this.productionTime = productionTime;
		this.speed = speed;
		this.healthPoint = healthPoint;
		this.attack = attack;
	}

	// getters
	public String getName()
	{
		return name;
	}

	public Sprite getSprite()
	{
		return sprite;
	}
	
	public int getProductionCost()
	{
		return productionCost;
	}

	public int getProductionTime()
	{
		return productionTime;
	}

	public int getSpeed()
	{
		return speed;
	}

	public int getHealthPoint()
	{
		return healthPoint;
	}

	public int getAttack()
	{
		return attack;
	}
}
