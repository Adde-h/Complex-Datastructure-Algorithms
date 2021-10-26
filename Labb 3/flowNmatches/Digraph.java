/*
* Adeel Hussain & Rakin Ali
* Generated: 2021-10-26, Updated: 2021-10-26
* Input: Any Items
* Dependencies: Bag.java
* Reference: 
* https://algs4.cs.princeton.edu/13stacks/Bag.java.html
* http://math.oxford.emory.edu/site/cs171/directedAndEdgeWeightedGraphs/
*/

public class Digraph
{
  // Attributes of the class. 
  private final int Nodes;          // Number of Nodes/Vertices (Hörn)
  private int Edges;                // Number of Edges  (Kanter)
  private Bag<Integer>[] list;      // A bag to contain

  //Initializes an empty Digraph with Nodes amount of Nodes/Vertices
  public Digraph(int Nodes)
  {
    if (Nodes < 0) throw new IllegalArgumentException("Number of Nodes/Vertices must be nonnegative");
    this.Nodes = Nodes;
    this.Edges = 0;
    list = (Bag<Integer>[]) new Bag[Nodes];

    for(int i = 0; i < Nodes; i++)         //For loop that iterates through all the vertices and 
    {
      list[i] = new Bag<Integer>();         //Creates a bag array for each vertex (Linked List)     
    }
  }
  
  //Returns the number of Nodes/Vertices in the Digraph
  public int Nodes()
  {
    return Nodes;
  }

  // Returns the number of edges in the Digraph
  public int Edges()
  {
    return Edges;
  }

  // Adds the edges to the nodes thus connecting them
  public void addEdge(int fromNode, int toNode, int weight )
  {
    list[fromNode].add(toNode,weight);
  }

  //Returns the adjecent bag to the chosen node
  public Iterable<Integer> adj(int n) 
  {
    return list[n];
  }
    
}