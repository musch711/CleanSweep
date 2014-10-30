package DePaul.SE459.CleanSweep;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Iterator;

public class CleanSweep {
	private Tile homeTile;
    private Tile currentTile;
    //Contains information about all visited tiles as well as all adjacent tiles of tiles we have visited
    private Map<Integer, Tile> internalMap;
    //Contains list of tiles that have been visited
    private List<Tile> visitedTiles;
    //Contains list of tiles that have not been directly accessed
    private List<Tile> unvisitedTiles;
        
	public CleanSweep(Tile ht) {
		homeTile = ht;
        currentTile = ht;
        internalMap = new HashMap<>();
        unvisitedTiles = new ArrayList<>();
        visitedTiles = new ArrayList<>();
	}

	public void run() {
        System.out.println("CleanSweep has finished cleaning.");
	}
    
    public void cleanFloor()
    {
    	//add current tile to visited list
    	visitedTiles.add(currentTile);
    	
        // Add unvisited surrounding tiles to unvisited tiles
    	surroundingTilesToUnvisted();
        
        // Add current and surrounding tiles to internal map
    	internalMap = new HashMap<Integer,Tile>();
    	internalMap = addTilesToInternalMap(internalMap);
    	
        
        //while unvisitedTiles is not empty
    	while(!unvisitedTiles.isEmpty())
    	{
    		// Determine closest tile from unvisitedTile list
    		Tile nextTile = getNextTile(unvisitedTiles);
    		
    		// Move to closest tile from unvisitedTile list
    		currentTile = nextTile;
    		// Add new currentTile to visitedList
    		visitedTiles.add(currentTile);
    		// Remove new currentTile from unvisitedTiles
    		unvisitedTiles.remove(currentTile);
    		// Add unvisited surrounding tiles to unvisitedTiles
    		surroundingTilesToUnvisted();
    		
    		// Add current and surrounding tiles to internal map, if not in map
    		internalMap = addTilesToInternalMap(internalMap);
    		
    		// Clean the tile (set dirt to 0)
    		currentTile.setDirtAmount(0);		
    	}
    }

    /**
	 * Adds current and surrounding tiles to internal map if they aren't already in contained in the map
	 */
    private Map<Integer,Tile> addTilesToInternalMap(Map<Integer, Tile> internalMap)
    {
    	Integer count = internalMap.size();
    	internalMap.put(count++,currentTile);
    	
    	List<Tile> surroundingTiles = getAvailableMoves(currentTile);
    	
    	Iterator<Tile> itr = surroundingTiles.iterator();
        while(itr.hasNext()) 
        {
        	Tile element = itr.next();
        	
        	if(!internalMap.containsValue(element))
        	{
        		count++;
        		internalMap.put(count,element);
        	}
        }
    	
    	return internalMap;
    }
    
    
    
    /**
	 * Adds unvisited surrounding tiles to the unvisitedTiles list
	 */
    private void surroundingTilesToUnvisted()
    {
        // List to store the surrounding tiles
        List<Tile> surroundingTiles = new ArrayList<Tile>();
        // Assign list of surrounding tiles to surroundingTiles list
        surroundingTiles = getAvailableMoves(currentTile);
        
        // Remove vistedTiles from surroundingTiles
        if(!visitedTiles.isEmpty())
        {
        	surroundingTiles.removeAll(visitedTiles);
        }
        
        // Copy surroundingTiles list to unvistedTiles list
        Iterator<Tile> itr = surroundingTiles.iterator();
        while(itr.hasNext()) 
        {
            unvisitedTiles.add(itr.next());
        }
    }
    
    
    /**
	 * Stupid move.  Does not utilize internalMap to determine next movement
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
    
    /**
	 * Determines which tile from the unvisitedTiles list is closest
	 * to the currentTile.
	 * @param unvisitedTile list: Tiles that have yet to be visited
	 * @return Tile: The tile the vacuum should move to next
	 */
    private Tile getNextTile(List<Tile> unvisitedTiles)
    {
        Tile nextTile = unvisitedTiles.get(0);
        double shortestDistance = Double.MAX_VALUE;
        
        for (Tile t : unvisitedTiles)
        {
            double currentDistance = currentTile.distance(t);
            if (currentDistance < shortestDistance)
            {
                nextTile = t;
                shortestDistance = currentDistance;
            }
        }

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
				
				//cs.cleanFloor();
			}
		} catch (Exception e) {
			System.err.println("Exception in main: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
