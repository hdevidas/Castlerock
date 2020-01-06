package castleGame.gameObjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.List;
import java.util.Optional;

import castleGame.Main;
import castleGame.base.Inputs;
import castleGame.gameObjects.Map;
import castleGame.base.KeyboardInputsReceiver;
import castleGame.base.MouseEventReceiver;
import castleGame.base.Sprite;
import castleGame.base.SpriteRender;
import castleGame.infoObjects.Owner;
import castleGame.infoObjects.Settings;
import castleGame.infoObjects.TroopType;
import javafx.geometry.Point2D;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class Castle extends TroopsManager implements MouseEventReceiver, KeyboardInputsReceiver, SpriteRender{

	// VARIABLES
	// game variable
	private Sprite sprite;
	private Random rnd = new Random();
	
	// Sprites
	static Image doorImage	= new Image("/images/door.png", Settings.CASTLE_SIZE/5, Settings.CASTLE_SIZE/5, true, true);
	
	// this object variable
	static public Castle clicked;
	static public Castle lastPlayerClicked;
	static public Castle launchingOstFrom;
	static public Boolean isLaunchingOst = false;
	private static int[] playerTroopsToLaunch;
	private static boolean limitOstSpeed;
	
	static public ButtonType yes = new ButtonType("Yes");
	static public ButtonType no = new ButtonType("No");
	
	public List<Ost> castle_ost = new ArrayList<>();
	
	
	public Map map;
	private String name;
	public OrderManager orderManager;
	private int level;
	private int money;
	private double x;
	private double y;
	private int gate; // 1:Nord, 2:Est, 3:Sud, 4:Ouest 
	private double gate_x;
	private double gate_y;
	
	private Text piquierTxt = new Text();
	private Text onagerTxt = new Text();
	private Text knightTxt = new Text();
	private Text moneyTxt = new Text();
	private Text levelTxt = new Text();
	private Point2D coord;
	
	
	// CONSTRUCTORS
	public Castle(Sprite sprite, Map map, String name, Owner owner, int money, int level, int[] init_army, Point2D coord) {
		super(owner, init_army);

		this.map = map;
		this.sprite = sprite;
		this.money = money;
		this.level = level;
		this.name = name;
		this.coord = coord;
		
		this.orderManager = new OrderManager(this);
		
		//TODO replace x and y by coord.x and coord.y in the rest of this class and same for door coordinates
		x = coord.getX();
		y = coord.getY();
		
		// Door drawing
		int gate = rnd.nextInt(4 - 1 + 1) + 1;
		createDoor(gate);
		
		create_piquier_bar();
		create_knight_bar();
		create_onager_bar();
		create_money_bar();
		create_level_bar();
		
		
		setMouseEventResponse();
	}
	
	
	
	// GETTERS AND SETTERS
	
	public int getLevel() {
		return level;
	}
	
	public void setLevel(int level)
	{
		this.level = level;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
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
	
	public void setGate(int gate) {
		this.gate = gate;
	}
	
	public Point2D getCoord() 
	{
		Point2D newCoord = new Point2D(coord.getX(), coord.getY());
		return newCoord;
	}

	public Point2D getGateCoord() 
	{
		return new Point2D(gate_x, gate_y);
	}
	
	
	// INHERITED METHODS
	// KeyboardInputsReceiver
	public void processInputs()
	{	
		//Possible actions from player
		if (owner == Owner.Player)
		{
			// Level up
			if (Main.inputs.isLevelUp()) {
				orderManager.newLevelUpOrder();
			}
			// build piquier
			if (Main.inputs.isBuildingPiquier()) {
				orderManager.newBuildTroopOrder(TroopType.Piquier);
			}
			// build knight
			if (Main.inputs.isBuildingKnight()) {
				orderManager.newBuildTroopOrder(TroopType.Knight);
			}
			// build onager
			if (Main.inputs.isBuildingOnager()) {
				orderManager.newBuildTroopOrder(TroopType.Onager);
			}
			
			// attacks a castle with all troops
			if (Main.inputs.isAttacksWithAll()) {
				this.launchOst(this.getNbTroops());
			}
			// attack a castle with custom troops
			if (Main.inputs.isAttacksWithCustom()) {
				this.launchOst(null);

			}
		}
		
	}

	// MouseEventReceiver
	public void setMouseEventResponse()
	{
		sprite.getView().setOnMousePressed(e -> {
			clicked = this;
			if (this.owner == Owner.Player)
			{
				lastPlayerClicked = this;
			}
			if (isLaunchingOst && this != launchingOstFrom)
			{
				this.receiveOst();
			}
			e.consume();
		});
		
		// creating the context menu

		ContextMenu contextMenu = new ContextMenu();
		
		//Level up
		MenuItem levelUp = new MenuItem("Level up");
		levelUp.setAccelerator(KeyCombination.keyCombination("L"));
		levelUp.setOnAction(evt -> this.orderManager.newLevelUpOrder());
		
		//Send an Ost
		Menu createOstFrom = new Menu("Launch ost");
		MenuItem troopItemOstAll = new MenuItem("All");
		troopItemOstAll.setAccelerator(KeyCombination.keyCombination("A"));
		troopItemOstAll.setOnAction(evt -> this.launchOst(this.getNbTroops()));
		createOstFrom.getItems().addAll(troopItemOstAll);
		MenuItem troopItemOstCustom = new MenuItem("Custom");
		troopItemOstCustom.setAccelerator(KeyCombination.keyCombination("C"));
		troopItemOstCustom.setOnAction(evt -> this.launchOst(null));
		createOstFrom.getItems().addAll(troopItemOstCustom);
		
		
		//Add troops
		Menu newTroop = new Menu("Create new Troop");
		String tab[]= {"P","K", "O"};
		int i =0;
		for (TroopType troop : TroopType.values())
		{
			MenuItem troopItem = new MenuItem(troop.getName());
			troopItem.setOnAction(evt -> this.orderManager.newBuildTroopOrder(troop));
			troopItem.setAccelerator(KeyCombination.keyCombination(tab[i]));
			newTroop.getItems().addAll(troopItem);
			i=i+1;
		}
		
		//abort current order
		MenuItem abortCurrentOrder = new MenuItem("Abort current order");
		abortCurrentOrder.setOnAction(evt -> this.orderManager.abortCurrentOrder());
	
		contextMenu.getItems().addAll(abortCurrentOrder, levelUp, createOstFrom, newTroop);
		
		if (this.owner == Owner.Player)
		{
			sprite.getView().setOnContextMenuRequested(e -> 
			{
				contextMenu.show(sprite.getView(), e.getScreenX(), e.getScreenY());
			});
		}
	}

	// Sprite
	public void updateUI()
	{
		sprite.updateUI();		
	}
	
	// troopsManager
	protected void updateThis() 
	{
		super.updateThis();
		
		processInputs();
		
		updateUI();		

		orderManager.update();
		
		if (!this.has_got_troops()) {
		}

		if (!map.player_is_alive(Settings.CASTLE_NAME)){
			System.out.println("You lost.");
		}
		
		update_piquier_bar();
		update_chevalier_bar();
		update_onagre_bar();
		update_money_bar();
		update_level_bar();
		
	}
	
	protected void updateChilds() 
	{
		super.updateChilds();
		// specific castle variables to update can be put here
	}

	
	
	// METHODS	
	private void launchOst(int[] troopsToSend) 
	{	
		playerTroopsToLaunch = troopsToSend;
		launchingOstFrom = this;
		isLaunchingOst = true;
		//Display : "Click on the castle to launch the ost to..."
	}
	
	private void receiveOst() 
	{

		if (playerTroopsToLaunch == null)
		{
			playerTroopsToLaunch = launchingOstFrom.popupTroopsChoice();
		}
		else
		{
			limitOstSpeed = false;
		}
		orderManager.createOst(launchingOstFrom, this, playerTroopsToLaunch, limitOstSpeed);
		isLaunchingOst = false;
	}
	
	private int[] popupTroopsChoice()
	{
		Main.pause = true;
		int tab[] = new int[Settings.NB_TROOP_TYPES];

		//PIQUIER
		List<String> choices = new ArrayList<>();
		choices.add("0");
		int piquierNb = this.getNbTroop(TroopType.Piquier);
		if (piquierNb >=1 ) {
			choices.add("1");
        }
		if (piquierNb > 2) {
        	choices.add("2");
        }
		if (piquierNb > 5) {
        	choices.add("5");
        }
		if (piquierNb > 10) {
        	choices.add("10");
        }
		if (piquierNb > 20) {
			choices.add("20");
        }
		if (piquierNb > 50) {
			choices.add("50");
        }
		choices.add(Integer.toString(piquierNb));

		ChoiceDialog<String> dialog = new ChoiceDialog<>("0", choices);
		dialog.setTitle("Choice Dialog");
		dialog.setHeaderText("You got "+piquierNb+" piquier(s).");
		dialog.setContentText("How many do you want to send ?");

		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();

		// The Java 8 way to get the response value (with lambda expression).
		result.ifPresent(nb -> tab[0] = Integer.parseInt(nb));
		
		//KNIGHT
		List<String> choicesB = new ArrayList<>();
		choicesB.add("0");
		int knightNb = this.getNbTroop(TroopType.Knight);
		if (knightNb >1 ) {
			choicesB.add("1");
        }
		if (knightNb > 2) {
        	choicesB.add("2");
        }
		if (knightNb > 5) {
        	choicesB.add("5");
        }
		if (knightNb > 10) {
        	choicesB.add("10");
        }
		if (knightNb > 20) {
			choicesB.add("20");
        }
		if (knightNb > 50) {
			choicesB.add("50");
        }
		choicesB.add(Integer.toString(knightNb));

		ChoiceDialog<String> dialog2 = new ChoiceDialog<>("0", choices);
		dialog2.setTitle("Choice Dialog");
		dialog2.setHeaderText("You got "+knightNb+" knight(s).");
		dialog2.setContentText("How many do you want to send ?");

		// Traditional way to get the response value.
		Optional<String> result2 = dialog2.showAndWait();

		// The Java 8 way to get the response value (with lambda expression).
		result2.ifPresent(nb -> tab[1] = Integer.parseInt(nb));
		
		//ONAGER
		List<String> choicesC = new ArrayList<>();
		choicesC.add("0");
		int onagerNb = this.getNbTroop(TroopType.Onager);
		if (onagerNb >1 ) {
			choicesC.add("1");
        }
		if (onagerNb > 2) {
        	choicesC.add("2");
        }
		if (onagerNb > 5) {
        	choicesC.add("5");
        }
		if (onagerNb > 10) {
        	choicesC.add("10");
        }
		if (onagerNb > 20) {
			choicesC.add("20");
        }
		if (onagerNb > 50) {
			choicesC.add("50");
        }
		choicesC.add(Integer.toString(onagerNb));

		ChoiceDialog<String> dialog3 = new ChoiceDialog<>("0", choicesC);
		dialog3.setTitle("Choice Dialog");
		dialog3.setHeaderText("You got "+onagerNb+" onager(s).");
		dialog3.setContentText("How many do you want to send ?");

		// Traditional way to get the response value.
		Optional<String> result3 = dialog3.showAndWait();

		// The Java 8 way to get the response value (with lambda expression).
		result3.ifPresent(nb -> tab[2] = Integer.parseInt(nb));
	
		
		//CHOICE SPEED
		Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Select");
        alert.setHeaderText("Do you want to send all your units at the same speed ?");
        
        // Remove default ButtonTypes
        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(yes,no); 
 
        // option != null.
        Optional<ButtonType> option = alert.showAndWait();
 
        if (option.get() == yes) {
        	limitOstSpeed = true;
        }
        else {
        	limitOstSpeed = false;
        }
		

		Main.pause = false;
		return tab;
	}
	
	void changeOwner(Owner newOwner) 
	{
		this.setOwner(newOwner);
		sprite = new Sprite(Map.playfieldLayer, newOwner.castleImage, this.getX(), this.getY());
		Sprite doorSprite = new Sprite(Map.playfieldLayer, doorImage, gate_x, gate_y);
		this.createDoor(gate); // TODO is it really necessary?
		setMouseEventResponse();
		//TODO change its list in map.castles
	}
	
	public boolean is_the_same_name(String name) {
		return this.name.equals(name);
	}
	
	public boolean is_player() {
		return (this.owner == Owner.Player);
	}	
	
	public void create_piquier_bar() {
		String nbPiquier = "P : " + Integer.toString(getNbTroop(TroopType.Piquier));
		HBox piquierBar = new HBox();
		piquierTxt.setText(nbPiquier);
		piquierBar.getChildren().addAll(piquierTxt);
		piquierBar.getStyleClass().add("piquier");
		piquierBar.relocate(x+Settings.CASTLE_SIZE *2/7, y+Settings.CASTLE_SIZE *2/10);
		Main.root.getChildren().add(piquierBar);
	}
	
	private void update_piquier_bar() {
		piquierTxt.setText("P : "+Integer.toString(getNbTroop(TroopType.Piquier)));                 
	}
	
	public void create_knight_bar() {
		String nbKnight = "K : " + Integer.toString(getNbTroop(TroopType.Knight));
		HBox knightBar = new HBox();
		knightTxt.setText(nbKnight);
		knightBar.getChildren().addAll(knightTxt);
		knightBar.getStyleClass().add("knight");
		knightBar.relocate(x+Settings.CASTLE_SIZE *2/7, y+Settings.CASTLE_SIZE *3/10);
		Main.root.getChildren().add(knightBar);
	}
	
	private void update_chevalier_bar() {
		knightTxt.setText("K : "+Integer.toString(getNbTroop(TroopType.Knight)));                 
	}
	
	public void create_onager_bar() {
		String nbOnager = "O : " + Integer.toString(getNbTroop(TroopType.Onager));
		HBox onagerBar = new HBox();
		onagerTxt.setText(nbOnager);
		onagerBar.getChildren().addAll(onagerTxt);
		onagerBar.getStyleClass().add("onager");
		onagerBar.relocate(x+Settings.CASTLE_SIZE *2/7, y+Settings.CASTLE_SIZE *4/10);
		Main.root.getChildren().add(onagerBar);
	}
	
	private void update_onagre_bar() {
		onagerTxt.setText("O : "+Integer.toString(getNbTroop(TroopType.Onager)));                 
	}
	
	public void create_money_bar() {
		String nbMoney = Integer.toString(this.getMoney())+" F";
		HBox moneyBar = new HBox();
		moneyTxt.setText(nbMoney);
		moneyBar.getChildren().addAll(moneyTxt);
		moneyBar.getStyleClass().add("money");
		moneyBar.relocate(x+Settings.CASTLE_SIZE *2/7, y+Settings.CASTLE_SIZE *7/10);
		Main.root.getChildren().add(moneyBar);
	}
	
	private void update_money_bar() {
		moneyTxt.setText(Integer.toString(this.getMoney())+ " F");                 
	}
	
	public void create_level_bar() {
		String nbLevel = "Lvl: " + Integer.toString(this.getLevel());
		HBox levelBar = new HBox();
		levelTxt.setText(nbLevel);
		levelBar.getChildren().addAll(levelTxt);
		levelBar.getStyleClass().add("level");
		levelBar.relocate(x+Settings.CASTLE_SIZE *2/7, y+Settings.CASTLE_SIZE *6/10);

		Main.root.getChildren().add(levelBar);
	}
	
	private void update_level_bar() {
		levelTxt.setText("Lvl : "+Integer.toString(this.getLevel()));                 
	}

	
	void createDoor(int gate) {
		if (gate == 1) {
			this.gate_x = x+(Settings.CASTLE_SIZE/5)*2;
			this.gate_y = y;
			Sprite doorSprite = new Sprite(Map.playfieldLayer, doorImage, gate_x, gate_y);
			//this.setSprite(doorSprite);
		}
		else if (gate ==2) {
			this.gate_x = x+(Settings.CASTLE_SIZE/5)*4;
			this.gate_y = y+(Settings.CASTLE_SIZE/5)*2;
			Sprite doorSprite = new Sprite(Map.playfieldLayer, doorImage, gate_x, gate_y);
			//this.setSprite(doorSprite);
		}
		else if (gate ==3) {
			this.gate_x = x+(Settings.CASTLE_SIZE/5)*2;
			this.gate_y = y+(Settings.CASTLE_SIZE/5)*4;
			Sprite doorSprite = new Sprite(Map.playfieldLayer, doorImage, gate_x, gate_y);
			//this.setSprite(doorSprite);
		}
		else if (gate ==4) {
			this.gate_x = x;
			this.gate_y = y+(Settings.CASTLE_SIZE/5)*2;
			Sprite doorSprite = new Sprite(Map.playfieldLayer, doorImage, gate_x,gate_y);
			//this.setSprite(doorSprite);
		}
	}



}
