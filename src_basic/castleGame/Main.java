package castleGame;

import java.awt.Insets;

import com.sun.prism.paint.Color;

import castleGame.base.Inputs;
import castleGame.base.KeyboardInputsReceiver;
import castleGame.gameObjects.Castle;
import castleGame.gameObjects.InfoBar;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class Main extends Application implements KeyboardInputsReceiver
{
	// VARIABLES
	private Map map;

	private Pane playfieldLayer;

	
	private static Scene scene;
	public static Inputs inputs;
	private AnimationTimer gameLoop;
	private long lastTurnTime = 0;

	//Bar d'info
	InfoBar infoBar;
	
	public static Group root;
	
	
	
	// CONSTRUCTORS
	public static void main(String[] args) 
	{
		launch(args);
	}
	

	
	// GETTERS AND SETTERS
	
	
	
	// INHERITED METHODS
	public void processInputs() 
	{
		if (inputs.isExit()) {
			Platform.exit();
			System.exit(0);
		}
	}
	
	
	// METHODS
	public void start(Stage primaryStage) {

		loadGame(primaryStage);

		// Lancement du jeu

		gameLoop = new AnimationTimer() {
			// MAIN LOOP
			public void handle(long now) {

				processInputs();
				
				if (lastTurnTime + Settings.SECONDS_PER_TURN <= now)
				{
					lastTurnTime = now;
					
					map.update();
					infoBar.update();
				}
			}

		};
		gameLoop.start();
	}
	
	private void loadGame(Stage primaryStage) 
	{ 
		// Préparation de la scene
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
