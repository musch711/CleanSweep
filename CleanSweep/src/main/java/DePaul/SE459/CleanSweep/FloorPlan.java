package DePaul.SE459.CleanSweep;

import java.util.ArrayList;

public class FloorPlan {
    private ArrayList<Floor> floors;

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
     * @param f
     */
    public void addFloor(Floor f) {
        this.floors.add(f);
    }
    
    public int numberOfFloors() {
        return this.floors.size();
    }
}