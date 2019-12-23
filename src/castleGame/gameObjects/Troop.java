package castleGame.gameObjects;

import castleGame.base.Sprite;
import castleGame.infoObjects.Settings;
import castleGame.infoObjects.TroopType;
import javafx.scene.image.Image;

public class Troop
{
	private String name;
	private Sprite sprite;
	private int productionCost;
	private int productionTime;
	private int speed;
	private int healthPoint;
	private int attack;
	private double x;
	private double y;
	
	static Image piquierImage		= new Image("/images/ost.png",  Settings.OST_SIZE, Settings.OST_SIZE, true, true);
	
	
	public Troop(Sprite sprite, String name, int productionCost, int productionTime, int speed, int healthPoint, int attack)
	{
		this.name = name;
		this.productionCost = productionCost;
		this.productionTime = productionTime;
		this.speed = speed;
		this.healthPoint = healthPoint;
		this.attack = attack;

	}
	
	public Troop(Sprite sprite, String name, int productionCost, int productionTime, int speed, int healthPoint, int attack, double x, double y)
	{
		this.name = name;
		this.productionCost = productionCost;
		this.productionTime = productionTime;
		this.speed = speed;
		this.healthPoint = healthPoint;
		this.attack = attack;
		
		this.sprite = new Sprite(Map.playfieldLayer, Troop.piquierImage, x + Settings.CASTLE_SIZE + 10, y);
		

	}
	
	public Troop(TroopType troopType)
	{
		this(troopType.getSprite(), troopType.getName(), troopType.getProductionCost(),troopType.getProductionTime(),
			 troopType.getSpeed(), troopType.getHealthPoint(), troopType.getAttack());
	}
	
	public Troop(TroopType troopType, double x, double y)
	{
		this(troopType.getSprite(), troopType.getName(), troopType.getProductionCost(),troopType.getProductionTime(),
			 troopType.getSpeed(), troopType.getHealthPoint(), troopType.getAttack(), x, y);
	}
	
	
}
