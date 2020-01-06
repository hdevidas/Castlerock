package castleGame.base;

import java.util.BitSet;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import static javafx.scene.input.KeyCode.*;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Inputs 
{
	// VARIABLES
	private BitSet keyboardBitSet = new BitSet();

	private Scene scene = null;

	
	
	// CONSTRUCTORS
	public Inputs(Scene scene) {
		this.scene = scene;
	}
	
	
	
	// GETTERS AND SETTERS
	
	
	
	// INHERITED METHODS
	
	
	
	// METHODS

	//??
	public void addListeners() {
		scene.addEventFilter(KeyEvent.KEY_PRESSED, keyPressedEventHandler);
		scene.addEventFilter(KeyEvent.KEY_RELEASED, keyReleasedEventHandler);
	}

	//??
	public void removeListeners() {
		scene.removeEventFilter(KeyEvent.KEY_PRESSED, keyPressedEventHandler);
		scene.removeEventFilter(KeyEvent.KEY_RELEASED, keyReleasedEventHandler);
	}

	/**
	 * "Key Pressed" handler for all input events: register pressed key in the
	 * bitset
	 */
	private EventHandler<KeyEvent> keyPressedEventHandler = event -> {
		// register key down
		keyboardBitSet.set(event.getCode().ordinal(), true);
		event.consume();
	};

	/**
	 * "Key Released" handler for all input events: unregister released key in the
	 * bitset
	 */
	private EventHandler<KeyEvent> keyReleasedEventHandler = new EventHandler<KeyEvent>() {
		@Override
		public void handle(KeyEvent event) {
			// register key up
			keyboardBitSet.set(event.getCode().ordinal(), false);
			event.consume();
		}
	};

	private boolean is(KeyCode key) {
		return keyboardBitSet.get(key.ordinal());
	}
	
	/**
	 * Same result as the is() method but a second call to this event in a short timeframe will return false
	 * @param key : the key to verify
	 * @return whether this key has just been pressed or not
	 */
	private boolean isOnce(KeyCode key)
	{
		if (is(key))
		{
			keyboardBitSet.set(key.ordinal(), false);
			return true;
		}
		else
		{
			return false;
		}
	}

	public boolean isExit() { //Exit game detection with escape
		return isOnce(ESCAPE);
	}
	public boolean isPause() { //Pause game detection with escape
		return isOnce(SPACE);
	}
	public boolean isLevelUp() { 
		return isOnce(L);
	}
	public boolean isAttacksWithAll() { 
		return isOnce(A);
	}
	public boolean isAttacksWithCustom() { 
		return isOnce(C);
	}
	public boolean isBuildingPiquier() { 
		return isOnce(P);
	}
	public boolean isBuildingKnight() { 
		return isOnce(K);
	}
	public boolean isBuildingOnager() { 
		return isOnce(O);
	}
	
	
}
