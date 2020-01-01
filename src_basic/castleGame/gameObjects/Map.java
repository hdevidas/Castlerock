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
	
	//public Castle player_castle;
	//private List<Castle> ai_castles = new ArrayList<>();
	//private List<Castle> neutral_castles = new ArrayList<>();
	
	public static List<Castle> all_castles = new ArrayList<>();
	
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
		//player_castle.update();
		//ai_castles.forEach(castle -> castle.update());
		//neutral_castles.forEach(castle -> castle.update());
		all_castles.forEach(castle -> castle.update());

		
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
		//Création chateau joueur
		double x = rnd.nextDouble() * (Settings.SCENE_WIDTH - Castle.playerCastleImage.getWidth() -Settings.OST_MIN_DISTANCE_FROM_CASTLE - (Settings.OST_MIN_DISTANCE_FROM_CASTLE + Castle.playerCastleImage.getWidth() ) + 1) + Settings.OST_MIN_DISTANCE_FROM_CASTLE + Castle.playerCastleImage.getWidth(); 
		double y = rnd.nextDouble() * (Settings.SCENE_HEIGHT - Castle.playerCastleImage.getHeight() -Settings.OST_MIN_DISTANCE_FROM_CASTLE - (Settings.OST_MIN_DISTANCE_FROM_CASTLE + Castle.playerCastleImage.getHeight() ) + 1) + Settings.OST_MIN_DISTANCE_FROM_CASTLE + Castle.playerCastleImage.getHeight(); 
		//double y = rnd.nextDouble() * (Settings.SCENE_HEIGHT - Castle.playerCastleImage.getHeight());
		Sprite sprite = new Sprite(playfieldLayer, Castle.playerCastleImage, x, y);
		int level = 1;
		Castle player_castle = new Castle(sprite ,Settings.PLAYER_NAME, Owner.Player, 1, level, Settings.ARMY_INIT,x,y);
		listXY.add(new javafx.geometry.Point2D(x+Settings.CASTLE_SIZE/2, y+Settings.CASTLE_SIZE/2));
		all_castles.add(player_castle);
		
		
		//Création chateaux IA
		for (int i=0; i<Settings.IA_CASTLE_NUMBER; i++) {
			level = rnd.nextInt(Settings.IA_NEUTRAL_CASTLE_MAX_LEVEL-1) + 1;
			while(true) {
				System.out.println("Cherche position pour les chateaux neutres...");
				x = rnd.nextDouble() * (Settings.SCENE_WIDTH - Castle.playerCastleImage.getWidth() -Settings.OST_MIN_DISTANCE_FROM_CASTLE - (Settings.OST_MIN_DISTANCE_FROM_CASTLE + Castle.playerCastleImage.getWidth() ) + 1) + Settings.OST_MIN_DISTANCE_FROM_CASTLE + Castle.playerCastleImage.getWidth(); 
				y = rnd.nextDouble() * (Settings.SCENE_HEIGHT - Castle.playerCastleImage.getHeight() -Settings.OST_MIN_DISTANCE_FROM_CASTLE - (Settings.OST_MIN_DISTANCE_FROM_CASTLE + Castle.playerCastleImage.getHeight() ) + 1) + Settings.OST_MIN_DISTANCE_FROM_CASTLE + Castle.playerCastleImage.getHeight(); 
			
				
				if(checkLocation(new javafx.geometry.Point2D(x, y),Settings.CASTLE_SIZE*2)) {
					
					switch (i+1)
					{
					  case 1:
						  sprite = new Sprite(playfieldLayer, Castle.ordi1CastleImage, x, y);
					    break;
					  case 2:
						  sprite = new Sprite(playfieldLayer, Castle.ordi2CastleImage, x, y);
					    break;
					  case 3:
						  sprite = new Sprite(playfieldLayer, Castle.ordi3CastleImage, x, y);
					    break;
					  case 4:
						  sprite = new Sprite(playfieldLayer, Castle.ordi4CastleImage, x, y);
					    break;
					  case 5:
						  sprite = new Sprite(playfieldLayer, Castle.ordi5CastleImage, x, y);
					    break;
					  case 6:
						  sprite = new Sprite(playfieldLayer, Castle.ordi6CastleImage, x, y);
					    break;
					  default:
					    System.out.println("error");
					}
					
					Castle iaCastle = new Castle(sprite, generate_castle_name(), Owner.Computer, 1, level, Settings.ARMY_INIT,x,y);
					all_castles.add(iaCastle);
					listXY.add(new javafx.geometry.Point2D(x+Settings.CASTLE_SIZE/2, y+Settings.CASTLE_SIZE/2));
					break;
				}
			}
		}
		
		
		
		//Création chateaux neutres
		for (int i=0; i<Settings.NEUTRAL_CASTLE_NUMBER; i++) {
			level = rnd.nextInt(Settings.IA_NEUTRAL_CASTLE_MAX_LEVEL-1) + 1;
			while(true) {
				System.out.println("Cherche position pour les chateaux neutres...");
				
				x = rnd.nextDouble() * (Settings.SCENE_WIDTH - Castle.playerCastleImage.getWidth() -Settings.OST_MIN_DISTANCE_FROM_CASTLE - (Settings.OST_MIN_DISTANCE_FROM_CASTLE + Castle.playerCastleImage.getWidth() ) + 1) + Settings.OST_MIN_DISTANCE_FROM_CASTLE + Castle.playerCastleImage.getWidth(); 
				y = rnd.nextDouble() * (Settings.SCENE_HEIGHT - Castle.playerCastleImage.getHeight() -Settings.OST_MIN_DISTANCE_FROM_CASTLE - (Settings.OST_MIN_DISTANCE_FROM_CASTLE + Castle.playerCastleImage.getHeight() ) + 1) + Settings.OST_MIN_DISTANCE_FROM_CASTLE + Castle.playerCastleImage.getHeight(); 
			
				
				if(checkLocation(new javafx.geometry.Point2D(x, y),Settings.CASTLE_SIZE*2)) {
					sprite = new Sprite(playfieldLayer, Castle.neutralCastleImage, x, y);
					Castle neutralCastle = new Castle(sprite, generate_castle_name(), Owner.Neutral, 1, level, Settings.ARMY_INIT,x,y);
					all_castles.add(neutralCastle);
					listXY.add(new javafx.geometry.Point2D(x+Settings.CASTLE_SIZE/2, y+Settings.CASTLE_SIZE/2));
					break;
				}
			}
		}
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

	
}
