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
		infoText.setText("Name :"+ castle.getName()+" ("+castle.getOwner()+")          Florins :" + castle.getMoney() + 
				"          Level : " + castle.getLevel()+"          Piquiers : " + 
				castle.getNbTroop(TroopType.Piquier)+"          "
						+ "Knights : "+castle.getNbTroop(TroopType.Knight)+"          "
								+ "Onagers : "+castle.getNbTroop(TroopType.Onager)+"          "
										+ "Order : " );                   
	}
	
	private void displayCustomMessage(String message)
	{
		infoText.setText(message);
	}
}
