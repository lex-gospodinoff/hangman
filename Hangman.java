import java.io.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Arrays;
import java.util.Scanner;



public class Hangman {

	String FILE_NAME = "wordList.txt";
	
	private String goal;
	private char[] currentGuessed;
	private char[] misses;
//	private int numGuesses;
	
	public Hangman() {
		this.goal = findNewGoalWord();
		this.misses = new char[6];
		for (int i = 0; i < 6; i++) {
			this.misses[i] = '~';
		}
		
//		this.numGuesses = 0;  //i don't think i need this
	}

	/*
	 * Reads FILE_NAME and chooses a random word from it.
	 */
	private String findNewGoalWord() {
		String newWord = "error";
		
		try {
			int numWords = 0;
			
			FileReader fileReader = new FileReader(FILE_NAME);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			while (bufferedReader.readLine() != null) {
				numWords++;
			}
			bufferedReader.close();
			
			int lineNum = ThreadLocalRandom.current().nextInt(numWords);
			
			fileReader = new FileReader(FILE_NAME);
			bufferedReader = new BufferedReader(fileReader);
			int i = 0;
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				if (i == lineNum) {
					newWord = line;
					break;
				}
				i++;
			}
			bufferedReader.close();
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		this.currentGuessed = new char[10];
		int j = 0;
		while (j < newWord.length()) {
			this.currentGuessed[j] = '_';
			j++;
		}
		while (j < 10) {
			this.currentGuessed[j] = ' ';
			j++;
		}
		
		return newWord;
	}
	
	/*
	 * Tests letter against goal. If correct, adds the letter to currentGuessed in all
	 * places it occurs. If incorrect, adds the letter to misses.
	 * (not finished)
	 */
	private void guess(char letter) {
		
		int index;
		index = this.goal.indexOf(letter);
		if (index == -1) {
			this.misses[5] = letter;
			Arrays.sort(this.misses);
		}
		while (index >= 0) {
			System.out.println(index);
			index = this.goal.indexOf(letter, index + 1);
		}
		printGame();
	}
	
	/*
	 * Prints out the current state of the game of hangman.
	 */
	private void printGame() {
		
		String printout = "";
		
		printout += "XXXXXXXXXXXXXXXXXXXXXXXXX\nX                       X\nX  ";
		for (int i = 0; i < currentGuessed.length; i++) {
			printout += currentGuessed[i] + " ";
		}
		printout += " X\nX                       X\nX  incorrect:           X\nX  ";
		
		printout += misses[0];
		for (int i = 1; i < 6; i++) {
			if (misses[i] == '~') {
				printout += "   ";
			}
			else {
				printout += ", " + misses[i];
			}
		}
		printout += "     X\nX                       X\n";
		printout += "X   ----                X\nX   |  |                X\n";
		
		printout += "X   |  " + (misses[0] == '~' ? " " : "O");
		printout += "                X\nX   | ";
		printout += (misses[2] == '~' ? " " : "/");
		printout += (misses[1] == '~' ? " " : "|");
		printout += (misses[3] == '~' ? " " : "\\");
		printout += "               X\nX   | ";
		printout += (misses[4] == '~' ? "  " : "/ ");
		printout += (misses[5] == '~' ? " " : "\\") + "               X\n";
		
		printout += "X   |                   X\nX  -+----               X\n";
		printout += "X                       X\nXXXXXXXXXXXXXXXXXXXXXXXXX\n\n";
		
		System.out.print(printout);
		
	}
	
	private String getGoal() {
		return this.goal;
	}
	
	private char[] getMisses() {
		return this.misses;
	}

	public static void main(String[] args) {
		Hangman exampleHangman = new Hangman();
		exampleHangman.printGame();
		System.out.println(exampleHangman.getGoal());
		Scanner in = new Scanner(System.in);
		String s = "";
		char letter;
		while (!s.equals("quit")){
			s = in.next().toLowerCase();
			if (s.matches("^[a-z]*")) {
				letter = s.charAt(0);
				exampleHangman.guess(letter);
			}
			else {
				System.out.println("Please enter a letter.");
			}
			

			
		}
		in.close();
		
	}

}
