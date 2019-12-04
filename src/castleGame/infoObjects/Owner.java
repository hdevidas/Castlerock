package castleGame.infoObjects;

public enum Owner
{
	Player("Joueureuse"),
	Computer("IA"),
	Neutral("Neutre");

	private String name;
	
	private Owner(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}
	
}
