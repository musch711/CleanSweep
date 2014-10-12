package DePaul.SE459.CleanSweep;

public class CleanSweep
  {
  public static void main(String args[])
    {
    String filePath;
    if (args.length == 0)
      {
      filePath = "floorplans//samplehome0.xml";
      }
    else
      {
      filePath = args[0];
      }

    try
      {
      FloorPlan testFloorPlan = FloorPlanUtility.loadFloorPlan(filePath);

      for (int i = 0; i < testFloorPlan.numberOfFloors(); i++)
        {
        Floor floor = testFloorPlan.getFloor(i);
        System.out.println("Scanning floor " + floor.getLevel() + "...");
        System.out.println("-------------------------------");
        floor.testMethod();
        }
      }
    catch (Exception e)
      {
      System.err.println("Exception in main: " + e.getMessage());
      e.printStackTrace();
      }
    }
  }
