package DePaul.SE459.Simulator;

import DePaul.SE459.CleanSweep.CleanSweep;
import DePaul.SE459.CleanSweep.FloorPlan;
import DePaul.SE459.CleanSweep.FloorPlanUtility;
import DePaul.SE459.CleanSweep.LoggingUtility;
import DePaul.SE459.CleanSweep.Tile;
import java.util.Map;

public class Simulator {
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
				Map<Integer,Tile> m = cs.cleanFloor();
                                LoggingUtility.logInternalMap(m);
			}
		} catch (Exception e) {
			System.err.println("Exception in main: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
