import java.awt.Point;
import java.util.ArrayList;

public class Lumber extends SupplyItem
{

	public Lumber(Point a)
	{
		this.location = a;
		this.weight = 3;
		this.length = 8;
		this.width = 0.3333f;
		this.height = 0.1667f;		
	}

	public static int initLumber(ArrayList<SupplyItem> supplies, int numLumber, Point a)
	{
		for (int i=0; i<numLumber; i++)
		{
			Lumber lumber = new Lumber(a);
			supplies.add(lumber);
		}
		return 1;
	}
}
