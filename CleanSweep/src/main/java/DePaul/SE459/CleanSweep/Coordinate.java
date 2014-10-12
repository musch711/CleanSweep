package DePaul.SE459.CleanSweep;

public class Coordinate {
    private int xCoordinate;
    private int yCoordinate;
    
    public Coordinate(int x, int y)
    {
        setXCoordinate(x);
        setYCoordinate(y);
    }

    public int getXCoordinate() {
        return xCoordinate;
    }

    public void setXCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public int getYCoordinate() {
        return yCoordinate;
    }

    public void setYCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }    

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + this.xCoordinate;
        hash = 79 * hash + this.yCoordinate;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Coordinate other = (Coordinate) obj;
        if (this.xCoordinate != other.xCoordinate) {
            return false;
        }
        if (this.yCoordinate != other.yCoordinate) {
            return false;
        }
        return true;
    }    
}
