package castleGame.gameObjects;

import java.util.ArrayList;
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
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class Castle extends TroopsManager implements MouseEventReceiver, KeyboardInputsReceiver, SpriteRender{

	// VARIABLES
	// game variable
	private Sprite sprite;
	private Random rnd = new Random();
	
	// Sprites TODO peut-être pas leur place définitive...
	static Image doorImage	= new Image("/images/door.png", Settings.CASTLE_SIZE/5, Settings.CASTLE_SIZE/5, true, true);
	
	// this object variable
	static public Castle clicked;
	static public Castle lastPlayerClicked;
	static public Castle launchingOstFrom;
	static public Boolean isLaunchingOst = false;
	
	static public int nbPiquierOst = 0;
	static public int nbKnightOst = 0;
	static public int nbOnagerOst = 0;
	static public ButtonType zero = new ButtonType("0");
	static public ButtonType one = new ButtonType("1");
    static public ButtonType two = new ButtonType("2");
    static public ButtonType five = new ButtonType("5");
    static public ButtonType ten = new ButtonType("10");
    static public ButtonType all = new ButtonType("ALL");
	
	
	public List<Ost> castle_ost = new ArrayList<>();
	
	
	private Map map;
	private String name;
	private OrderManager orderManager;
	private int level;
	private int money;
	private double x;
	private double y;
	private int gate; // 1:Nord, 2:Est, 3:Sud, 4:Ouest 
	private double gate_x;
	private double gate_y;
	
	private Text piquierTxt = new Text();
	private Text onagreTxt = new Text();
	private Text chevalierTxt = new Text();
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
		
		// Dessin de la Porte
		int gate = rnd.nextInt(4 - 1 + 1) + 1;
		createDoor(gate);
		
		create_piquier_bar();
		create_chevalier_bar();
		create_onagre_bar();
		create_money_bar();
		create_level_bar();
		
		
		setMouseEventResponse();
	}
	
	
	
	// GETTERS AND SETTERS
	
	public int getLevel() {
		return level;
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
		//actions possibles du joueur :
		if (owner == Owner.Player)
		{
			// monter d'un niveau
			if (Main.inputs.isLevelUp()) {
				orderManager.newLevelUpOrder();
			}
			// construire un piquier
			if (Main.inputs.isBuilding()) {
				orderManager.newBuildTroopOrder(TroopType.Piquier);
			}
			// attaquer un chateau x
			if (Main.inputs.isAttacks() && clicked != lastPlayerClicked && this == lastPlayerClicked) {
				this.createOst(lastPlayerClicked, clicked, nbPiquierOst, nbKnightOst, nbOnagerOst);
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
				levelUp.setOnAction(evt -> this.orderManager.newLevelUpOrder());
				
				//Send an Ost
				Menu createOstFrom = new Menu("Launch ost");
				MenuItem troopItemOstAll = new MenuItem("All");
				troopItemOstAll.setOnAction(evt -> this.launchOst());
				createOstFrom.getItems().addAll(troopItemOstAll);
				MenuItem troopItemOstCustom = new MenuItem("Custom");
				
				troopItemOstCustom.setOnAction(evt -> this.popupPiquierChoice());
				//troopItemOstCustom.setOnAction(evt -> this.launchOst(1,1,1));
				createOstFrom.getItems().addAll(troopItemOstCustom);
				
				//Add troops
				Menu newTroop = new Menu("Create new Troop");
				for (TroopType troop : TroopType.values())
				{
					MenuItem troopItem = new MenuItem(troop.getName());
					troopItem.setOnAction(evt -> this.orderManager.newBuildTroopOrder(troop));
					newTroop.getItems().addAll(troopItem);
				}
			
				contextMenu.getItems().addAll(levelUp, createOstFrom, newTroop);
		
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
		
		this.money_up();
		
		updateUI();		

		orderManager.update();
		
		if (!this.has_got_troops()) {
			//System.out.println("un de vos chateau n'a plus d'unités, il est vulnérable.");
			// mis en commentaire pour l'instant parce que sinon ça flood la console...
		}

		if (!map.player_is_alive(Settings.PLAYER_NAME)){
			System.out.println("Vous avez perdus.");
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
	public void level_up() { //MONTE D'UN NIVEAU LE CHATEAU
		level ++;
	}
	
	public void money_up() { //GENERATION FLORINS
		setMoney(getMoney() + getLevel()*10);
	}

	public int getLevelUpTime() 
	{
		return ((this.getLevel() + 1) * 50) + 100;
	}
	
	public int getLevelUpCost()
	{
		return ((this.getLevel() + 1) * 1000);
	}
	
	private void launchOst() 
	{	
		nbPiquierOst = this.getNbTroop(TroopType.Piquier);
		nbKnightOst = this.getNbTroop(TroopType.Knight);
		nbOnagerOst = this.getNbTroop(TroopType.Onager);
		launchingOstFrom = this;
		isLaunchingOst = true;
		//Display : "Click on the castle to launch the ost to..."
	}
	
	private void launchOst(int p, int k, int o) 
	{	
		nbPiquierOst = p;
		nbKnightOst = k;
		nbOnagerOst = o;
		launchingOstFrom = this;
		isLaunchingOst = true;
		//Display : "Click on the castle to launch the ost to..."
	}

	private void receiveOst() 
	{
		createOst(launchingOstFrom, this, nbPiquierOst, nbKnightOst, nbOnagerOst);
		isLaunchingOst = false;
	}
	
	private void createOst(Castle castleFrom, Castle castleTo, int p, int k, int o) 
	{
		// TODO : modify to actually create an ost and not simulate a direct attack between castles
		//castleFrom.fakeAttackPlaceHolder(castleFrom, castleTo);
		Ost ost = new Ost(map, castleFrom, castleTo);
		int tab[]= {p,k,o};
		castleFrom.orderManager.startLaunchingNewOst(ost, tab);
	}
	
	/*
	private void fakeAttackPlaceHolder(Castle castleFrom, Castle castleTo)
	{
		//this only works if castleFrom has more troops than castleTo for every troopType... (good enough for a placeholder)
		for (TroopType troopType : TroopType.values()) 
		{
			while (castleFrom.has_got_troops(troopType) && castleTo.has_got_troops(troopType)) {
				castleFrom.removeTroop(troopType);
				castleTo.removeTroop(troopType);
			}
		}
		
		// The attacking castle is always the winner if it has some troops left (good enough for a placeholder)
		if (castleFrom.has_got_troops()) 
		{
			System.out.println("Vous avez détruis un chateau.");
			
			castleTo.changeOwner(castleFrom.owner);
			
			System.out.println("Ce chateau porte maintenant votre nom.");
			System.out.println("Ce chateau peut maintenant être utilisé comme si c'était le votre.");
		}
		else
		{
			System.out.println("Vous avez perdus cette attaque... reformez des troupes et relancez-vous!");
		}
	}*/
	
	void changeOwner(Owner newOwner) 
	{
		this.setOwner(newOwner);
		sprite = new Sprite(Map.playfieldLayer, newOwner.castleImage, this.getX(), this.getY());
		Sprite doorSprite = new Sprite(Map.playfieldLayer, doorImage, gate_x, gate_y);
		this.createDoor(gate); // TODO is it really necessary?
		setMouseEventResponse();
	}
	
	public boolean is_the_same_name(String name) {
		return this.name.equals(name);
	}
	
	public boolean is_player() {
		return (this.owner == Owner.Player);
	}	
	
	public void create_piquier_bar() {
		String nbPiquier = "Piquiers: " + Integer.toString(getNbTroop(TroopType.Piquier));
		HBox piquierBar = new HBox();
		piquierTxt.setText(nbPiquier);
		piquierBar.getChildren().addAll(piquierTxt);
		piquierBar.getStyleClass().add("piquier");
		piquierBar.relocate(x+Settings.CASTLE_SIZE *2/7, y+Settings.CASTLE_SIZE *2/10);
		//piquierBar.setPrefSize(Settings.SCENE_WIDTH, Settings.STATUS_BAR_HEIGHT); // inutile ?
		Main.root.getChildren().add(piquierBar);
	}
	
	private void update_piquier_bar() {
		piquierTxt.setText("Piquiers: "+Integer.toString(getNbTroop(TroopType.Piquier)));                 
	}
	
	public void create_chevalier_bar() {
		String nbChevalier = "Chevaliers: " + Integer.toString(getNbTroop(TroopType.Knight));
		HBox chevalierBar = new HBox();
		chevalierTxt.setText(nbChevalier);
		chevalierBar.getChildren().addAll(chevalierTxt);
		chevalierBar.getStyleClass().add("chevalier");
		chevalierBar.relocate(x+Settings.CASTLE_SIZE *2/7, y+Settings.CASTLE_SIZE *3/10);
		Main.root.getChildren().add(chevalierBar);
	}
	
	private void update_chevalier_bar() {
		chevalierTxt.setText("Chevaliers: "+Integer.toString(getNbTroop(TroopType.Knight)));                 
	}
	
	public void create_onagre_bar() {
		String nbOnagre = "Onagres: " + Integer.toString(getNbTroop(TroopType.Onager));
		HBox onagreBar = new HBox();
		onagreTxt.setText(nbOnagre);
		onagreBar.getChildren().addAll(onagreTxt);
		onagreBar.getStyleClass().add("onagre");
		onagreBar.relocate(x+Settings.CASTLE_SIZE *2/7, y+Settings.CASTLE_SIZE *4/10);
		Main.root.getChildren().add(onagreBar);
	}
	
	private void update_onagre_bar() {
		onagreTxt.setText("Onagres: "+Integer.toString(getNbTroop(TroopType.Onager)));                 
	}
	
	public void create_money_bar() {
		String nbMoney = "Florins: " + Integer.toString(this.getMoney());
		HBox moneyBar = new HBox();
		moneyTxt.setText(nbMoney);
		moneyBar.getChildren().addAll(moneyTxt);
		moneyBar.getStyleClass().add("money");
		moneyBar.relocate(x+Settings.CASTLE_SIZE *2/7, y+Settings.CASTLE_SIZE *7/10);
		Main.root.getChildren().add(moneyBar);
	}
	
	private void update_money_bar() {
		moneyTxt.setText("Florins: "+Integer.toString(this.getMoney()));                 
	}
	
	public void create_level_bar() {
		String nbLevel = "Niveau: " + Integer.toString(this.getLevel());
		HBox levelBar = new HBox();
		levelTxt.setText(nbLevel);
		levelBar.getChildren().addAll(levelTxt);
		levelBar.getStyleClass().add("level");
		levelBar.relocate(x+Settings.CASTLE_SIZE *2/7, y+Settings.CASTLE_SIZE *6/10);

		Main.root.getChildren().add(levelBar);
	}
	
	private void update_level_bar() {
		levelTxt.setText("Niveau: "+Integer.toString(this.getLevel()));                 
	}
	
	void popupPiquierChoice() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		int piquierNb = this.getNbTroop(TroopType.Piquier);
        alert.setTitle("Select");
        alert.setHeaderText("You got "+piquierNb+" piquier(s), how many do you want to send ?");
        
        // Remove default ButtonTypes
        alert.getButtonTypes().clear();
         
        if (piquierNb > 10) {
        	alert.getButtonTypes().addAll(zero,one,two,five,ten,all,all);
        } else if (piquierNb >= 10) {
        	alert.getButtonTypes().addAll(zero,one,two,five,ten,all);
        } else if (piquierNb >= 5) {
        	alert.getButtonTypes().addAll(zero,one,two,five,all);
        } else if (piquierNb >= 2) {
        	alert.getButtonTypes().addAll(zero,one,two,all);
        } else if (piquierNb >= 1) {
        	alert.getButtonTypes().addAll(zero,one);
        } else {
        	alert.getButtonTypes().addAll(zero);
        }
 
        // option != null.
        Optional<ButtonType> option = alert.showAndWait();
 
        if (option.get() == zero) {
        	this.popupKnightChoice(0);
        } else if (option.get() == one) {
        	this.popupKnightChoice(1);
        } else if (option.get() == two) {
        	this.popupKnightChoice(2);
        } else if (option.get() == five) {
        	this.popupKnightChoice(5);
        } else if (option.get() == ten) {
        	this.popupKnightChoice(10);
        } else if (option.get() == all) {
        	this.popupKnightChoice(this.getNbTroop(TroopType.Piquier));
        } else {
            System.out.println("erreur");
        }        
	}
	
	void popupKnightChoice(int p) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		int knightNb = this.getNbTroop(TroopType.Knight);
        alert.setTitle("Select");
        alert.setHeaderText("You got "+knightNb+" knight(s), how many do you want to send ?");
        
        // Remove default ButtonTypes
        alert.getButtonTypes().clear();

        if (knightNb > 10) {
        	alert.getButtonTypes().addAll(zero,one,two,five,ten,all);
        } else if (knightNb >= 10) {
        	alert.getButtonTypes().addAll(zero,one,two,five,ten,all);
        } else if (knightNb >= 5) {
        	alert.getButtonTypes().addAll(zero,one,two,five,all);
        } else if (knightNb >= 2) {
        	alert.getButtonTypes().addAll(zero,one,two,all);
        } else if (knightNb >= 1) {
        	alert.getButtonTypes().addAll(zero,one);
        } else {
        	alert.getButtonTypes().addAll(zero);
        }
 
        // option != null.
        Optional<ButtonType> option = alert.showAndWait();
 
        if (option.get() == zero) {
        	this.popupOnagerChoice(p,0);
        } else if (option.get() == one) {
        	this.popupOnagerChoice(p,1);
        } else if (option.get() == two) {
        	this.popupOnagerChoice(p,2);
        } else if (option.get() == five) {
        	this.popupOnagerChoice(p,5);
        } else if (option.get() == ten) {
        	this.popupOnagerChoice(p,10);
        } else if (option.get() == all) {
        	this.popupOnagerChoice(p,this.getNbTroop(TroopType.Piquier));
        } else {
            System.out.println("erreur");
        }        
	}
	
	void popupOnagerChoice(int p, int k) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		int onagerNb = this.getNbTroop(TroopType.Onager);
        alert.setTitle("Select");
        alert.setHeaderText("You got "+onagerNb+" onager(s), how many do you want to send ?");
        
        // Remove default ButtonTypes
        alert.getButtonTypes().clear();
 
        if (onagerNb > 10) {
        	alert.getButtonTypes().addAll(zero,one,two,five,ten,all);
        } else if (onagerNb >= 10) {
        	alert.getButtonTypes().addAll(zero,one,two,five,ten,all);
        } else if (onagerNb >= 5) {
        	alert.getButtonTypes().addAll(zero,one,two,five,all);
        } else if (onagerNb >= 2) {
        	alert.getButtonTypes().addAll(zero,one,two,all);
        } else if (onagerNb >= 1) {
        	alert.getButtonTypes().addAll(zero,one);
        } else {
        	alert.getButtonTypes().addAll(zero);
        }
 
        // option != null.
        Optional<ButtonType> option = alert.showAndWait();
 
        if (option.get() == zero) {
        	this.launchOst(p,k,0);
        } else if (option.get() == one) {
        	this.launchOst(p,k,1);
        } else if (option.get() == two) {
        	this.launchOst(p,k,2);
        } else if (option.get() == five) {
        	this.launchOst(p,k,5);
        } else if (option.get() == ten) {
        	this.launchOst(p,k,10);
        } else if (option.get() == all) {
        	this.launchOst(p,k,(this.getNbTroop(TroopType.Onager)));
        } else {
            System.out.println("erreur");
        }        
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
