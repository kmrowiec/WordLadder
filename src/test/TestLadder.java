package test;

import java.io.File;
import java.util.Random;
import static org.junit.Assert.*;

import org.junit.Test;

import main.WordLadder;

public class TestLadder {
	
	/**
	 * This test to work requires a dictionary with 5 letter words.
	 * This string contains the path to that dictionary.
	 */
	public static String DICT_5_LETTERS = "dict5.dat";

	private static WordLadder ladder = new WordLadder(new File(DICT_5_LETTERS));
	
	/**
	 * Testing generation mode.
	 * Since the best way to assert whether the ladder is correct is 
	 * for user to check it manually, that is how it works.
	 */
	@Test
	public void testGenerationMode(){
		System.out.println("Testing generation mode:");
		String[] genWords = {"beard", "mines", "plane"};
		Random r = new Random();
		for(String word : genWords){
			int length = r.nextInt(20)+2;
			System.out.println("Generating ladder with starting word \""+word+"\" and length "+length);
			try{
				System.out.println(ladder.getLadder(word, length));
			}catch(Exception x){
				System.out.println(x);
			}
		}
	}
	
	/**
	 * Same situation as testing generation mode.
	 */
	@Test
	public void testDiscoveryMode(){
		System.out.println("Testing discovery mode");
		String[][] discWords = {{"beard", "bread"}, {"prone", "plane"}, {"moles", "greed"}};
		for(String[] words : discWords){
			System.out.println("Generating ladder from \""+words[0]+"\" to \""+words[1]+"\"");
			try{
				System.out.println(ladder.getLadder(words[0], words[1]));
			}catch(Exception x){
				System.out.println(x);
			}
		}
	}
	
	/**
	 * Testing whether we get null, if user asks for a ladder too big to be generated.
	 */
	@Test
	public void testTooLongGenerationMode(){
		try{
			assertEquals(ladder.getLadder("bread", 5000), "Such ladder does not exist");
		}catch(Exception x){
			System.out.println(x);
		}
	}
	
	/**
	 * Testing whether null will be returned when trying to create a ladder 
	 * between two words that cannot be connected.
	 */
	@Test
	public void testNonExistingPath(){
		try{
			assertEquals(ladder.getLadder("vodka", "vomit"), "Such ladder does not exist"); // what an irony
		}catch(Exception x){
			System.out.println(x);
		}
	}
	
	/**
	 * Testing inputting words that do not exist in a dictionary.
	 * @throws Exception
	 */
	@Test 
	public void testNonExistingWords() throws Exception{
		String expected = "The dictionary does not contain";
		ladder.getLadder("asfdgh", 5);
		assertTrue(ladder.getLadder("asfdgh", 5).contains(expected));
		assertTrue(ladder.getLadder("asfdgh", "bones").contains(expected));
		assertTrue(ladder.getLadder("bored", "qwert").contains(expected));
		
	}
	
}
