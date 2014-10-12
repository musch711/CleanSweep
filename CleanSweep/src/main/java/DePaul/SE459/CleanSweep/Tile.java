package DePaul.SE459.CleanSweep;

public class Tile 
{
    private Coordinate coordinate;
    private int ss;
    private int r;
    private int l;
    private int u;
    private int d;
    private int ds;
    private boolean cs;
    
    public Tile(int xPos, int yPos, int surface, int right, int left, int up, int down, int dirt, boolean charging)
    {
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
        return ss;
    }

    /**
     * Sets the surface type of the Tile.
     * 1 = bare floor
     * 2 = low-pile carpet
     * 4 = high-pile carpet
     * @param surfaceType
     */
    public void setSurfaceType(int surfaceType) {
        this.ss = surfaceType;
    }

    /**
     * @return The path of the Tile to the right of this Tile.
     * 0 = unknown
     * 1 = open
     * 2 = obstacle
     * 4 = stairs
     */
    public int getRightPath() {
        return r;
    }

    /**
     * Sets the path of the Tile to the right of this Tile.
     * 0 = unknown
     * 1 = open
     * 2 = obstacle
     * 4 = stairs
     * @param rightPath
     */
    public void setRightPath(int rightPath) {
        this.r = rightPath;
    }

    /**
     * @return The path of the Tile to the left of this Tile.
     * 0 = unknown
     * 1 = open
     * 2 = obstacle
     * 4 = stairs
     */
    public int getLeftPath() {
        return l;
    }

    /**
     * Sets the path of the Tile to the left of this Tile.
     * 0 = unknown
     * 1 = open
     * 2 = obstacle
     * 4 = stairs
     * @param leftPath
     */
    public void setLeftPath(int leftPath) {
        this.l = leftPath;
    }

    /**
     * @return The path of the Tile above this Tile.
     * 0 = unknown
     * 1 = open
     * 2 = obstacle
     * 4 = stairs
     */
    public int getUpperPath() {
        return u;
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
        this.u = up;
    }

    /**
     * @return The path of the Tile below this Tile.
     * 0 = unknown
     * 1 = open
     * 2 = obstacle
     * 4 = stairs
     */
    public int getLowerPath() {
        return d;
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
        this.d = down;
    }

    /**
     * TODO: Since there is a requirement that the Clean Sweep not know how dirty something is,
     * should this return a boolean, true if ds > 0, false if ds = 0?
     * @return The amount of dirt in the Tile.
     */
    public int getDirtAmount() {
        return ds;
    }

    /**
     * Sets the amount of dirt present in the Tile.
     * @param dirtAmount
     */
    public void setDirtAmount(int dirtAmount) {
        this.ds = dirtAmount;
    }

    /**
     * @return True if this Tile contains the charging station, false if it does not.
     */
    public boolean isChargingStation() {
        return cs;
    }

    /**
     * Sets whether or not this Tile contains the charging station.
     * @param isCs
     */
    public void setChargingStation(boolean isCs) {
        this.cs = isCs;
    }
	
     /**
     * 
     * This method returns the HashCode from the Coordinate object.
     * The floor class uses this method to determine what the key should be
     * for the tiles map in that class.
     * @return Integer
     */
    public Integer getCoordinateHashCode()
    {
        return (Integer) this.coordinate.hashCode();
    }	
}
