import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class Planner
{
	public static ArrayList<Method> operators;
	public static ArrayList<String> plan;
	
	public static void declareOperators(ArrayList<Actor> actors)
	{
		operators = new ArrayList();
		
		//System.out.println("Actors array size ... " + actors.size());
		for (int i=0; i<actors.size(); i++)
		{
			//System.out.println("looping through actors arrary; index is ... " + i);
			Method[] operatorListSuper = actors.get(i).getClass().getSuperclass().getDeclaredMethods();
			
			//System.out.println("operatorListSuper array size ... " + operatorListSuper.length);
			
			for (int j=0; j<operatorListSuper.length; j++)
			{				
				//System.out.println("looping through operatorListSuper arrary; index is ... " + j);
				operators.add(operatorListSuper[j]);				
			}
			Method[] operatorList = actors.get(i).getClass().getDeclaredMethods();
			//System.out.println("operatorList array size ... " + operatorList.length);
			
			
			for (int j=0; j<operatorList.length; j++)
			{
				//System.out.println("looping through operatorList arrary; index is ... " + j);
				operators.add(operatorListSuper[j]);
			}
		}		
	}
	
	public static void printOperators()
	{
		System.out.println("********  Operators  ********");
		for (int i=0; i<operators.size(); i++)
		{			
			System.out.println(operators.get(i).getName());					
		}
		System.out.println("*****************************");
	}
	
//	public static void initPlan()
//	{
//		Planner.plan = new ArrayList();
//	}
	public static void genPlan(LinkedHashMap<Method, ArrayList<Object>> taskList, Actor actor)
	{		
		int success = 0;
		Method task = taskList.entrySet().iterator().next().getKey();
		System.out.println("Entering genPlan with task... " + task.toString() + ";  parameters = " + taskList.get(task).toString());
		
		// Case 1:  Task is empty, so return completed plan
		if (task.equals(null))
		{
			return;
		}
		
		// Case 2:  Task is an operator... Perform operator and update world state
		if (operators.contains(task))
		{
			System.out.println("  Entering 'operators' branch... ");
			Method operator = task;
			taskList.get(operator).remove(0);
			try {
				System.out.println("    invoking task " + operator.getName() + ".  parameters = " + taskList.get(operator));
				operator.invoke(actor, taskList.get(operator)); } 
			catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace(); }
			System.out.println(actor.getClass());
			System.out.println(operator.getName());
			System.out.println(taskList.get(operator).toString());
			Planner.plan.add(actor.getClass().getName() + " performs " + operator.getName() + " with params = " + taskList.get(operator).toString());
			//taskList.remove(taskList.entrySet().iterator().next());
			taskList.remove(operator);
			System.out.println(taskList.toString());
//			Actor newActor = (Actor)taskList.entrySet().iterator().next().getValue().get(0);
//			Planner.genPlan(taskList, newActor);	
			return;
		}	
		
		// Case 3:  Task is a method, decompose task and recurse on the subtasks
		System.out.println("  Entering 'methods' branch...");
		LinkedHashMap<Method, ArrayList<Object>> subtasks = new LinkedHashMap();
		try {
			subtasks = (LinkedHashMap<Method, ArrayList<Object>>)task.invoke(actor, taskList.get(task)); } 
		catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace(); }
		
		System.out.println("    Subtasks = " + subtasks.keySet().toString());
		
		if (!subtasks.equals(null))
		{
			Iterator it = subtasks.entrySet().iterator();
			while (it.hasNext())
			{
				Map.Entry<Method, ArrayList<Object>> entry = (Map.Entry<Method, ArrayList<Object>>)it.next();				
				
				LinkedHashMap<Method, ArrayList<Object>> taskPair = new LinkedHashMap();
				taskPair.put((Method)entry.getKey(), (ArrayList<Object>)entry.getValue());
				
				actor = (Actor)(entry.getValue().get(0));
				System.out.println("    Re-entering genPlan with task " + entry.getKey().toString() + " and actor " + actor.toString());
				Planner.genPlan(taskPair, actor);				
			}
		}
		
		
		
		// Case 4:  Task list is not an operator or a method... Plan failure
		
		System.out.println("********   Plan Failure.  Task is neither an operator or method.   ********");
		return;	
		
	}
	
	public static void printPlan()
	{
		System.out.println("====================    Plan    ====================");
		int step = 1;
		for (int i=0; i<plan.size(); i++)
		{
			System.out.println("Step " + step + ": " + plan.get(i));			
		}
		System.out.println("====================================================");
	}

}
