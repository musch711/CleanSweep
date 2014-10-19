package DePaul.SE459.CleanSweep;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

class Floor {
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
	
	public void buildAdjacentTiles() {
		Iterator<Entry<Integer, Tile>> it = tiles.entrySet().iterator();
		System.out.println("-----------------------Tile Adjacency For Floor Level " + getLevel() + "-----------------------");
		while (it.hasNext()) {
			Map.Entry<Integer, Tile> pairs = it.next();
			Tile currentTile = pairs.getValue();

			if (currentTile.isChargingStation())
			{
				setHomeTile(currentTile);
			}
			
			if (currentTile.getRightPath() < 2) {
				Tile rightTile = getTile(currentTile.getX() + 1, currentTile.getY());

				if (rightTile != null) {
					currentTile.setRightTile(rightTile);
				}

				if (rightTile == null) {
					System.out.println("Right tile for tile " + currentTile.getX() + "," + currentTile.getY()
							           + " does not exist.");
				} else {
					System.out.println("Right tile for tile " + currentTile.getX() + "," 
									   + currentTile.getY() + " is at " + rightTile.getX() + "," + rightTile.getY());
				}
			}
			else{
				System.out.println("Right tile for tile " + currentTile.getX() + "," + currentTile.getY()
								   + " does not exist because path is blocked.");
			}
			if (currentTile.getLeftPath() < 2) {
				Tile leftTile = getTile(currentTile.getX() - 1, currentTile.getY());

				if (leftTile != null) {
					currentTile.setLeftTile(leftTile);
				}

				if (leftTile == null) {
					System.out.println("Left  tile for tile " + currentTile.getX() + "," + currentTile.getY()
									   + " does not exist.");
				} else {
					System.out.println("Left  tile for tile " + currentTile.getX() + ","
								 	   + currentTile.getY() + " is at " + leftTile.getX() + "," + leftTile.getY());
				}
			}
			else{
				System.out.println("Left  tile for tile " + currentTile.getX() + "," + currentTile.getY()
								   + " does not exist because path is blocked.");
			}
			if (currentTile.getLowerPath() < 2) {
				Tile lowerTile = getTile(currentTile.getX(), currentTile.getY() - 1);

				if (lowerTile != null) {
					currentTile.setLowerTile(lowerTile);
				}

				if (lowerTile == null) {
					System.out.println("Lower tile for tile " + currentTile.getX() + "," + currentTile.getY()
									   + " does not exist.");
				} else {
					System.out.println("Lower tile for tile " + currentTile.getX() + ","
									   + currentTile.getY() + " is at " + lowerTile.getX() + "," + lowerTile.getY());
				}
			}
			else{
				System.out.println("Lower tile for tile " + currentTile.getX() + "," + currentTile.getY()
								   + " does not exist because path is blocked.");
			}
			if (currentTile.getUpperPath() < 2) {
				Tile upperTile = getTile(currentTile.getX(), currentTile.getY() + 1);

				if (upperTile != null) {
					currentTile.setUpperTile(upperTile);
				}

				if (upperTile == null) {
					System.out.println("Upper tile for tile " + currentTile.getX() + "," + currentTile.getY()
									   + " does not exist.");
				} else {
					System.out.println("Upper tile for tile " + currentTile.getX() + "," + currentTile.getY()
									   + " is at " + upperTile.getX() + "," + upperTile.getY());
				}
			}
			else{
				System.out.println("Upper tile for tile " + currentTile.getX() + "," + currentTile.getY()
								   + " does not exist because path is blocked.");
			}
		}
	}
}
