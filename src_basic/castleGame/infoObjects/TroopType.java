package castleGame.infoObjects;

import javafx.scene.image.Image;

/**
 * This Enum contains the infos about each troop that the game will manage.
 */
public enum TroopType
{
	/**
	 * The TroopType containing info about Piquiers
	 */
	Piquier ("Piquier", new Image("/images/piquier.png", Settings.OST_SIZE, Settings.OST_SIZE, true, true), 100, 5, 2, 1, 1),
	/**
	 * The TroopType containing info about Knights
	 */
	Knight ("Chevalier", new Image("/images/knight.png", Settings.OST_SIZE, Settings.OST_SIZE, true, true), 500, 20, 6, 3, 5),
	/**
	 * The TroopType containing info about Onagers
	 */
	Onager ("Onagre", new Image("/images/onager.png", Settings.OST_SIZE, Settings.OST_SIZE, true, true), 1000, 50, 1, 5, 10);
	
	// VARIABLES
	/**
	 * The name of this troop
	 */
	private String name;
	/**
	 * The image this troop should display when in an ost
	 */
	private Image image;
	/**
	 * The production cost to create this troop in florins
	 */
	private int productionCost;
	/**
	 * The Time taken to produce this troop in number of turns
	 */
	private int productionTime;
	/**
	 * The maximum speed this troop can go
	 */
	private int speed;
	/**
	 * The Health this troop shall spawn with
	 */
	private int healthPoint;
	/**
	 * The damage this troop can do (this is the number of 1 HP attacks this troop can do when arriving at a castle) 
	 */
	private int attack;

	// CONSTRUCTORS
	/**
	 * The constructor for this enum.
	 * 
	 * @param name : The name of this troop 
	 * @param image : The image this troop should display when in an ost
	 * @param productionCost : The production cost to create this troop in florins
	 * @param productionTime : The Time taken to produce this troop
	 * @param speed : The maximum speed this troop can go
	 * @param healthPoint : The Health this troop shall spawn with
	 * @param attack : The damage this troop can do
	 */
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
	/**
	 * get this troop's name
	 * @return this troop's name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * get this troop's image (to display when in an ost)
	 * @return this troop's image
	 */
	public Image getImage()
	{
		return image;
	}

	/**
	 * get this troop's production cost (in florins)
	 * @return this troop's production cost 
	 */
	public int getProductionCost()
	{
		return productionCost;
	}

	/**
	 * get this troop's time to be produced (in number of turns)
	 * @return this troop's time to be produce 
	 */
	public int getProductionTime()
	{
		return productionTime;
	}

	/**
	 * get this troop's maximum speed
	 * @return this troop's maximum speed
	 */
	public int getSpeed()
	{
		return speed;
	}

	/**
	 * get this troop's max health point
	 * @return this troop's max health point
	 */
	public int getHealthPoint()
	{
		return healthPoint;
	}

	/**
	 * get this troop's damages (this is the number of 1 HP attacks this troop can do when arriving at a castle)
	 * @return this troop's damages
	 */
	public int getAttack()
	{
		return attack;
	}
	
	
	
	// INHERITED METHODS
	
	
	
	// METHODS
}
