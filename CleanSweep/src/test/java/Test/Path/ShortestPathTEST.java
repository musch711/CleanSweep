package Test.Path;

import java.util.ArrayList;
import java.util.List;

import DePaul.SE459.CleanSweep.ShortestPath;
import DePaul.SE459.CleanSweep.Tile;
import junit.framework.TestCase;

public class ShortestPathTEST extends TestCase{

	public ShortestPathTEST(String name){
		super(name);


	}
	
	List<Tile> allVisitedTiles;

	
	final Tile a1 = new Tile(0, 0, 1, 1, 1, 1, 1, 1, true);
	final Tile a2 = new Tile(1, 0, 4, 1, 1, 1, 1, 1, false);
	final Tile a3 = new Tile(2, 0, 1, 4, 1, 1, 1, 1, false);
	final Tile a4 = new Tile(3, 0, 1, 1, 4, 1, 1, 1, false);
	final Tile a5 = new Tile(0, 1, 1, 1, 1, 2, 1, 1, false); 
	final Tile a6 = new Tile(1, 1, 4, 1, 1, 2, 1, 1, false);
	final Tile a7 = new Tile(2, 1, 4, 1, 1, 1, 1, 1, false);
	final Tile a8 = new Tile(3, 1, 2, 1, 1, 1, 1, 1, false);
	
	public void testGetWeightOfShortestPath(){
		allVisitedTiles = new ArrayList<Tile>();

		allVisitedTiles.add(a1); 
		allVisitedTiles.add(a2); 
		allVisitedTiles.add(a3); 
		allVisitedTiles.add(a4);
		allVisitedTiles.add(a5);
		allVisitedTiles.add(a6);
		allVisitedTiles.add(a7);
		allVisitedTiles.add(a8);

		ShortestPath sp = new ShortestPath(a1, a2, allVisitedTiles);
		assertTrue(sp.getWeightOfShortestPath() == 2.0);
		
		ShortestPath sp2 = new ShortestPath(a1, a6, allVisitedTiles);
		assertFalse(sp2.getWeightOfShortestPath() == 5.0);
		assertTrue(sp2.getWeightOfShortestPath() == 3.0);
		
		ShortestPath sp3 = new ShortestPath(a1, a4, allVisitedTiles);
		assertEquals(sp3.getWeightOfShortestPath(), 10.0);
	}

	public void testGetShortestPath(){
		allVisitedTiles = new ArrayList<Tile>();

		allVisitedTiles.add(a1); 
		allVisitedTiles.add(a2); 
		allVisitedTiles.add(a3); 
		allVisitedTiles.add(a4);
		allVisitedTiles.add(a5);
		allVisitedTiles.add(a6);
		allVisitedTiles.add(a7);
		allVisitedTiles.add(a8);

		ShortestPath sp = new ShortestPath(a1, a2, allVisitedTiles);
		assertTrue(sp.getShortestPath().contains(a1));
		assertTrue(sp.getShortestPath().contains(a2));
		assertFalse(sp.getShortestPath().contains(a8));
		
		ShortestPath sp2 = new ShortestPath(a1, a6, allVisitedTiles);
		assertTrue(sp2.getShortestPath().size() == 3);
		assertTrue(sp2.getShortestPath().get(0).sameTile(a1));
		assertTrue(sp2.getShortestPath().get(1).sameTile(a5));
		assertTrue(sp2.getShortestPath().get(2).sameTile(a6));
		assertTrue(sp2.getShortestPath().contains(a1));
		assertFalse(sp2.getShortestPath().contains(a2));

		ShortestPath sp3 = new ShortestPath(a1, a4, allVisitedTiles);
		assertTrue(sp3.getShortestPath().size() == 6);
		assertTrue(sp3.getShortestPath().contains(a1));
		assertFalse(sp3.getShortestPath().contains(a2));
		assertFalse(sp3.getShortestPath().contains(a3));
		assertTrue(sp3.getShortestPath().contains(a4));
		assertTrue(sp3.getShortestPath().contains(a5));
		assertTrue(sp3.getShortestPath().contains(a6));
		assertTrue(sp3.getShortestPath().contains(a7));
		assertTrue(sp3.getShortestPath().contains(a8));
		assertEquals(sp3.getShortestPath().get(0), a1);
		assertEquals(sp3.getShortestPath().get(1), a5);
		assertEquals(sp3.getShortestPath().get(2), a6);
		assertEquals(sp3.getShortestPath().get(3), a7);
		assertEquals(sp3.getShortestPath().get(4), a8);
		assertEquals(sp3.getShortestPath().get(5), a4);

	}
}
