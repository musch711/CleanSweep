package DePaul.SE459.CleanSweep;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

class Floor {
    private Map<Integer, Tile> tiles;
    private int level;
    
    public Floor(int l)
    {
        this.level = l;
        tiles = new HashMap<>();
    }    
    
    public Tile getTile(int x, int y) {
        Coordinate c = new Coordinate(x,y);
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
    
    public void testMethod() {
    // TODO: this doesn't belong here, just threw it here for quick verification that everything was loaded properly
    Iterator<Entry<Integer, Tile>> it = tiles.entrySet().iterator();
    
    while (it.hasNext()) {
        Map.Entry<Integer, Tile> pairs = it.next();
        Tile t = pairs.getValue();
        System.out.println("    xPos: " + t.getX());
        System.out.println("    yPos: " + t.getY());
        System.out.println("    surfaceType: " + t.getSurfaceType());
        System.out.println("    right: " + t.getRightPath());
        System.out.println("    left: " + t.getLeftPath());
        System.out.println("    down: " + t.getLowerPath());
        System.out.println("    up: " + t.getUpperPath());
        System.out.println("    dirtAmount: " + t.getDirtAmount());
        System.out.println("    chargingStation: " + t.isChargingStation());
        System.out.println("-------------------------------");
        it.remove();
    }
}
}
