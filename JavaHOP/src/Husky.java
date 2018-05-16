import java.awt.Point;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ArrayList;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Husky extends Actor
{
	public Husky(Point a)
	{
		this.location = a;
		this.speed = 10;
		this.weightCapacity = 30;
		this.carryableObjects = Arrays.asList(new String[]{"Lumber","NailBucket","WireCoil"});
		this.carryingItem = false;		
	}
	
	public static int initHusky(ArrayList<Actor> actors, int numHusky, Point a)
	{
		for (int i=0; i<numHusky; i++)
		{
			Husky husky = new Husky(a);
			actors.add(husky);
		}
		return 1;
	}
	
	public int breakdownPallet(ArrayList<Object> paramsIn)
	{
		// Parse the args
		Pallet pallet = (Pallet)paramsIn.get(0);
		ArrayList<SupplyItem> supplies = (ArrayList<SupplyItem>)paramsIn.get(1);
		
		
		if (this.location.equals(pallet.location))
		{
			// Identify proper class and constructor for instantiating the subelements
			Class<?> clas = null;
			Constructor<?> constr = null;
			try
			{				
				clas = Class.forName(pallet.composedOf);
				constr = clas.getDeclaredConstructor(Point.class);
			}
			catch (ClassNotFoundException | NoSuchMethodException | SecurityException e)
			{				
				e.printStackTrace();
			}			
			
			for (int i=0; i<pallet.qtySubelements; i++)
			{
				Object object = null;
				try
				{
					object = constr.newInstance(new Object[] {pallet.location});
				} 
				catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
				{
					e.printStackTrace();
				} 
				SupplyItem supply = (SupplyItem)object;
				supplies.add(supply);				
			}
			supplies.remove(pallet);
			return 1;
		}
		return 0;
	}
}
