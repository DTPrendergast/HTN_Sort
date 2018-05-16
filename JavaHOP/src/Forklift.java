import java.awt.Point;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ArrayList;

public class Forklift extends Actor
{
	public Forklift(Point a)
	{
		this.location = a;
		this.speed = 4;
		this.weightCapacity = 800;
		this.carryableObjects = Arrays.asList(new String[]{"Pallet"});
		this.carryingItem = false;
	}
	
	public static int initForklift(ArrayList<Actor> actors, int numForklift, Point a)
	{
		for (int i=0; i<numForklift; i++)
		{
			Forklift forklift = new Forklift(a);
			actors.add(forklift);			
		}
		return 1;
	}
}
