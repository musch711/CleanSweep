package DePaul.SE459.CleanSweep;

public class Tile {
	private Coordinate coordinate;
	private int surfaceType;
	private int rightPath;
	private int leftPath;
	private int upperPath;
	private int lowerPath;
	private Tile rightTile;
	private Tile leftTile;
	private Tile upperTile;
	private Tile lowerTile;
	private int dirtAmount;
	private boolean chargingStation;

	public Tile(int xPos, int yPos, int surface, int right, int left, int up, int down, int dirt, boolean charging) {
		coordinate = new Coordinate(xPos, yPos);
		setSurfaceType(surface);
		setRightPath(right);
		setLeftPath(left);
		setUpperPath(up);
		setLowerPath(down);
		setDirtAmount(dirt);
		setChargingStation(charging);
	}

	public int getX() {
		return coordinate.getXCoordinate();
	}

	public void setX(int xPos) {
		coordinate.setXCoordinate(xPos);
	}

	public int getY() {
		return coordinate.getYCoordinate();
	}

	public void setY(int yPos) {
		coordinate.setXCoordinate(yPos);
	}

	/**
	 * @return The surface type of the Tile.
	 * 1 = bare floor
	 * 2 = low-pile carpet
	 * 4 = high-pile carpet
	 */
	public int getSurfaceType() {
		return surfaceType;
	}

	/**
	 * Sets the surface type of the Tile.
	 * 1 = bare floor
	 * 2 = low-pile carpet
	 * 4 = high-pile carpet
	 * @param st
	 */
	public void setSurfaceType(int st) {
		this.surfaceType = st;
	}

	/**
	 * @return The path of the Tile to the right of this Tile.
	 * 0 = unknown
	 * 1 = open
	 * 2 = obstacle
	 * 4 = stairs
	 */
	public int getRightPath() {
		return rightPath;
	}

	/**
	 * Sets the path of the Tile to the right of this Tile.
	 * 0 = unknown
	 * 1 = open
	 * 2 = obstacle
	 * 4 = stairs
	 * @param rp
	 */
	public void setRightPath(int rp) {
		this.rightPath = rp;
	}

	/**
	 * @return The path of the Tile to the left of this Tile.
	 * 0 = unknown
	 * 1 = open
	 * 2 = obstacle
	 * 4 = stairs
	 */
	public int getLeftPath() {
		return leftPath;
	}

	/**
	 * Sets the path of the Tile to the left of this Tile.
	 * 0 = unknown
	 * 1 = open
	 * 2 = obstacle
	 * 4 = stairs
	 * @param lp
	 */
	public void setLeftPath(int lp) {
		this.leftPath = lp;
	}

	/**
	 * @return The path of the Tile above this Tile.
	 * 0 = unknown
	 * 1 = open
	 * 2 = obstacle
	 * 4 = stairs
	 */
	public int getUpperPath() {
		return upperPath;
	}

	/**
	 * Sets the path of the Tile above this Tile.
	 * 0 = unknown
	 * 1 = open
	 * 2 = obstacle
	 * 4 = stairs
	 * @param up
	 */
	public void setUpperPath(int up) {
		this.upperPath = up;
	}

	/**
	 * @return The path of the Tile below this Tile.
	 * 0 = unknown
	 * 1 = open
	 * 2 = obstacle
	 * 4 = stairs
	 */
	public int getLowerPath() {
		return lowerPath;
	}

	/**
	 * Sets the path of the Tile below this Tile.
	 * 0 = unknown
	 * 1 = open
	 * 2 = obstacle
	 * 4 = stairs
	 * @param down
	 */
	public void setLowerPath(int down) {
		this.lowerPath = down;
	}

	/**
	 * @return Returns the Tile that is to the right of this Tile. Could be null if the path in this direction is blocked.
	 */
	public Tile getRightTile() {
		return rightTile;
	}
	
	/**
	 * Sets the Tile that is to the right of this Tile.
	 * @param rt The Tile to set this to. Could be null if the path for this direction is blocked.
	 */
	public void setRightTile(Tile rt) {
		this.rightTile = rt;
	}
	
	/**
	 * @return Returns the Tile that is to the left of this Tile. Could be null if the path in this direction is blocked.
	 */
	public Tile getLeftTile() {
		return leftTile;
	}

	/**
	 * Sets the Tile that is to the left of this Tile.
	 * @param lt The Tile to set this to. Could be null if the path for this direction is blocked.
	 */
	public void setLeftTile(Tile lt) {
		this.leftTile = lt;
	}
	
	/**
	 * @return Returns the Tile that is below this Tile. Could be null if the path in this direction is blocked.
	 */
	public Tile getLowerTile() {
		return lowerTile;
	}
	
	/**
	 * Sets the Tile that is below this Tile.
	 * @param lt The Tile to set this to. Could be null if the path for this direction is blocked.
	 */
	public void setLowerTile(Tile lt) {
		this.lowerTile = lt;
	}
	
	/**
	 * @return Returns the Tile that is above this Tile. Could be null if the path in this direction is blocked.
	 */
	public Tile getUpperTile() {
		return upperTile;
	}

	/**
	 * Sets the Tile that is above this Tile.
	 * @param ut The Tile to set this to. Could be null if the path for this direction is blocked.
	 */
	public void setUpperTile(Tile ut) {
		this.upperTile = ut;
	}
	
	/**
	 * Returns the amount of dirt present on this Tile.
	 * @return The amount of dirt in the Tile.
	 */
	public int getDirtAmount() {
		return dirtAmount;
	}

	/**
	 * Sets the amount of dirt present in the Tile.
	 * @param da
	 */
	public void setDirtAmount(int da) {
		this.dirtAmount = da;
	}

	/**
	 * @return True if this Tile contains the charging station, false if it does not.
	 */
	public boolean isChargingStation() {
		return chargingStation;
	}

	/**
	 * Sets whether or not this Tile contains the charging station.
	 * @param isCs
	 */
	public void setChargingStation(boolean isCs) {
		this.chargingStation = isCs;
	}



	/**
	* 
	* This method returns the HashCode from the Coordinate object.
	* The floor class uses this method to determine what the key should be
	* for the tiles map in that class.
	* @return Integer
	*/
	public Integer getCoordinateHashCode() {
		return (Integer) this.coordinate.hashCode();
	}
        
    /*
    * This method returns true if the tiles are the same, otherwise false
    * @param t The tile to compare with
    * @return boolean
    */
    public boolean sameTile(Tile t)
    {
        return this.getX()==t.getX()&&this.getY()==t.getY();
    }
    
    public double distance(Tile t)
    { 
        double x = Math.pow(this.getX()-t.getX(), 2);
        double y = Math.pow(this.getY()-t.getY(), 2);
        return Math.sqrt(x+y);
    }
}
