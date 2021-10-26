/*
Adeel Hussain
Generated: 2020-09-03, Updated: 2020-09-04
Creates a queue that is FIFO (Does not contain dequeue function!)
Input: Any Data
Reference: https://algs4.cs.princeton.edu/13stacks/DoublyLinkedList.java.html, 
https://algs4.cs.princeton.edu/43mst/Queue.java.html
*/

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyQueue<Data> implements Iterable<Data> //Queue, Generic datatype
{    
    
    private Node<Data> top;         //Creates top of queue
    private Node<Data> bottom;      //Creates bottom of queue
    private int counter;            //Counter

    private static class Node<Data> //Datatypes for queue
    {
        private Data data;
        private Node<Data> next;
    }

    public MyQueue()                //Constructor for double linked queue, initializes two nodes, top and bottom
    {
        top = null;                 //Create top of queue
        bottom = null;              //Create bottom of queue
        counter = 0;
    }

    public boolean isEmpty()        //If counter equals 0 then return true, else false
    {  
        return counter == 0;
    }

    public void enqueue(Data data) 
    {
        Node<Data> oldbottom = bottom;
        bottom = new Node<Data>();
        bottom.data = data;
        bottom.next = null;

        if (isEmpty()) 
        {
            top = bottom;
        }
        else           
        {
            oldbottom.next = bottom;
        }
        counter++;
    }

    public Data dequeue() 
    {
        Data data = top.data;
        top = top.next;
        counter--;
        
        if (isEmpty()) 
        {
            bottom = null;
        }

        return data;
    }
    
    public Iterator<Data> iterator()                        //Set up Iterator and use iterateQueue class
	{
		return new iterateQueue();
	}

    private class iterateQueue implements Iterator<Data>    //Define interateQueue class that implements Iterator
	{
		private Node<Data> current = top;                   //Create local variable "current" and point to top of queue
        private int index = 0;
        public boolean hasNext()                            //If queue has a next data value, return true else false
        {
			return index < counter;
		}

        public Data next() 
        {
            if(!hasNext())
            {
                throw new NoSuchElementException();
            }
            else
            {
                Data data = current.data;
                current = current.next;
                index++;
                return data;
            }	
		}
	}
}
