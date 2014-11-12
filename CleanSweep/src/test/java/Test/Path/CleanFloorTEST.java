package Test.Path;

import java.util.Map;

import org.w3c.dom.Document;

import DePaul.SE459.CleanSweep.CleanSweep;
import DePaul.SE459.CleanSweep.FloorPlan;
import DePaul.SE459.CleanSweep.Tile;
import DePaul.SE459.Simulator.FloorPlanUtility;
import junit.framework.TestCase;

public class CleanFloorTEST extends TestCase
{
	public CleanFloorTEST(String name)
	{
		super(name);
	}
	
	public void testCleanFloor()
	{
		String filePath = "floorplans//samplehome0.xml";
		
		try 
		{
			Document doc = FloorPlanUtility.loadFromFile(filePath);
			FloorPlan floorPlan = FloorPlanUtility.loadFloorPlan(doc);
			
			int numOfTilesInFloor = floorPlan.getFloor(0).numberOfTiles();
			
			Tile homeTile = null;
			
			for (int i = 0; i < floorPlan.numberOfFloors(); i++) 
			{
				Tile home = floorPlan.getFloor(i).getHomeTile();
				if (home != null) 
				{
					homeTile = home;
				}
			}

			if (homeTile != null) 
			{
				CleanSweep cs = new CleanSweep(homeTile);
				Map<Integer, Tile> m = cs.cleanFloor();
				
				assertEquals(numOfTilesInFloor, m.size());       
			}
		} 
		catch (Exception e) 
		{
			System.err.println("Exception in main: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
