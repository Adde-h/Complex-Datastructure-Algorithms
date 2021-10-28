/*
Adeel Hussain
Generated: 2020-10-07, Updated: 2020-10-07
Dependencies: Graph.java, MyQueue.java
Input: Graph & Vertex Source
Reference: https://algs4.cs.princeton.edu/41graph/BreadthFirstPaths.java.html
*/

public class BreadthFirstSearch 
{
    private boolean[] marked;           //Creates a boolean marked array to indicate if BFS has been used on the chosen vertex
    private int[] edgeTo;               //Last vertex on known path to this vertex
    private final int s;                //Start vertex
    
    //Computes the shortest path to the Vertex (s) in Graph (G) using BFS
    public BreadthFirstSearch(Graph G, int s) 
    {
        marked = new boolean[G.V()];    //Creates a boolean array with size of amount of vertices to store visited vertices
        edgeTo = new int[G.V()];        //Creates an edgeTo int Array to store edge to vertices
        this.s = s;                     //Sets the start vertex
        bfs(G, s);                      //Runs the BFS method to look for the shortest path
    }

    //Iterates through all vertexes and fills the edgeTo array
    private void bfs(Graph G, int s) 
    {
        MyQueue<Integer> q = new MyQueue<Integer>();    //Creates a queue to store shortest path
        marked[s] = true;                               //Marks the source vertex as true
        q.enqueue(s);                                   //Queues the source vertex

        while (!q.isEmpty())                            //While queue is not empty
        {
            int v = q.dequeue();                        //Dequeue top node and set (v) as its int value
            for (int w : G.adj(v))                      //Iterate through (v) adj bag array
            {
                if (!marked[w])                         //If node retrieved from bag array is not marked (i.e not visited already)
                {
                    edgeTo[w] = v;                      //Connect those two by inserting (v) to (w) edgeTo array
                    marked[w] = true;                   //Mark the newly visited vertex (w) as true
                    q.enqueue(w);                       //Dequeue the visited vertex (w) from the queue, moving on to next vertex if avalible
                }
            }
        }
    }
    
    //Returns true if there is a path to vertex in question (i.e has been mapped by BFS)
    public boolean hasPathTo(int v) 
    {
        return marked[v];
    }

    public Iterable<Integer> pathTo(int v)              //Loads in stack for each vertex from Start (s) to Finish (v)
    {
        if (!hasPathTo(v)) return null;                 //If path does not exist, return null
        Stack<Integer> path = new Stack<Integer>();     //Create a stack called Path containing integers
        for (int x = v; x != s; x = edgeTo[x])          //For loop that begins at last vertex and iterates all the way to start vertex
        {
            path.push(x);                               //Pushing all the vertex from last vertex to start vertex to stack
        }
        path.push(s);                                   //Pushes start vertex to the stack  
        
        return path;                                    //Returns the stack
    }
}
