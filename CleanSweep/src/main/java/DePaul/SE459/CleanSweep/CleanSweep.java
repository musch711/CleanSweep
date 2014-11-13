package DePaul.SE459.CleanSweep;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Iterator;

public class CleanSweep {
    private Tile homeTile;
    private Tile currentTile;
    private BatteryManager battery;
    private DirtMeterUtility dirtMeter;
    //Contains information about all visited tiles as well as all adjacent tiles of tiles we have visited
    private Map<Integer, Tile> internalMap;
    //Contains list of tiles that have been visited
    private List<Tile> visitedTiles;
    //Contains list of tiles that have not been directly accessed
    private List<Tile> unvisitedTiles;
        
	public CleanSweep(Tile ht) {
            homeTile = ht;
            currentTile = ht;
            battery = new BatteryManager(homeTile);
            dirtMeter = new DirtMeterUtility();
            internalMap = new HashMap<>();
            unvisitedTiles = new ArrayList<>();
            visitedTiles = new ArrayList<>();
	}

	/**
	 * Cleans the floor
	 */
    public Map<Integer,Tile> cleanFloor()
    {
    	//add current tile to visited list
    	visitedTiles.add(currentTile);
    		
        // Add unvisited surrounding tiles to unvisited tiles
    	surroundingTilesToUnvisted();
    	
        // Add current and surrounding tiles to internal map
    	internalMap = new HashMap<Integer,Tile>();
    	internalMap = addTilesToInternalMap(internalMap);
    	
        //First tile was not being cleaned.  This is a temp fix. -Steven
        currentTile.setDirtAmount(0);
    	
        //while unvisitedTiles is not empty
    	while(!unvisitedTiles.isEmpty())
    	{
    		Tile nextTile = getNextTile(unvisitedTiles);

                List<Tile> traversed;
                if (battery.needToRecharge(currentTile, nextTile, new ArrayList<>(internalMap.values())) && !currentTile.isChargingStation())
                {
                    LoggingUtility.logDiscoveredCell("Going home to get more juice!!!");
                    traversed = move(homeTile);
                    battery.chargeBattery();
                    LoggingUtility.logRecharge();
                    List<Tile> backToCurrent = move(nextTile);
                    traversed.addAll(backToCurrent);
                }
                
                if (dirtMeter.emptyMeLightIsOn())
                {
                    LoggingUtility.logDiscoveredCell("Going home to get bag emptied!!!");
                    traversed = move(homeTile);
                    dirtMeter.emptyDirtBag();
                    LoggingUtility.logDiscoveredCell("Bag emptied!!!");
                    List<Tile> backToCurrent = move(nextTile);
                    traversed.addAll(backToCurrent);                    
                }

                    traversed = move(nextTile);
                    
                    while(currentTile.getDirtAmount()>0)
                    {
                        if (dirtMeter.emptyMeLightIsOn())
                            break;
                        dirtMeter.cleanOneDirtUnitFromTile(currentTile);
                        LoggingUtility.logDiscoveredCell("Current Dirt level at (" + currentTile.getX() + ", " + currentTile.getY() + ") is " + currentTile.getDirtAmount() );
                    }
                    if (currentTile.getDirtAmount()<=0)
                    {
                        unvisitedTiles.remove(currentTile);
                    }
                
    		// Add unvisited surrounding tiles to unvisitedTiles list
            visitedTiles.addAll(traversed);
    		surroundingTilesToUnvisted();
    		internalMap = addTilesToInternalMap(internalMap);
    	}
    	
    	// Now that's it done cleaning, return to the charging station
        LoggingUtility.logDiscoveredCell("Bag Capacity: " + dirtMeter.getCurrentDirtCapacity());
    	LoggingUtility.logReturn();
    	move(homeTile);
        // Once at the charging station re-charge.
    	battery.chargeBattery();
    	LoggingUtility.logRecharge();    	
    	
        return internalMap;
    }

    /**
	 * Adds current and surrounding tiles to internal map if they aren't already contained in the map
	 */
    private Map<Integer,Tile> addTilesToInternalMap(Map<Integer, Tile> internalMap)
    {
    	Integer count = internalMap.size();
    	
    	if(!internalMap.containsValue(currentTile))
    	{
    		internalMap.put(count,currentTile);
    	}
    	
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
        surroundingTiles.removeAll(visitedTiles);
        // Also need to remove unvisited Tiles from surroundingTiles,
        // because otherwise we may be counting tiles more than once
        surroundingTiles.removeAll(unvisitedTiles);
        
        
        // Copy surroundingTiles list to unvistedTiles list
        Iterator<Tile> itr = surroundingTiles.iterator();
        while(itr.hasNext()) 
        {
            unvisitedTiles.add(itr.next());
        }
    }
  
    /**
     * @param destination The target destination for the CleenSweep
     * @return List The path the CleanSweep traversed to get to the destination
     */
    private List<Tile> move(Tile destination)
    {
        List<Tile> tilesTraversed = new ArrayList<>();
        tilesTraversed.add(currentTile);
        Tile next;
        
        while(!currentTile.sameTile(destination))
        {
            ShortestPath path = new ShortestPath(currentTile, destination, new ArrayList<>(internalMap.values()));
            next = path.getShortestPath().get(1);
            
            tilesTraversed.add(next);
            battery.decrementBatteryLevel(currentTile, next);                    
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
            
            if (currentDistance == 1)
            {
            	// If the tile is below the current tile
            	if(t.getX() == currentTile.getX() && t.getY() < currentTile.getY())
            	{
            		if(currentTile.getLowerPath() == 2 || currentTile.getLowerPath() == 4) { }
            		else if(currentTile.getLowerPath() == 1)
            		{
            			nextTile = t;
            			shortestDistance = currentDistance;
            		}
            	}
            	// If the tile is above the current tile
            	else if(t.getX() == currentTile.getX() && t.getY() > currentTile.getY())
            	{
            		if(currentTile.getUpperPath() == 2 || currentTile.getUpperPath() == 4) { }
            		else if(currentTile.getUpperPath() == 1)
            		{
            			nextTile = t;
            			shortestDistance = currentDistance;
            		}
            	}
            	// If the tile is to the left of the current tile 
            	else if(t.getX() < currentTile.getX() && t.getY() == currentTile.getY())
            	{
            		if(currentTile.getLeftPath() == 2 || currentTile.getLeftPath() == 4) { }
            		else if(currentTile.getLeftPath() == 1)
            		{
            			nextTile = t;
            			shortestDistance = currentDistance;
            		}
            	}
            	// If the tile is to the right of the current tile
            	else if(t.getX() > currentTile.getX() && t.getY() == currentTile.getY())
            	{
            		if(currentTile.getRightPath() == 2 || currentTile.getRightPath() == 4) { }
            		else if(currentTile.getRightPath() == 1)
            		{
            			nextTile = t;
            			shortestDistance = currentDistance;
            		}
            	}
                
            }
            else if (currentDistance < shortestDistance && currentDistance != 1)
            {
                nextTile = t;
                shortestDistance = currentDistance;
            }
        }

        return nextTile;
    }
}
