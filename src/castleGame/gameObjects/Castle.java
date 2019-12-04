package castleGame.gameObjects;

import java.util.ArrayList;
import java.util.List;

import castleGame.Main;
import castleGame.base.Inputs;
import castleGame.base.KeyboardInputsReceiver;
import castleGame.base.MouseEventReceiver;
import castleGame.base.Sprite;
import castleGame.base.SpriteRender;
import castleGame.infoObjects.Owner;
import castleGame.infoObjects.Settings;
import castleGame.infoObjects.TroopType;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.image.Image;

public class Castle extends TroopsManager implements MouseEventReceiver, KeyboardInputsReceiver, SpriteRender{

	// VARIABLES
	// game variable
	private Sprite sprite;
	
	// Sprites TODO peut-être pas leur place définitive...
	static Image iaCastleImage		= new Image("/images/playerCastle.png",  Settings.CASTLE_SIZE, Settings.CASTLE_SIZE, true, true);
	static Image neutralCastleImage	= new Image("/images/iaCastle.png", 	 Settings.CASTLE_SIZE, Settings.CASTLE_SIZE, true, true);
	static Image playerCastleImage	= new Image("/images/neutralCastle.png", Settings.CASTLE_SIZE, Settings.CASTLE_SIZE, true, true);
	
	// this object variable
	static public Castle clicked;
	private String name;
	private int level;
	private int money;
	
	
	// CONSTRUCTOR
	
	public Castle(Sprite sprite, String name, Owner owner, int money, int level, int[] army_life) {
		super(army_life);
		
		this.sprite = sprite;
		
		this.money = money;
		this.level = level;
		this.name = name;
		this.owner = owner;
		
		
		setMouseEventResponse();
	}
	
	
	
	// GETTERS AND SETTERS
	
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}
	
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Owner getOwner() {
		return owner;
	}

	// INTERFACE METHODS
	// KeyboardInputsReceiver
	public void processInputs(Inputs inputs)
	{
		if (owner == Owner.Player)
		{
			if (inputs.isLevelUp()) {
				level_up();
			}
			if (inputs.isBuilding()) {
				build_troop(TroopType.Piquier);
			}
		}
		
	}

	
	// MouseEventReceiver
	public void setMouseEventResponse()
	{
		sprite.getView().setOnMousePressed(e -> {
			clicked = this;
			e.consume();
		});
	}
	
	// Sprite
	public void updateUI()
	{
		sprite.updateUI();		
	}
	
	// troopsManager


	
	
	
	// OBJECT METHODS

	public void level_up() { //MONTE D'UN NIVEAU LE CHATEAU
		if (getMoney() >= (getLevel()*1000)) {
			setMoney(getMoney()-getLevel()*1000);
			setLevel(getLevel()+1);
		}
	}

	public void build_troop(TroopType troopType) { //CONSTRUIS UNE TROUPE
		if (money >= troopType.getProductionCost() ) {
			money -= troopType.getProductionCost() ;
			this.addTroop(troopType);
		}
	}
	
	public void money_up() { //GENERATION FLORINS
		setMoney(getMoney() + getLevel());
	}
	
	public boolean is_the_same_name(String name) {
		if (this.getName() == name) {
			return true;
		}
		return false;
	}

	
	public boolean is_player() {
		return (this.owner == Owner.Player);
	}






}
