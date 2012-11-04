package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Stack;

import util.Graph;

/**
 * Main Word Ladder class.
 * @author Kamil Mrowiec <kam20@aber.ac.uk>
 */
public class WordLadder {

	private Graph<String> graph = new Graph<String>();
	
	/**
	 * Creates a graph based on given dictionary.
	 * @param dict
	 */
	public WordLadder(File dict){
		loadGraph(dict);
	}
	
	/**
	 * Default constructor.
	 */
	public WordLadder(){
	}
	
	/**
	 * Creates a graph based on the dictionary loaded from given file.
	 * The dictionary file should contain only words of the same length,
	 * each in new line.
	 * @param dict dictionary file
	 */
	public void loadGraph(File dict) {

		try {

			//Getting total number of lines in a file.
			LineNumberReader nReader = new LineNumberReader(new FileReader(dict));
			nReader.skip(Long.MAX_VALUE);
			int totalLines = nReader.getLineNumber();
			nReader.close();

			BufferedReader reader = new BufferedReader(new FileReader(dict));

			for (int i = 0; i < totalLines; i++) 
				graph.addNode(reader.readLine());
			

			reader.close();
			
			for (String parent : graph.getGraph().keySet()) {
				for (String word : graph.getGraph().keySet()) {
					if (areSimilar(parent, word)) {
						graph.addChild(parent, word);
					}
				}
			}

			
		}catch(FileNotFoundException ex){
			System.out.println("Missing dictionary file : file " + dict.getName() +" could not be found.");
		}
		catch (Exception ex) {
			System.out.println(ex);
		}
	}

	/**
	 * Checks whether the two words differ only by one letter,
	 * i. e. if they can form part of a ladder.
	 * @param w1 first word to compare
	 * @param w2 second word two compare
	 * @return
	 */
	private boolean areSimilar(String w1, String w2) {

		int diff = 0;
		//Counts how many different letters are there.
		//If more than one, loop brakes and false is returned.
		for (int i = 0; i < w1.length(); i++) {
			if (w1.charAt(i) == (w2.charAt(i)))
				continue;
			else{
				//Incrementing diff and checking if there is a point in continuing the loop.
				if(++diff > 1) return false;
			}
		}
		//If we got to this point, means there is only one difference, or none.
		return diff == 1;
	}

	/**
	 * Returns word ladder for given start and end words. (i. e. discovery mode)
	 * @param start 
	 * @param end 
	 * @return
	 */
	public String getLadder(String start, String end){
	
		if(!graph.contains(start)){
			return "The dictionary does not contain \""+start+"\"";
		}
		if(!graph.contains(end)){
			return "The dictionary does not contain \""+end+"\"";
		}
		LinkedList<String> route = graph.findRoute(start, end);
		return route==null ? "Such ladder does not exist" : route.toString();
	}
	

	/**
	 * Generation mode. Returns word ladder that starts with given word and
	 * consist of specified number of steps
	 * @param start start word
	 * @param steps number of steps in a ladder
	 * @return word ladder
	 * @throws Exception
	 */
	public String getLadder(String start, int steps){
		
		//First of all, checking whether given word exist in a dictionary.
		if(!graph.contains(start)){
			return "The dictionary does not contain \""+start+"\"";
		}
		
		//Stack that we will be temporarily using.
		Stack<String> stack = new Stack<String>();
		
		//List of explored nodes, to avoid loops.
		LinkedList<String> explored = new LinkedList<String>();
		
		//List of children that we can use;
		LinkedList<String> usable = new LinkedList<String>();
		
		//Inserting start word into the stack, i. e. getting the party started.
		stack.push(start);
		
		//If there is something in the stack, that means not every possibility was checked.
		while(!stack.isEmpty()){
			String item = stack.peek();
			
			//Emptying usable and adding to it all children
			//that are not in explored, neither already in stack.
			usable.clear();
			if(graph.getChildren(item).size()!=0)
				for(String child : graph.getChildren(item)){
					if(!(explored.contains(child)||stack.contains(child))){
						usable.add(child);
					}
						
			}
			//If there is no words we can use, it means we
			//are in a dead end.
			if(usable.isEmpty()){
				//The item leading to nowhere is added to explored.
				explored.add(item);
				//Removing that item from the stack.
				stack.pop();
				continue;
			}else{
				//If we have children that can be put into ladder,
				//we shuffle "usable", so that the results will be more interesting - 
				//two ladders from the same word will not necessarily be the same.
				Collections.shuffle(usable);
				//And pushing one of the words to the stack.
				stack.push(usable.getFirst());
			}
			//Checking if we already have enough words in the ladder.
			if(stack.size()>=steps) break;
			
		}
		
		return stack.isEmpty() ? "Such ladder does not exist" : stack.toString();
	}
	
	
}
