package DePaul.SE459.CleanSweep;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class Floor {
	private Map<Integer, Tile> tiles;
	private int level;
	private Tile homeTile;

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

	public Tile getHomeTile() {
		return homeTile;
	}

	public void setHomeTile(Tile home) {
		this.homeTile = home;
	}

	public Map<Integer, Tile> getTiles() {
		return this.tiles;
	}

	public void setTiles(Map<Integer, Tile> t) {
		this.tiles = t;
	}

	public void buildAdjacentTiles() {
		Iterator<Entry<Integer, Tile>> it = tiles.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Integer, Tile> pairs = it.next();
			Tile currentTile = pairs.getValue();

			if (currentTile.isChargingStation()) {
				setHomeTile(currentTile);
			}

			if (currentTile.getRightPath() < 2) {
				Tile rightTile = getTile(currentTile.getX() + 1, currentTile.getY());

				if (rightTile != null) {
					currentTile.setRightTile(rightTile);
				}
			}
			if (currentTile.getLeftPath() < 2) {
				Tile leftTile = getTile(currentTile.getX() - 1, currentTile.getY());

				if (leftTile != null) {
					currentTile.setLeftTile(leftTile);
				}
			}
			if (currentTile.getLowerPath() < 2) {
				Tile lowerTile = getTile(currentTile.getX(), currentTile.getY() - 1);

				if (lowerTile != null) {
					currentTile.setLowerTile(lowerTile);
				}
			}
			if (currentTile.getUpperPath() < 2) {
				Tile upperTile = getTile(currentTile.getX(), currentTile.getY() + 1);

				if (upperTile != null) {
					currentTile.setUpperTile(upperTile);
				}
			}
		}
	}
}
