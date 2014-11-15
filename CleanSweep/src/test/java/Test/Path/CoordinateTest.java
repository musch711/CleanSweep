package Test.Path;

import DePaul.SE459.CleanSweep.Coordinate;
import junit.framework.TestCase;

public class CoordinateTest extends TestCase {

	public CoordinateTest(String name){
		super(name);
	}
	
	/*
	 * test the hashCode method.  Check that two hashCodes of different coordinates
         * are not equal.  Also check that two different objects with the same coordinates
         * are equal.
	 */
	public void testHashCode(){
		Coordinate a = new Coordinate(1,2);
                Coordinate b = new Coordinate(5,12);
                Coordinate c = new Coordinate(1,2);
		assertFalse(a.hashCode()==b.hashCode());
                assertTrue(a.hashCode()==c.hashCode());
	}

	/*
	 * test the equals method.  Check that different coordinates are not equal.
         * Also check that two different objects with the same coordinates are equal.
	 */        
	public void testEquals(){
            Coordinate a = new Coordinate(1,2);
            Coordinate b = new Coordinate(5,12);
            Coordinate c = new Coordinate(1,2);
            
            assertTrue(a.equals(c));
            assertFalse(a.equals(b));
	}
}
