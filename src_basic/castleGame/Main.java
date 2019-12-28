package castleGame;

import castleGame.base.Inputs;
import castleGame.base.KeyboardInputsReceiver;
import castleGame.gameObjects.Castle;
import castleGame.gameObjects.Map;
import castleGame.gameObjects.Ost;
import castleGame.infoObjects.Settings;
import castleGame.infoObjects.TroopType;
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

public class Main extends Application implements KeyboardInputsReceiver
{
	// VARIABLES
	private Map map;

	private Pane playfieldLayer;

	
	private Scene scene;
	private Inputs inputs;
	private AnimationTimer gameLoop;

	//Bar d'info
	private Text Message = new Text();
	
	Group root;
	
	
	
	// CONSTRUCTORS
	public static void main(String[] args) 
	{
		launch(args);
	}
	

	
	// GETTERS AND SETTERS
	
	
	
	// INHERITED METHODS
	public void processInputs(Inputs inputs) {
		if (inputs.isExit()) {
			Platform.exit();
			System.exit(0);
		}
		map.processInputs(inputs);
	}
	
	
	// METHODS
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
				processInputs(inputs);
				
				map.update();
				
				//update bar
				update_bar();
			}

		};
		gameLoop.start();
	}
	
	private void loadGame() { 
		
		map = new Map(playfieldLayer);

		inputs = new Inputs(scene);
		inputs.addListeners();

		createStatusBar();
	}


	private void update_bar() {
		if (Castle.clicked != null) {
				String attaquer;
			String levelup;
			String fairetroupe;
			if (Castle.clicked.is_player()) {
				levelup = "   MONTER DE NIVEAU (Haut)";
				fairetroupe = "   CONSTRUIRE TROUPE (Droite)";
				attaquer = "";
			}
			else {
				levelup = "";
				fairetroupe = "";
				attaquer = "   ATTAQUER CE CHATEAU (Bas)";
			}
			Message.setText("Nom :"+ Castle.clicked.getName()+"          Florins :" + Castle.clicked.getMoney() + 
					"          Niveau : " + Castle.clicked.getLevel()+"          Troupes : " + 
					Castle.clicked.getNbTroop(TroopType.Piquier)+"/"+Castle.clicked.getNbTroop(TroopType.Knight)+"/"+Castle.clicked.getNbTroop(TroopType.Onager) +
					"     |     "+levelup+fairetroupe+attaquer);                   
		}
		
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

}
