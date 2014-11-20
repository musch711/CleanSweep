CleanSweepWithMaven
===================

The project has been separated into two packages. 
- DePaul.SE459.CleanSweep
	-- Contains all of the vacuum's logic.
- DePaul.SE459.Simulator
	-- Contains all of the simulator logic
		-- Reads in an xml floor plan
		-- Runs the CleanSweep 

To run the program, you can do so via the terminal.

Change directory to CleanSweepWithMaven/CleanSweep

>> mvn clean
>> mvn package

Then copy/move the "floorplans" directory from the CleanSweepWithMaven/CleanSweep directory into "target" directory

>> cd target
>> java -jar CleanSweep-1.jar

This will create a "tracking" directory within the "target" directory.
It will contain all of the output.
- afterCleaning.txt
	-- All of the tiles that the vacuum has cleaned
- movement.txt
	-- All of the tiles that the vacuum has visited



Link to Github Repository:

https://github.com/musch711/CleanSweepWithMaven


Link to Jenkins:

http://selab.cdm.depaul.edu/jenkins/job/CleanSweepWithMaven_Daniel_Richard_Steven_Sue/


Link to Sonar:

http://selab.cdm.depaul.edu/sonar/dashboard/index/408
