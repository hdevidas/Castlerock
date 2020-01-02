package castleGame.gameObjects;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import castleGame.base.GameObject;
import castleGame.base.Inputs;
import castleGame.base.KeyboardInputsReceiver;
import castleGame.base.Sprite;
import castleGame.infoObjects.Owner;
import castleGame.infoObjects.Settings;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;


public class Map extends GameObject
{
	// VARIABLES
	public static Pane playfieldLayer;
	static Image oceanImage	= new Image("/images/water.png", Settings.SCENE_WIDTH/50, Settings.SCENE_WIDTH/50, true, true);

	//Liste contenant les coordonnées des chateaux (coo au centre du chateau)
	private List<javafx.geometry.Point2D> listXY = new ArrayList<javafx.geometry.Point2D>();
	
	private List<javafx.geometry.Point2D> listXY_ost_player = new ArrayList<javafx.geometry.Point2D>();
	
	private boolean[] chosen_name = new boolean[Settings.LIST_CASTLE_NAME.length];
	
	// Castles
	ArrayList<ArrayList<Castle>> castles = new ArrayList<ArrayList<Castle>>(1 + 1 + Settings.IA_CASTLE_NUMBER);
	
	public List<Ost> player_ost = new ArrayList<>();

	private Random rnd = new Random();
	
	
	
	// CONSTRUCTORS
	public Map(Pane playfieldLayer)
	{
		this.playfieldLayer = playfieldLayer;
		
		// chargement de la map
		spawnOcean();
		//Création des chateaux
		spawnCastles();
		//spawnTroops();
		
	}
	

	
	// GETTERS AND SETTERS
	
	
	
	// INHERITED METHODS
	protected void updateThis()
	{

	}
	
	protected void updateChilds()
	{
		// Update Castles
		castles.forEach(castleList -> castleList.forEach(castle -> castle.update()));

		// Update Osts
		//TODO
	}
	
	private void spawnOcean() {
		
		for (int x =0; x< Settings.SCENE_WIDTH ; x= x + (int)oceanImage.getWidth()) {
			Sprite sprite = new Sprite(playfieldLayer, oceanImage, x, 0);
		}
		for (int x =0; x< Settings.SCENE_WIDTH ; x= x + (int)oceanImage.getWidth()) {
			Sprite sprite = new Sprite(playfieldLayer, oceanImage, x, Settings.SCENE_HEIGHT - (int)oceanImage.getHeight());
		}
		for (int y =0; y< Settings.SCENE_HEIGHT ; y= y + (int)oceanImage.getHeight()) {
			Sprite sprite = new Sprite(playfieldLayer, oceanImage, 0, y);
		}
		for (int y =0; y< Settings.SCENE_HEIGHT ; y= y + (int)oceanImage.getHeight()) {
			Sprite sprite = new Sprite(playfieldLayer, oceanImage, Settings.SCENE_WIDTH - (int)oceanImage.getWidth(), y);
		}
		
		
	}
	
	
	// METHODS
	private void spawnCastles() { // Création chateaux
		
		//creation des listes de Châteaux (player + neutral + IAs )
		for (int i = 0; i < Settings.IA_CASTLE_NUMBER + 2; i++)
		{
			this.castles.add(i, new ArrayList<Castle>());
		}		
		
		int errors = 0;
		
		//Création chateaux
		if (!createCastle(Owner.Player, 1, 100000))
		{
			errors ++;
		}
		
		for (int i = 0; i < Settings.NEUTRAL_CASTLE_NUMBER; i++)
		{
			if (!createCastle(Owner.Neutral, rnd.nextInt(Settings.IA_NEUTRAL_CASTLE_MAX_LEVEL-1) + 1, 100))
			{
				errors ++;
			}
		}
		
		for (int i = 0; i < Settings.IA_CASTLE_NUMBER; i++)
		{
			if (!createCastle(Owner.valueOf("IA" + i), rnd.nextInt(Settings.IA_NEUTRAL_CASTLE_MAX_LEVEL-1) + 1 , 100))
			{
				errors ++;
			}
		}
		
		if (errors >= 1)
		{
			System.out.println("Error when spawning Castles : missing " + errors + " castle(s)... Try to tweak the number of castles to spawn in the settings and relaunch the game!");
		}
	}
				
	private Boolean createCastle(Owner owner, int level, int money)
	{
		for (int i = 0; i < Settings.NB_TRIES_TO_SPAWN; i++)
		{
			double x = rnd.nextDouble() * (Settings.SCENE_WIDTH - Owner.Player.castleImage.getWidth() -Settings.OST_MIN_DISTANCE_FROM_CASTLE - (Settings.OST_MIN_DISTANCE_FROM_CASTLE + Owner.Player.castleImage.getWidth() ) + 1) + Settings.OST_MIN_DISTANCE_FROM_CASTLE + Owner.Player.castleImage.getWidth(); 
			double y = rnd.nextDouble() * (Settings.SCENE_HEIGHT - Owner.Player.castleImage.getHeight() -Settings.OST_MIN_DISTANCE_FROM_CASTLE - (Settings.OST_MIN_DISTANCE_FROM_CASTLE + Owner.Player.castleImage.getHeight() ) + 1) + Settings.OST_MIN_DISTANCE_FROM_CASTLE + Owner.Player.castleImage.getHeight(); 
			
			if (checkLocation(new javafx.geometry.Point2D(x, y),Settings.CASTLE_SIZE*2))
			{
				Sprite sprite = new Sprite(playfieldLayer, owner.castleImage, x, y);
				
				String name = owner == Owner.Player ? owner.getName() : generate_castle_name();
				
				Castle newCastle = new Castle(sprite, this, name, owner, money, level, Settings.ARMY_INIT, x, y);
				
				listXY.add(new javafx.geometry.Point2D(x+Settings.CASTLE_SIZE/2, y+Settings.CASTLE_SIZE/2));
				
				castles.get(owner.ordinal()).add(newCastle);
	
				return true;
			}
		}
		return false; // TODO Raise an error instead of just returning false.
	}

	//Selection d'un nom (different) dans une liste pour spawn chateaux
	private String generate_castle_name() {
		String name;
		while (true) {
			int index = rnd.nextInt(Settings.LIST_CASTLE_NAME.length);
			if(chosen_name[index] == false) {
				chosen_name[index] = true;
				name = Settings.LIST_CASTLE_NAME[index];
				break;
			}
		}
		return name;
	}
	
	//Fonction aidant la création des chateaux pour éviter spawn même coordonnées
	private boolean checkLocation(javafx.geometry.Point2D point2d, double minDistance) {
		for (javafx.geometry.Point2D p : listXY) {
			if (p.distance(point2d) < minDistance)
				return false;
		}
		return true;
	}

	public boolean player_is_alive(String name) {
		int counter = 0;
	    for(ArrayList<Castle> castleList : castles) {
	    	for (Castle castle : castleList) {
		    	if ((castle.getName() == name)/* && (castle.has_got_troops())*/){ // même si il n'as plus de troupe dans un chateau ce joueur posède encore ce chateua(et pourra refaire des troupes) et est donc vivant
		    		counter ++;
		    	}
	    	}
	    }
	    if (counter == 0) {
	    	return false;
	    }
	    return true;
	}
	
}
