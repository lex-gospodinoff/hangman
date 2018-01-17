
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Arrays;
import java.util.Scanner;



public class Hangman {

	String FILE_NAME = "wordList.txt";
	
	private String goal;
	private char[] currentGuessed;
	private char[] misses;
	
	public Hangman() {
		this.goal = findNewGoalWord();
		this.misses = new char[6];
		for (int i = 0; i < 6; i++) {
			this.misses[i] = '~';
		}
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
			
			boolean foundWord = false;
			while (!foundWord) {
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
				if (newWord.matches("\\p{Alpha}{0,10}")) foundWord = true; 
			}
			
			
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
	 * places it occurs. If incorrect, adds the letter to misses. Checks to see whether 
	 * the game has been won or lost and prints out an appropriate message.
	 */
	private void guess(char letter) {
		
		int index;
		index = this.goal.indexOf(letter);
		if (index == -1) {
			if (Arrays.binarySearch(this.misses, letter) < 0) {
				this.misses[5] = letter;
				Arrays.sort(this.misses);
			}
		}
		while (index >= 0) {
			this.currentGuessed[index] = letter;
			index = this.goal.indexOf(letter, index + 1);
		}
		
		if (hasWon()) {
			printGame();
			System.out.println("Congratulations!");
		}
		else if (hasLost()) {
			for (int i = 0; i < this.goal.length(); i++) {
				this.currentGuessed[i] = this.goal.charAt(i);
			}
			printGame();
			System.out.println("Better luck next time!");
		}
		else {
			printGame();
		}
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
		
		printout += misses[0] == '~' ? " " : misses[0];
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
		printout += misses[2] == '~' ? " " : "/";
		printout += misses[1] == '~' ? " " : "|";
		printout += misses[3] == '~' ? " " : "\\";
		printout += "               X\nX   | ";
		printout += misses[4] == '~' ? "  " : "/ ";
		printout += (misses[5] == '~' ? " " : "\\") + "               X\n";
		
		printout += "X   |                   X\nX  -+----               X\n";
		printout += "X                       X\nXXXXXXXXXXXXXXXXXXXXXXXXX\n\n";
		
		System.out.print(printout);
		
	}
	
	private boolean hasWon() {
		for (char value : this.currentGuessed) {
			if (value == '_') return false;
		}
		return true;
	}
	
	private boolean hasLost() {
		for (char value : this.misses) {
			if (value == '~') return false;
		}
		return true;
	}
	
	
	public static void main(String[] args) {
		
		Hangman hangman; 
		Scanner in = new Scanner(System.in);
		String s = "";
		char letter;
		boolean quit = false;
		
		do {
			hangman = new Hangman();
			hangman.printGame();
			
			while (!hangman.hasWon() & !hangman.hasLost()){
				s = in.next().toLowerCase();
				if (s.matches("^[a-z]*")) {
					letter = s.charAt(0);
					hangman.guess(letter);
				}
				else {
					System.out.println("Please enter a letter.");
				}
			}
			
			System.out.println("Would you like to play again?");
			
			s = in.next().toLowerCase();
			while (s.charAt(0) != 'y' & s.charAt(0) != 'n') {
				System.out.println("Please answer 'yes' or 'no'.");
				s = in.next().toLowerCase();
			}
			if (s.charAt(0) == 'n') {
				System.out.println("Thanks for playing!");
				quit = true;
			}
			
		} while (!quit);
		in.close();		
	}

}
