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

	public boolean isExit() { //Exit game detection with escape
		return is(ESCAPE);
	}
	public boolean isPause() { //Pause game detection with escape
		return is(SPACE);
	}
	public boolean isLevelUp() { 
		return is(L);
	}
	public boolean isAttacksWithAll() { 
		return is(A);
	}
	public boolean isAttacksWithCustom() { 
		return is(C);
	}
	public boolean isBuildingPiquier() { 
		return is(P);
	}
	public boolean isBuildingKnight() { 
		return is(K);
	}
	public boolean isBuildingOnager() { 
		return is(O);
	}
	
	
}
