package Test.Path;


import java.io.File;

import junit.framework.TestCase;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import DePaul.SE459.CleanSweep.Floor;
import DePaul.SE459.CleanSweep.FloorPlan;
import DePaul.SE459.CleanSweep.LoggingUtility;
import DePaul.SE459.CleanSweep.Tile;

/*
 * NOTE: The logging files (cleaning.txt, movement.txt, logDiscoveredCell.txt, logDiscoveredFloorPlan.txt)
 * need to be deleted and should not exist when LoggingUtilityTEST is run.
 */
public class LoggingUtilityTEST extends TestCase{

	public LoggingUtilityTEST(String name){
		super(name);
	}


	int counter = 0;
	final File trackingDirectory = new File("tracking"); //the directory name that tracking files are stored in

	public void testLogCleaning() throws IOException{
		File f = new File("tracking\\movement.txt");
		if(f.exists()) f.delete();
		//write to the movement.txt file
		LoggingUtility.LogCleaning(1, 1);
		LoggingUtility.LogCleaning(2, 2);
		LoggingUtility.LogCleaning(3, 3);
		LoggingUtility.LogCleaning(4, 4);
		LoggingUtility.LogCleaning(5, 5);
		LoggingUtility.LogCleaning(6, 6);
		
		//create a buffered reader to read from the movement.txt file
		BufferedReader reader = new BufferedReader(new FileReader("tracking\\movement.txt"));
		
		
		String line = null;
		line = reader.readLine();
		assertEquals(true, line.equals(" Cleaned tile at: (1, 1)"));
		assertEquals(false, line.equals(" Cleaned tile at: (2, 1)"));
		
		
		line = reader.readLine();
		assertEquals(true, line.equals(" Cleaned tile at: (2, 2)"));
		assertEquals(false, line.equals(" Cleaned tile at: (3, 2)"));

		line = reader.readLine();
		assertEquals(true, line.equals(" Cleaned tile at: (3, 3)"));
		assertEquals(false, line.equals(" Cleaned tile at: (25, 13)"));

		line = reader.readLine();
		assertEquals(true, line.equals(" Cleaned tile at: (4, 4)"));
		assertEquals(false, line.equals(" Cleaned tile at: (42, 16)"));

		line = reader.readLine();
		assertEquals(true, line.equals(" Cleaned tile at: (5, 5)"));
		assertEquals(false, line.equals(" Cleaned tile at: (32, 51)"));

		line = reader.readLine();
		assertEquals(true, line.equals(" Cleaned tile at: (6, 6)"));
		assertEquals(false, line.equals(" Cleaned tile at: (23, 11)"));
		reader.close();
	}
	
	
	public void testLogDiscoveredCell() throws IOException{
		File f = new File("tracking\\movement.txt");
		if(f.exists()) f.delete();

		//write to the logDiscoveredCell.txt file
		LoggingUtility.logDiscoveredCell("<cell x = 1, y = 2, ss = 1, ps = 1121, ds = 2, cs = 1 />");
		LoggingUtility.logDiscoveredCell("<cell x = 2, y = 3, ss = 2, ps = 1212, ds = 1, cs = 1 />");
		LoggingUtility.logDiscoveredCell("<cell x = 3, y = 4, ss = 3, ps = 2112, ds = 2, cs = 1 />");
		LoggingUtility.logDiscoveredCell("<cell x = 4, y = 5, ss = 1, ps = 1211, ds = 1, cs = 0 />");
		
		//create a buffered reader to read from the cleaning.txt file
		BufferedReader reader = new BufferedReader(new FileReader("tracking\\movement.txt"));

		String line = null;
		line = reader.readLine();
		assertEquals(true, line.equals("<cell x = 1, y = 2, ss = 1, ps = 1121, ds = 2, cs = 1 />"));
		assertEquals(false, line.equals("<cell x = 2, y = 3, ss = 2, ps = 1212, ds = 1, cs = 1 />"));
		
		
		line = reader.readLine();
		assertEquals(true, line.equals("<cell x = 2, y = 3, ss = 2, ps = 1212, ds = 1, cs = 1 />"));
		assertEquals(false, line.equals("<cell x = 3, y = 4, ss = 3, ps = 2112, ds = 2, cs = 1 />"));

		line = reader.readLine();
		assertEquals(true, line.equals("<cell x = 3, y = 4, ss = 3, ps = 2112, ds = 2, cs = 1 />"));
		assertEquals(false, line.equals("<cell x = 1, y = 2, ss = 1, ps = 1121, ds = 2, cs = 1 />"));

		line = reader.readLine();
		assertEquals(true, line.equals("<cell x = 4, y = 5, ss = 1, ps = 1211, ds = 1, cs = 0 />"));
		assertEquals(false, line.equals("<cell x = 5, y = 5, ss = 1, ps = 1211, ds = 1, cs = 0 />"));

		reader.close();

	}
	
	public void testLogMovement() throws IOException{
		File f = new File("tracking\\movement.txt");
		if(f.exists()) f.delete();

		//write to the cleaning.txt file
		LoggingUtility.logMovement(1, 1);
		LoggingUtility.logMovement(2, 2);
		LoggingUtility.logMovement(3, 3);
		LoggingUtility.logMovement(4, 4);
		
		//create a buffered reader to read from the cleaning.txt file
		BufferedReader reader = new BufferedReader(new FileReader("tracking\\movement.txt"));

		String line = null;
		line = reader.readLine();
		assertEquals(true, line.equals("Current location at tile: (1, 1)"));
		assertEquals(false, line.equals("Current location at tile: (2, 1)"));
		
		
		line = reader.readLine();
		assertEquals(true, line.equals("Current location at tile: (2, 2)"));
		assertEquals(false, line.equals("Current location at tile: (3, 2)"));

		line = reader.readLine();
		assertEquals(true, line.equals("Current location at tile: (3, 3)"));
		assertEquals(false, line.equals("Current location at tile: (25, 13)"));

		line = reader.readLine();
		assertEquals(true, line.equals("Current location at tile: (4, 4)"));
		assertEquals(false, line.equals("Current location at tile: (42, 16)"));

		reader.close();

	}
	
	public void testLogDiscoveredFloorPlan() throws IOException{
		File f = new File("tracking\\movement.txt");
		if(f.exists()) f.delete();

		//create tiles
		//								  r  l  u  d
		final Tile t1 = new Tile(1, 1, 1, 1, 1, 1, 2, 1, false);
		final Tile t2 = new Tile(1, 2, 2, 4, 1, 4, 1, 2, false);
		final Tile t3 = new Tile(2, 1, 4, 2, 4, 2, 2, 2, false);
		final Tile t4 = new Tile(2, 2, 2, 1, 2, 4, 4, 2, false);
		
		//create a floor (1st floor) from those tiles
		Floor floor1 = new Floor(1);
		
		//add tiles to the floor:
		floor1.addTile(t1);
		floor1.addTile(t2);
		floor1.addTile(t3);
		floor1.addTile(t4);
		
		//create a new floorplan
		FloorPlan floorPlan1 = new FloorPlan();
		
		//add the floor to the floorplan:
		floorPlan1.addFloor(floor1);
		
		LoggingUtility.logDiscoveredFloorPlan(floorPlan1);

		//create a buffered reader to read from the cleaning.txt file
		BufferedReader reader = new BufferedReader(new FileReader("tracking\\logDiscoveredFloorPlan.txt"));

		String line = null;
		line= reader.readLine();
		assertEquals(true, line.equals("---Begin Floor 1---"));
		
		line= reader.readLine();
		assertEquals(true, line.equals("Tile: x=1 y=1 ss=1 ds=1"));
		assertEquals(false, line.equals("Tile: x=3 y=11 ss=21 ds=14"));

		line= reader.readLine();
		assertEquals(true, line.equals("Tile: x=1 y=2 ss=2 ds=2"));
		assertEquals(false, line.equals("Tile: x=13 y=21 ss=1 ds=1"));

		line= reader.readLine();
		assertEquals(true, line.equals("Tile: x=2 y=1 ss=4 ds=2"));
		assertEquals(false, line.equals("Tile: x=41 y=22 ss=2 ds=2"));

		line= reader.readLine();
		assertEquals(true, line.equals("Tile: x=2 y=2 ss=2 ds=2"));
		assertEquals(false, line.equals("Tile: x=12 y=12 ss=2 ds=2"));

		line= reader.readLine();
		assertEquals(true, line.equals("---End Floor 1---"));
		assertEquals(false, line.equals("End Floor 1---"));

		reader.close();

		
	}
	
}

