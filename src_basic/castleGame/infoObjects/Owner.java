package castleGame.infoObjects;

import javafx.scene.image.Image;

public enum Owner
{
	Player(Settings.PLAYER_NAME, new Image("/images/black.png", Settings.CASTLE_SIZE, Settings.CASTLE_SIZE, true, true)),
	Neutral("Neutre", new Image("/images/white.png", Settings.CASTLE_SIZE, Settings.CASTLE_SIZE, true, true)),
	IA0("Ordi1", new Image("/images/green.png", Settings.CASTLE_SIZE, Settings.CASTLE_SIZE, true, true)),
	IA1("Ordi2", new Image("/images/red.png", Settings.CASTLE_SIZE, Settings.CASTLE_SIZE, true, true)),
	IA2("Ordi3", new Image("/images/yellow.png", Settings.CASTLE_SIZE, Settings.CASTLE_SIZE, true, true)),
	IA3("Ordi4", new Image("/images/blue.png", Settings.CASTLE_SIZE, Settings.CASTLE_SIZE, true, true)),
	IA4("Ordi5", new Image("/images/pink.png", Settings.CASTLE_SIZE, Settings.CASTLE_SIZE, true, true)),
	IA5("Ordi6", new Image("/images/marine.png", Settings.CASTLE_SIZE, Settings.CASTLE_SIZE, true, true));
	
	
	// VARIABLES
	private String name;
	public Image castleImage;
	
	
	
	// CONSTRUCTORS
	private Owner(String name, Image castleImage)
	{
		this.name = name;
		this.castleImage = castleImage;
		
	}

	
	
	// GETTERS AND SETTERS
	
	
	
	// INHERITED METHODS
	
	
	
	// METHODS
	public String getName()
	{
		return name;
	}
	
}
