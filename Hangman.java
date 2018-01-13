import java.io.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Arrays;
import java.util.Scanner;



public class Hangman {

	String FILE_NAME = "wordList.txt";
	
	private String goal;
	private String currentGuessed;
	private char[] misses;
	private int numGuesses;
	
	public Hangman() {
		this.goal = findNewGoalWord();
		this.misses = new char[6];
		for (int i = 0; i < 6; i++) {
			this.misses[i] = ' ';
		}
		this.misses[0] = 'e'; //test code vvv
		this.misses[1] = 'd';
		this.misses[2] = 'a';
		this.misses[3] = 'l';
		this.misses[4] = 'm';
		this.misses[5] = 'o';
		
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
		
		this.currentGuessed = "";
		int j = 0;
		while (j < newWord.length()) {
			this.currentGuessed += "_ ";
			j++;
		}
		while (j < 10) {
			this.currentGuessed += "  ";
			j++;
		}
		
		return newWord;
	}
	
	private void printGame() {
		
		String printout = "";
		
		printout += "XXXXXXXXXXXXXXXXXXXXXXXXX\nX                       X\n";
		printout += "X  " + this.currentGuessed + " X\n";
		printout += "X                       X\nX  incorrect:           X\nX  ";
		
		//may need to be changed if ' ' turns out to sort before letters
		printout += misses[0];
		for (int i = 1; i < 6; i++) {
			if (misses[i] == ' ') {
				printout += "   ";
			}
			else {
				printout += ", " + misses[i];
			}
		}
		printout += "     X\nX                       X\n";
		printout += "X   ----                X\nX   |  |                X\n";
		
		printout += "X   |  " + (misses[0] == ' ' ? " " : "O");
		printout += "                X\nX   | ";
		printout += (misses[2] == ' ' ? " " : "/");
		printout += (misses[1] == ' ' ? " " : "|");
		printout += (misses[3] == ' ' ? " " : "\\");
		printout += "               X\nX   | ";
		printout += (misses[4] == ' ' ? "  " : "/ ");
		printout += (misses[5] == ' ' ? " " : "\\") + "               X\n";
		
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
		while (!s.equals("q")){
			s = in.next();
			System.out.println("here's " + s);
		}
		
	}

}
