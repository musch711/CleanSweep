package Test.Path;

import DePaul.SE459.CleanSweep.DiscoverPaths;
import DePaul.SE459.CleanSweep.Tile;
import junit.framework.TestCase;

public class DiscoverPathsTEST extends TestCase{

	public DiscoverPathsTEST(String name){
		super(name);
	}
	//								  r  l  u  d
	final Tile t1 = new Tile(1, 2, 1, 1, 1, 1, 2, 1, false);
	final Tile t2 = new Tile(3, 4, 2, 4, 1, 4, 1, 2, false);
	final Tile t3 = new Tile(5, 6, 4, 2, 4, 2, 2, 2, false);
	final Tile t4 = new Tile(7, 8, 2, 1, 2, 4, 4, 2, true);


		public void testStairsAtLeft() {
			assertEquals(false, DiscoverPaths.stairsAtLeft(t1));
			assertEquals(false, DiscoverPaths.stairsAtLeft(t2));
			assertEquals(true, DiscoverPaths.stairsAtLeft(t3));
			assertEquals(false, DiscoverPaths.stairsAtLeft(t4));
		}

		public void testStairsAtRight() {
			assertEquals(false, DiscoverPaths.stairsAtRight(t1));
			assertEquals(true, DiscoverPaths.stairsAtRight(t2));
			assertEquals(false, DiscoverPaths.stairsAtRight(t3));
			assertEquals(false, DiscoverPaths.stairsAtRight(t4));

		}
		public void testStairsAbove(){
			assertEquals(false, DiscoverPaths.stairsAbove(t1));
			assertEquals(true, DiscoverPaths.stairsAbove(t2));
			assertEquals(false, DiscoverPaths.stairsAbove(t3));
			assertEquals(true, DiscoverPaths.stairsAbove(t4));
		}
		public void testStairsBelow(){
			assertEquals(false, DiscoverPaths.stairsBelow(t1));
			assertEquals(false, DiscoverPaths.stairsBelow(t2));
			assertEquals(false, DiscoverPaths.stairsBelow(t3));
			assertEquals(true, DiscoverPaths.stairsBelow(t4));
		}

}

