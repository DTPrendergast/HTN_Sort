import java.util.*;
import com.google.common.collect.*;
import java.awt.Point;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Sort_Example {

	public static void main(String[] args)
	{
		int numForklift, numHusky, numPallet, numLumber, numNailBucket, numWireCoil, qtySubelements;
		String composedOf;
		Point locActors, locSupplies;
				
		// Build World State
		// ... Define the actors
		ArrayList<Actor> actors = new ArrayList();
		//ArrayList<Actor> actors = new ArrayList<Actor>();
		numForklift = 0;
		numHusky = 1;
		locActors = new Point(0, 0);
				
		Forklift.initForklift(actors, numForklift, locActors);
		Husky.initHusky(actors, numHusky, locActors);			
		
		// ... Define the supplies
		//ListMultimap<String, SupplyItem> supplies = ArrayListMultimap.create();
		ArrayList<SupplyItem> supplies = new ArrayList<SupplyItem>();
		numPallet = 0;
		composedOf = "NailBucket";
		qtySubelements = 3;
		numNailBucket = 1;
		numWireCoil = 0;
		numLumber = 0;
		locSupplies = new Point(5, 5);
		
		Pallet.initPallet(supplies, numPallet, locSupplies, composedOf, qtySubelements);
		NailBucket.initNailBucket(supplies, numNailBucket, locSupplies);
		WireCoil.initWireCoil(supplies, numWireCoil, locSupplies);
		Lumber.initLumber(supplies, numLumber, locSupplies);
		
		System.out.println("Actors:   " + actors + " at location " + actors.get(0).location.toString());
		System.out.println("Supplies: " + supplies + " at location " + supplies.get(0).location.toString());			

		
		// Give destinations for where we want the separated piles of supplies
		HashMap<String, Point> supplyDest = new HashMap();
		supplyDest.put(Lumber.class.getSimpleName(), new Point(2,2));
		supplyDest.put(NailBucket.class.getSimpleName(), new Point(8,6));
		supplyDest.put(WireCoil.class.getSimpleName(), new Point(3, 7));	
		

				
		// Declare Operators		
		LinkedHashMap<String, Method> operators = new LinkedHashMap();
		Planner.declareOperators(actors);
		Planner.printOperators();
		
		// Define "sort" problem		
		Actor actor = null;
		
		Method goal = null;
		try {
			goal = ProjectPlan.class.getMethod("sortSupplies", ArrayList.class); } 
		catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace(); } 
		ArrayList<Object> params = new ArrayList();
		params.add(actor);
		params.add(actors);
		params.add(supplies);
		params.add(locSupplies);
		params.add(supplyDest);	
		LinkedHashMap<Method, ArrayList<Object>> taskList = new LinkedHashMap();
		taskList.put(goal, params);
		
		Planner.plan = new ArrayList();
		Planner.genPlan(taskList, actor);
		Planner.printPlan();
		
		System.out.println("Actors:   " + actors + " at location " + actors.get(0).location.toString());
		System.out.println("Supplies: " + supplies + " at location " + supplies.get(0).location.toString());			

		
		// Print Results

	}

}
