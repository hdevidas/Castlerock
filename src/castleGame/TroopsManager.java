package castleGame;

// this interface contains all the methods to manage troops and their combined health as int arrays
// It is used in the following classes : Castle, Ost
public interface TroopsManager
{
	default void setTroops(int objectTroops[], int troopsValues[])
	{
		if (objectTroops.length != troopsValues.length)
		{
			return;
		}
		for (int i = 0; i < objectTroops.length; i++ )
		{
			objectTroops[i] = troopsValues[i];
		}
	}
	
	//void computeHealthToTroops();
	//TODO : add missing methods...
}
