package castleGame.gameObjects;

import java.util.ArrayList;

import castleGame.base.Inputs;
import castleGame.base.Sprite;
import castleGame.base.SpriteRender;
import castleGame.infoObjects.Settings;
import castleGame.infoObjects.TroopType;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Ost extends TroopsManager
{
	// VARIABLES
	private Map map;
	private Castle castleFrom;
	private Castle castleTarget;
	boolean isComplete;
	
	
	
	// CONSTRUCTORS
	
	public Ost( Map map, Castle castleFrom, Castle castleTarget) 
	{
		this.map = map;
		this.castleFrom = castleFrom;
		this.castleTarget = castleTarget;

		isComplete = false;
		
		this.map.addOst(this);
	}

	
	
	// GETTERS AND SETTERS
	
	
	
	// INHERITED METHODS
	protected void updateThis() 
	{
		if (isDead())
		{
			map.removeOst(this);
		}
	}
	
	protected void updateChilds()
	{
		super.updateChilds();
		// specific ost variable to update can be put here
	}
	
	
	
	// METHODS
	
	boolean isDead()
	{
		boolean isDead = isComplete;
		for (TroopType troopType : TroopType.values())
		{
			isDead = isDead && (troops.get(troopType.ordinal()).size() == 0);
		}
		return isDead;
	}
}
