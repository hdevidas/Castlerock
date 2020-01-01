package castleGame.gameObjects;

import castleGame.base.GameObject;
import castleGame.infoObjects.Settings;
import castleGame.infoObjects.TroopType;
import javafx.scene.Group;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class InfoBar extends GameObject
{
	// VARIABLES
	private HBox statusBar; 
	private Text Message = new Text();
	
	
	// CONSTRUCTORS
	public InfoBar(Group root)
	{
		statusBar = new HBox();
		statusBar.getChildren().addAll(Message);
		statusBar.getStyleClass().add("statusBar");
		statusBar.relocate(0, Settings.SCENE_HEIGHT);
		statusBar.setPrefSize(Settings.SCENE_WIDTH, Settings.STATUS_BAR_HEIGHT);
		root.getChildren().add(statusBar);
	}
	
	
	
	// GETTERS AND SETTERS
	
	
	
	// INHERITED METHODS
	// from GameObject :
	protected void updateThis() 
	{
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

	protected void updateChilds() 
	{
		// no child
	}
		
	
		
	// METHODS
}
