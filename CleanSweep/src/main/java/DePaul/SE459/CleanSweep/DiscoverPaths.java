package DePaul.SE459.CleanSweep;

public class DiscoverPaths {

	/*
	 * @param Tile currentTile - the tile the Clean Sweep is currently on
	 * @return boolean - true if left path has stairs; false if left path has no stairs
	 */
	public static boolean stairsAtLeft(Tile currentTile){
		int path = 0; //path 0 = unknown path
		path = currentTile.getLeftPath();
		return findStairs(path);
	}
	
	/*
	 * @param Tile currentTile - the tile the Clean Sweep is currently on
	 * @return boolean - true if right path has stairs; false if right path has no stairs
	 */
	public static boolean stairsAtRight(Tile currentTile){
		int path = 0; //path 0 = unknown path
		path = currentTile.getRightPath();
		return findStairs(path);
	}

	/*
	 * @param Tile currentTile - the tile the Clean Sweep is currently on
	 * @return boolean - true if upper path has stairs; false if upper path has no stairs
	 */
	public static boolean stairsAbove(Tile currentTile){
		int path = 0; //path 0 = unknown path
		path = currentTile.getUpperPath();
		return findStairs(path);
	}
	
	/*
	 * @param Tile currentTile - the tile the Clean Sweep is currently on
	 * @return boolean - true if lower path has stairs; false if lower path has no stairs
	 */
	public static boolean stairsBelow(Tile currentTile){
		int path = 0; //path 0 = unknown path
		path = currentTile.getLowerPath();
		return findStairs(path);
	}
	
	/*
	 * @param: int path - the integer value of the path of the direction being calculated
	 * @return boolean - true if the path in the given direction has stairs; false if the path does not have stairs
	 *
	 * path = 4 <-- stairs in the path
	 */
	private static boolean findStairs(int path){
		if(path == 4)
			return true;
		return false;
	}
}
