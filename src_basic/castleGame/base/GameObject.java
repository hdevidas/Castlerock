package castleGame.base;

/**
 * Parent class of every gameObject
 * 
 *  <p>
 *  This class is used to ensure that every object that extends it is updated at each turn and update its 
 *  variable that are also gameObject
 * 
 */
public abstract class GameObject 
{	
	// VARIABLES
	
	
	
	// CONSTRUCTORS
	/**
	 * The method called at each turn
	 * <p>
	 * This method calls the two methods to update this object and the game objects it contains.
	 */
	public void update()
	{
		updateThis();
		updateChilds();
	}
	
	
	
	// GETTERS AND SETTERS
	
	
	
	// INHERITED METHODS
	
	
	
	// METHODS
	/**
	 * The method used to update this object variable
	 * <p>
	 * this method is called at each turn by the update() method, it must contain the updates to do for this object at each turn.
	 */
	protected abstract void updateThis();
	
	/**
	 * The method used to call the update() method on this object's gameObjects
	 * <p>
	 * This methods is also called at each turn and must contain calls to this object's gameObjects' update function.
	 */
	protected abstract void updateChilds();
}
