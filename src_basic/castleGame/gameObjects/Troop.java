package castleGame.gameObjects;

import castleGame.base.GameObject;
import castleGame.base.Sprite;
import castleGame.base.SpriteRender;
import castleGame.infoObjects.TroopType;

public class Troop extends GameObject implements SpriteRender
{
	// VARIABLES
	private String name;
	private int speed;
	private int healthPoint;
	private int attack;
	
	double x, y;
	
	Sprite sprite;
	
	
	
	// CONSTRUCTORS
	public Troop(TroopType troopType, double x, double y)
	{
		this.name = troopType.getName();
		this.speed = troopType.getSpeed();
		this.healthPoint = troopType.getHealthPoint();
		this.attack = troopType.getAttack();
		this.x = x;
		this.y = y;
	}
	
	
	
	// GETTERS AND SETTERS
	
	
	
	// INHERITED METHODS
	protected void updateThis() 
	{
			
	}
	
	protected void updateChilds()
	{
		
	}

	public void updateUI() 
	{
		sprite.updateUI();
	}
	
	
	// METHODS
	
}
