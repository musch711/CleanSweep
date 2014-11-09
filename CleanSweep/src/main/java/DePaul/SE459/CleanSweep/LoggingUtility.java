package DePaul.SE459.CleanSweep;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Logs activity to console and to file in the tracking directory.
 */
public final class LoggingUtility {
	/**
	 * Logs cleaning action to disk and to console.
	 * @param x The x-coordinate of the Tile that was cleaned.
	 * @param y The y-coordinate of the Tile that was cleaned.
	 */
	public static void LogCleaning(int x, int y) {
		String msg = " Cleaned tile at: (" + x + ", " + y + ")";
		writeToFile("movement.txt", msg);
	}

	/**
	 * Logs discovered floor plan by the clean sweep.
	 * @param cell A string representing the information of the Tile (ie. <cell x = 0, y = 0, ss = 2, ps = 1212, ds = 1, cs = 1 />)
	 */
	public static void logDiscoveredCell(String cell) {
		writeToFile("movement.txt", cell);
	}

		/**
	 * Logs current location to disk and to console to track Clean Sweep's movement.
	 * @param int x: The x-coordinate of the Tile that was cleaned.
	 * @param int y: The y-coordinate of the Tile that was cleaned.
	 */
	public static void logMovement(int x, int y){
		String msg = "Current location at tile: (" + x + ", " + y + ")";
		writeToFile("movement.txt", msg);
	}

	/**
	 * Logs current location to disk and to console to track Clean Sweep's movement.
	 * @param Tile t = the current tile the Clean Sweep is on
	 */
	public static void logMovement(Tile t){
		String msg = "Current location at tile: (" + t.getX() + ", " + t.getY()+ ")";
		writeToFile("movement.txt", msg);
	}
        
        public static void logPath(Tile t){
            String msg = "Shortest Path: (" + t.getX() + ", " + t.getY()+ ")";
            writeToFile("movement.txt", msg);
	}
	
	public static void logStart() {
		String msg = "CleanSweep has started...";
		writeToFile("movement.txt", msg);
	}
	
	public static void logFinish() {
		String msg = "CleanSweep has finished.";
		writeToFile("movement.txt", msg);
	}
      
	public static void logRecharge() {
		String msg = "CleanSweep has finished recharging.";
		writeToFile("movement.txt", msg);
	}
	
	public static void logReturn() {
		String msg = "CleanSweep is returning to charging station.";
		writeToFile("movement.txt", msg);
	}
	
    public static void logPath(List<Tile> path)
    {
        for (Tile t: path)
        {
            LoggingUtility.logMovement(t);
        }
    }
    
    public static void logInternalMap(Map<Integer,Tile> m)
    {
        StringBuilder sb = new StringBuilder();
        Iterator<Map.Entry<Integer, Tile>> it = m.entrySet().iterator();
        while (it.hasNext())
        {
            Tile t = it.next().getValue();
            sb.append("Tile: x=").append(t.getX()).append(" y=").append(t.getY());
            sb.append(" ss=").append(t.getSurfaceType()).append(" ds=").append(t.getDirtAmount()).append("\n");
        }
        writeToFile("afterCleaning.txt", sb.toString());
    }
        
    public static void logDiscoveredFloorPlan(FloorPlan fp)
    {
        StringBuilder sb = new StringBuilder();
        
        List<Floor> floors = fp.getFloors();
        Iterator<Floor> floorIterator = floors.iterator();
        while (floorIterator.hasNext())
        {
            Floor f = floorIterator.next();
            sb.append("---Begin Floor ").append(f.getLevel()).append("---\n");
            Map<Integer,Tile> m = f.getTiles();
            Iterator<Map.Entry<Integer, Tile>> it = m.entrySet().iterator();
            while (it.hasNext())
            {
                Tile t = it.next().getValue();
                sb.append("Tile: x=").append(t.getX()).append(" y=").append(t.getY());
                sb.append(" ss=").append(t.getSurfaceType()).append(" ds=").append(t.getDirtAmount()).append("\n");
            }
            sb.append("---End Floor ").append(f.getLevel()).append("---\n");
        }
        writeToFile("logDiscoveredFloorPlan.txt", sb.toString());
    }
	
	
	private static void writeToFile(String fileName, String msg) {
		FileWriter fw;
		BufferedWriter bw = null;

		try {
			createDirectory();

			//create new file in the tracking folder:
			File outputFile = new File("tracking", fileName);

			if (outputFile.createNewFile()) {
				System.out.println("New file created: " + fileName);
			}

			fw = new FileWriter(outputFile.getPath(), true);
			bw = new BufferedWriter(fw);

			System.out.println(msg);                                          // COMMENTED OUT TO MAKE DEBUGGING EASIER ... NEED TO UNCOMMENT WHEN DONE DEBUGGING
			bw.write(msg);
			bw.write(System.getProperty("line.separator"));
		} catch (IOException e) {
			System.out.println("Error when attempting to write to file.");
			e.printStackTrace();
		} finally {
			try {
				if (bw != null) {
					bw.close();
				}
			} catch (Exception ex) {
				// at least we tried
			}
		}
	}

	private static void createDirectory() {
		//create the "tracking" directory if one does not already exist:
		File trackingDirectory = new File("tracking");

		// if the directory does not exist, create it
		if (!trackingDirectory.exists()) {
			trackingDirectory.mkdir();
			System.out.println("tracking directory created");
		}
	}
}
