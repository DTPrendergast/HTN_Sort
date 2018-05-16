import java.awt.Point;
import java.util.ArrayList;

public class Pallet extends SupplyItem
{
	public String composedOf;
	public int qtySubelements;
	public float weight;
	
	public Pallet(Point a, String composedOf, int qtySubelements)
	{
		this.location = a;
		this.weight = this.calcWeight(this);
		this.length = 4;
		this.width = 4;
		this.height = 4;
		this.composedOf = composedOf;
		this.qtySubelements = qtySubelements;
	}
	
	public static int initPallet(ArrayList<SupplyItem> supplies, int numPallet, Point a, String composedOf,
			int qtySubelements)
	{
		for (int i=0; i<numPallet; i++)
		{
			Pallet pallet = new Pallet(a, composedOf, qtySubelements);
			supplies.add(pallet);
		}
		return 1;
	}
	
	public float calcWeight(Pallet obj)
	{
		return 200;
		//TODO:  Enter function to calculate pallet weight
	}



}
