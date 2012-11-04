package util;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Represents simple, unweighted, undirected graph.
 * The implementation is based on a HashMap, where every node of the graph is a key in the hashmap,
 * and its value is a LinkedList of all its children (i. e. nodes that are connected to it).
 * @author Kamil Mrowiec <kam20@aber.ac.uk>
 *
 * @param <T> Type of objects held in the graph
 */
public class Graph<T> {
	
	private HashMap<T, LinkedList<T>> graph = new HashMap<T, LinkedList<T>>();
	
	/**
	 * Adds single node to the graph.
	 * @param node
	 */
	public void addNode(T node){
		graph.put(node, new LinkedList<T>());
	}
	
	/**
	 * Connects two nodes with each other.
	 * @param one first node
	 * @param two second node
	 */
	public void addEdge(T one, T two){
		//Checking if those two are already connected.
		if(!graph.get(one).contains(two))
		graph.get(one).add(two);
		if(!graph.get(two).contains(one))
			graph.get(two).add(one);
	}
	
	/**
	 * Connects one word with another.
	 * NOTE: its only one-way connection, directed from parent to child.
	 * To connect two nodes with each other, check addEdge() method.
	 * @param parent
	 * @param child
	 */
	public void addChild(T parent, T child){
		graph.get(parent).add(child);
	}
	
	/**
	 * Return all children of given parent node.
	 * @param parent
	 * @return
	 */
	public LinkedList<T> getChildren(T parent){
		return graph.get(parent);
	}
	
	/**
	 * Finds the shortest route between two nodes of the graph, 
	 * using Breadth First Search algorithm.
	 * @param start root node
	 * @param end end node
	 * @return route
	 */
	public LinkedList<T> findRoute(T start, T end){
		
		//To keep track of the route, we have to assign parent to every node.
		HashMap<T, T> parents = new HashMap<T, T>();
		//Queue, holding items awaiting inspection.
		ConcurrentLinkedQueue<T> items = new ConcurrentLinkedQueue<T>();
		//Already explored nodes.
		LinkedList<T> explored = new LinkedList<T>();
		
		
		//Root node does not have a parent.
		parents.put(start, null); 
		
		//Adding root node to the queue - here the party starts.
		items.add(start);
		explored.add(start);
		
		T solution = null;
		
		//While there are still items in the queue.
		while(!items.isEmpty()){
			//If the head of the queue is what we are looking for, loop finishes.
			if(items.peek().equals(end)){
				solution = items.poll();
				break;
			}else{
				//Queueing all nodes that are connected to 
				//first element of the queue.
				for(T item : graph.get(items.peek())){
					//Checking whether the element has already been inspected,
					//to avoid loops.
					if(!explored.contains(item)){ 
						//Connecting current item with his parent,
						//so that it's possible to build the route.
						parents.put(item, items.peek()); 
						//Adding the item to queue and setting as explored.
						items.add(item); 						
						explored.add(item);
					}		
				}
				//Removing head of the queue.
				items.poll();	
			}	
			
		}
		//If solution is found.
		if(solution!=null){
			//Building the route, going from solution, to its parent, to its parents parent etc.
			LinkedList<T> route = new LinkedList<T>();
			T node = solution;
			while(node!=null){ //Node with parent set to null is root node.
				route.add(node);
				node = parents.get(node);
			}
			//Route is backwards, needs to be reverted.
			Collections.reverse(route);
		return route;
		}else{
			//When the solution was not found - route does not exist.
			return null;
		}
		
		
	}

	/**
	 * Returns the hashmap on which the graph is based.
	 * @return
	 */
	public HashMap<T, LinkedList<T>> getGraph() {
		return graph;
	}
	
	/**
	 * Returns true if graph contains given item.
	 * @param item
	 * @return
	 */
	public boolean contains(T item){
		return (graph.containsKey(item));
	}
	
}
