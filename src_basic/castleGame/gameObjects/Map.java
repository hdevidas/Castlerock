package castleGame.gameObjects;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import castleGame.base.GameObject;
import castleGame.base.Inputs;
import castleGame.base.KeyboardInputsReceiver;
import castleGame.base.Sprite;
import castleGame.infoObjects.Owner;
import castleGame.infoObjects.Settings;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;


public class Map extends GameObject
{
	// VARIABLES
	public static Pane playfieldLayer;
	static Image oceanImage	= new Image("/images/water.png", Settings.SCENE_WIDTH/50, Settings.SCENE_WIDTH/50, true, true);

	//List of castle coordinates
	private List<javafx.geometry.Point2D> listXY = new ArrayList<javafx.geometry.Point2D>();
	
	private List<javafx.geometry.Point2D> listXY_ost_player = new ArrayList<javafx.geometry.Point2D>();
	
	private boolean[] chosen_name = new boolean[Settings.LIST_CASTLE_NAME.length];
	
	// Castles
	ArrayList<ArrayList<Castle>> castles = new ArrayList<ArrayList<Castle>>(1 + 1 + Settings.IA_CASTLE_NUMBER);
	
	public ArrayList<Ost> osts = new ArrayList<Ost>();

	private Random rnd = new Random();
	
	
	
	// CONSTRUCTORS
	public Map(Pane playfieldLayer)
	{
		this.playfieldLayer = playfieldLayer;
		
		// map loading
		spawnOcean();
		//Castle creation
		spawnCastles();
		//spawnTroops();
		
	}
	

	
	// GETTERS AND SETTERS
	Castle getRandomCastle()
	{
		if (Settings.NB_TOTAL_CASTLES > 0)
		{
			return getCastle(rnd.nextInt(Settings.NB_TOTAL_CASTLES));
		}
		else
		{
			System.out.println("error no castle found : Null castle returned by Map.getRandomCastle()");
			return null;
		}
	}
	
	Castle getCastle(int castleID)
	{
		for (Owner owner : Owner.values())
		{
			if (castleID < castles.get(owner.ordinal()).size())
			{
				for (Castle castle : castles.get(owner.ordinal()))
				{
					if (castleID == 0)
					{
						return castle;
					}
					else
					{
						castleID --;
					}
				}
			}
			else
			{
				castleID -= castles.get(owner.ordinal()).size();
			}
		}
		System.out.println("error castle not found : Null castle returned by Map.getCastle()");
		return null;
	}
	
	
	// INHERITED METHODS
	protected void updateThis()
	{
		Ost ost;
		for (Iterator<Ost> iterator = osts.iterator(); iterator.hasNext();)
		{
			ost = iterator.next();
			if (ost.isDead())
			{
				iterator.remove();
			}
		}
	}
	
	protected void updateChilds()
	{
		// Update Castles
		castles.forEach(castleList -> castleList.forEach(castle -> castle.update()));

		// Update Osts
		osts.forEach(ost -> ost.update());
	}
	
	private void spawnOcean() {
		
		for (int x =0; x< Settings.SCENE_WIDTH ; x= x + (int)oceanImage.getWidth()) {
			Sprite sprite = new Sprite(playfieldLayer, oceanImage, x, 0);
		}
		for (int x =0; x< Settings.SCENE_WIDTH ; x= x + (int)oceanImage.getWidth()) {
			Sprite sprite = new Sprite(playfieldLayer, oceanImage, x, Settings.SCENE_HEIGHT - (int)oceanImage.getHeight());
		}
		for (int y =0; y< Settings.SCENE_HEIGHT ; y= y + (int)oceanImage.getHeight()) {
			Sprite sprite = new Sprite(playfieldLayer, oceanImage, 0, y);
		}
		for (int y =0; y< Settings.SCENE_HEIGHT ; y= y + (int)oceanImage.getHeight()) {
			Sprite sprite = new Sprite(playfieldLayer, oceanImage, Settings.SCENE_WIDTH - (int)oceanImage.getWidth(), y);
		}
		
		
	}
	
	
	// METHODS
	private void spawnCastles() { // Création chateaux
		
		//creation of castle lists(player + neutral + IAs )
		for (int i = 0; i < Settings.IA_CASTLE_NUMBER + 2; i++)
		{
			this.castles.add(i, new ArrayList<Castle>());
		}		
		
		int errors = 0;
		
		//Castle creation
		if (!createCastle(Owner.Player, 1, 100000))
		{
			errors ++;
		}
		
		for (int i = 0; i < Settings.NEUTRAL_CASTLE_NUMBER; i++)
		{
			if (!createCastle(Owner.Neutral, rnd.nextInt(Settings.IA_NEUTRAL_CASTLE_MAX_LEVEL-1) + 1, 100))
			{
				errors ++;
			}
		}
		
		for (int i = 0; i < Settings.IA_CASTLE_NUMBER; i++)
		{
			if (!createCastle(Owner.valueOf("IA" + i), rnd.nextInt(Settings.IA_NEUTRAL_CASTLE_MAX_LEVEL-1) + 1 , 100))
			{
				errors ++;
			}
		}
		
		if (errors >= 1)
		{
			System.out.println("Error when spawning Castles : missing " + errors + " castle(s)... Try to tweak the number of castles to spawn in the settings and relaunch the game!");
		}
	}
				
	private Boolean createCastle(Owner owner, int level, int money)
	{
		for (int i = 0; i < Settings.NB_TRIES_TO_SPAWN; i++)
		{
			Point2D pos = new Point2D(
				/*x*/	rnd.nextDouble() * (Settings.SCENE_WIDTH - Owner.Player.castleImage.getWidth() -Settings.OST_MIN_DISTANCE_FROM_CASTLE - (Settings.OST_MIN_DISTANCE_FROM_CASTLE + Owner.Player.castleImage.getWidth() ) + 1) + Settings.OST_MIN_DISTANCE_FROM_CASTLE + Owner.Player.castleImage.getWidth(),
				/*y*/	rnd.nextDouble() * (Settings.SCENE_HEIGHT - Owner.Player.castleImage.getHeight() -Settings.OST_MIN_DISTANCE_FROM_CASTLE - (Settings.OST_MIN_DISTANCE_FROM_CASTLE + Owner.Player.castleImage.getHeight() ) + 1) + Settings.OST_MIN_DISTANCE_FROM_CASTLE + Owner.Player.castleImage.getHeight() ); 
			
			if (checkLocation(pos, Settings.CASTLE_SIZE*2))
			{
				Sprite sprite = new Sprite(playfieldLayer, owner.castleImage, pos); // TODO move inside castle constructor
				
				String name = owner == Owner.Player ? owner.getName() : generate_castle_name();
				
				Castle newCastle = new Castle(sprite, this, name, owner, money, level, Settings.ARMY_INIT, pos);
				
				listXY.add(new javafx.geometry.Point2D(pos.getX()+Settings.CASTLE_SIZE/2, pos.getY()+Settings.CASTLE_SIZE/2));
				
				castles.get(owner.ordinal()).add(newCastle);
	
				return true;
			}
		}
		return false; // TODO Raise an error instead of just returning false.
	}

	//Selection of a name on a castle list for spawning
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
	
	//Auxiliary function to help castle creation
	private boolean checkLocation(javafx.geometry.Point2D point2d, double minDistance) {
		for (javafx.geometry.Point2D p : listXY) {
			if (p.distance(point2d) < minDistance)
				return false;
		}
		return true;
	}
	
	public void addOst (Ost ost)
	{
		osts.add(ost);
	}
	
	public void removeOst (Ost ost)
	{
		osts.remove(ost);
	}

	public boolean player_is_alive(String name) {
		int counter = 0;
	    for(ArrayList<Castle> castleList : castles) {
	    	for (Castle castle : castleList) {
		    	if ((castle.getName() == name)/* && (castle.has_got_troops())*/){
		    		counter ++;
		    	}
	    	}
	    }
	    if (counter == 0) {
	    	return false;
	    }
	    return true;
	}
	
}
