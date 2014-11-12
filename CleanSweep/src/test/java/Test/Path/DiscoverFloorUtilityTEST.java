package Test.Path;

import org.w3c.dom.Document;
import junit.framework.TestCase;
import DePaul.SE459.CleanSweep.DiscoverFloorUtility;
import DePaul.SE459.CleanSweep.FloorPlan;
import DePaul.SE459.CleanSweep.Tile;
import DePaul.SE459.Simulator.FloorPlanUtility;

public class DiscoverFloorUtilityTEST extends TestCase
{
	public DiscoverFloorUtilityTEST(String name) 
	{
		super(name);
	}
	
	// Version 1 w/ samplehome0.xml
	public void testGetOriginTileVersionOne()
	{
		String filePath = "floorplans//samplehome0.xml";
		FloorPlan floorPlan = null;
		
		try 
		{
			Document doc = FloorPlanUtility.loadFromFile(filePath);
			floorPlan = FloorPlanUtility.loadFloorPlan(doc);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		final Tile resultTile = DiscoverFloorUtility.getOriginTile(floorPlan);
		final Tile testResultTile = new Tile(0, 0, 2, 1, 2, 1, 2, 1, true);
		
		assertEquals(resultTile.getX(), testResultTile.getX());
		assertEquals(resultTile.getY(), testResultTile.getY());
		assertEquals(resultTile.isChargingStation(), testResultTile.isChargingStation());
	}
	
	// Version 1 w/ samplehome1.xml
	public void testGetOriginTileVersionTwo()
	{
		String filePath = "floorplans//samplehome1.xml";
		FloorPlan floorPlan = null;
		
		try 
		{
			Document doc = FloorPlanUtility.loadFromFile(filePath);
			floorPlan = FloorPlanUtility.loadFloorPlan(doc);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		final Tile resultTile = DiscoverFloorUtility.getOriginTile(floorPlan);
		final Tile testResultTile = new Tile(0, 0, 2, 1, 2, 1, 2, 1, true);
		
		assertEquals(resultTile.getX(), testResultTile.getX());
		assertEquals(resultTile.getY(), testResultTile.getY());
		assertEquals(resultTile.isChargingStation(), testResultTile.isChargingStation());
	}
}
