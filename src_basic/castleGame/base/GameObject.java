package castleGame.base;

// this class is used to ensure that every game object is updated at each turn
public abstract class GameObject 
{	
	// VARIABLES
	
	
	
	// CONSTRUCTORS
	// this method is called at each turn and call the methods to update this object and the game objects it contains.
	public void update()
	{
		updateThis();
		updateChilds();
	}
	
	
	
	// GETTERS AND SETTERS
	
	
	
	// INHERITED METHODS
	
	
	
	// METHODS
	// this method is called at each turn must contain the the updates to do for this object.
	protected abstract void updateThis();
	
	// this one is also called at each turn and is used to call the update function in the game objects contained in this object.
	protected abstract void updateChilds();
}
