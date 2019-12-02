package castleGame;

import castleGame.Inputs;
import castleGame.Settings;
import castleGame.Inputs;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Ost extends Sprite implements TroopsManager, SpriteRender{

	private double minX;
	private double maxX;
	private double minY;
	private double maxY;

	private Inputs inputs;
	
	
	public Ost(Pane layer, Image image, double x, double y, Inputs inputs,String name, int money, int level) {
		super(layer, image, x, y);
		init();
	}
	
	private void init() {
		minX = 0 - getWidth() / 2.0;
		maxX = Settings.SCENE_WIDTH - getWidth() / 2.0;
		minY = 0 - getHeight() / 2.0;
		maxY = Settings.SCENE_HEIGHT - getHeight();
	}

	
}
