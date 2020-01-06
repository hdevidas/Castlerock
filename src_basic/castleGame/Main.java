package castleGame;

import castleGame.base.Inputs;
import castleGame.base.KeyboardInputsReceiver;
import castleGame.gameObjects.InfoBar;
import castleGame.gameObjects.Map;
import castleGame.infoObjects.Settings;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * Main class of the castleGame
 * 
 * <p>
 * This class is the starting point of the application, it generates the base for the window, the bottom bar
 * and the Map instance which will hold all the other gameObjects of the game. This class is also the one
 * managing the inputs from the keyboard and the game loop in the AnimationTimer.
 *
 */
public class Main extends Application implements KeyboardInputsReceiver
{
	// VARIABLES
	/**
	 * The Map on which the game take place
	 */
	private Map map;

	/**
	 * The Pane on which everything is drawn
	 */
	private Pane playfieldLayer;
	
	//TODO what is the purpose of this variable
	private static Scene scene;
	
	/**
	 * The inputs instance that will be used throughout the game to transmit the keyboard inputs 
	 */
	public static Inputs inputs;
	
	/**
	 * The game loop used at each turn of the game
	 */
	private AnimationTimer gameLoop;
	
	/**
	 * a variable used to compute the time since the last turn and control each turn duration
	 */
	private long lastTurnTime = 0;

	/**
	 * The object representing the bar at the bottom of the window
	 */
	InfoBar infoBar;
	
	//TODO what is the purpose of this variable
	public static Group root;
	
	
	
	// CONSTRUCTORS
	/**
	 * The main method of the program
	 * <p>
	 * It is the starting point of the application and only call the JavaFX launch method.
	 * 
	 * @param args : The arguments the the program was called with
	 */
	public static void main(String[] args) 
	{
		launch(args);
	}
	

	
	// GETTERS AND SETTERS
	
	
	
	// INHERITED METHODS
	// from application
	public void start(Stage primaryStage) {

		loadGame(primaryStage);

		// Start of the game

		gameLoop = new AnimationTimer() {
			// MAIN LOOP
			public void handle(long now) {

				processInputs();
				
				if (lastTurnTime + Settings.NANOSECONDS_PER_TURN <= now)
				{
					lastTurnTime = now;
					
					map.update();
					infoBar.update();
				}
			}

		};
		gameLoop.start();
	}
	
	// from KeyboardInputsReceiver :
	public void processInputs() 
	{
		if (inputs.isExit()) {
			Platform.exit();
			System.exit(0);
		}
	}
	
	
	// METHODS
	/**
	 * The method which load all the necessary variables before starting the game.
	 * 
	 * @param primaryStage : the primary stage on which to build  
	 */
	private void loadGame(Stage primaryStage) 
	{ 
		// Préparation of the scene
		Image sandImage	= new Image("/images/sand.png", Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT, true, true);
		ImageView mv = new ImageView(sandImage);
		mv.setFitWidth(Settings.SCENE_WIDTH); 
        mv.setFitHeight(Settings.SCENE_HEIGHT);
		root = new Group();
		root.setId("pane");
		scene = new Scene(root, Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT + Settings.STATUS_BAR_HEIGHT);
		scene.getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
		// create layers
		playfieldLayer = new Pane();
		root.getChildren().add(mv);
		root.getChildren().add(playfieldLayer);

		
		map = new Map(playfieldLayer);

		// create inputs instance
		inputs = new Inputs(scene);
		inputs.addListeners();

		infoBar = new InfoBar(root);
	}
}
