package castleGame.gameObjects;

import castleGame.base.GameObject;
import castleGame.base.Sprite;
import castleGame.base.SpriteRender;
import castleGame.infoObjects.Settings;
import castleGame.infoObjects.TroopType;
import javafx.scene.image.Image;

public class Troop extends GameObject implements SpriteRender
{
	// VARIABLES
	private TroopType type; 
	private String name;
	private int speed;
	private int healthPoint;
	private int attack;
	
	double x, y;
	
	Sprite sprite;
	
	
	//plus utilis�
	static Image image[] = new Image[]{
			new Image("/images/ost.png",  Settings.OST_SIZE, Settings.OST_SIZE, true, true),
			new Image("/images/ost.png",  Settings.OST_SIZE, Settings.OST_SIZE, true, true),
			new Image("/images/ost.png",  Settings.OST_SIZE, Settings.OST_SIZE, true, true)};
	
	
	
	// CONSTRUCTORS
	public Troop(TroopType troopType, double x, double y)
	{
		this.type = troopType;
		this.name = troopType.getName();
		this.speed = troopType.getSpeed();
		this.healthPoint = troopType.getHealthPoint();
		this.attack = troopType.getAttack();
		this.x = x;
		this.y = y;
		//this.sprite = new Sprite(Map.playfieldLayer, image[troopType.ordinal()], x, y); //plus utilis�
	}
	
	
	
	// GETTERS AND SETTERS
	public int getSpeed()
	{
		return speed;
	}
	
	public TroopType getType()
	{
		return type;
	}
	
	
	// INHERITED METHODS
	protected void updateThis() 
	{
		this.updateUI();
	}
	
	protected void updateChilds()
	{
		// no variable of this object is a gameObject to update
	}

	public void updateUI() 
	{
		//sprite.updateUI(); //plus utilis� car plus de sprite de troop a update
	}
	
	
	// METHODS
	
}
