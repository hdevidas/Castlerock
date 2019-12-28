package castleGame.infoObjects;

public enum Owner
{
	Player("Joueureuse"),
	Computer("IA"),
	Neutral("Neutre");

	// VARIABLES
	private String name;
	
	
	
	// CONSTRUCTORS
	private Owner(String name)
	{
		this.name = name;
	}

	
	
	// GETTERS AND SETTERS
	
	
	
	// INHERITED METHODS
	
	
	
	// METHODS
	public String getName()
	{
		return name;
	}
	
}
