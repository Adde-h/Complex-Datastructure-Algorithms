import java.io.*;
import java.util.LinkedList;

public class BipMatch 
{
	//Class attributes
	Kattio io = new Kattio(System.in, System.out);
	int e, nodes, s, t, edges, x, y, capacity, totFlow;
	Edge edgeX, edgeY;
	Node[] graph;

	// Step 3 of lab 3
	BipMatch() throws IOException 
	{
		// Reads the bipartite graph input and creates a flowgraph
		readWriteBipartiteGraph();
		// Adds the total flow to global Variable
		totFlow = edmondsKarp();
		// Outputs the graph, Deletes the edges that contains s and t 
		writeTotFlow();

		io.close();
	}

	// Reads input and creates a flowgraph at the same time.
	void readWriteBipartiteGraph() 
	{
		// Reads total notes from X and Y side and total edges between
		x = io.getInt();
		y = io.getInt();
		e = io.getInt();

		// We're going to add S and T nodes and connect them to the bipartite graph
		nodes = (x + y + 2);
		edges = (e + x + y);
		s = 0;
		t = nodes - 1;
		capacity = 1;
		graph = new Node[nodes];

		// Initialize each node, creating a linked list inside each cell
		for (int i = 0; i < nodes; i++) 
		{
			graph[i] = new Node();
		}

		// Connect Sink to X and vice versa
		for (int i = 1; i < x + 1; i++) 
		{
			// Create the edges
			edgeX = new Edge(s, i, 0, capacity);
			edgeY = new Edge(i, s, 0, 0);
			
			// Fix their reverse pointers
			edgeX.setReverse(edgeY);
			edgeY.setReverse(edgeX);

			// Now add them to the graph. 
			graph[s].edges.add(edgeX);
			graph[i].edges.add(edgeY);
		}

		// Connect X to Y and vice versa
		for (int i = 0; i < e; i++) 
		{
			int a = io.getInt();
			int b = io.getInt();

			edgeX = new Edge(a, b, 0, capacity);
			edgeY = new Edge(b, a, 0, 0);

			edgeX.setReverse(edgeY);
			edgeY.setReverse(edgeX);

			graph[a].edges.add(edgeX);
			graph[b].edges.add(edgeY);
		}

		// Connect Y to T and vice versa
		for (int i = x + 1; i < t; i++) 
		{
			edgeX = new Edge(i, t, 0, capacity);
			edgeY = new Edge(t, i, 0, 0);

			edgeX.setReverse(edgeY);
			edgeY.setReverse(edgeX);

			graph[i].edges.add(edgeX);
			graph[t].edges.add(edgeY);

		}

	}
	/*
	Edmondkarp algorithm. 
		Do BFS to find path from S till T. then
			Extract bottleneck capacity from the path
				for each Node in the path 
					Capacity = Capacity - BottleNeck
					Flow = flow + bottlneck 
			maxflow = maxflow + bottleneck
		return bottleneck 
	*/
	int edmondsKarp() 
	{
		int maxFlow = 0;

		while (true) 
		{
			// Stores edge used to get to node i
			Edge[] toEdge = new Edge[nodes];
			
			// Queue implementation using a linkedList
			LinkedList<Node> queue = new LinkedList<>();
			queue.add(graph[s]);

			// Queue cannot be empty. If empty no neighbours
			while (!queue.isEmpty()) 
			{
				// Take out first element in queue
				Node currentNode = queue.remove(0);

				// Check each neighbour from the node
				for (Edge e : currentNode.edges) 
				{
					// If edge hasn't been visited, doesn't point to source and can send flow
					if (toEdge[e.y] == null && e.y != s && e.capacity > e.flow) 
					{
						toEdge[e.y] = e;
						queue.add(graph[e.y]);
					}
				}
			}

			// If there's no path from s to t. Break then return maxFlow
			if (toEdge[t] == null) 
			{
				break;
			}

			int bottleNeck = Integer.MAX_VALUE;

			// Find bottleNeck value in the path
			for (Edge e = toEdge[t]; e != null; e = toEdge[e.x]) 
			{
				bottleNeck = Math.min(bottleNeck, e.capacity - e.flow);
			}

			// Add flow values, flow comes in reverseved
			for (Edge e = toEdge[t]; e != null; e = toEdge[e.x]) 
			{
				e.flow += bottleNeck;
				e.reverse.flow -= bottleNeck;
			}
			maxFlow += bottleNeck;
		}
		return maxFlow;
	}

	void writeTotFlow() 
	{
		io.println(x + " " + y);
		io.println(totFlow);

		for (int i = 1; i < x + 1; i++) 
		{
			for (Edge e : graph[i].edges) 
			{
				// Print what the edges connects to, 
				if (e.flow > 0 && e.x != s && e.y != t) 
				{
					io.println((e.x) + " " + (e.y));
				}
			}
		}
	}

	public static void main(String[] args) throws IOException 
	{
		new BipMatch();
	}

}
