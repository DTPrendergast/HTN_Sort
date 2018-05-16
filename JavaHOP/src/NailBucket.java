import java.awt.Point;
import java.util.ArrayList;

public class NailBucket extends SupplyItem
{
	public int qtyNails;
	
	public NailBucket(Point a)
	{
		this.location = a;
		this.weight = 20;
		this.length = .75f;
		this.width = .75f;
		this.height = .75f;
		this.qtyNails = 1000;
	}

	public static int initNailBucket(ArrayList<SupplyItem> supplies, int numNailBucket, Point a)
	{
		for (int i=0; i<numNailBucket; i++)
		{
			NailBucket nailBucket = new NailBucket(a);
			supplies.add(nailBucket);
		}
		return 1;
	}

}
