package castleGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.sun.javafx.geom.Point2D;

import castleGame.Castle;
import castleGame.Ost;
import castleGame.Inputs;
import castleGame.Settings;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class Main extends Application {

	private Random rnd = new Random();

	private Pane playfieldLayer;

	private Image iaCastleImage;
	private Image neutralCastleImage;
	private Image playerCastleImage;
	
	private boolean[] chosen_name = new boolean[Settings.LIST_CASTLE_NAME.length];
	
	private Castle player_castle;
	private List<Castle> ia_castles = new ArrayList<>();
	private List<Castle> neutral_castles = new ArrayList<>();

	//Liste contenant les coordonnées des chateaux (coo au centre du chateau)
	private List<javafx.geometry.Point2D> listXY = new ArrayList<javafx.geometry.Point2D>();
	
	private Scene scene;
	private Inputs inputs;
	private AnimationTimer gameLoop;

	//Bar d'info
	private Castle castle_On_Click;
	private Text Message = new Text();
	
	Group root;

	public void start(Stage primaryStage) {

		// Préparation de la scene
		root = new Group();
		scene = new Scene(root, Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT + Settings.STATUS_BAR_HEIGHT);
		scene.getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();

		// create layers
		playfieldLayer = new Pane();
		root.getChildren().add(playfieldLayer);

		// Lancement du jeu
		loadGame();

		gameLoop = new AnimationTimer() {
			// MAIN LOOP
			public void handle(long now) {
				processInput(inputs, now);
				
				

				// UPDATE SPRITES
				player_castle.updateUI();
				ia_castles.forEach(sprite -> sprite.updateUI());
				neutral_castles.forEach(sprite -> sprite.updateUI());
				
				//update money
				player_castle.money_up();
				ia_castles.forEach(castle -> castle.money_up());
				neutral_castles.forEach(castle -> castle.money_up());
				
				//update level
				ia_castles.forEach(castle -> castle.enough_money_to_level_up());
				neutral_castles.forEach(castle -> castle.enough_money_to_level_up());
				
				//update bar
				update_bar();
			}

			private void processInput(Inputs input, long now) {
				if (input.isExit()) {
					Platform.exit();
					System.exit(0);
				} else if (input.isLevelUp()) {
					player_castle.enough_money_to_level_up();
				}
				else if(input.isBuilding()) {
					player_castle.build_troupe();
				}

			}

		};
		gameLoop.start();
	}

	private void loadGame() { // chargement de la map
		//Définition des sprites
		playerCastleImage = new Image(getClass().getResource("/images/playerCastle.png").toExternalForm(), Settings.CASTLE_SIZE, Settings.CASTLE_SIZE,
				true, true);
		iaCastleImage = new Image(getClass().getResource("/images/iaCastle.png").toExternalForm(), Settings.CASTLE_SIZE, Settings.CASTLE_SIZE, true,
				true);
		neutralCastleImage = new Image(getClass().getResource("/images/neutralCastle.png").toExternalForm(), Settings.CASTLE_SIZE, Settings.CASTLE_SIZE,
				true, true);

		inputs = new Inputs(scene);
		inputs.addListeners();

		//Création des chateaux
		spawnCastles();
		createStatusBar();
	}


	private void spawnCastles() { // Création chateaux
		//Création chateau joueur
		double x = rnd.nextDouble() * (Settings.SCENE_WIDTH - playerCastleImage.getWidth());
		double y = rnd.nextDouble() * (Settings.SCENE_HEIGHT - playerCastleImage.getHeight());
		int level = 1;
		player_castle = new Castle(playfieldLayer, playerCastleImage, x, y, inputs,"Player", 1, level, Settings.ARMY_LIFE_INIT,"player" );
		SetCastleOnClick(player_castle);
		listXY.add(new javafx.geometry.Point2D(x+Settings.CASTLE_SIZE/2, y+Settings.CASTLE_SIZE/2));
		
		//Création chateaux IA
		for (int i=0; i<Settings.IA_CASTLE_NUMBER; i++) {
			level = rnd.nextInt(Settings.IA_NEUTRAL_CASTLE_MAX_LEVEL-1) + 1;
			while(true) {
				x = rnd.nextDouble() * (Settings.SCENE_WIDTH - iaCastleImage.getWidth());
				y = rnd.nextDouble() * (Settings.SCENE_HEIGHT - iaCastleImage.getHeight());
				
				if(checkLocation(new javafx.geometry.Point2D(x, y),Settings.CASTLE_MIN_DISTANCE)) {
					Castle iaCastle = new Castle(playfieldLayer, iaCastleImage, x, y,inputs,generate_castle_name(), 1, level, Settings.ARMY_LIFE_INIT,"ai");
					SetCastleOnClick(iaCastle);
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
					SetCastleOnClick(neutralCastle);
					neutral_castles.add(neutralCastle);
					listXY.add(new javafx.geometry.Point2D(x+Settings.CASTLE_SIZE/2, y+Settings.CASTLE_SIZE/2));
					break;
				}
			}
		}
	}
	
	//Fonction détectant le clique chateau afin de connaitre son état
	//Difficulté pour atteindre le chateau...
	private void SetCastleOnClick(Castle castle_clicked) {
		castle_clicked.getView().setOnMousePressed(e -> {
			castle_On_Click = castle_clicked;
			e.consume();
		});
	}
	
	private void update_bar() {
		if (castle_On_Click != null) {
				String attaquer;
			String levelup;
			String fairetroupe;
			if (castle_On_Click.is_player()) {
				levelup = "   MONTER DE NIVEAU (Haut)";
				fairetroupe = "   CONSTRUIRE TROUPE (Droite)";
				attaquer = "";
			}
			else {
				levelup = "";
				fairetroupe = "";
				attaquer = "   ATTAQUER CE CHATEAU (Bas)";
			}
			Message.setText("Nom :"+ castle_On_Click.getName()+"          Florins :" + castle_On_Click.getMoney() + "          Niveau : " + castle_On_Click.getLevel()+"          Troupes : " + castle_On_Click.getArmy_life()[0]+"/"+castle_On_Click.getArmy_life()[1]+"/"+castle_On_Click.getArmy_life()[2]+"     |     "+levelup+fairetroupe+attaquer);                   
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
	
	
	public void createStatusBar() {
		HBox statusBar = new HBox();
		//Message.setText("Chateau cliqué          Nom :"+ player_castle.getName()+"          Florins :" + player_castle.getMoney() + "          Niveau : " + player_castle.getLevel());
		statusBar.getChildren().addAll(Message);
		statusBar.getStyleClass().add("statusBar");
		statusBar.relocate(0, Settings.SCENE_HEIGHT);
		statusBar.setPrefSize(Settings.SCENE_WIDTH, Settings.STATUS_BAR_HEIGHT);
		root.getChildren().add(statusBar);
	}

	public static void main(String[] args) {
		launch(args);
	}

}
