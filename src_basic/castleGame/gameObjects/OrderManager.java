package castleGame.gameObjects;

import java.util.ArrayList;
import java.util.Comparator;

import castleGame.base.GameObject;
import castleGame.infoObjects.Settings;
import castleGame.infoObjects.TroopType;

public class OrderManager extends GameObject
{
	// VARIABLES
	Castle castle;
	ArrayList<Order> orderList;
	Order currentOrder;
	boolean launchingOst;
	Ost ostToLaunch;
	ArrayList<Troop> troopsToSend;
	boolean waitToStart; // waiting for specific conditions to start the current order (more money for example)
	boolean waitToFinish; // waiting for specific conditions to finish the current order (no example for this case (yet?))
	
	
	
	// CONSTRUCTORS
	public OrderManager (Castle castle)
	{
		this.castle = castle; 
	}
	
	
	// GETTERS AND SETTERS
	
	
	
	// INHERITED METHODS
	// GameObject
	protected void updateThis() 
	{
		if (currentOrder != null) // if there's an order to do...
		{
			if (waitToStart) // if it hasn't started yet...
			{
				if (currentOrder.startNewOrder(castle)) // try to start it and if it worked...
				{
					waitToStart = false;
					currentOrder.build();
				}
			}
			else
			{
				if (currentOrder.isComplete()) // if this order is completely executed...
				{
					if (currentOrder.finishOrder(castle)) // try to finish it and if it worked... (for now it is always working)
					{
						waitToFinish = false;
						orderList.remove(currentOrder);
						if (orderList.size() > 0) // if there's another order in the list to execute next...
						{
							currentOrder = orderList.get(0);
							waitToStart = true;
						}
						else
						{
							currentOrder = null;
						}
					}
					else 
					{
						waitToFinish = true;
					}
				}
				else
				{
					currentOrder.build();
				}
			}
		}
		
		if (launchingOst)
		{
			continueLaunchingOst();
		}
	}

	protected void updateChilds() 
	{
		//this.orderList.forEach(order -> order.update()); // Order is not a game object (but it could be if necessary), so this line won't work 
	}
	
	
	// METHODS
	public void newBuildTroopOrder(TroopType troopType)
	{
		addNewOrder(new Order(this, troopType));
	}
	
	public void newLevelUpOrder()
	{
		addNewOrder(new Order(this));
	}
	
	public void addNewOrder(Order order)
	{
		orderList.add(order);
		if (currentOrder == null)
		{
			currentOrder = order;
		}
 	}
	
	public boolean startLaunchingNewOst(Ost ost, ArrayList<Troop> troopsToSend)
	{
		if (this.launchingOst)
		{
			return false;
		}
		else
		{
			launchingOst = true;
			this.troopsToSend = troopsToSend;
			
			Comparator<Troop> troopSpeedComparator = new Comparator<Troop>() {
				public int compare(Troop troop1, Troop troop2)
				{
					return troop1.getSpeed() - troop2.getSpeed();
				}
			};
			
			this.troopsToSend.sort(troopSpeedComparator);;
			
			return true;
		}
	}
	
	private void continueLaunchingOst()
	{
		for (int i = 0; i < Settings.CASTLE_DOOR_OUT_FLOW_LIMIT; i++)
		{
			if (troopsToSend.size() != 0)
			{
				this.ostToLaunch.addTroop(troopsToSend.get(0));
				troopsToSend.remove(0);
			}
			else
			{
				stopLaunchingOst();
			}
		}
	}
	
	public boolean stopLaunchingOst()
	{
		if (launchingOst)
		{
			ostToLaunch.isComplete = true;
			launchingOst = false;
			troopsToSend = null;
			return true;
		}
		else 
		{
			return false;
		}
	}
}
