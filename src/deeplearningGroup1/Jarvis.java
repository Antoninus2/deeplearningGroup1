package deeplearningGroup1;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Jarvis {
	
	private final String fileNameWordList = "words.txt";
	private String[] wordList;
	
	
	public static void main(String args[]) {
		Essay essay = new Essay(1, 200, 4, "starter topic");
		essay.writeEssay("Hello, worldd !");
		Jarvis j = new Jarvis();
		j.loadWordList();
		
		System.out.println("\nMisspellings:");
		System.out.println(j.checkSpelling(essay));
		
		System.out.println("\nWithin word Limit:");
		System.out.println(j.checkWordLimit(essay));
		
		System.out.println("\nChecking Grammar:");
		System.out.println(j.checkGrammar(essay));
	}
	
	public Jarvis() {
		
	}
	
	// Returns number of grammatical mistakes
	public int checkGrammar(Essay e) {
		String essay = e.getEssay();
		int numBadGrammar = 0;

		// Check for spaces before punctuation
		String punctuation = ",.;:?/!";
		for (int i = 1; i < essay.length(); i++) {
//			System.out.println(essay.charAt(i) + " | " + punctuation.indexOf(essay.charAt(i)) + " | " + essay.substring(i-1, i).equals(" "));
			if (punctuation.indexOf(essay.charAt(i)) >= 0 && essay.substring(i-1, i).equals(" ")) {
				numBadGrammar++;
				System.out.println("{" + essay.substring(i-1, i+1) + "}");
			}
		}
		return numBadGrammar;
	}
	
	// Returns true if within word limit, false otherwise
	public boolean checkWordLimit(Essay e) {
		String essay = e.getEssay();
		return essay.length() >= e.getLength()[0] && essay.length() < e.getLength()[1];
	}
	
	// Returns number of misspelled words
	public int checkSpelling(Essay e) {
		String essay = e.getEssay();
		System.out.println("Essay: " + essay);
		String[] words = essay.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
		System.out.println("Words:");
		
		int numMisspelled = 0;
		boolean isRealWord;
		for (int i = 0; i < words.length; i++) {
			isRealWord = Arrays.stream(wordList).anyMatch(words[i]::equals);
			if (!isRealWord) {
				numMisspelled++;
			}
			System.out.println(words[i] + "; " + isRealWord + "; " + numMisspelled);
			
		}
		
		return numMisspelled;
	}
	
	// Loads dictionary of words from text file as String[]
	public void loadWordList() {
		try {
			Scanner sc = new Scanner(new File(fileNameWordList));
			List<String> lines = new ArrayList<String>();
			while (sc.hasNextLine()) {
				lines.add(sc.nextLine());
			}
			sc.close();
			
			wordList = lines.toArray(new String[0]);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// Purely for debugging purposes
	public void printWords(int n) {
		for (int i = 0; i < n; i++) {
			System.out.println(wordList[i]);
		}
	}

}
