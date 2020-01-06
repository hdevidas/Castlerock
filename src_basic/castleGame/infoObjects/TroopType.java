package castleGame.infoObjects;

import castleGame.base.Sprite;
import javafx.scene.image.Image;

public enum TroopType
{
	Piquier ("Piquier", new Image("/images/piquier.png", Settings.OST_SIZE, Settings.OST_SIZE, true, true), 100, 5, 2, 1, 1),
	Knight ("Chevalier", new Image("/images/knight.png", Settings.OST_SIZE, Settings.OST_SIZE, true, true), 500, 20, 6, 3, 5),
	Onager ("Onagre", new Image("/images/onager.png", Settings.OST_SIZE, Settings.OST_SIZE, true, true), 1000, 50, 1, 5, 10);
	
	// VARIABLES
	private String name;
	private Image image;
	private int productionCost;
	private int productionTime;
	private int speed;
	private int healthPoint;
	private int attack;

	// CONSTRUCTORS
	private TroopType(String name, Image image, int productionCost, int productionTime, int speed, int healthPoint, int attack)
	{
		this.name = name;
		this.image = image;
		this.productionCost = productionCost;
		this.productionTime = productionTime;
		this.speed = speed;
		this.healthPoint = healthPoint;
		this.attack = attack;
	}

	
	
	// GETTERS AND SETTERS
	public String getName()
	{
		return name;
	}
	
	public Image getImage()
	{
		return image;
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
	
	
	
	// INHERITED METHODS
	
	
	
	// METHODS
}
