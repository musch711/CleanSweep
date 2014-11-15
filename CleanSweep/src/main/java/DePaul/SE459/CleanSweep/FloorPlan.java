package DePaul.SE459.CleanSweep;

import java.util.ArrayList;
import java.util.List;

public class FloorPlan {
	private List<Floor> floors;

	public FloorPlan() {
		floors = new ArrayList<>();
	}

	public Floor getFloor(int floorNum) {
		return floors.get(floorNum);
	}

	public List<Floor> getFloors() {
		return this.floors;
	}

	public void addFloor(Floor f) {
		this.floors.add(f);
	}

	public int numberOfFloors() {
		return this.floors.size();
	}
}