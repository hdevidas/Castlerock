package castleGame.infoObjects;

import javafx.scene.image.Image;
import castleGame.Main;

/**
 * An enum describing the different possible Owner of any troopType.
 *
 */
public enum Owner
{
	/**
	 * The player Owner
	 */
	Player(Settings.CASTLE_NAME, new Image("/images/black.png", Settings.CASTLE_SIZE, Settings.CASTLE_SIZE, true, true)),
	/**
	 * The neutral Owner, this owner is not supposed to attack
	 */
	Neutral("Neutral", new Image("/images/white.png", Settings.CASTLE_SIZE, Settings.CASTLE_SIZE, true, true)),
	/**
	 * The 1st possible IA Owner
	 */
	IA0("Computer1", new Image("/images/green.png", Settings.CASTLE_SIZE, Settings.CASTLE_SIZE, true, true)),
	/**
	 * The 2nd possible IA Owner
	 */
	IA1("Computer2", new Image("/images/red.png", Settings.CASTLE_SIZE, Settings.CASTLE_SIZE, true, true)),
	/**
	 * The 3rd possible IA Owner
	 */
	IA2("Computer3", new Image("/images/yellow.png", Settings.CASTLE_SIZE, Settings.CASTLE_SIZE, true, true)),
	/**
	 * The 4th possible IA Owner
	 */
	IA3("Computer4", new Image("/images/blue.png", Settings.CASTLE_SIZE, Settings.CASTLE_SIZE, true, true)),
	/**
	 * The 5th possible IA Owner
	 */
	IA4("Computer5", new Image("/images/pink.png", Settings.CASTLE_SIZE, Settings.CASTLE_SIZE, true, true)),
	/**
	 * The 6th possible IA Owner
	 */
	IA5("Computer6", new Image("/images/marine.png", Settings.CASTLE_SIZE, Settings.CASTLE_SIZE, true, true));
	
	
	// VARIABLES
	/*
	 * The name of the Owner
	 */
	private String name;
	/**
	 * The image to give to the castle this Owner has
	 */
	public Image castleImage;
	
	
	
	// CONSTRUCTORS
	/**
	 * The constructor for an Owner
	 * 
	 * @param name : the name this Owner should have
	 * @param castleImage : the Image the castles of this owner should display
	 */
	private Owner(String name, Image castleImage)
	{
		this.name = name;
		this.castleImage = castleImage;
		
	}

	
	
	// GETTERS AND SETTERS
	
	
	
	// INHERITED METHODS
	
	
	
	// METHODS
	/**
	 * Get this Owner name
	 * 
	 * @return This Owner name
	 */
	public String getName()
	{
		return name;
	}
	
}
