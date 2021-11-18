/*This class reduces graph-coloring problem to "Roll-besättningsproblemet" */
public class Reduce
{
	// Global variables 
	static int v, e, colours, actors, roles, scenes; 
 	static Kattio io = new Kattio(System.in, System.out);
	public static void main(String[] args) 
	{
		// Get the in-data
		v = io.getInt();					// Number of vertices (roles)
		e = io.getInt(); 					// Number of edges (scenes)
		colours = io.getInt(); 		// Number of colours (actors)

		/* If nodes less than or equal to total nodes => Always Yes - instance -> Minimal rolecrew problem */ 
		if(v <= colours)
		{
			io.println("3\n2\n3");
			io.println("1 1\n1 2\n1 3");
			io.println("2 1 3\n2 2 3");
		}
		else
		{
			/*
			* Minimum required production to solve roleproblem is:
			* 3 roles
			* 3 actors
			* 2 scenes
			*/

			// Convert to coloring problem
			roles = v + 3;							//Minimum number of roles needed is 3, minimum v is 1
			scenes = e + v + 2;					//Minimum number of scenes needed is 2, minimum e is 0
			actors = colours + 3;				//Minimum number of actors needed is 3, minimum colors is 1
			io.println(roles + "\n" + scenes + "\n" + actors);
			
			for(int i = 4; i <= roles; i++)
			{
				io.print(colours + " ");
				for(int j = 0; j < colours; j++)
				{
					io.print((4 + j) + " ");
				}
				io.println("");
			}

			io.println("1 1\n1 2\n1 3");

			for(int i = 1; i < roles; i++)
			{
				io.println(2 + " " + i + " " + roles);
			}
			
			int a, b;
			for(int i = 0; i < e; i++)
			{
				a = io.getInt();
				b = io.getInt();
				io.println(2 + " " + a + " " + b);
			}
		}	
		io.close();
	}
}