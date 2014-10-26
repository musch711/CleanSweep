package DePaul.SE459.CleanSweep;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CleanSweep {
	private Tile homeTile;
        private Tile currentTile;
        private Map<Integer, Tile> internalMap;
        private List<Tile> unvisitedTiles;
        
	public CleanSweep(Tile ht) {
		homeTile = ht;
                currentTile = ht;
                internalMap = new HashMap<>();
                unvisitedTiles = new ArrayList<>();
	}

	public void run() {
            System.out.println("CleanSweep has finished cleaning.");
	}
    
        public void cleanFloor()
        {
            
            
        }
        /**
	 * Stupid move.  Does not utilize internalMap to determine next movement
	 * TODO: populate CleanSweep's internalMap object
	 * @param destination The target destination for the CleenSweep
	 * @return List The path the CleanSweep traversed to get to the destination
	 */
        private List<Tile> move(Tile destination)
        {
            List<Tile> tilesTraversed = new ArrayList<>();
            tilesTraversed.add(currentTile);
            List<Tile> availableTiles;
            List<Tile> availableMinusVisited;
            Tile next;
            Tile prev = currentTile;
            
            while(!currentTile.sameTile(destination))
            {

                availableTiles = getAvailableMoves(currentTile);

                //Ignore prior tiles at first
                availableMinusVisited = new ArrayList<>(availableTiles);
                availableMinusVisited.removeAll(tilesTraversed);
                if (!availableMinusVisited.isEmpty())
                {
                    
                    next = getNextTile(destination, availableMinusVisited);
                }
                else
                {
                    next = getNextTile(destination, availableTiles);
                    if (next.sameTile(prev))
                    {
                        availableTiles.remove(prev);
                        next = getNextTile(destination, availableTiles);
                    }
                }
                tilesTraversed.add(next);
                unvisitedTiles.remove(next);
                prev = currentTile;
                currentTile = next;
                LoggingUtility.logMovement(next);
            }
            return tilesTraversed;
        }
        
        /**
	 * Given a tile, creates a list of all acceptable moves for the vacuum
	 * 
	 * @param currentPos The current position of the tile
	 * @return List A list of tiles that the vacuum can legally move to
	 */
        private List<Tile> getAvailableMoves(Tile currentPos)
        {
            List<Tile> availableMoves = new ArrayList<>();
            if (currentPos.getLeftPath()==1)
                availableMoves.add(currentPos.getLeftTile());
            if (currentPos.getRightPath()==1)
                availableMoves.add(currentPos.getRightTile());
            if (currentPos.getUpperPath()==1)
                availableMoves.add(currentPos.getUpperTile());
            if (currentPos.getLowerPath()==1)
                availableMoves.add(currentPos.getLowerTile());
            return availableMoves;
        }
        
        /**
	 * Calculates the shortest distance to the destination tile
	 * given a list of tiles that the vacuum can move to.
	 * @param d The destination tile
         * @param prospectiveTiles list of tiles that can be reached from the current tile
	 * @return Tile The tile the vacuum should move to next
	 */
        private Tile getNextTile(Tile d, List<Tile> prospectiveTiles)
        {
            Tile nextTile = prospectiveTiles.get(0);
            double shortestDistance = Double.MAX_VALUE;
            //System.out.println("Distance Begin");
            //LoggingUtility.logMovement(d);
            for (Tile t : prospectiveTiles)
            {
                double currentDistance = d.distance(t);
                if (currentDistance < shortestDistance)
                {
                    nextTile = t;
                    shortestDistance = currentDistance;
                }
                
                //LoggingUtility.logMovement(t);
                //System.out.println(currentDistance);

            }
            //System.out.println("Distance End");
            return nextTile;
        }
        
        //For testing of the move method
        public static void main(String args[]) {
		String filePath;
		if (args.length == 0) {
			filePath = "floorplans//samplehome0.xml";
		} else {
			filePath = args[0];
		}

		try {
			FloorPlan floorPlan = FloorPlanUtility.loadFloorPlan(filePath);

			Tile homeTile = null;
			for (int i = 0; i < floorPlan.numberOfFloors(); i++) {
				Tile home = floorPlan.getFloor(i).getHomeTile();
				if (home != null) {
					homeTile = home;
				}
			}

			if (homeTile != null) {
				CleanSweep cs = new CleanSweep(homeTile);
				System.out.println("CleanSweep is starting...");
				cs.move(floorPlan.getFloor(0).getTile(5, 0));
                                
			}
		} catch (Exception e) {
			System.err.println("Exception in main: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
