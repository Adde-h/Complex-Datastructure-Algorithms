import java.io.*;
import java.util.ArrayList;

public class BipMatch 
{
  Kattio io = new Kattio(System.in, System.out);
  int e;
	int graphX[];
	int graphY[];
	int nodes, s, t, edges, x, y, capacity, totFlow; 
  Edge edgeX, edgeY;
  Node[] graph;
	

  // Step 3 of lab 3 
	BipMatch() throws IOException
	{
		// Convert it to a flow graph
		String flowGraph = readWriteBipartiteGraph();
		// Create flow Graph  
		readInput(flowGraph);
		// Edmond Karp nu 
		totFlow = edmondsKarp();
		// Write totFlow and the updated flowGraph 
		String updateFlow = writeTotFlow();
		// Cut out S and T, print out the graph to STDIO
		readWriteMaxFlowSolution(updateFlow);

		io.close();
	}
  
	// Reads input and creates a flowgraph at the same time. 
	String readWriteBipartiteGraph() 
	{
		// Läs antal hörn och kanter
		x = io.getInt(); y = io.getInt(); e = io.getInt();

		StringBuilder flowGraph = new StringBuilder();
    int nodes = (x+y+2), edges = (e+x+y), s = 1, t = nodes;
		capacity = 1; 

		flowGraph.append(nodes + "\n");
    flowGraph.append(s + " " + t + "\n");
    flowGraph.append(edges + "\n");

		graphX = new int[e];
		graphY = new int[e];
		// Koppla S till X
		for(int i = 2; i < x+2; i++) 
		{
			flowGraph.append(s + " " + i + " " + capacity + "\n");
		}

		// Läs in kanterna
		for (int i = 0; i < e; i++) 
		{
			int a = io.getInt();
			int b = io.getInt();
			graphX[i] = a+1;
			graphY[i] = b+1;
			flowGraph.append(graphX[i] + " " + graphY[i] + " " + capacity + "\n");
		}
		
		for (int i = x+2; i < t; i++) 
		{
			flowGraph.append(i + " " + t + " " + capacity + "\n");
		}
    
		return flowGraph.toString();
	}

	void readInput(String flowGraph) throws IOException
  {
		Reader inputString = new StringReader(flowGraph);
		BufferedReader reader = new BufferedReader(inputString);
		
    nodes = Integer.parseInt(reader.readLine());
		String[] sourceSink = reader.readLine().split(" ");
		s = Integer.parseInt(sourceSink[0]) - 1;
		t = Integer.parseInt(sourceSink[1]) - 1;
		edges = Integer.parseInt(reader.readLine());
    
    graph = new Node[nodes];

    // Initialize each node, creating a linked list inside each cell
    for (int i = 0; i < nodes; i++)
    {
      graph[i] = new Node();
    }

    for(int i = 0; i < edges; i++)
    {
			int a,b;
			String[] xyc = reader.readLine().split(" ");
      a = Integer.parseInt(xyc[0]) - 1;
      b = Integer.parseInt(xyc[1]) - 1;
		  capacity = Integer.parseInt(xyc[2]);

      edgeX = new Edge(a, b, 0, capacity);
      edgeY = new Edge(b, a, 0, 0);

      edgeX.setReverse(edgeY);
      edgeY.setReverse(edgeX);

      graph[a].edges.add(edgeX);
      graph[b].edges.add(edgeY);
    } 
  }

	int edmondsKarp()
  {
    int maxFlow = 0;
    
    while (true)
    {
      // Stores edge used to get to node i 
      Edge[] toEdge = new Edge[nodes];
      
      ArrayList<Node> queue = new ArrayList<>();
      queue.add(graph[s]);


      //BFS körs. Medans vi har en stig från s till t 
      while(!queue.isEmpty())
      {
        Node currentNode = queue.remove(0);

        for (Edge e : currentNode.edges) 
        {
          // If edge hasn't been visited, doesn't point to source and can send flow
          if(toEdge[e.y] == null && e.y != s && e.capacity > e.flow)
          {
            toEdge[e.y] = e;
            queue.add(graph[e.y]);
          }
        }
      }

      // If there's no path from s to t. Break then return maxFlow
      if(toEdge[t] == null)
      {
        break;
      }

      int bottleNeck = Integer.MAX_VALUE;

      // Find bottleNeck value in the path
      for(Edge e = toEdge[t]; e != null; e = toEdge[e.x])
      {
        bottleNeck = Math.min(bottleNeck, e.capacity - e.flow);
      }
      
      // Add flow values, flow comes in reverseved
      for(Edge e = toEdge[t]; e != null; e = toEdge[e.x])
      {
        e.flow += bottleNeck;
        e.reverse.flow -= bottleNeck;
      }

      maxFlow += bottleNeck;
    }

    return maxFlow;
  }

	String writeTotFlow()
  {
    StringBuilder sb = new StringBuilder();
    StringBuilder sb2 = new StringBuilder();

    sb.append(nodes + "\n");
    sb.append((s+1) + " " + (t+1) + " " + totFlow + "\n");

    int count = 0;
    for (int i = 0; i < nodes; i++) 
    {
      for (Edge e : graph[i].edges) 
      {
        if(e.flow > 0)
        {
          count++;
          sb2.append((i+1) + " " + (e.y+1) + " " + e.flow);
          sb2.append("\n");
        }
      }
    }

    sb.append(count + "\n");
		sb.append(sb2);
    return sb.toString();
  }

	void readWriteMaxFlowSolution(String flow) throws IOException
  {
		Reader inputString = new StringReader(flow);
		BufferedReader reader = new BufferedReader(inputString);
		// Läs in antal hörn, kanter, källa, sänka, och totalt flöde
		// (Antal hörn, källa och sänka borde vara samma som vi i grafen vi
		// skickade iväg)
		String v = reader.readLine();
		String[] stf = reader.readLine().split(" ");
		int s = Integer.parseInt(stf[0]);
		int t = Integer.parseInt(stf[1]);
		int totflow = Integer.parseInt(stf[2]);
		int e = Integer.parseInt(reader.readLine());

		io.println(x + " " + y);
		io.println(totflow);

		for (int i = 0; i < e; ++i) 
		{
			String[] xyf = reader.readLine().split(" ");
			// Get the flows 
			int a = Integer.parseInt(xyf[0]);
			int b = Integer.parseInt(xyf[1]);
    
      // If start and end points aren't included. Print them but minus 1 
			if(a != s && t != b)
			{
				io.println((a-1) + " " + (b-1));
			}
		}
		io.flush();
	}

	public static void main(String[] args) throws IOException
	{
		new BipMatch();
	}

}
