package DePaul.SE459.CleanSweep;

public class BatteryManager {
	/**
	 * Determines weight of moving from Tile A to Tile B based on surface type of each tile.
	 * This method assumes the two tiles are adjacent to one another.
	 * Weights: bare floor = 1, low-pile carpet = 2, high-pile carpet = 3
	 *   bare to bare:              = 1
	 *   bare to low/low to bare:   = 1.5
	 *   bare to high/high to bare: = 2
	 *   low to low:                = 2
	 *   low to high/high to low:   = 2.5
	 *   high to high:              = 3
	 * @return The cost, in battery life, of moving from Tile A to Tile B.
	 */
	public double caluculateWeight(Tile tileA, Tile tileB) {
		double weight = 0;
		// if moving from bare floor to bare floor
		if (tileA.getSurfaceType() == 1 && tileB.getSurfaceType() == 1)
		{
			weight = 1;
		}
		// if moving from bare floor to low-pile carpet or vice versa
		else if ((tileA.getSurfaceType() == 1 && tileB.getSurfaceType() == 2) ||
				 (tileA.getSurfaceType() == 2 && tileB.getSurfaceType() == 1))
		{
			weight = 1.5;
		}
		// if moving from bare to high-pile carpet or vice versa
		else if ((tileA.getSurfaceType() == 1 && tileB.getSurfaceType() == 3) ||
				 (tileA.getSurfaceType() == 3 && tileB.getSurfaceType() == 1))
		{
			weight = 2;
		}
		// if moving from low-pile carpet to low-pile carpet
		else if (tileA.getSurfaceType() == 2 && tileB.getSurfaceType() == 2)
		{
			weight = 2;
		}
		// if moving from low-pile carpet to high-pile carpet or vice versa
		else if ((tileA.getSurfaceType() == 2 && tileB.getSurfaceType() == 3) ||
				 (tileA.getSurfaceType() == 3 && tileB.getSurfaceType() == 2))
		{
			weight = 2.5;
		}
		// if moving from high-pile carpet to high-pile carpet
		else if (tileA.getSurfaceType() == 3 && tileB.getSurfaceType() == 3)
		{
			weight = 3;
		}
		
		return weight;
	}
}
