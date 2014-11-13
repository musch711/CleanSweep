package DePaul.SE459.CleanSweep;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;


/*
 * ShortestPath class - calculates the shortest path, where weight = power consumption over the distance traveled
 * Tile coordinates = the vertices
 * Charge between 2 tiles = the weight = the edges
 */
public class ShortestPath {
	private List<Vertex> allVertices;
	private List<Edge> allEdges;
	private Vertex source;
	private Vertex destination;
	private PriorityQueue<Vertex> pq;
	private double weightOfShortestPath;
	private List<Tile> shortestPath;

	/*
	 * ShortestPath constructor
	 * @params Tile source, Tile destination, visitedTiles Arraylist
	 */
	public ShortestPath(Tile source, Tile destination, List<Tile> visitedTiles){
		pq = new PriorityQueue<Vertex>();
		shortestPath = new ArrayList<Tile>();
		allVertices = new ArrayList<Vertex>();
		allEdges = new ArrayList<Edge>();
		this.source = new Vertex(source);
		this.destination = new Vertex(destination);

		//add source and destination to allVertices arraylist
		allVertices.add(this.source);
		allVertices.add(this.destination);

		setAllVertices(source, destination, visitedTiles);	
		setAllEdges(allVertices);

		weightOfShortestPath = Double.POSITIVE_INFINITY;

		setShortestPath();
	}
	private void setShortestPath(){
		List<Vertex> shortestVertexPath = new ArrayList<Vertex>();

		List<Vertex> pathTemp = new ArrayList<Vertex>();

		//do all of this while the heap is not empty OR until the pathTemp list doesn't contain the destination vertex
		while(!pq.isEmpty() && !pathTemp.contains(destination)){
			if(pq.peek() != null){
				Vertex getV = pq.poll(); //get the Vertex with the smallest distance from source that is left in the PQ

				pathTemp.add(getV); //add the vertex to the end of the pathTemp sequence

				//get the neighbors of the getV
				//if their current weight is GREATER than getV's weight + weight between getV and neighbor,
				//then set their weights, and set their parent to getV
				Vertex[] getNeighbors = getV.getNeighbors();
				for(int i = 0; i<4; i++){
					Vertex neighbor = getNeighbors[i];
					if(neighbor != null){
						double sumOfDistances = getV.getDistanceFromSource() +getWeightOfEdge(getV,neighbor);
						if(neighbor.getDistanceFromSource() > sumOfDistances){
							//remove it from pq, update neighbor, then read neighbor to PQ
							pq.remove(neighbor);
							neighbor.setParent(getV);
							neighbor.setDistanceFromSource(sumOfDistances);
							pq.add(neighbor);
						}
					}
				}
			}
		}

		Vertex v;
		v = pathTemp.get(pathTemp.size()-1);
		shortestVertexPath.add(v);

		if(pathTemp.size()>1){

			while(v!=pathTemp.get(0)){
				v = v.getParent();
				shortestVertexPath.add(v);

			}
		}
		Collections.reverse(shortestVertexPath); //reverse it so that it's in order from source to destination

		//change it to return TILES and not Vertices
		List<Tile> shortestPathInTiles = new ArrayList<Tile>();
		for(int i = 0; i<shortestVertexPath.size(); i++){
			shortestPathInTiles.add(shortestVertexPath.get(i).getTile());
		}
		this.shortestPath = shortestPathInTiles;

		//set the shortest path weight
		double pathWeight = 0;
		for(int i = 0; i<shortestVertexPath.size()-1; i++){
			double weight = getWeightOfEdge(shortestVertexPath.get(i), shortestVertexPath.get(i+1));
			pathWeight+=weight;
		}
		setWeightOfShortestPath(pathWeight);
	}
	/*
	 * getShortestPath()
	 * @return List - Arraylist of vertices - in sequence starting from source at index 0
	 */	
	public List<Tile> getShortestPath(){
		return shortestPath;
	}
	/*
	  For testing purposes - prints the shortest path to the console

	public void printShortestPath(){
		for(int i = 0; i<shortestPath.size(); i++){
			System.out.println("("+shortestPath.get(i).getX()+", "+shortestPath.get(i).getY()+")");
		}
	}
	 */
	private void setWeightOfShortestPath(double weight){
		/*if(weightOfShortestPath==Double.POSITIVE_INFINITY){
			weightOfShortestPath = weight;
		}
		else{
			weightOfShortestPath += weight;
		}*/
		weightOfShortestPath = weight;
	}

	public double getWeightOfShortestPath(){
		return weightOfShortestPath;
	}


	/*
	 * getWeightOfEdge - looks for a specific edge in the allEdges arrayList 
	 * @return the weight of that edge between 2 vertices
	 */
	private double getWeightOfEdge(Vertex vertexA, Vertex vertexB){
		Edge getEdge;
		for(int i = 0; i<allEdges.size(); i++){
			if(allEdges.get(i).isEdgeBetween(vertexA, vertexB)){
				getEdge = allEdges.get(i);
				return getEdge.getWeight();

			}
		}		
		return Double.POSITIVE_INFINITY;
	}
	/*
	private List<Edge> getAllEdges(){
		return allEdges;
	}

	private List<Vertex> getAllVertices(){
		return allVertices;
	}
	 */
	private void setAllVertices(Tile source, Tile destination, List<Tile> visitedTiles){
		//set the source vertex's distanceToSource to 0 (because it's the source)
		//keep destination vertex distanceToSource to POSITIVE_INFINITY
		//add them both to pq
		this.source.setDistanceFromSource(0);
		pq.add(this.source);
		if(!this.destination.sameAs(this.source))pq.add(this.destination);

		for(int i = 0; i<visitedTiles.size(); i++){
			//if the tile is not the source or destination, then create a vertex
			//and add to the allVertices array and add to pq
			if(!(visitedTiles.get(i).sameTile(source)|| visitedTiles.get(i).sameTile(destination))){
				//if the tile was not already added, then add it
				boolean wasAdded = false;
				for(int j=0; j<i; j++){
					if(visitedTiles.get(i).sameTile(visitedTiles.get(j))) wasAdded = true;
				}
				if(!wasAdded){
					Vertex createVertex = new Vertex(visitedTiles.get(i));
					allVertices.add(createVertex); 
					pq.add(createVertex);


				}
			}
		}
	}

	/*
	 * For testing purposes - prints all vertices to console
	public void printAllVerticesToConsole(){
		System.out.println("All vertices: ");
		for(int i = 0; i<allVertices.size(); i++){
			System.out.println(allVertices.get(i).toString());
		}
	}
	 */
	private void setAllEdges(List<Vertex> allVertices){
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
						//add it as a neighbor and edge ONLY if path = 1 for the lower path
						if(current.getTile().getLowerPath()==1){
							//it's the lower neighbor
							//set it as a neighbor at index=1 for current
							current.setNeighbor(1, following);
							//set current as a neighbor of following at index=0
							following.setNeighbor(0, current);

							//create an edge between them and add to the allEdges arraylist
							Edge newEdge = new Edge(current, following);
							allEdges.add(newEdge);
						}
					}
					if((currentY+1) == following.getTile().getY()){
						//add it as a neighbor and edge ONLY if path = 1 for the upper path
						if(current.getTile().getUpperPath()==1){

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
				}
				if(currentY == following.getTile().getY()){
					if((currentX-1) == following.getTile().getX()){
						//add it as a neighbor and edge ONLY if path = 1 for the left path
						if(current.getTile().getLeftPath()==1){

							//it's the left neighbor
							//set it as a neighbor at index=2 for current
							current.setNeighbor(2, following);
							//set current as a neighbor of following at index=3
							following.setNeighbor(3, current);

							//create an edge between them and add to the allEdges arraylist
							Edge newEdge = new Edge(current, following);
							allEdges.add(newEdge);
						}
					}
					if((currentX+1) == following.getTile().getX()){
						//add it as a neighbor and edge ONLY if path = 1 for the right path
						if(current.getTile().getRightPath()==1){

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
	}

	/*
	 * For testing purposes - prints all edges with weights to console
	 
	public void printAllEdgesToConsole(){
		System.out.println("All edges: ");
		for(int i = 0; i<allEdges.size(); i++){
			System.out.println(allEdges.get(i).toString());
		}
	}
	*/
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

	/*
	 * For testing purposes - returns String of tile coordinates of the Vertex
	 */
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

	/*
	  For testing purposes - returns String of Edge's vertices and weights

	public String toString(){
		return "Edge between "+startingVertex.toString()+" and "+endingVertex.toString()+" --- weight="+weight;
	}
	 */
}