package castleGame.gameObjects;

import java.util.ArrayList;

import castleGame.base.Inputs;
import castleGame.base.Sprite;
import castleGame.base.SpriteRender;
import castleGame.infoObjects.Settings;
import castleGame.infoObjects.TroopType;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Ost extends TroopsManager
{
	// VARIABLES
	private Map map;
	private Castle castleFrom;
	private Castle castleTarget;
	boolean isComplete;
	
	ArrayList<Point2D> path;
	
	
	
	// CONSTRUCTORS
	
	public Ost(Map map, Castle castleFrom, Castle castleTarget) 
	{
		super(castleFrom.owner);
		
		this.map = map;
		this.castleFrom = castleFrom;
		this.castleTarget = castleTarget;

		isComplete = false;
		
		makePathFinding();
		
		this.map.addOst(this);
	}

	
	
	// GETTERS AND SETTERS
	Point2D getNextPathDestination(int directionsLeft)
	{
		Point2D nextDestination = new Point2D ( path.get(directionsLeft).getX(),  path.get(directionsLeft).getY());
		return nextDestination;
	}
	
	
	// INHERITED METHODS
	//gameObject :
	protected void updateThis() 
	{
		if (isDead())
		{
			map.removeOst(this);
		}
	}
	
	protected void updateChilds()
	{
		super.updateChilds();
		// specific ost variable to update can be put here
	}
	
	//TroopManager :
	void addTroop(Troop troop)
	{
		super.addTroop(troop);
		troop.initJourney(this, castleFrom.getGateCoord(), path.size() , -1);
	}
	
	
	
	// METHODS
	void makePathFinding()
	{
		// TODO work on pathFinding
		path = new ArrayList<Point2D>();
		path.add(castleTarget.getGateCoord());
	}
	
	boolean isDead()
	{
		return (isComplete && !has_got_troops());
	}
	
	public void troopOnTarget(Troop troop) 
	{
		//TODO make the troop attack or put it in this castle
		//System.out.println("a troop has arrived at the target" + castleTarget.getCoord());
		
		troop.endJourneyTo(castleTarget);
	}
	
}
