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
      }
    catch (Exception e)
      {
      System.err.println("Exception in main: " + e.getMessage());
      e.printStackTrace();
      }
    }
  }
