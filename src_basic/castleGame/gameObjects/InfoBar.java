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
	private Text infoText = new Text();
	
	
	// CONSTRUCTORS
	public InfoBar(Group root)
	{
		statusBar = new HBox();
		statusBar.getChildren().addAll(infoText);
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
		if (Castle.isLaunchingOst)
		{
			displayCustomMessage("Cliquez sur le chateau vers lequel lancer l'ost");
		}
		else if (Castle.clicked != null)
		{
			displayCastleInfo(Castle.clicked);
		}	
	}

	protected void updateChilds() 
	{
		// no child
	}
		
	
		
	// METHODS
	private void displayCastleInfo(Castle castle)
	{
		String attaquer;
		String levelup;
		String fairetroupe;
		if (castle.is_player()) {
			levelup = "   MONTER DE NIVEAU (Haut)";
			fairetroupe = "   CONSTRUIRE TROUPE (Droite)";
			attaquer = "";
		}
		else {
			levelup = "";
			fairetroupe = "";
			attaquer = "   ATTAQUER CE CHATEAU (Bas)";
		}
		infoText.setText("Nom :"+ castle.getName()+"          Florins :" + castle.getMoney() + 
				"          Niveau : " + castle.getLevel()+"          Troupes : " + 
				castle.getNbTroop(TroopType.Piquier)+"/"+castle.getNbTroop(TroopType.Knight)+"/"+castle.getNbTroop(TroopType.Onager) +
				"     |     "+levelup+fairetroupe+attaquer);                   
	}
	
	private void displayCustomMessage(String message)
	{
		infoText.setText(message);
	}
}
