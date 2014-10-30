package DePaul.SE459.CleanSweep;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/*
 * In Progress
 * getShortestPath is little buggy but will fix
 */

/*
 * tile coordinates = the vertices
 * charge between 2 tiles = the edges
 */
public class ShortestPath {
	List<Vertex> allVertices;
	List<Edge> allEdges;
	List<Vertex> shortestPath;
	Vertex source;
	Vertex destination;
	PriorityQueue<Vertex> pq;
	double weightOfShortestPath;

	/*
	 * getShortestPath()
	 * @return List - Arraylist of vertices - in sequence starting from source at index 0
	 */
	public List<Vertex> getShortestPath(){
		
		//do all of this while the heap is not empty OR until the shortestPath list doesn't contain the destination vertex
		while(!pq.isEmpty() && !shortestPath.contains(destination)){
			if(pq.peek() != null){
				Vertex getV = pq.poll(); //get the Vertex with the smallest distance from source that is left in the PQ
				System.out.println("removed vertex: "+getV.toString());

				shortestPath.add(getV); //add the vertex to the end of the shortestPath sequence

				//get the neighbors of the getV
				//if their current weight is GREATER than getV's weight + weight between getV and neighbor,
				//then set their weights, and set their parent to getV
				Vertex[] getNeighbors = getV.getNeighbors();
				for(int i = 0; i<4; i++){
					Vertex neighbor = getNeighbors[i];
					if(neighbor != null){
						double sumOfDistances = getV.getDistanceFromSource() +getWeightOfEdge(getV,neighbor);
						if(neighbor.getDistanceFromSource() > sumOfDistances){
							//remove it from pq, update neighbor, then readd neighbor to PQ
							pq.remove(neighbor);
							neighbor.setParent(getV);
							neighbor.setDistanceFromSource(sumOfDistances);
							pq.add(neighbor);
						}
					}
				}
			}
		}
		//set the shortestPath weight:
		int size = shortestPath.size();
		double totalWeight = shortestPath.get(size-1).getDistanceFromSource();
		setWeightOfShortestPath(totalWeight);
		
		return shortestPath;
	}
	
	private void setWeightOfShortestPath(double weight){
		if(weightOfShortestPath==Double.POSITIVE_INFINITY){
			weightOfShortestPath = weight;
		}
		else{
			weightOfShortestPath += weight;
		}
	}
	
	public double getWeightOfShortestPath(){
		return weightOfShortestPath;
	}
	

	//looks for a specific edge in the allEdges arraylist and returns the weight of that edge
	public double getWeightOfEdge(Vertex vertexA, Vertex vertexB){
		Edge getEdge;
		for(int i = 0; i<allEdges.size(); i++){
			if(allEdges.get(i).isEdgeBetween(vertexA, vertexB)){
				getEdge = allEdges.get(i);
				return getEdge.getWeight();

			}
		}		
		return Double.POSITIVE_INFINITY; //should be ok?
	}

	public ShortestPath(Tile source, Tile destination, List<Tile> visitedTiles){
		pq = new PriorityQueue<Vertex>();

		allVertices = new ArrayList<Vertex>();
		allEdges = new ArrayList<Edge>();
		shortestPath = new ArrayList<Vertex>();
		this.source = new Vertex(source);
		this.destination = new Vertex(destination);

		//add source and destination to allVertices arraylist
		allVertices.add(this.source);
		allVertices.add(this.destination);

		setAllVertices(source, destination, visitedTiles);	
		setAllEdges(allVertices);
		
		weightOfShortestPath = Double.POSITIVE_INFINITY;
	}

	public List<Edge> getAllEdges(){
		return allEdges;
	}

	public List<Vertex> getAllVertices(){
		return allVertices;
	}

	public void setAllVertices(Tile source, Tile destination, List<Tile> visitedTiles){
		//set the source vertex's distanceToSource to 0 (because it's the source)
		//keep destination vertex distanceToSource to POSITIVE_INFINITY
		//add them both to pq
		this.source.setDistanceFromSource(0);
		pq.add(this.source);
		pq.add(this.destination);
		System.out.println("Source is: "+this.source.toString());
		//System.out.println("first out: "+pq.peek().toString());

		/* TEST:
		this.destination.setDistanceFromSource(-100);
		System.out.println("first out: "+pq.peek().toString());
		pq.remove(this.destination);
		this.destination.setDistanceFromSource(-100);
		pq.add(this.destination);
		System.out.println("first out: "+pq.peek().toString());
		pq.remove(this.source);
		this.source.setDistanceFromSource(-200);
		pq.add(this.source);
		System.out.println("first out: "+pq.peek().toString());
		//***NEED TO REMOVE TILE, UPDATE DISTANCE, THEN ADD IT BACK TO PQ WHEN CHANGING DISTANCE/ANYTHINGINVERTEX ***
		 */

		for(int i = 0; i<visitedTiles.size(); i++){
			//if the tile is not the source or destination, then create a vertex
			//and add to the allVertices array and add to pq
			if(!(visitedTiles.get(i).sameTile(source)|| visitedTiles.get(i).sameTile(destination))){
				Vertex createVertex = new Vertex(visitedTiles.get(i));
				allVertices.add(createVertex); 
				pq.add(createVertex);
			}
		}
		//System.out.println("All done adding to pq... pq's size= "+pq.size());
	}

	//a fake toString to print allVertices for testing purposes - prints to console when called:
	public void printAllVerticesToConsole(){
		System.out.println("All vertices: ");
		for(int i = 0; i<allVertices.size(); i++){
			System.out.println(allVertices.get(i).toString());
		}
	}

	public void setAllEdges(List<Vertex> allVertices){
		for(int i = 0; i < allVertices.size(); i++){
			//go down the array and look for each vertex's neighbor
			Vertex current = allVertices.get(i);
			int currentX = current.getTile().getX();
			int currentY = current.getTile().getY();

			//go through all the vertices after the current vertex to compare coordinates
			for(int j = i+1; j < allVertices.size(); j++){
				Vertex following = allVertices.get(j);
				if(currentX == following.getTile().getX()){
					if((currentY-1)==following.getTile().getY()){
						//it's the lower neighbor
						//set it as a neighbor at index=1 for current
						current.setNeighbor(1, following);
						//set current as a neighbor of following at index=0
						following.setNeighbor(0, current);

						//create an edge between them and add to the allEdges arraylist
						Edge newEdge = new Edge(current, following);
						allEdges.add(newEdge);
					}
					if((currentY+1) == following.getTile().getY()){
						//it's the upper neighbor
						//set it as a neighbor at index=0 for current
						current.setNeighbor(0, following);
						//set current as a neighbor of following at index=1
						following.setNeighbor(1, current);

						//create an edge between them and add to the allEdges arraylist
						Edge newEdge = new Edge(current, following);
						allEdges.add(newEdge);
					}
				}
				if(currentY == following.getTile().getY()){
					if((currentX-1) == following.getTile().getX()){
						//it's the left neighbor
						//set it as a neighbor at index=2 for current
						current.setNeighbor(2, following);
						//set current as a neighbor of following at index=3
						following.setNeighbor(3, current);

						//create an edge between them and add to the allEdges arraylist
						Edge newEdge = new Edge(current, following);
						allEdges.add(newEdge);
					}
					if((currentX+1) == following.getTile().getX()){
						//it's the right neighbor
						//set it as a neighbor at index=3 for current
						current.setNeighbor(3, following);
						//set current as a neighbor of following at index=2
						following.setNeighbor(2, current);

						//create an edge between them and add to the allEdges arraylist
						Edge newEdge = new Edge(current, following);
						allEdges.add(newEdge);
					}
				}
			}
		}
	}

	//a fake toString to print allEdges for testing purposes - prints to console when called:
	public void printAllEdgesToConsole(){
		System.out.println("All edges: ");
		for(int i = 0; i<allEdges.size(); i++){
			System.out.println(allEdges.get(i).toString());
		}
	}

}

class Vertex implements Comparable<Vertex>{
	Tile tile;
	Vertex[] neighbors; //the discovered adjacent tiles = the vertex's neighbors
	double distanceFromSource;
	Vertex parent; //the vertex/tile that led to the current tile

	protected Vertex(Tile t){
		tile = t;
		neighbors = new Vertex[4]; //index 0: up, 1:down, 2:left; 3:right
		distanceFromSource = Double.POSITIVE_INFINITY; //initialize its distance from source to POSITIVE_INFINITY
		parent = null;
	}

	protected void setParent(Vertex v){
		parent = v;
	}

	protected Vertex getParent(){
		return parent;
	}

	protected void setNeighbor(int index, Vertex neighbor){
		neighbors[index] = neighbor;
	}

	protected Vertex[] getNeighbors(){
		return neighbors;
	}

	protected Tile getTile(){
		return tile;
	}

	protected void setDistanceFromSource(double distance){
		distanceFromSource = distance;
	}

	protected double getDistanceFromSource(){
		return distanceFromSource;
	}

	@Override
	public int compareTo(Vertex o) {
		if(this.distanceFromSource > o.distanceFromSource){
			return 1;//the current vertex is larger weight / longer distance to the destination
		}
		else if(this.distanceFromSource < o.distanceFromSource){
			return -1;//the current vertex is shorter weight / SHORTER DISTANCE to the destination
		}
		else{
			return 0; //the 2 vertices are equal weights/distanceToTarget	
		}
	}

	//toString method for testing purposes:
	public String toString(){
		return "Tile at ("+tile.getX()+", "+tile.getY()+")";
	}

	public boolean sameAs(Vertex v){
		if((this.getTile().getX() == v.getTile().getX()) && (this.getTile().getY() == v.getTile().getY())) return true;
		return false;
	}
}

class Edge{
	Vertex startingVertex;
	Vertex endingVertex; //the 2nd tile - the tile at the end of the edge
	double weight;

	//edge constructor
	protected Edge(Vertex start, Vertex end){
		startingVertex = start;
		endingVertex = end; //set the vertex that's at the end of the edge
		weight = BatteryManager.calculateWeight(start.getTile(), end.getTile()); // calculate the weight between the 2 vertices
	}

	public Vertex getStartingVertex(){
		return startingVertex;
	}
	public Vertex getEndingVertex(){
		return endingVertex;
	}

	public double getWeight(){
		return weight;
	}

	public boolean isEdgeBetween(Vertex vertexA, Vertex vertexB){
		if((this.getStartingVertex().sameAs(vertexA)&&this.getEndingVertex().sameAs(vertexB))
				|| (this.getStartingVertex().sameAs(vertexB)&&this.getEndingVertex().sameAs(vertexA)))
		return true; 

		return false;
	}

	//toString method for testing purposes
	public String toString(){
		return "Edge between "+startingVertex.toString()+" and "+endingVertex.toString()+" --- weight="+weight;
	}
}