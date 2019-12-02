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

	private Map map;

	private Pane playfieldLayer;

	
	private Scene scene;
	private Inputs inputs;
	private AnimationTimer gameLoop;

	//Bar d'info
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
				
				map.updateCastle();
				
				//update bar
				update_bar();
			}

			private void processInput(Inputs input, long now) {
				if (input.isExit()) {
					Platform.exit();
					System.exit(0);
				} else if (input.isLevelUp()) {
					map.player_castle.enough_money_to_level_up();
				}
				else if(input.isBuilding()) {
					map.player_castle.build_troupe();
				}

			}

		};
		gameLoop.start();
	}

	private void loadGame() { 
		
		map = new Map(inputs, playfieldLayer);

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
			Message.setText("Nom :"+ Castle.clicked.getName()+"          Florins :" + Castle.clicked.getMoney() + "          Niveau : " + Castle.clicked.getLevel()+"          Troupes : " + Castle.clicked.getArmy_life()[0]+"/"+Castle.clicked.getArmy_life()[1]+"/"+Castle.clicked.getArmy_life()[2]+"     |     "+levelup+fairetroupe+attaquer);                   
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

	public static void main(String[] args) {
		launch(args);
	}

}
