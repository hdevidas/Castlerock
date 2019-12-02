package castleGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.scene.layout.Pane;

import javafx.scene.image.Image;

public class Map
{

	private Inputs inputs;
	private Pane playfieldLayer;
	private Image iaCastleImage;
	private Image neutralCastleImage;
	private Image playerCastleImage;

	//Liste contenant les coordonnées des chateaux (coo au centre du chateau)
	private List<javafx.geometry.Point2D> listXY = new ArrayList<javafx.geometry.Point2D>();
	
	private boolean[] chosen_name = new boolean[Settings.LIST_CASTLE_NAME.length];
	
	public Castle player_castle;
	private List<Castle> ia_castles = new ArrayList<>();
	private List<Castle> neutral_castles = new ArrayList<>();

	private Random rnd = new Random();
	
	public Map(Inputs inputs, Pane playfieldLayer)
	{
		this.inputs = inputs;
		this.playfieldLayer = playfieldLayer;
		
		// chargement de la map
		
		//Définition des sprites
		playerCastleImage = new Image(getClass().getResource("/images/playerCastle.png").toExternalForm(), Settings.CASTLE_SIZE, Settings.CASTLE_SIZE,
				true, true);
		iaCastleImage = new Image(getClass().getResource("/images/iaCastle.png").toExternalForm(), Settings.CASTLE_SIZE, Settings.CASTLE_SIZE, true,
				true);
		neutralCastleImage = new Image(getClass().getResource("/images/neutralCastle.png").toExternalForm(), Settings.CASTLE_SIZE, Settings.CASTLE_SIZE,
				true, true);
		
		//Création des chateaux
		spawnCastles();
	}
	

	private void spawnCastles() { // Création chateaux
		//Création chateau joueur
		double x = rnd.nextDouble() * (Settings.SCENE_WIDTH - playerCastleImage.getWidth());
		double y = rnd.nextDouble() * (Settings.SCENE_HEIGHT - playerCastleImage.getHeight());
		int level = 1;
		player_castle = new Castle(playfieldLayer, playerCastleImage, x, y, inputs,"Player", 1, level, Settings.ARMY_LIFE_INIT,"player" );
		listXY.add(new javafx.geometry.Point2D(x+Settings.CASTLE_SIZE/2, y+Settings.CASTLE_SIZE/2));
		
		//Création chateaux IA
		for (int i=0; i<Settings.IA_CASTLE_NUMBER; i++) {
			level = rnd.nextInt(Settings.IA_NEUTRAL_CASTLE_MAX_LEVEL-1) + 1;
			while(true) {
				x = rnd.nextDouble() * (Settings.SCENE_WIDTH - iaCastleImage.getWidth());
				y = rnd.nextDouble() * (Settings.SCENE_HEIGHT - iaCastleImage.getHeight());
				
				if(checkLocation(new javafx.geometry.Point2D(x, y),Settings.CASTLE_MIN_DISTANCE)) {
					Castle iaCastle = new Castle(playfieldLayer, iaCastleImage, x, y,inputs,generate_castle_name(), 1, level, Settings.ARMY_LIFE_INIT,"ai");
					ia_castles.add(iaCastle);
					listXY.add(new javafx.geometry.Point2D(x+Settings.CASTLE_SIZE/2, y+Settings.CASTLE_SIZE/2));
					break;
				}
			}
		}
		
		//Création chateaux neutres
		for (int i=0; i<Settings.NEUTRAL_CASTLE_NUMBER; i++) {
			level = rnd.nextInt(Settings.IA_NEUTRAL_CASTLE_MAX_LEVEL-1) + 1;
			while(true) {
				x = rnd.nextDouble() * (Settings.SCENE_WIDTH - neutralCastleImage.getWidth());
				y = rnd.nextDouble() * (Settings.SCENE_HEIGHT - neutralCastleImage.getHeight());
				if(checkLocation(new javafx.geometry.Point2D(x, y),Settings.CASTLE_MIN_DISTANCE)) {
					Castle neutralCastle = new Castle(playfieldLayer, neutralCastleImage, x, y,inputs, generate_castle_name(), 1, level, Settings.ARMY_LIFE_INIT, "neutral");
					neutral_castles.add(neutralCastle);
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
	
	public void updateCastle()
	{
		// UPDATE SPRITES
		player_castle.updateUI();
		ia_castles.forEach(castle -> castle.updateUI());
		neutral_castles.forEach(castle -> castle.updateUI());
		
		//update money
		player_castle.money_up();
		ia_castles.forEach(castle -> castle.money_up());
		neutral_castles.forEach(castle -> castle.money_up());
		
		//update level
		ia_castles.forEach(castle -> castle.enough_money_to_level_up());
		neutral_castles.forEach(castle -> castle.enough_money_to_level_up());
	}
}
