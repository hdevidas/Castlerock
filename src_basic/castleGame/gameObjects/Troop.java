package castleGame.gameObjects;

import castleGame.base.GameObject;
import castleGame.base.Sprite;
import castleGame.base.SpriteRender;
import castleGame.infoObjects.Settings;
import castleGame.infoObjects.TroopType;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

public class Troop extends GameObject implements SpriteRender
{
	// VARIABLES
	private TroopType type; 
	TroopsManager troopsManager;
	private String name;
	private int speed;
	private int healthPoint;
	private int attack;

	//when in an ost:
	Ost ost = null;
	private int speedLimit;
	Point2D position;
	Point2D direction;
	int stepsLeft;
	int directionsLeft;
	boolean onTarget;
	Sprite sprite;
	
	
	
	// CONSTRUCTORS
	public Troop(TroopsManager troopsManager, TroopType troopType)
	{
		this.type = troopType;
		this.name = troopType.getName();
		this.speed = troopType.getSpeed();
		this.speedLimit = this.speed;
		this.healthPoint = troopType.getHealthPoint();
		this.attack = troopType.getAttack();
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
	
	public void setTroopsManager(TroopsManager troopsManager)
	{
		this.troopsManager = troopsManager;
	}
	
	
	
	// INHERITED METHODS
	//gameObject :
	protected void updateThis() 
	{
		if (ost != null)
		{
			if (onTarget)
			{
				if (directionsLeft <= 0)
				{
					ost.troopOnTarget(this);
				}
				else
				{
					onTarget = false;
					directionsLeft --;
					ost.getNextPathDestination(directionsLeft);
					computeStep();
					makeStep();
				}
			}
			else
			{
				makeStep();
				if (stepsLeft <= 0)
				{
					onTarget = true;
				}
			}
			this.updateUI();
		}
		if (isDead())
		{
			if (ost != null)
			{
				endJourney();
			}
			troopsManager.removeTroop(this);
		}
	}
	
	protected void updateChilds()
	{
		// no variable of this object is a gameObject to update
	}

	//spriteRenderer :
	public void updateUI() 
	{
		sprite.setX(position.getX());
		sprite.setY(position.getY());
		sprite.updateUI();
	}
	
	
	// METHODS
	void initJourney(Ost ost, Point2D spawnCoord, int directionsLeft, int speedLimit)
	{
		this.ost = ost;
		this.position = new Point2D(spawnCoord.getX(), spawnCoord.getY());
		this.directionsLeft = directionsLeft;
		
		if (speedLimit <= -1)
		{
			this.speedLimit = speed;
		}
		else
		{
			this.speedLimit = speedLimit;
		}
		
		onTarget = true;
		
		if (sprite == null)
		{
			this.sprite = new Sprite(Map.playfieldLayer, this.type.getImage(), spawnCoord);
		}
		else
		{
			sprite.addToLayer();
		}
	}
	
	void endJourney()
	{
		ost = null;
		position = null;
		direction = null;
		speedLimit = speed;
		sprite.removeFromLayer();
	}
	
	void endJourneyTo(TroopsManager troopsManager)
	{
		ost.giveTroopTo(troopsManager, this);
		endJourney();
	}
	
	void computeStep()
	{
		direction = ost.getNextPathDestination(directionsLeft);
		stepsLeft = (int) ((position.distance(direction) + 0.5)/speedLimit);
		direction = direction.subtract(position);
		direction = direction.multiply(1.0/(double) (stepsLeft));
	}
	
	void makeStep()
	{
		if (stepsLeft > 0)
		{
			position = position.add(direction);
			stepsLeft --;
			//System.out.println(stepsLeft + " " + direction + " " + position);
		}
	}
	
	void attack(Troop troop)
	{
		//TODO
	}
	
	boolean isDead()
	{
		return (healthPoint <= 0);
	}
}
