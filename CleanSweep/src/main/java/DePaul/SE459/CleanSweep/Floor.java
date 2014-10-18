package DePaul.SE459.CleanSweep;

import java.util.HashMap;
import java.util.Map;

class Floor {
	private Map<Integer, Tile> tiles;
	private int level;

	public Floor(int l) {
		this.level = l;
		tiles = new HashMap<>();
	}

	public Tile getTile(int x, int y) {
		Coordinate c = new Coordinate(x, y);
		return tiles.get(c.hashCode());
	}

	public void addTile(Tile t) {
		tiles.put(t.getCoordinateHashCode(), t);
	}

	public int getLevel() {
		return level;
	}

	public int numberOfTiles() {
		return this.tiles.size();
	}
}
