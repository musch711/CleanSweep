package DePaul.SE459.CleanSweep;

import java.util.ArrayList;
import java.util.List;

public class FloorPlan {
    private List<Floor> floors;

    public FloorPlan()
    {
        floors = new ArrayList<>();
    }
    /**
     *
     * @param floorNum
     * @return
     */
    public Floor getFloor(int floorNum) {
        return floors.get(floorNum);
    }

     /**
     *
     * @return List of Floors
     */
    public List<Floor> getFloors() {
        return this.floors;
    }

    /**
     *
     * @param f
     */
    public void addFloor(Floor f) {
        this.floors.add(f);
    }
    
    public int numberOfFloors() {
        return this.floors.size();
    }
}