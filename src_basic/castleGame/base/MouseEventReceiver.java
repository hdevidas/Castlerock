package castleGame.base;

/**
 * This interface contain the methods to manage events coming from the mouse.
 * a sprite is needed to use them it extend spriteRender is extended.
 */
public interface MouseEventReceiver extends SpriteRender 
{
	// VARIABLES
	
	
	
	// CONSTRUCTORS
	
	

	// GETTERS AND SETTERS
	
	
	
	// INHERITED METHODS
	
	
	
	// METHODS
	/**
	 * The method which precise the behavior to have for this object when its sprite is clicked.
	 * This method should be called in the constructor and whenever its behavior is supposed to change.
	 */
	void setMouseEventResponse();
}
