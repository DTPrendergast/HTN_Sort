import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public abstract class Actor
{
	protected Point location;
	protected float speed;
	protected float weightCapacity;	
	protected List<String> carryableObjects;
	protected boolean carryingItem;
	
	public String identify(ArrayList<Object> paramsIn)
	{
		SupplyItem obj = (SupplyItem)paramsIn.get(0);
		if (this.location.equals(obj.location))
		{
			return obj.getClass().getName();
		}
		return null;
	}
	public int pickup(ArrayList<Object> paramsIn)
	{
		SupplyItem obj = (SupplyItem)paramsIn.get(0);
		if (this.carryableObjects.contains(obj.getClass().getName()) && obj.weight<=this.weightCapacity && this.location.equals(obj.location))
		{
			this.carryingItem = true;
			return 1;
		}
		return 0;
	}
	public int moveObject(ArrayList<Object> paramsIn)
	{
		SupplyItem obj = (SupplyItem)paramsIn.get(0);
		Point b = (Point)paramsIn.get(1);
		if (this.carryingItem==true)
		{
			this.location = b;
			obj.location = b;
			return 1;
		}	
		return 0;
	}
	public int place(ArrayList<Object> paramsIn)
	{
		SupplyItem obj = (SupplyItem)paramsIn.get(0);
		if (this.carryingItem==true)
		{
			this.carryingItem = false;
			return 1;
		}
		return 0;
	}
	public int moveSelf(ArrayList<Object> paramsIn)
	{
		Point b = (Point)paramsIn.get(0);
		this.location = b;
		return 1;		
	}
	public int breakdownPallet(ArrayList<Object> objArray)
	{
		return 0;
	}
	
}