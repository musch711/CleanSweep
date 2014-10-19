package DePaul.SE459.CleanSweep;

// To be used by the clean sweep to discover the floor plan
public class DiscoverFloorUtility
{
	// Get the origin tile ~ tile with the charging unit
	public static Tile getOriginTile(FloorPlan floorPlan)
	{
		int counter = floorPlan.numberOfFloors();
		Tile originTile = null;
		
	    for(int i = 0; i < counter; i++)
	    {
	    	Floor floorElement = floorPlan.getFloor(i);
	    	Tile tile = floorElement.getHomeTile();
	    	
	    	if(tile != null)
	    	{
	    		originTile = tile;
	    	}  
	    }
	    
		return originTile;
	}
	
	// Discovers information about current tile, and begins to construct/discover the floor plan
	// Saves to text file in the following format:
	// <cell x = 0, y = 0, ss = 2, ps = 1212, ds = 1, cs = 1 />
	public static void discoverCurrentTile(Tile tile)
	{
		int x = tile.getX();                         // x position
		int y = tile.getY();                         // y position
		int ss = tile.getSurfaceType();              // Surface Type 
		String obstacles = discoverObstacles(tile);  // Path Attributes
		int ds = tile.getDirtAmount();               // Dirt Sensor 
		boolean charging = tile.isChargingStation(); // Contains Charging Station?
		
		String cell = "<cell x = " + x + ", y = " + y + ", ss = " + ss + ", ps = " + obstacles + ", ds = " + ds + ", cs = " + charging + " />";
		
		LoggingUtility.LogDiscoveredFloorPlan(cell);
	}
	
	// Given a tile, determines the obstacles around it.
	// Returns a string formatted just as the paths attribute (ps)
	public static String discoverObstacles(Tile tile)
	{
		StringBuilder sb = new StringBuilder();
		
		int right = tile.getRightPath();
		int left = tile.getLeftPath();
		int upper = tile.getUpperPath();
		int lower = tile.getLowerPath();
		
		sb.append(right);
		sb.append(left);
		sb.append(upper);
		sb.append(lower);
		
		String obstacles = sb.toString();
		
		return obstacles;
	}
	
}
