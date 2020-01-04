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
	private Castle castleTarget;
	public int compteur = 0;
	
	
	// CONSTRUCTORS
	/*Je ne comprends pas 
	 * public Ost() 
	{
		this(new int[Settings.NB_TROOP_TYPES], 1, 1);
	}
	*/
	
	public Ost(int initialArmy[], double x, double y, Castle castleTarget) 
	{
		super(initialArmy, x, y);
		this.castleTarget = castleTarget;
	}

	
	
	// GETTERS AND SETTERS
	
	
	
	// INHERITED METHODS
	protected void updateThis() 
	{
		System.out.println("VOUS ETES ICICICICICICIICICICICIICICIIC");
		System.out.println(compteur);
		compteur = compteur +1;
	}
	
	protected void updateChilds()
	{
		super.updateChilds();
		System.out.println("VOUS ETES LALALALALALLALAALAAAAAAAAAAALALALAL");
		System.out.println(compteur);
		compteur = compteur +1;
		// specific ost variable to update can be put here
	}
	
	
	
	// METHODS
}
