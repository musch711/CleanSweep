package Test.Path;

import DePaul.SE459.CleanSweep.DirtMeterUtility;
import DePaul.SE459.CleanSweep.Tile;
import junit.framework.TestCase;

public class DirtMeterUtilityTEST extends TestCase {

	public DirtMeterUtilityTEST(String name){
		super(name);
	}
	
	//create a new dirtmeter
	DirtMeterUtility dm = new DirtMeterUtility();
	
	/*
	 * test the constructor and make sure then when new DirtMeterUtility is created,
	 * then the current dirt is = 0 and the light is turned off
	 */
	public void testDirtMeterUtility(){
		DirtMeterUtility dm = new DirtMeterUtility();
		assertFalse(dm.emptyMeLightIsOn());
		assertEquals(dm.getCurrentDirtCapacity(), 0);
	}
	
	public void testEmptyDirtBag(){
		DirtMeterUtility dm = new DirtMeterUtility();
		assertFalse(dm.emptyMeLightIsOn());
		assertEquals(dm.getCurrentDirtCapacity(), 0);
		
		for(int i = 0; i<10; i++){
			Tile t = new Tile(0, 2, 2, 1, 2, 1, 2, 1, false);
			dm.cleanOneDirtUnitFromTile(t);
		}
		assertFalse(dm.emptyMeLightIsOn());
		assertEquals(dm.getCurrentDirtCapacity(), 10);
		
		dm.emptyDirtBag();
		assertFalse(dm.emptyMeLightIsOn());
		assertEquals(dm.getCurrentDirtCapacity(), 0);

		for(int i = 0; i<50; i++){
			Tile t = new Tile(0, 2, 2, 1, 2, 1, 2, 1, false);
			dm.cleanOneDirtUnitFromTile(t);
		}
		assertTrue(dm.emptyMeLightIsOn());
		assertEquals(dm.getCurrentDirtCapacity(), 50);
		
		dm.emptyDirtBag();
		assertFalse(dm.emptyMeLightIsOn());
		assertEquals(dm.getCurrentDirtCapacity(), 0);


	}
	
	public void testEmptyMeLightIsOn(){
		DirtMeterUtility dm = new DirtMeterUtility();
		assertFalse(dm.emptyMeLightIsOn());
		for(int i = 0; i<10; i++){
			Tile t = new Tile(0, 2, 2, 1, 2, 1, 2, 1, false);
			dm.cleanOneDirtUnitFromTile(t);
		}
		assertFalse(dm.emptyMeLightIsOn());
		
		for(int i = 0; i<10; i++){
			Tile t = new Tile(0, 2, 2, 1, 2, 1, 2, 1, false);
			dm.cleanOneDirtUnitFromTile(t);
		}
		assertFalse(dm.emptyMeLightIsOn());
		
		for(int i = 0; i<10; i++){
			Tile t = new Tile(0, 2, 2, 1, 2, 1, 2, 1, false);
			dm.cleanOneDirtUnitFromTile(t);
		}
		assertFalse(dm.emptyMeLightIsOn());
		
		for(int i = 0; i<10; i++){
			Tile t = new Tile(0, 2, 2, 1, 2, 1, 2, 1, false);
			dm.cleanOneDirtUnitFromTile(t);
		}
		assertFalse(dm.emptyMeLightIsOn());
		
		for(int i = 0; i<10; i++){
			Tile t = new Tile(0, 2, 2, 1, 2, 1, 2, 1, false);
			dm.cleanOneDirtUnitFromTile(t);
		}
		assertTrue(dm.emptyMeLightIsOn());
	}
	
	public void testCleanOneDirtUnitFromTile(){
		DirtMeterUtility dm = new DirtMeterUtility();
		assertFalse(dm.emptyMeLightIsOn());
		assertEquals(dm.getCurrentDirtCapacity(), 0);

		/*(for(int i = 0; i<10; i++){
			Tile t = new Tile(0, 2, 2, 1, 2, 1, 2, 1, false);
			dm.cleanOneDirtUnitFromTile(t);
		}
		*/
		Tile t = new Tile(0, 2, 2, 1, 2, 1, 2, 2, false);
		assertEquals(t.getDirtAmount(), 2); //dirt amount of tile t = 2
		dm.cleanOneDirtUnitFromTile(t);
		assertEquals(t.getDirtAmount(), 1);
		dm.cleanOneDirtUnitFromTile(t);
		assertEquals(t.getDirtAmount(), 0);
		assertEquals(dm.getCurrentDirtCapacity(), 2);
		
		//just in case current tile's dirt = 0 already, make sure it still returns 0 and not -1
		dm.cleanOneDirtUnitFromTile(t);
		assertEquals(t.getDirtAmount(), 0);
		assertEquals(dm.getCurrentDirtCapacity(), 2);
		
		dm.cleanOneDirtUnitFromTile(t);
		assertEquals(t.getDirtAmount(), 0);
		assertEquals(dm.getCurrentDirtCapacity(), 2);
	}
	
	public void testGetCurrentDirtCapacity(){
		DirtMeterUtility dm = new DirtMeterUtility();
		assertFalse(dm.emptyMeLightIsOn());
		assertEquals(dm.getCurrentDirtCapacity(), 0);

		for(int i = 0; i<10; i++){
			Tile t = new Tile(0, 2, 2, 1, 2, 1, 2, 1, false);
			dm.cleanOneDirtUnitFromTile(t);
		}
		assertEquals(dm.getCurrentDirtCapacity(), 10);
		
		for(int i = 0; i<20; i++){
			Tile t = new Tile(0, 2, 2, 1, 2, 1, 2, 1, false);
			dm.cleanOneDirtUnitFromTile(t);
		}
		assertEquals(dm.getCurrentDirtCapacity(), 30);

		for(int i = 0; i<20; i++){
			Tile t = new Tile(0, 2, 2, 1, 2, 1, 2, 1, false);
			dm.cleanOneDirtUnitFromTile(t);
		}
		assertEquals(dm.getCurrentDirtCapacity(), 50);
		
		dm.emptyDirtBag();
		assertEquals(dm.getCurrentDirtCapacity(), 0);

		for(int i = 0; i<30; i++){
			Tile t = new Tile(0, 2, 2, 1, 2, 1, 2, 1, false);
			dm.cleanOneDirtUnitFromTile(t);
		}
		assertEquals(dm.getCurrentDirtCapacity(), 30);

	}
}
