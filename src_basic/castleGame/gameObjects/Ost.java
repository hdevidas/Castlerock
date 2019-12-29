package castleGame.gameObjects;

import java.util.ArrayList;

import castleGame.base.Inputs;
import castleGame.base.Sprite;
import castleGame.base.SpriteRender;
import castleGame.infoObjects.Settings;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Ost extends TroopsManager
{
	// VARIABLES	
	private Castle castleTarget;
	
	
	
	// CONSTRUCTORS
	public Ost() 
	{
		this(new int[Settings.NB_TROOP_TYPES], 1, 1);
	}
	
	public Ost(int initialArmy[], double x, double y) 
	{
		super(initialArmy, x, y);
	}

	
	
	// GETTERS AND SETTERS
	
	
	
	// INHERITED METHODS
	protected void updateThis() 
	{
		
	}
	
	protected void updateChilds()
	{
		super.updateChilds();
		// specific ost variable to update can be put here
	}
	
	
	
	// METHODS
}
