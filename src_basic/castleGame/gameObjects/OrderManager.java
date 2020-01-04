package castleGame.gameObjects;

import java.util.ArrayList;

import castleGame.base.GameObject;

public class OrderManager extends GameObject
{
	// VARIABLES
	Castle castle;
	ArrayList<Order> orderList;
	Order currentOrder;
	boolean launchingOst;
	boolean waitingConditions;
	
	
	// CONSTRUCTORS
	
	
	
	// GETTERS AND SETTERS
	
	
	
	// INHERITED METHODS
	// GameObject
	protected void updateThis() 
	{
		if (orderList.size() != 0)
		{
			currentOrder = orderList.get(0);
			currentOrder.build();
			if (currentOrder.isComplete())
			{
				currentOrder.finishOrder(this.castle);
				orderList.remove(0);
				
			}
		}
	}
	
	protected void updateChilds() 
	{
		//this.orderList.forEach(order -> order.update()); // Order is not a game object so this line won't work (but it could be if necessary)
	}
	
	
	// METHODS
}
