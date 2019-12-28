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
import javafx.scene.layout.Pane;


public class Map extends GameObject implements KeyboardInputsReceiver
{
	// VARIABLES
	private Pane playfieldLayer;

	//Liste contenant les coordonnées des chateaux (coo au centre du chateau)
	private List<javafx.geometry.Point2D> listXY = new ArrayList<javafx.geometry.Point2D>();
	
	private List<javafx.geometry.Point2D> listXY_ost_player = new ArrayList<javafx.geometry.Point2D>();
	
	private boolean[] chosen_name = new boolean[Settings.LIST_CASTLE_NAME.length];
	
	public Castle player_castle;
	private List<Castle> ai_castles = new ArrayList<>();
	private List<Castle> neutral_castles = new ArrayList<>();
	
	public List<Ost> player_ost = new ArrayList<>();

	private Random rnd = new Random();
	
	
	
	// CONSTRUCTORS
	public Map(Pane playfieldLayer)
	{
		this.playfieldLayer = playfieldLayer;
		
		// chargement de la map
		
		//Création des chateaux
		spawnCastles();
		//spawnTroops();
	}
	

	
	// GETTERS AND SETTERS
	
	
	
	// INHERITED METHODS
	public void processInputs(Inputs inputs)
	{
		player_castle.processInputs(inputs);
		ai_castles.forEach(castle -> castle.processInputs(inputs));
		neutral_castles.forEach(castle -> castle.processInputs(inputs));
		// les deux commande suivantes seraient mieux définies dans la classe chateaux TODO
	}
	
	protected void updateThis()
	{
		
	}
	
	protected void updateChilds()
	{
		// Update Castles
		// update sprite
		player_castle.updateUI();
		ai_castles.forEach(castle -> castle.updateUI());
		neutral_castles.forEach(castle -> castle.updateUI());
		
		//update money
		player_castle.money_up();
		ai_castles.forEach(castle -> castle.money_up());
		neutral_castles.forEach(castle -> castle.money_up());
		
		//update level
		ai_castles.forEach(castle -> castle.level_up());
		neutral_castles.forEach(castle -> castle.level_up());
	}
	
	
	
	// METHODS
	private void spawnCastles() { // Création chateaux
		//Création chateau joueur
		double x = rnd.nextDouble() * (Settings.SCENE_WIDTH - Castle.playerCastleImage.getWidth());
		double y = rnd.nextDouble() * (Settings.SCENE_HEIGHT - Castle.playerCastleImage.getHeight());
		Sprite sprite = new Sprite(playfieldLayer, Castle.playerCastleImage, x, y);
		int level = 1;
		player_castle = new Castle(sprite ,"Player", Owner.Player, 1, level, Settings.ARMY_LIFE_INIT);
		listXY.add(new javafx.geometry.Point2D(x+Settings.CASTLE_SIZE/2, y+Settings.CASTLE_SIZE/2));
		
		
		//Création ost joueur
		double x2 = x - Settings.OST_MIN_DISTANCE_FROM_CASTLE + rnd.nextDouble() * ((x + Settings.OST_MIN_DISTANCE_FROM_CASTLE) - (x - Settings.OST_MIN_DISTANCE_FROM_CASTLE));
		double y2 = y - Settings.OST_MIN_DISTANCE_FROM_CASTLE + rnd.nextDouble() * ((y + Settings.OST_MIN_DISTANCE_FROM_CASTLE) - (y - Settings.OST_MIN_DISTANCE_FROM_CASTLE));
		sprite = new Sprite(playfieldLayer, Ost.piquierImage, x2, y2);
		Ost ost = new Ost();
		player_ost.add(ost);
		listXY_ost_player.add(new javafx.geometry.Point2D(x2+Settings.OST_SIZE/2, y2+Settings.OST_SIZE/2));
		
		while(true) {
			x2 = x - Settings.OST_MIN_DISTANCE_FROM_CASTLE + rnd.nextDouble() * ((x + Settings.OST_MIN_DISTANCE_FROM_CASTLE) - (x - Settings.OST_MIN_DISTANCE_FROM_CASTLE));
			y2 = y - Settings.OST_MIN_DISTANCE_FROM_CASTLE + rnd.nextDouble() * ((y + Settings.OST_MIN_DISTANCE_FROM_CASTLE) - (y - Settings.OST_MIN_DISTANCE_FROM_CASTLE));
			if(checkLocation(new javafx.geometry.Point2D(x2, y2),Settings.OST_MIN_DISTANCE)) {
				sprite = new Sprite(playfieldLayer, Ost.piquierImage, x2, y2);
				ost= new Ost();
				player_ost.add(ost);
				listXY_ost_player.add(new javafx.geometry.Point2D(x2+Settings.OST_SIZE/2, y2+Settings.OST_SIZE/2));
				break;
			}
		}
		
		
		
		//Création chateaux IA
		for (int i=0; i<Settings.IA_CASTLE_NUMBER; i++) {
			level = rnd.nextInt(Settings.IA_NEUTRAL_CASTLE_MAX_LEVEL-1) + 1;
			while(true) {
				x = rnd.nextDouble() * (Settings.SCENE_WIDTH - Castle.iaCastleImage.getWidth());
				y = rnd.nextDouble() * (Settings.SCENE_HEIGHT - Castle.iaCastleImage.getHeight());
				if(checkLocation(new javafx.geometry.Point2D(x, y),Settings.CASTLE_MIN_DISTANCE)) {
					sprite = new Sprite(playfieldLayer, Castle.iaCastleImage, x, y);
					Castle iaCastle = new Castle(sprite, generate_castle_name(), Owner.Computer, 1, level, Settings.ARMY_LIFE_INIT);
					ai_castles.add(iaCastle);
					listXY.add(new javafx.geometry.Point2D(x+Settings.CASTLE_SIZE/2, y+Settings.CASTLE_SIZE/2));
					break;
				}
			}
		}
		
		//Création chateaux neutres
		for (int i=0; i<Settings.NEUTRAL_CASTLE_NUMBER; i++) {
			level = rnd.nextInt(Settings.IA_NEUTRAL_CASTLE_MAX_LEVEL-1) + 1;
			while(true) {
				x = rnd.nextDouble() * (Settings.SCENE_WIDTH - Castle.neutralCastleImage.getWidth());
				y = rnd.nextDouble() * (Settings.SCENE_HEIGHT - Castle.neutralCastleImage.getHeight());
				if(checkLocation(new javafx.geometry.Point2D(x, y),Settings.CASTLE_MIN_DISTANCE)) {
					sprite = new Sprite(playfieldLayer, Castle.neutralCastleImage, x, y);
					Castle neutralCastle = new Castle(sprite, generate_castle_name(), Owner.Neutral, 1, level, Settings.ARMY_LIFE_INIT);
					neutral_castles.add(neutralCastle);
					listXY.add(new javafx.geometry.Point2D(x+Settings.CASTLE_SIZE/2, y+Settings.CASTLE_SIZE/2));
					break;
				}
			}
		}
	}

	private void spawnTroops() {
		//Création ost joueur
				double x = rnd.nextDouble() * (Settings.SCENE_WIDTH - Castle.playerCastleImage.getWidth());
				double y = rnd.nextDouble() * (Settings.SCENE_HEIGHT - Castle.playerCastleImage.getHeight());
				Sprite sprite = new Sprite(playfieldLayer, Ost.piquierImage, x, y);
				Ost ost = new Ost();
				player_ost.add(ost);
				listXY_ost_player.add(new javafx.geometry.Point2D(x+Settings.CASTLE_SIZE/2, y+Settings.CASTLE_SIZE/2));
				
				while(true) {
					x = rnd.nextDouble() * (Settings.SCENE_WIDTH - Castle.iaCastleImage.getWidth());
					y = rnd.nextDouble() * (Settings.SCENE_HEIGHT - Castle.iaCastleImage.getHeight());
					if(checkLocation(new javafx.geometry.Point2D(x, y),Settings.CASTLE_MIN_DISTANCE)) {
						sprite = new Sprite(playfieldLayer, Ost.piquierImage, x, y);
						Ost ost2= new Ost();
						player_ost.add(ost2);
						listXY.add(new javafx.geometry.Point2D(x+Settings.CASTLE_SIZE/2, y+Settings.CASTLE_SIZE/2));
						break;
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
