/*
* Adeel Hussain & Rakin Ali
* Generated: 2021-10-25, Updated: 2021-10-26
* Input: 
* Dependencies: Kattio.java
* Reference: https://kth.instructure.com/courses/27078/assignments/167028
*/

public class solveFlowProblem 
{
  Kattio io;
  int nodes; 
  int s;
  int t;
  int edges;
  int x;
  int y;
  int capacity; 
  Digraph graph;

  solveFlowProblem()
  {
    // Brings the input
    io = new Kattio(System.in, System.out);
    // Reads the input and builds the graph 
    readInput();

  }

  /**
   * Den första raden består av ett heltal som anger antalet hörn i V.
   * Den andra raden består av två heltal s och t som anger vilka hörn som är källa respektive utlopp.
   * Den tredje raden består av ett tal som anger |E|, det vill säga antalet kanter i grafen.
   * De följande |E| raderna består var och en av tre heltal som svarar mot en kant.
   * 
   * Indata:
   *  4
   *  1 4
   *  5
   *  1 2 1
   *  1 3 2
   *  2 4 2
   *  3 2 2
   *  3 4 1
   */

  // A method that reads off an input
  void readInput()
  {
    nodes = io.getInt();
		s = io.getInt();
		t = io.getInt();
		edges = io.getInt();
    graph = new Digraph(nodes+1); //1 indexed list
  
    for(int i = 0; i < edges; i++)
    {
      x = io.getInt();
      y = io.getInt();
		  capacity = io.getInt();
      graph.addEdge(x, y, capacity);
      
    }    
  }

// PUSHa til git! 
/*
  void bfs(Digraph graph,)
  {

  }
*/
  public static void main(String[] args) 
  {
    new solveFlowProblem();
  }


}
