package main;

import java.io.File;
import java.util.Scanner;

/**
 * Provides console interface for WordLadder program.
 * @author Kamil Mrowiec <kam20@aber.ac.uk>
 *
 */
public class RunWordLadder {

	//Core module of the program.
	private static WordLadder ladder = null;
	
	//Name of the dictionary file, to be displayed in menu.
	private static String dictFileName = "Dictionary not loaded yet";

	
	/**
	 * Asks user to choose the dictionary file, then displays the main menu and takes appropriate actions, 
	 * unless the user chooses to quit the program.
	 * @param args
	 */
	public static void main(String args[]) {

		loadDictionary();

		int choice = 0;
		do {
			choice = showMainMenu();
			switch (choice) {
			case 0:
				return;
			case 1:
				generationMode();
				break;
			case 2:
				discoveryMode();
				break;
			case 3:
				loadDictionary();
				break;
			default:
				System.out.println("Incorrect option. Try once again.");
			}
		} while (true);

	}

	/**
	 * Displays main menu.
	 * @return user's choice
	 */
	public static int showMainMenu() {
		Scanner in = new Scanner(System.in);
		
		System.out.println("Welcome to WordLadder program.");
		System.out.println("MENU:");
		System.out.println("1. Generation mode.");
		System.out.println("2. Discovery mode.");
		System.out.println("3. Change dictionary file. (currently: "+dictFileName+")");
		System.out.println("0. Quit.");

		int option = in.nextInt();

		return option;
	}

	/**
	 * Runs discovery mode.
	 */
	public static void discoveryMode() {
		Scanner in = new Scanner(System.in);

		String w1, w2;
		System.out.println("Discovery mode.");
		System.out.println("Give starting word:");
		w1 = in.nextLine();
		System.out.println("Give ending word:");
		w2 = in.nextLine();

		try {
			String result = ladder.getLadder(w1, w2);
			System.out
					.println(result != null ? "Word ladder generated successfully: \n"
							+ result
							: "Such ladder cannot be generated.");
		} catch (Exception ex) {
			System.out.println(ex);
		}

	}

	/**
	 * Runs generation mode.
	 */
	public static void generationMode() {
		String word;
		int n;

		Scanner in = new Scanner(System.in);

		System.out.println("Generation mode.");
		System.out.println("Give starting word:");
		word = in.nextLine();
		System.out.println("Give number of steps:");
		n = in.nextInt();

		try {

			String result = ladder.getLadder(word, n).toString();
			System.out
					.println(result != null ? "Word ladder generated successfully: \n"
							+ result
							: "Such ladder cannot be generated.");
		} catch (Exception ex) {
			System.out.println(ex);
		}

	}

	/**
	 * Asks the user for dictionary file and loads it.
	 */
	public static void loadDictionary() {
		Scanner in = new Scanner(System.in);
		System.out
				.println("The dictionary file must contain only words with identical length, \neach in new line.");
		System.out.println("Please give a path to the dictionary file:");

		File dict = null;
		do {
			dict = new File(in.nextLine());
			if (dict.exists())
				break;
			else
				System.out.println("Such file does not exist. Try again.");
		} while (true);

		ladder = new WordLadder(dict);
		dictFileName = dict.getName();
	}

}
