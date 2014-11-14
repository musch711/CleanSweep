package Test.Path;

import DePaul.SE459.CleanSweep.BatteryManager;
import DePaul.SE459.CleanSweep.CleanSweep;
import DePaul.SE459.CleanSweep.FloorPlan;
import DePaul.SE459.CleanSweep.Tile;
import DePaul.SE459.Simulator.FloorPlanUtility;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static junit.framework.Assert.assertEquals;
import junit.framework.TestCase;
import org.w3c.dom.Document;

public class BatteryManagerTEST extends TestCase {

	public BatteryManagerTEST(String name){
		super(name);
	}
        
        protected Tile homeTile;
        protected FloorPlan floorPlan;
        protected BatteryManager bm;
        protected CleanSweep cs;
   
        protected void setUp(){
            String filePath = "floorplans//samplehome0.xml";
		
            try 
            {
                    Document doc = FloorPlanUtility.loadFromFile(filePath);
                    floorPlan = FloorPlanUtility.loadFloorPlan(doc);

                    for (int i = 0; i < floorPlan.numberOfFloors(); i++) 
                    {
                            Tile home = floorPlan.getFloor(i).getHomeTile();
                            if (home != null) 
                            {
                                    homeTile = home;
                            }
                    }
                    cs = new CleanSweep(homeTile);
            } 
            catch (Exception e) 
            {
                    System.err.println("Exception in main: " + e.getMessage());
                    e.printStackTrace();
            }
            
            bm = new BatteryManager(homeTile);
        }
        
	public void testNeedToRecharge(){
            bm.chargeBattery();
            boolean b = bm.needToRecharge(floorPlan.getFloor(0).getTile(2, 2), floorPlan.getFloor(0).getTile(2, 3), new ArrayList<> (floorPlan.getFloor(0).getTiles().values()));
            assertTrue(b);
	}
        
        public void testCalculateWeight(){
            double x = BatteryManager.calculateWeight(homeTile, floorPlan.getFloor(0).getTile(0, 1));
            assertEquals(x,2.0);
            
            double y = BatteryManager.calculateWeight(homeTile, floorPlan.getFloor(0).getTile(4, 0));
            assertEquals(y,1.5);
        }
        
        public void testDecrementBatteryLevel()
        {
            bm.chargeBattery();
            assertEquals(bm.getBatteryLevel(),50.0);
            
            bm.decrementBatteryLevel(homeTile, floorPlan.getFloor(0).getTile(0, 1));
            assertEquals(bm.getBatteryLevel(),48.0);
            
            bm.decrementBatteryLevel(homeTile, floorPlan.getFloor(0).getTile(4, 0));
            assertEquals(bm.getBatteryLevel(),46.5);
        }
	
}
