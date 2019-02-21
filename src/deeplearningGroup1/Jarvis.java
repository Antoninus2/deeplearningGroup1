package deeplearningGroup1;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Jarvis {
	
	private final String fileNameWordList = "words.txt";
	private String[] wordList;
	
	
	public static void main(String args[]) {
		Jarvis j = new Jarvis();
		j.loadWordList();
		j.printWords(5);
	}
	
	public Jarvis() {
		
	}
	
	public void checkSpelling(Essay essay) {
		
	}
	
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
	
	public void printWords(int n) {
		for (int i = 0; i < n; i++) {
			System.out.println(wordList[i]);
		}
	}

}
