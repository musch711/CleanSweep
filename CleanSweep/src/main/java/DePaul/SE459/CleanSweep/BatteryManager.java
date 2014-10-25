package DePaul.SE459.CleanSweep;

public class BatteryManager {
	private static final int MAX_BATTERY_CAPACITY = 50;
	private double currentBatteryLevel;
	private Tile homeTile;
		
	public BatteryManager(Tile homeTile) {
		this.homeTile = homeTile;
		chargeBattery();
	}
	
	/**
	 * Determines if there is enough battery life remaining traverse to the next Tile and still have enough energy to return to the charging station.
	 * @param currentTile The Tile the CleanSweep is currently occupying.
	 * @param nextTile The Tile the CleanSweep is attempting to occupy.
	 * @return True if the CleanSweep should return to the charging station to recharge, false if it is okay to traverse to the next Tile.
	 */
	public boolean needToRecharge(Tile currentTile, Tile nextTile) {
		double costToHome = 0;
		double nextMoveCost = 0;
		
		// TODO: find the least expensive distance to charging station from nextTile
		// we may need to add some kind of "visited" property to the Tile class that the movement team would set
		// when the tile is visited for the first time, since we don't want to include unvisited tiles in the
		// calculations here since we are not supposed to be discovering the floor plan at this point.
		// [insert complex logic here]
				
		nextMoveCost = calculateWeight(currentTile, nextTile);
		
		// if cost to return to charging station is greater than the current battery level
		// minus the cost of the proposed move, turn back.
		if (costToHome > currentBatteryLevel - nextMoveCost)
		{
			return true;
		}
		
		return false;
	}
	
	/**
	 * Returns the CleanSweep to the charging station based on the floor plan that has been visited during this cleaning session.
	 * @param currentTile The Tile the Clean Sweep is currently occupying.
	 */
	public void returnToChargingStation(Tile currentTile)
	{
		// [insert logic here]
	}
	
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
	private static double calculateWeight(Tile tileA, Tile tileB) {
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

	/**
	 * Decrements the battery capacity based on the cost of moving from tile A to tile B. 
	 */
	public void decrementBatteryLevel(Tile tileA, Tile tileB) {
		this.currentBatteryLevel -= calculateWeight(tileA, tileB);
	}
	
	/**
	 * Charges the battery to maximum capacity.
	 */
	public void chargeBattery() {
		this.currentBatteryLevel = MAX_BATTERY_CAPACITY;
	}
}
