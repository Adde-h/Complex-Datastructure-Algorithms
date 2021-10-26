public class solveFlowProblem 
{
  Kattio io;
  int nodes; 
  int s;
  int t;
  int edges;
  int x;
  int y;
  int flow; 

  solveFlowProblem()
  {
    // First read the input
    readInput();

  }


  // A method that reads off an input
  void readInput()
  {
    nodes = io.getInt();
		s = io.getInt();
		t = io.getInt();
		edges = io.getInt();
  
    for(int i = 0; i<edges; i++)
    {
      x = io.getInt();
      y = io.getInt();
		  flow = io.getInt();
    }    
   
  }

  public static void main(String[] args) 
  {
    new solveFlowProblem();
  }


}
