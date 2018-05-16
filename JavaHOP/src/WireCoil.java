import java.awt.Point;
import java.util.ArrayList;

public class WireCoil extends SupplyItem
{
	public int qtyWire;
	public int gauge;

	public WireCoil(Point a)
	{
		this.location = a;
		this.weight = 30;
		this.length = 1.5f;
		this.width = 1.5f;
		this.height = .75f;
		this.qtyWire = 300;
		this.gauge = 14;
	}

	public static int initWireCoil(ArrayList<SupplyItem> supplies, int numWireCoil, Point a)
	{
		for (int i=0; i<numWireCoil; i++)
		{
			WireCoil wireCoil = new WireCoil(a);
			supplies.add(wireCoil);
		}
		return 1;
	}

}
