package DePaul.SE459.CleanSweep;

public class DirtMeterUtility {
	private static final int MAX_DIRT_CAPACITY = 50;
	private boolean emptyMeLight;
	private int currentDirtCapacity;

	public DirtMeterUtility(){
		emptyDirtBag();
		setEmptyMeLight(false);
	}

	/*
	 * emptyDirtBag() - empties the dirt bag
	 * -resets currentDirtCapacity to zero
	 * -turns off the light
	 */
	public void emptyDirtBag(){
		currentDirtCapacity = 0;
		setEmptyMeLight(false);
	}
	/*
	 * emptyMeLightIsOn() - returns true if it's on (current capacity >= 50)
	 *                    - returns false if it's off (current capacity < 50)
	 */
	public boolean emptyMeLightIsOn(){
		return emptyMeLight;
	}
	/*
	 * cleanOneDirtUnitFromTile(Tile tileToClean)
	 * -increments the currentDirtCapacity by 1
	 * -decrements the current tile's dirt amount by 1
	 * -after cleaning 1 unit of dirt, if dirt=50 units, turn light on
	 */
	public void cleanOneDirtUnitFromTile(Tile tileToClean){
		if(tileToClean.getDirtAmount()>0){
			incrementCurrentDirtCapacity();
			decrementDirtOnTile(tileToClean);
		}
		if(getCurrentDirtCapacity()>=MAX_DIRT_CAPACITY){
			setEmptyMeLight(true);
		}
	}

	/*
	 * getDirtCapacity():
	 * get current dirt amount
	 */
	public int getCurrentDirtCapacity(){
		return currentDirtCapacity;
	}
	/*
	 * setEmptyMeLight(boolean isLightOn)
	 * -sets the emptyMeLight to true/false depending on the parameter input
	 */
	private void setEmptyMeLight(boolean isLightOn){
		emptyMeLight = isLightOn;
	}
	/*
	 * incrementDirtCapacity()
	 * -increments the currentDirtCapacity by 1
	 */
	private void incrementCurrentDirtCapacity(){
		currentDirtCapacity++;
	}
	/*
	 * decrementDirtOnTile(Tile tileToClean)
	 * -decrements the dirt amount of the current tile by 1 if it's greater than 0
	 * -sets the current tile's dirt amount after cleaning 1 unit of dirt
	 */
	private void decrementDirtOnTile(Tile tileToClean){
		int dirtOnTile = tileToClean.getDirtAmount();
		if(dirtOnTile>0){
			dirtOnTile--;
		}
		tileToClean.setDirtAmount(dirtOnTile);
	}

}
