/* 
	Submited by:
		Ben Wolfe | V00205547

	Course:
		CSC 226 - Algorithms and Data Structures II

	Deliverable:
		Assignment 3 - Dijkstra's Shortest Path Algorithm

	Description:
		This assignment calculates the shortest paths to all vertices given a starting source
		node.  The program runs in O(V^2 + ElogV) where V^2 is due to the cost of reading in 
		the adjacency matrix.
*/

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class ShortestPaths{
 
	public static int numVerts;
	public static Vertex[] graphVertices;
	
// ============================================= SHORTEST PATHS

	static void ShortestPaths(int[][] G, int source){
		
		numVerts = G.length;

		// Create an array of all vertices in the graph
		graphVertices = new Vertex[numVerts];

		// Iterate through all vertices in the graph to get edge weights
		// and to/from relationships
		Edge tempEdge;
		Vertex tempVertex;

		for (int from = 0; from < numVerts; from++) {
			tempVertex = new Vertex(from);
			graphVertices[from] = tempVertex;

			for (int to = 0; to < numVerts; to++) {
				if (G[from][to] != 0) {

					// Create and edge: Edge(<weight>, <vertex_to>)
					tempEdge = new Edge(G[from][to], to); 

					// Add the edge to the adjacent edge list
					graphVertices[from].adjacentEdges.add(tempEdge); 	
				}
			}
		}

		graphVertices[source].minDistance = 0;

		// Create a priority queue of vertices ordered by minimum distance
		PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
		vertexQueue.add(graphVertices[source]);

		while (!vertexQueue.isEmpty()) {

			Vertex u = vertexQueue.poll();

			for (Edge e : u.adjacentEdges) {
			
				int weight = e.weight;
				int newDistance = u.minDistance + weight;

				Vertex v = graphVertices[e.to];
				if (newDistance < v.minDistance) {
					// Remove the vertice if it exists since priority queue does
					// provide change key functionality
					vertexQueue.remove(v);
					v.minDistance = newDistance;
					v.previous = u.id;
					vertexQueue.add(v);
				}
			}
		}
	}


// ================================================ PRINT PATHS

	static void PrintPaths(int source){

		int shortestDistance = 0;
		List<Vertex> shortestPath = new ArrayList<Vertex>();

		// Print the path for every vertice in the graph
		for (int target = 0; target < numVerts; target++) {
			
			int distance = graphVertices[target].minDistance;

			// Get the path for the vertex
			for (Vertex v = graphVertices[target]; v.id != source; v = graphVertices[v.previous]) {
				shortestPath.add(v);
			}

			// Reverse the path to start from source 
			Collections.reverse(shortestPath);

			System.out.print("The path from " +source+ " to " +target+ " is: " +source);

			// Print the path
			for (Vertex v : shortestPath) {
					System.out.print(" --> " +v.id);
			}

			System.out.println(" and the total distance is: " + distance);
			shortestPath.clear();
		}
	}


// =============================================== VERTEX CLASS

	public static class Vertex implements Comparable<Vertex> {

		int id, previous;
		int minDistance = Integer.MAX_VALUE;
		List<Edge> adjacentEdges = new ArrayList<Edge>();

		public Vertex(int id) {
			this.id = id;
			this.previous = id;
		}

		@Override
		public int compareTo(Vertex v2) {
			if (this.minDistance < v2.minDistance) return -1;
			else if (this.minDistance > v2.minDistance) return 1;
			else return 0; 
		}
	}


// ================================================= EDGE CLASS

	public static class Edge {
		int weight, to;
		public Edge(int weight, int to) {
			this.weight = weight;
			this.to = to;
		}
	}


// ======================================================= MAIN
	public static void main(String[] args) 
	throws FileNotFoundException{
	
		Scanner s;

		if (args.length > 0){

			try{
				s = new Scanner(new File(args[0]));
			} catch(java.io.FileNotFoundException e){
				System.out.printf("Unable to open %s\n",args[0]);
				return;
			}
			System.out.printf("Reading input values from %s.\n",args[0]);
		
		}else{
			s = new Scanner(System.in);
			System.out.printf("Reading input values from stdin.\n");
		}
		
		int graphNum = 0;
		double totalTimeSeconds = 0;
		
		//Read graphs until EOF is encountered (or an error occurs)
		while(true){

			graphNum++;
			if(graphNum != 1 && !s.hasNextInt())
				break;

			System.out.printf("Reading graph %d\n",graphNum);
			int n = s.nextInt();
			int[][] G = new int[n][n];
			int valuesRead = 0;

			for (int i = 0; i < n && s.hasNextInt(); i++){
				for (int j = 0; j < n && s.hasNextInt(); j++){
					G[i][j] = s.nextInt();
					valuesRead++;
				}
			}

			if (valuesRead < n*n){
				System.out.printf("Adjacency matrix for graph %d contains too few values.\n",graphNum);
				break;
			}

			long startTime = System.currentTimeMillis();
			
			ShortestPaths(G, 0);
			PrintPaths(0);

			long endTime = System.currentTimeMillis();
			totalTimeSeconds += (endTime-startTime)/1000.0;
			
			//System.out.printf("Graph %d: Minimum weight of a 0-1 path is %d\n",graphNum,totalWeight);
		}

		graphNum--;
		System.out.printf("Processed %d graph%s.\nAverage Time (seconds): %.2f\n",graphNum,(graphNum != 1)?"s":"",(graphNum>0)?totalTimeSeconds/graphNum:0);
	}
}
