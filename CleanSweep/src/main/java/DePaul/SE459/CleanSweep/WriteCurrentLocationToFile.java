package DePaul.SE459.CleanSweep;


/*
 * NOTE: will probably change parameter (int x, int y) to (Coordinate c)
 * 
 * 10/16/2014 - so far this creates a tracking directory if doesn't already exist,
 * creates a movement.txt file if doesn't already exist,
 * and writes "Location: x=(x param), y=(y param)
 * 
 * Will probably have to edit this to make code a little better
 * 
 * Will probably change the name of this class
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteCurrentLocationToFile {
	FileWriter fw;
	BufferedWriter bw;

	/*
	 * writeToFile(): writes the x-coordinate and y-coordinate to a text file
	 */
	
		
	public void writeToFile(int x, int y){
		
		//create the "tracking" directory if one does not already exist:
		File trackingDirectory= new File("tracking");
		  // if the directory does not exist, create it
		  if (!trackingDirectory.exists()) {
		    trackingDirectory.mkdir();    
		    System.out.println("tracking directory created");
		  }
		  
		try{
			//create new file in the tracking folder:
			File outputFile = new File("tracking", "movement.txt");
			
			if(outputFile.createNewFile()){
				//file is created
				System.out.println("new file created");
				fw = new FileWriter(outputFile.getPath(), true);
				bw = new BufferedWriter(fw);
				bw.write("Location: x="+x+", y="+y);
				bw.write(System.getProperty( "line.separator" ));
			}
			else{
				//file is not created because it already exists
				//so just write to the file:
				fw = new FileWriter(outputFile.getPath(), true);
				bw = new BufferedWriter(fw);
				bw.write("Location: x="+x+", y="+y);
				bw.write(System.getProperty( "line.separator" ));
				
			}
		} catch(IOException e){
			System.out.println("Error when attempting to write to file.");
			e.printStackTrace();
		}finally {
			   try {bw.close();} catch (Exception ex) {}
		}
	}
	

}
