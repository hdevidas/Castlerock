package castleGame.gameObjects;

import java.util.ArrayList;
import java.util.List;

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
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.image.Image;

public class Castle extends TroopsManager implements MouseEventReceiver, KeyboardInputsReceiver, SpriteRender{

	// VARIABLES
	// game variable
	private Sprite sprite;
	
	// Sprites TODO peut-Ãªtre pas leur place dÃ©finitive...
	static Image iaCastleImage		= new Image("/images/playerCastle.png",  Settings.CASTLE_SIZE, Settings.CASTLE_SIZE, true, true);
	static Image neutralCastleImage	= new Image("/images/iaCastle.png", 	 Settings.CASTLE_SIZE, Settings.CASTLE_SIZE, true, true);
	static Image playerCastleImage	= new Image("/images/neutralCastle.png", Settings.CASTLE_SIZE, Settings.CASTLE_SIZE, true, true);
	
	// this object variable
	static public Castle clicked;
	private String name;
	private int level;
	private int money;
	private double x;
	private double y;
	
	private Text piquierTxt = new Text();
	
	
	// CONSTRUCTORS
	public Castle(Sprite sprite, String name, Owner owner, int money, int level, int[] init_army, double x, double y) {
		super(init_army, x,y);
		//System.out.println(x);
		this.sprite = sprite;
		this.money = money;
		this.level = level;
		this.name = name;
		this.owner = owner;
		this.x = x;
		this.y = y;
		
		create_piquier_bar();
		
		
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

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public Sprite getSprite() {
		return sprite;
	}
	
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
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
				level_up();
			}
			// construire un piquier
			if (Main.inputs.isBuilding()) {
				build_troop(TroopType.Piquier);
			}
			// attaquer un chateau x
			if (Main.inputs.isAttacks()) {
				this.attack(clicked);
				
				if (this.has_got_troops() == false) {
					if (this.player_is_alive(this.getName())) {
						System.out.println("un de vos chateau n'a plus d'unités, il est vulnérable.");
					}
					else {
						System.out.println("Vous avez perdus.");
					}
				}
				else {
					System.out.println("Vous avez détruis un chateau.");
					System.out.println("Ce chateau porte maintenant votre nom.");
					clicked.setName(this.getName());
					System.out.println("Ce chateau peut maintenant être utilisé comme si c'était le votre.");
					clicked.setOwner(Owner.Player);
					
					clicked.setMouseEventResponse();
				}
				
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
	protected void updateThis() 
	{
		processInputs();
		this.money_up();
		if (this.owner == Owner.Computer || this.owner == Owner.Neutral)
		{
			this.level_up();
		}
		updateUI();
		
		// manque le changement des sprites neutral et computer (a faire plus tard)
		if ( this.owner == Owner.Player && this.getSprite().getImage() != playerCastleImage ) {
			this.change_castle_sprite(playerCastleImage);
		}
		
		update_piquier_bar();
		
	}
	
	protected void updateChilds() 
	{
		super.updateChilds();
		// specific castle variable to update can be put here
	}

	
	// METHODS
	public void level_up() { //MONTE D'UN NIVEAU LE CHATEAU
		if (getMoney() >= (getLevel()*1000)) {
			setMoney(getMoney()-getLevel()*1000);
			setLevel(getLevel()+1);
		}
	}

	public void build_troop(TroopType troopType) { //CONSTRUIS UNE TROUPE
		if (money >= troopType.getProductionCost() ) {
			money -= troopType.getProductionCost() ;
			int nbp = getNbTroop(troopType);
			double ny = y + nbp*10;
			this.addTroop(troopType,x,ny);
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
	
	public boolean player_is_alive(String name) {
		int counter = 0;
	    for(Castle castle:Map.all_castles) {
	    	if ((castle.getName() == name) && (castle.has_got_troops())){
	    		counter = counter +1 ;
	    	}
	    }
	    if (counter == 0) {
	    	return false;
	    }
	    return true;
	}
	
	
	void change_castle_sprite(Image img) {
		Sprite sprite = new Sprite(Map.playfieldLayer,img, this.getX(), this.getY());
		this.setSprite(sprite);
		setMouseEventResponse();
	}
	
	
	public void create_piquier_bar() {
		String nbPiquier = "Piq: " + Integer.toString(getNbTroop(TroopType.Piquier));
		HBox piquierBar = new HBox();
		piquierTxt.setText(nbPiquier);
		piquierBar.getChildren().addAll(piquierTxt);
		piquierBar.getStyleClass().add("piquier");
		piquierBar.relocate(x, y);
		//piquierBar.setPrefSize(Settings.SCENE_WIDTH, Settings.STATUS_BAR_HEIGHT); // inutile ?
		Main.root.getChildren().add(piquierBar);
	}
	
	private void update_piquier_bar() {
		piquierTxt.setText("Piq: "+Integer.toString(getNbTroop(TroopType.Piquier)));                 
	}
	
}
