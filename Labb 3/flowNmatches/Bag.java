/*
* Adeel Hussain & Rakin Ali
* Generated: 2021-10-26, Updated: 2021-10-26
* Input: Any Items
* Reference: https://algs4.cs.princeton.edu/13stacks/Bag.java.html
*/

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Bag<Item> implements Iterable<Item>
{

  private Node<Item> first;   //The start of the bag
  private int n;              //Number of elements in the bag
  
  //Helper linked list class
  private static class Node<Item>
  {
    private Item item;
    private int weight;
    private Node<Item> next;
  }
  
  //Constructur to start a bag
  public Bag()
  {
    first = null;
    n = 0;
  }
  
  // Boolean that returns if the bag is empty
  public boolean isEmpty()
  {
    return first == null;
  }

  //Returns the size of the bag
  public int size()
  {
    return n;
  }
	
  // Adds an item to the bag
  // The item added becomes the first on the linked list
	public void add(Item item, int weight)
	{
    Node<Item> oldfirst = first;
		first = new Node<Item>();
		first.item = item;
		first.weight = weight;
		first.next = oldfirst;
		n++;
	}

	public Iterator<Item> iterator()  
	{
		return new LinkedIterator(first);  
	}

  private class LinkedIterator implements Iterator<Item> 
  {
		private Node<Item> current;

		public LinkedIterator(Node<Item> first) 
		{
			current = first;
		}

		public boolean hasNext()  
		{ 
			return current != null;                     
		}

		public Item next() 
		{
			if (!hasNext()) throw new NoSuchElementException();
			Item item = current.item;
			//int weight = current.weight;
			current = current.next; 
			return item;
		}
  }
}