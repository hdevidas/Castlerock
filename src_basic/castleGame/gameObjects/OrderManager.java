package castleGame.gameObjects;

import java.util.ArrayList;
import java.util.Comparator;

import castleGame.base.GameObject;
import castleGame.infoObjects.Owner;
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
	int[] troopsToSend;
	ArrayList<TroopType> priorityList;
	boolean waitToStart; // waiting for specific conditions to start the current order (more money for example)
	boolean waitToFinish; // waiting for specific conditions to finish the current order (no example for this case (yet?))
	
	
	
	// CONSTRUCTORS
	public OrderManager (Castle castle)
	{
		this.castle = castle; 
		orderList = new ArrayList<Order>();
	}
	
	
	// GETTERS AND SETTERS
	
	
	
	// INHERITED METHODS
	// GameObject
	protected void updateThis() 
	{
		if (castle.owner != Owner.Player)
		{
			makeAIOrder();
		}
		
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
		//this.orderList.forEach(order -> order.update()); 
		// Order is not a game object (but it could be if necessary), so ^this line^ won't work 
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
	
	void makeAIOrder()
	{
		//TODO Implement AI
		if (currentOrder == null)
		{
			this.newLevelUpOrder();
		}
	}
	
	public ArrayList<TroopType> makeTroopPriorityList(int[] troopsToSend)
	{
		ArrayList<TroopType> troopPriorityList = new ArrayList<TroopType>();
		
		for (TroopType troopType : TroopType.values()) 
		{
			if (troopsToSend[troopType.ordinal()] != 0)
			{
				troopPriorityList.add(troopType);
			}
		}
		
		Comparator<TroopType> troopSpeedComparator = new Comparator<TroopType>() {
			public int compare(TroopType troop1, TroopType troop2)
			{
				return troop1.getSpeed() - troop2.getSpeed();
			}
		};
		
		troopPriorityList.sort(troopSpeedComparator); // TODO test if this work as intended
		
		return troopPriorityList;
	}
	
	public boolean startLaunchingNewOst(Ost ost, int[] troopsToSend)
	{
		if (this.launchingOst)
		{
			return false;
		}
		else
		{
			launchingOst = true;
			ostToLaunch = ost;
			this.troopsToSend = troopsToSend;
			
			this.priorityList = makeTroopPriorityList(troopsToSend);
			
			return true;
		}
	}
	
	private void continueLaunchingOst()
	{
		boolean troopSent;
		for (int i = 0; i < Settings.CASTLE_DOOR_OUT_FLOW_LIMIT; i++)
		{
			troopSent = false;
			if (priorityList.size() != 0)
			{
				for (TroopType troopType : priorityList)
				{
					if (castle.giveTroopTo(ostToLaunch, troopType))
					{
						this.troopsToSend[troopType.ordinal()] --;
						if (troopsToSend[troopType.ordinal()] <= 0)
						{
							priorityList.remove(troopType);
							// Ideally this would need to be done via an iterator but as the 
							// for loop is broken just after it is not necessary
						}
						troopSent = true;
						break;
					}
					else
					{
						//there is a troop missing... faster troops will be sent for this turn
					}
				}
				if (!troopSent)
				{
					break;
				}
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
