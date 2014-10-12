package DePaul.SE459.CleanSweep;

public class DiscoverStairs{
	private Tile currentTile;
	private boolean stairsAtLeft;
	private boolean stairsAtRight;
	private boolean stairsAbove;
	private boolean stairsBelow;

    /*
     * Constructor for DiscoverStairs
     * -sets the current tile the CleanSweep is on
     */
	public DiscoverStairs(Tile t){
		this.currentTile = t;
		
		setStairsAtLeft();
		setStairsAtRight();
		setStairsAbove();
		setStairsBelow();
	}
	
	/*
	 * setStairsAtLeft
	 * Sets stairsAtLeft to true if there are stairs - when currentTile.getLeftPath() = 4
	 * Sets to false if no stairs
	 */
	public void setStairsAtLeft(){
		stairsAtLeft =  findStairs(currentTile.getLeftPath());
	}

	/*
	 * setStairsAtRight
	 * Sets stairsAtRight to true if there are stairs - when currentTile.getRightPath() = 4
	 * Sets to false if no stairs
	 */
	public void setStairsAtRight(){
		stairsAtRight = findStairs(currentTile.getRightPath());
	}
	
	/*
	 * getStairsAbove
	 * Sets stairsAbove to true if there are stairs - when currentTile.getUpperPath() = 4
	 * Sets to false if no stairs
	 */
	public void setStairsAbove(){
		stairsAbove = findStairs(currentTile.getUpperPath());
	}
	
	/*
	 * getStairsBelow
	 * Sets stairsBelow to true if there are stairs - when currentTile.getStairsBelow() = 4
	 * Sets to false if no stairs
	 */
	public void setStairsBelow(){
		stairsBelow = findStairs(currentTile.getLowerPath());
	}
	
	/*
	 * Find if the given path contains stairs.
	 * Returns true if there are stairs (= 4).
	 * Returns false if there are no stairs in the path.
	 */
	public boolean findStairs(int p){
		if(p == 4)
			return true;
		return false;
	}
	
	public boolean getStairsAtLeft(){
		return stairsAtLeft;
	}
	public boolean getStairsAtRight(){
		return stairsAtRight;
	}
	public boolean getStairsAbove(){
		return stairsAbove;
	}
	public boolean getStairsBelow(){
		return stairsBelow;
	}
}