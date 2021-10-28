/*
Adeel Hussain
Generated: 2020-10-07, Updated: 2020-10-07
A program based on BFS which can answer questions of the type: "Find the a path from X to Y" 
Which results in a list of vertices traversed from X to Y if there is a path.
Should return shortest path from X to Y in a Undirected Graph
Input: txt file
Dependencies: BreadthFirstSearch.java, Graph.java, SymbolGraph.java
Reference: https://algs4.cs.princeton.edu/41graph/
*/

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Assignment2 
{
    public static void main(String[] args) throws FileNotFoundException 
    {
        //Construct a symbolgraph using inserted filename
        SymbolGraph sg = new SymbolGraph("TheDatabase.txt");
        Scanner in = new Scanner(System.in);
        
        System.out.println("Enter State to start from: ");
        String startState = in.nextLine().toUpperCase();
        
        int startIndex = sg.indexOf(startState);
        System.out.println("Enter state to end in: ");
        String endState = in.nextLine().toUpperCase();
        
        int endIndex = sg.indexOf(endState);

        //Construct the graph
        Graph graph = sg.graph();
        
        //Start the BFS search and map all vertices and edges
        BreadthFirstSearch bfs = new BreadthFirstSearch(graph, startIndex);
        
        if(!bfs.hasPathTo(endIndex))
        {
            System.out.println("There is no path between the chosen states");
        }
        else
        {
            for(int f : bfs.pathTo(endIndex)) //Retrieve stack of vertices from start vertex to end vertex
            {
                if(f == endIndex)
                {
                    System.out.print(sg.nameOf(f));
                }
                else
                {
                    System.out.print(sg.nameOf(f) + " ==> ");
                }
            }
        }

        in.close();
    }
}
