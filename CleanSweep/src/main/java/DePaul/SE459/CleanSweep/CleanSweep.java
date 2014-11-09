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
        internalMap = new HashMap<>();
        unvisitedTiles = new ArrayList<>();
        visitedTiles = new ArrayList<>();
	}

	/**
	 * Cleans the floor
	 */
    public Map<Integer,Tile> cleanFloor()
    {
    	/*
    	// DEBUGGING
    	System.out.println("STARTING WITH HOME TILE");
    	*/
    	
    	//add current tile to visited list
    	visitedTiles.add(currentTile);
    	
    	/*
    	// DEBUGGING
		System.out.println("------------------ The size of visitedTiles is " + visitedTiles.size() + "-----------------");
    	*/
    	
        // Add unvisited surrounding tiles to unvisited tiles
    	surroundingTilesToUnvisted();
    	
    	/*
    	// DEBUGGING
		System.out.println("------------------ The size of UnvisitedTiles is " + unvisitedTiles.size() + "-----------------");
		*/
        
        // Add current and surrounding tiles to internal map
    	internalMap = new HashMap<Integer,Tile>();
    	internalMap = addTilesToInternalMap(internalMap);
    	
        //First tile was not being cleaned.  This is a temp fix. -Steven
        currentTile.setDirtAmount(0);
    	/*
    	// DEBUGGING
    	Integer count = internalMap.size();
    	System.out.println("------------------ The size of the INTERNAL MAP is " + count + "-----------------");
    	System.out.println("The contents of the INTERNAL MAP:");
    	// Get a set of the entries
        Set set = internalMap.entrySet();
        // Get an iterator
        Iterator i = set.iterator();
        // Display elements
        while(i.hasNext()) 
        {
           Map.Entry me = (Map.Entry)i.next();
           System.out.println(me.getKey() + ": (" + ((Tile) me.getValue()).getX() + ", " + ((Tile) me.getValue()).getY() + ")"); 
        }
    	
        // FOR DEBUGGIG WHILE LOOP
        int counting = 0;
        */
    	
        //while unvisitedTiles is not empty
    	while(!unvisitedTiles.isEmpty()) // TODO this may loop forever if some unvisited tiles are inaccessible
    	{
    		/*
    		// FOR DEBUGGING WHILE LOOP
    		counting++;
    		System.out.println("\n\n\nIn WHILE LOOP for " + counting + "th time\n\n"); 		
    		*/
    		
    		// Determine closest tile from unvisitedTile list
    		Tile nextTile = getNextTile(unvisitedTiles);
    		//if shortest path from charging station to nextTile * 2 > 50
                //remove that tile from unvisitedTile and log message indicating that
                //tile won't be cleaned - Steven 11/7
                
    		/*
    		// DEBUGGIN FOR 59ST TIME IN LOOP
    		if(counting == 59)
    		{
    			System.out.println("About to call move() with parameter tile: (" + nextTile.getX() + ", " + nextTile.getY() + ")");
    			System.out.println("Size of visitedTiles list: " + visitedTiles.size());
    			Iterator<Tile> itr = visitedTiles.iterator();
                while(itr.hasNext()) 
                {
                	Tile element = itr.next(); 
                	System.out.println("(" + element.getX() + ", " + element.getY() + ")");
                }
    		}
    		*/
    		
    		// Move to closest tile from unvisitedTile list
                List<Tile> traversed;
                if (battery.needToRecharge(currentTile, nextTile, new ArrayList<>(internalMap.values())) && !currentTile.isChargingStation())
                {
                    traversed = move(homeTile);
                    LoggingUtility.logDiscoveredCell("Back to charging station");
                    List<Tile> backToCurrent = move(nextTile);
                    traversed.addAll(backToCurrent);
                }
                else
                {
                    traversed = move(nextTile);
                }
                       
    		
    		/*
    		// DEBUGGIN FOR 51ST TIME IN LOOP
    		if(counting == 51)
    		{
    			System.out.println("Just called move()");
    		}
    		*/
    		
    		// Add new currentTile to visitedTiles list
    		//visitedTiles.add(currentTile);
                visitedTiles.addAll(traversed);
    		
    		/*
    		// DEBUGGING
    		System.out.println("Now at tile: (" + currentTile.getX() + ", " + currentTile.getY() + ")");
    		System.out.println("The visitedTiles list should increase and unvisitedTiles list should decrease");
    		System.out.println("------------------ The size of visitedTiles is " + visitedTiles.size() + " -----------------");
    		*/
    		
    		// Remove new currentTile from unvisitedTiles
    		unvisitedTiles.remove(currentTile);
    		
    		/*
    		// DEBUGGING
    		System.out.println("------------------ The size of UnvisitedTiles is " + unvisitedTiles.size() + " -----------------");
    		*/
    		
    		// Add unvisited surrounding tiles to unvisitedTiles list
    		surroundingTilesToUnvisted();
    		
    		/*
    		// DEBUGGING
    		System.out.println("Now that we're on a new tile, add surrounding tiles to unvisitedTiles list");
    		System.out.println("------------------ The size of UnvisitedTiles is " + unvisitedTiles.size() + "-----------------");
    		System.out.println("Current Tiles in unvisitedTiles list:");
    		Iterator<Tile> itr = unvisitedTiles.iterator();
            while(itr.hasNext()) 
            {
            	Tile element = itr.next(); 
            	System.out.println("(" + element.getX() + ", " + element.getY() + ")");
            }
            */
            
    		/*
    		// DEBUGGING
    		System.out.println("Now adding new tiles to internalMap");
    		*/
    		
    		// Add current and surrounding tiles to internal map, if not in map
    		internalMap = addTilesToInternalMap(internalMap);
    		
    		/*
    		// DEBUGGING
        	Integer counter = internalMap.size();
        	System.out.println("------------------ The size of the INTERNAL MAP is " + counter + "-----------------");
        	System.out.println("The contents of the INTERNAL MAP:");
        	// Get a set of the entries
            Set setTwo = internalMap.entrySet();
            // Get an iterator
            Iterator j = set.iterator();
            // Display elements
            while(j.hasNext()) 
            {
               Map.Entry me = (Map.Entry)j.next();
               System.out.println(me.getKey() + ": (" + ((Tile) me.getValue()).getX() + ", " + ((Tile) me.getValue()).getY() + ")"); 
            }
            */
    		
    		// Clean the tile (set dirt to 0)
            while(currentTile.getDirtAmount()>0)
            {
                currentTile.setDirtAmount(0);
            }
    	}
    	
    	/*
    	// DEBUGGING
    	System.out.println("------------------ The size of visitedTiles is " + visitedTiles.size() + "-----------------");
    	System.out.println("------------------ The size of internalMap is " + internalMap.size() + "-----------------");
    	*/
    	
    	// Now that's it done cleaning, return to the charging station
    	LoggingUtility.logReturn();
    	ShortestPath sPath = new ShortestPath(currentTile, homeTile, visitedTiles);
    	List<Tile> sPathList = sPath.getShortestPath();
    	Iterator<Tile> itr = sPathList.iterator();
        while(itr.hasNext()) 
        {
        	Tile element = itr.next();
        	move(element);
        }
    	
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
	 * Checks to see if the currentTile is dirty. 
	 * Returns true if the tile is dirty
	 * @param tile
	 * @return boolean
	 */
    private boolean isTileDirty(Tile tile)
    {
    	if(tile.getDirtAmount() > 0)
    	{
    		return true;
    	}
    	else 
    	{
    		return false;
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
            /*
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
            */
            ShortestPath path = new ShortestPath(currentTile, destination, new ArrayList<>(internalMap.values()));
            next = path.getShortestPath().get(1);
            
            /*
            List<Tile> visited = new ArrayList<Tile>();
            visited.addAll(visitedTiles);
            visited.addAll(tilesTraversed);
            if (battery.needToRecharge(currentTile, next, visited) && !currentTile.isChargingStation())
            {
		        LoggingUtility.logReturn();
		        List<Tile> shortestPath = battery.getShortestPath();
		        
		        for(int i = 1; i < shortestPath.size(); i++)
		        {
		        	if (shortestPath.get(i) != null)
		        	{
		        		prev = currentTile;
		        		currentTile = shortestPath.get(i);
		        		battery.decrementBatteryLevel(currentTile, prev);
		        		LoggingUtility.logMovement(currentTile);
		        	}
		        	
		        	if (currentTile.isChargingStation())
		        	{
		        		battery.chargeBattery();
		        		LoggingUtility.logRecharge();
		        		destination = currentTile;
		        		break;
		        	}
		        }
            }
            */
            //else
            //{
            tilesTraversed.add(next);
            //unvisitedTiles.remove(next);                                         // NOT SURE IF THIS SHOULD BE HERE  ... Think it's best placed in cleanFloor()  
            prev = currentTile;
            battery.decrementBatteryLevel(currentTile, next);                    
            currentTile = next;
            LoggingUtility.logMovement(next);
            //}                                  
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
