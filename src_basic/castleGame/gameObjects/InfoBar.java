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
			displayCustomMessage("Click on the castle to launch the ost");
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
			levelup = "   LEVEL UP (Up)";
			fairetroupe = "   BUILD TROOP (Right)";
			attaquer = "";
		}
		else {
			levelup = "";
			fairetroupe = "";
			attaquer = "   ATTACKS THIS CASTLE (Down)";
		}
		infoText.setText("Name :"+ castle.getName()+"          Florins :" + castle.getMoney() + 
				"          Level : " + castle.getLevel()+"          Troops : " + 
				castle.getNbTroop(TroopType.Piquier)+"/"+castle.getNbTroop(TroopType.Knight)+"/"+castle.getNbTroop(TroopType.Onager) +
				"     |     "+levelup+fairetroupe+attaquer);                   
	}
	
	private void displayCustomMessage(String message)
	{
		infoText.setText(message);
	}
}
