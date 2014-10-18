package DePaul.SE459.CleanSweep;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
		writeToFile("cleaning.txt", msg);
	}

	private static void writeToFile(String fileName, String msg) {
		FileWriter fw;
		BufferedWriter bw = null;

		try {
			createDirectory();

			//create new file in the tracking folder:
			File outputFile = new File("tracking", fileName);

			if (outputFile.createNewFile()) {
				System.out.println("new file created");
			}

			fw = new FileWriter(outputFile.getPath(), true);
			bw = new BufferedWriter(fw);

			System.out.println(msg);
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
