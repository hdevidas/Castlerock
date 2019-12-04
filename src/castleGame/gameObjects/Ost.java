package castleGame.gameObjects;

import castleGame.base.Inputs;
import castleGame.base.Sprite;
import castleGame.base.SpriteRender;
import castleGame.infoObjects.Settings;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Ost extends TroopsManager implements SpriteRender{

	private double minX;
	private double maxX;
	private double minY;
	private double maxY;
	
	private Sprite sprite;
	private Castle castleTarget;
	
	public Ost(Sprite sprite) 
	{
		this(sprite, new int[Settings.NB_TROOP_TYPES]);
	}
	
	public Ost(Sprite sprite, int initialArmy[]) 
	{
		super(initialArmy);
		
		this.sprite = sprite;
		
		minX = 0 - sprite.getWidth() / 2.0;
		maxX = Settings.SCENE_WIDTH - sprite.getWidth() / 2.0;
		minY = 0 - sprite.getHeight() / 2.0;
		maxY = Settings.SCENE_HEIGHT - sprite.getHeight();
	}

	public void updateUI()
	{
		sprite.updateUI();
	}
}
