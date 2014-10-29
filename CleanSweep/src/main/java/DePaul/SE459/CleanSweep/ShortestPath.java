package DePaul.SE459.CleanSweep;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/*
 * In Progress
 * 
 * Still needs to calculate add to heap, find distances, and add/remove from heap
 * Created Vertex and Edges so far.
 */

/*
 * tile coordinates = the vertices
 * charge between 2 tiles = the edges
 */
public class ShortestPath {
	Vertex[] allVertices;
	List<Edge> allEdges;
	List<Vertex> shortestPath;
	Vertex source;
	Vertex destination;
	PriorityQueue<Vertex> pq;
	
	public ShortestPath(Tile source, Tile destination, List<Tile> visitedTiles){
		pq = new PriorityQueue<Vertex>();
		
		int numVertices = visitedTiles.size();
		allVertices = new Vertex[numVertices];
		allEdges = new ArrayList<Edge>();
		shortestPath = new ArrayList<Vertex>();
		this.source = new Vertex(source);
		this.destination = new Vertex(destination);
		
		//add source and destination to allVertices array
		allVertices[0] = this.source;
		allVertices[1] = this.destination;
		
		setAllVertices(source, destination, visitedTiles);
		
	}
	
	public void setAllVertices(Tile source, Tile destination, List<Tile> visitedTiles){
		for(int i = 0; i<visitedTiles.size(); i++){
			//if the tile is not the source or destination, then create a vertex
			//and add to the allVertices array
			if(!(visitedTiles.get(i).isSameTileAs(source)|| visitedTiles.get(i).isSameTileAs(destination))){
				//don't create new tile, but add to arraylist
				allVertices[i+2] = new Vertex(visitedTiles.get(i));
			}
		}
	}
	
	public void setAllEdges(Vertex[] allVertices){
		for(int i = 0; i < allVertices.length; i++){
			//go down the array and look for each vertex's neighbor
			Vertex current = allVertices[i];
			int currentX = current.getTile().getX();
			int currentY = current.getTile().getY();
			
			//go through all the vertices after the current vertex to compare coordinates
			for(int j = i+1; j < allVertices.length; j++){
				Vertex following = allVertices[j];
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
					if((currentX+1) == following.getTile().getY()){
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

class Vertex implements Comparable<Vertex>{
	Tile tile;
	Vertex[] neighbors; //the discovered adjacent tiles = the vertex's neighbors
	double distanceToDestination;
	Vertex parent; //the vertex/tile that led to the current tile

	protected Vertex(Tile t){
		tile = t;
		neighbors = new Vertex[4]; //index 0: up, 1:down, 2:left; 3:right
		distanceToDestination = Double.POSITIVE_INFINITY; //initialize its distance to infinity
		parent = null;
	}
	protected void setNeighbor(int index, Vertex neighbor){
		neighbors[index] = neighbor;
	}

	protected Tile getTile(){
		return tile;
	}

	@Override
	public int compareTo(Vertex o) {
		// TODO Auto-generated method stub
		if(this.distanceToDestination > o.distanceToDestination){
			return 1;//the current vertex is larger weight / longer distance to the destination
		}
		else if(this.distanceToDestination < o.distanceToDestination){
			return -1;//the current vertex is shorter weight / SHORTER DISTANCE to the destination
		}
		else{
			return 0; //the 2 vertices are equal weights/distanceToTarget	
		}
	}
}

class Edge{
	Vertex endingVertex; //the 2nd tile - the tile at the end of the edge
	double weight;
	
	//edge constructor
	protected Edge(Vertex start, Vertex end){
		endingVertex = end; //set the vertex that's at the end of the edge
		calculateWeight(start, end); // calculate the weight betwen the 2 vertices
	}
	
	
	protected void calculateWeight(Vertex start, Vertex end){
		weight = (start.getTile().getSurfaceType()+end.getTile().getSurfaceType())/2;
	}
}