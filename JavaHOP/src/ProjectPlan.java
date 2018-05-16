import java.awt.Point;
import java.lang.reflect.Method;
import java.util.*;

public class ProjectPlan
{
	public static LinkedHashMap<Method, ArrayList<Object>> sortSupplies(ArrayList<Object> paramsIn)
	{		
		// Parse incoming args
		Actor actor1 = (Actor)paramsIn.get(0);
		ArrayList<Actor> actors = (ArrayList<Actor>)paramsIn.get(1);		
		ArrayList<SupplyItem> supplies = (ArrayList<SupplyItem>)paramsIn.get(2);
		Point locStuffToBeSorted = (Point)paramsIn.get(3);
		HashMap<String, Point> supplyDestinations = (HashMap<String, Point>)paramsIn.get(4);
		
		LinkedHashMap<Method, ArrayList<Object>> methodList = new LinkedHashMap();
		ArrayList<Object> params = new ArrayList();
		Method method = null;
		
		// Identify items to be sorted
		ArrayList<SupplyItem> pile = new ArrayList();
		for (int i=0; i<supplies.size(); i++)
		{
			if (supplies.get(i).location.equals(locStuffToBeSorted))
			{
				pile.add(supplies.get(i));
			}
		}
		
		// Resolve actors present at the site
		Actor forklift = null;
		Actor husky = null;	
		
		boolean forkliftPresent = false;
		boolean huskyPresent = false;
		for (Actor actor: actors)
		{
			
			if (actor.getClass().getSimpleName().equals("Forklift"))
			{
				forkliftPresent = true;
				forklift = actor;				
			}
			if (actor.getClass().getSimpleName().equals("Husky"))
			{
				huskyPresent = true;
				husky = actor;
			}				
		}
		
		int count = 1;
		// Sort the pile by moving each item starting at front of pile
		for (SupplyItem item: pile)
		{
			count++;
			if (item instanceof Pallet && forkliftPresent)
			{
				Pallet pallet = (Pallet)pile.get(0);
				try
				{
					method = ProjectPlan.class.getMethod("transportPallet", ArrayList.class);
				}
				catch (NoSuchMethodException | SecurityException e)
				{
					e.printStackTrace();
				}
				if (!method.equals(null))
				{
					params.clear();
					params.add(forklift);
					params.add(pallet);
					params.add(supplyDestinations);					
					methodList.put(method, params);				
				}				
			}
			else
			{				
				try	{
					method = ProjectPlan.class.getMethod("transportObj", ArrayList.class); } 
				catch (NoSuchMethodException | SecurityException e) {
					e.printStackTrace(); }
				if (!method.equals(null))
				{
					params.clear();
					params.add(husky);					
					params.add(item);
					params.add(pile);
					params.add(supplyDestinations);
					methodList.put(method, params);					
				}
			}
		}	
		System.out.println("        Exiting sortSupplies with methodList = " + methodList.toString());
		return methodList;
		
	}
	public static LinkedHashMap<Method, ArrayList<Object>> transportPallet(ArrayList<Object> paramsIn)
	//Forklift forklift, Pallet pallet, HashMap<String, Point> supplyDest)
	{
		// Parse incoming args
		Forklift forklift = (Forklift)paramsIn.get(0);
		Pallet pallet = (Pallet)paramsIn.get(1);
		HashMap<String, Point> supplyDest = (HashMap<String, Point>)paramsIn.get(2);
		
		// TODO: write this method
		
		return null;
	}
	public static LinkedHashMap<Method, ArrayList<Object>> transportObj(ArrayList<Object> paramsIn)
	//Husky husky, SupplyItem supplyItem, ArrayList<SupplyItem> pile, HashMap<String, Point> supplyDestinations)
	{
		// Parse incoming arguments
		Husky husky = (Husky)paramsIn.get(0);
		SupplyItem supplyItem = (SupplyItem)paramsIn.get(1);
		ArrayList<SupplyItem> pile = (ArrayList<SupplyItem>)paramsIn.get(2);
		HashMap<String, Point> supplyDestinations = (HashMap<String, Point>)paramsIn.get(3);
				
		LinkedHashMap<Method, ArrayList<Object>> methodList = new LinkedHashMap();
		ArrayList<Object> params = new ArrayList();		
		Method method = null;
		
		if (supplyItem instanceof Pallet)
		{
			Pallet pallet = (Pallet)supplyItem;
			Point dest = supplyDestinations.get(pallet.composedOf);
			try
			{
				method = husky.getClass().getMethod("breakdownPallet", pallet.getClass(), pile.getClass());
			} 
			catch (NoSuchMethodException | SecurityException e)
			{
				e.printStackTrace();
			}
			if (!method.equals(null))
			{
				params.add(husky);
				params.add(pallet);
				params.add(pile);
				methodList.put(method, params);
			}
			
			try {
				method = ProjectPlan.class.getMethod("transportSingleObj", ArrayList.class); } 
			catch (NoSuchMethodException | SecurityException e) {
				e.printStackTrace(); }
			if (!method.equals(null))
			{
				for (int i=0; i<pallet.qtySubelements; i++)	{
					params.clear();
					params.add(husky);
					params.add(pile.get(pile.size()-1));
					params.add(dest);					
					methodList.put(method, params); }
			}
			
		}
		else if (!(supplyItem instanceof Pallet))
		{
			Point dest = supplyDestinations.get(supplyItem.getClass().getSimpleName());
			params.clear();
			params.add(husky);
			params.add(supplyItem);
			params.add(dest);
			try {
				method = ProjectPlan.class.getMethod("transportSingleObj", ArrayList.class);	} 
			catch (NoSuchMethodException | SecurityException e) {
				e.printStackTrace(); }
			if (!method.equals(null))
			{
				methodList.put(method, params);
			}			
		}
		System.out.println("        Exiting transportObj method with methodList = " + methodList.toString());
		return methodList;
	}
	public static LinkedHashMap<Method, ArrayList<Object>> transportSingleObj(ArrayList<Object> paramsIn)
	//Husky husky, SupplyItem supplyItem, Point dest)
	{
		// Parse incoming args
		Husky husky = (Husky)paramsIn.get(0);
		SupplyItem supplyItem = (SupplyItem)paramsIn.get(1);
		Point dest = (Point)paramsIn.get(2);
			
		LinkedHashMap<Method, ArrayList<Object>> methodList = new LinkedHashMap();
		
		Method method = null;
		
		// Move robot to where the object is located, if necessary
		if (!husky.location.equals(supplyItem.location))
		{
			ArrayList<Object> params = new ArrayList();
			params.add(husky);
			params.add(supplyItem.location);
			try	{
				method = husky.getClass().getMethod("moveSelf", ArrayList.class);} 
			catch (NoSuchMethodException | SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();}
			if (!method.equals(null))
			{
				methodList.put(method, params);
			}
		}
		
		// Pickup object
		ArrayList<Object> params1 = new ArrayList();
		params1.add(husky);
		params1.add(supplyItem);
		
		try {
			method = husky.getClass().getMethod("pickup", ArrayList.class);} 
		catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();}
		if (!method.equals(null))
		{
			methodList.put(method, params1);
		}				
		
		// Move with object
		ArrayList<Object> params2 = new ArrayList();
		params2.add(husky);
		params2.add(supplyItem);
		params2.add(dest);
		try {
			method = husky.getClass().getMethod("moveObject", ArrayList.class);} 
		catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();}
		if (!method.equals(null))
		{
			methodList.put(method, params2);	
		}
		
		// Place 
		ArrayList<Object> params3 = new ArrayList();
		params3.add(husky);
		params3.add(supplyItem);
		try {
			method = husky.getClass().getMethod("place", ArrayList.class);} 
		catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace(); }
		if (!method.equals(null))
		{
			methodList.put(method, params3);			
		}		
		System.out.println("        Exiting transportSingleObj with ... " + methodList.toString());
		return methodList;		
	}

}
