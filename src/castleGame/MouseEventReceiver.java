package castleGame;

// This interface contain the methods to manage events comming from the mouse.
// a sprite is needed to use them thus spriteRender is extended.
public interface MouseEventReceiver extends SpriteRender 
{
	void setMouseEventResponse();
}
