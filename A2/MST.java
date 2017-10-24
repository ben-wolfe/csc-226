/* 
	
	Submited by:
		Ben Wolfe | V00205547

	Course:
		CSC 226 - Algorithms and Data Structures II

	Deliverable:
		Assignment 2 - Kruskal's Algorithms and MSTs

	Description:
		This assignment builds a miniumum spanning tree using Kruskal's algorithm and the weighted 
		quick-union version of union-find.  The MST method computes a minimum spanning tree and 
		returns the total weight of the tree.  The operation is completed with O(m*log(n)) time 
		complexity on a graph with m edges and n vertices. 

	Instructions:
		- To provide test inputs with standard input, run the program with "java MST".  Terminate 
		  with Ctrl-D (signalling EOF)
		- To read test inputs from a file, run the program with "java MST filename.txt"

	Input:   
       <number of vertices>
       <adjacency matrix row 1>
       ...
       <adjacency matrix row n>
*/

import java.util.Scanner;
import java.io.File;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Iterator;

public class MST {

	/* Finds a MST using Kruskal's algorithm and returns the count of all edges; input is assumed 
	   to be non-negative. */
    static int mst(int[][] G) {

		int numVerts = G.length;

		// Step 1. Set up the priority queue of edges
		/* Edge priority queue; the Java PriorityQueue class provides O(log(n)) add and remove 
		   methods https://docs.oracle.com/javase/7/docs/api/java/util/PriorityQueue.html */
		Queue<Edge> edgePQ = new PriorityQueue<Edge>();

		// Only consider 1 half of the matrix (half separated by diagonal) to avoid reading duplicates
		Edge tempEdge;
		int diagMarker = 0;		
		for (int row = 0; row < numVerts; row++, diagMarker++) {
			for (int col = 0; col < diagMarker; col++) {
				// Only consider if the edge has a weight greater than zero
				if (G[row][col] > 0) {
					tempEdge = new Edge(row, col, G[row][col]);
					edgePQ.add(tempEdge); // Add the edge to the priority queue
				}
			}
		}

		// Step 2. Instantiate weighted quick union
		WeightedQuickUnion weightedQuickUnion = new WeightedQuickUnion(numVerts);

		// Step 3. Iterate through edges, check for cycles, record weight, add to MST
		int totalWeight = 0;
		Queue<Edge> minimumSpanningTree = new PriorityQueue<Edge>();

		while (!edgePQ.isEmpty() && minimumSpanningTree.size() < numVerts) {

			tempEdge = edgePQ.remove();
			int vert1 = tempEdge.v1;
			int vert2 = tempEdge.v2;

			if (!weightedQuickUnion.connected(vert1, vert2)) {
				weightedQuickUnion.union(vert1, vert2);
				totalWeight += tempEdge.weight;
				minimumSpanningTree.add(tempEdge);
			}
		} 

		return totalWeight;
    }

	public static class Edge implements Comparable<Edge> {

		public int v1, v2, weight;
		
		// Constructor for the edge class
		public Edge(int v1, int v2, int weight) {
			this.v1 = v1;
			this.v2 = v2;
			this.weight = weight;
		}

		// Override the default comparison to be able to use the PriorityQueue class
		@Override
		public int compareTo(Edge e2) {
			if (this.weight < e2.weight) return -1;
			else if (this.weight > e2.weight) return 1;
			else return 0;
		}

	} // End Edge

	public static class WeightedQuickUnion {

		public int[] vertices;
		public int[] size;

		// Constructor for the weighted quick union class
		public WeightedQuickUnion(int numVerts) {
			size = new int[numVerts];
			vertices = new int[numVerts];

			for (int index = 0; index < numVerts; index++) {
				vertices[index] = index;
				size[index] = 1;
			}
		}

		// Traces up the tree to find the parent of a specific vertice
		public int getRoot(int index) {
			while (index != vertices[index]) index = vertices[index];
			return index;
		}

		// Checks if two vertices are connected by finding their respective roots
		public boolean connected(int comp1, int comp2) {
			int root1 = getRoot(comp1);
			int root2 = getRoot(comp2);

			if (root1 == root2) return true;
			else return false;
		}

		// Joins two components using weighted quick union; the smaller tree takes the root of 
		// the larger tree as its own root
		public void union(int vert1, int vert2) {

			int root1 = getRoot(vert1);
			int root2 = getRoot(vert2);

			if (size[root1] < size[root2]) {

				vertices[root1] = root2;
				size[root2] += size[root1];

			} else {

				vertices[root2] = root1;
				size[root1] += size[root2];

			}
		}

		/* Printing for testing purposes */
		public void printSizeAndVert() {
			System.out.println("Size Array");
			for (int index = 0; index < size.length; index++) {
				System.out.printf("%d ", size[index]);
			}
			System.out.println();

			System.out.println("Parent Array");
			for (int index = 0; index < vertices.length; index++) {
				System.out.printf("%d ", vertices[index]);
			}
			System.out.println();
		}

	} // End WeightedQuickUnion

    /* isConnected(G) tests whether the graph, G, is connected; nothing here will be marked */
    static boolean isConnected(int[][] G) {
		
		boolean[] covered = new boolean[G.length];
		for (int i = 0; i < covered.length; i++) {
		    covered[i] = false;
		}

		isConnectedDFS(G,covered,0);

		for (int i = 0; i < covered.length; i++) {
	    	if (!covered[i]) {
				return false;
	    	}
		}
		
		return true;
    }

    /* Called by isConnected; nothing here will be marked */
    static void isConnectedDFS(int[][] G, boolean[] covered, int v) {
		
		covered[v] = true;
		for (int i = 0; i < G.length; i++) {
		    if (G[v][i] > 0 && !covered[i]) {
				isConnectedDFS(G,covered,i);
		    }
		}
    }

    /* For testing purposes only; nothing in this function will be marked */
    public static void main(String[] args) {

		int graphNum = 0;
		Scanner s;

		/* Read the graph from a file */
		if (args.length > 0) {

	    	try {
				s = new Scanner(new File(args[0]));
	    	} catch(java.io.FileNotFoundException e) {
				System.out.printf("Unable to open %s\n",args[0]);
				return;
	    	}

	    	System.out.printf("Reading input values from %s...\n",args[0]);
		
		/* Or, read the graph from standard input */
		} else {
	    	s = new Scanner(System.in);
	    	System.out.printf("Reading input values from stdin...\n");
		}
		
		//Read graphs until EOF is encountered (or an error occurs)
		while(true) {

		    graphNum++;
		    if(!s.hasNextInt()) {
				break;
		    }

		    System.out.printf("Reading graph %d...\n",graphNum);
		    
		    int n = s.nextInt();
		    int[][] G = new int[n][n];
		    int valuesRead = 0;
		    
		    for (int i = 0; i < n && s.hasNextInt(); i++) {
				G[i] = new int[n];
				
				for (int j = 0; j < n && s.hasNextInt(); j++) {
			    	G[i][j] = s.nextInt();
			    	valuesRead++;
				}
		    }

		    if (valuesRead < n * n) {
				System.out.printf("Adjacency matrix for graph %d contains too few values.\n",graphNum);
				break;
		    }
		    
		    if (!isConnected(G)) {
				System.out.printf("Graph %d is not connected (no spanning trees exist)\n",graphNum);
				continue;
		    }

		    int totalWeight = mst(G);
		    System.out.printf("Graph %d: Total weight of MST is %d\n",graphNum,totalWeight);		
		}
    }
}
