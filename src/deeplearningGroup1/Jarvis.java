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
	private List<NeuralLayer> neuralLayers;
	
	public static void main(String args[]) {
		Essay essay = new Essay(1, 200, 4, "starter topic");
		essay.writeEssay("Hello , worldd !");
		Jarvis j = new Jarvis();
		j.loadWordList();
		double spelling = j.checkSpelling(essay);
		double wordLimit = (j.checkWordLimit(essay) ? 1 : 0);
		double grammar = j.checkGrammar(essay);
		j.log("spelling = " + spelling);
		j.log("wordLimit = " + wordLimit);
		j.log("grammar = " + grammar);
		Matrix input = new Matrix(new double[][] {
				{spelling},
				{wordLimit},
				{grammar}
		});
		Matrix result = j.calculateOutput(input, j.neuralLayers.size()-1);
		result.print();
		
	}
	
	public Jarvis() {
		
		List <Matrix> weights = new ArrayList<>();
		weights.add(new Matrix(new double[][] {
			{.01, .02, .03},
			{.04, .05, .06},
			{.07, .08, .09},
			{.10, .11, .12}
		}));
		weights.add(new Matrix(new double[][] {
			{.13, .14, .15, .16}
		}));
		
		List <Matrix> biases = new ArrayList<>();
		biases.add(new Matrix(new double[][] {
			{1},
			{2},
			{3},
			{4}
		}));
		
		biases.add(new Matrix(new double[][] {
			{5}
		}));
		
		 createNeuralNet(weights, biases);
	}
	
	/**
	 * Creates a neural network based on the input id strings
	 * @param layerNames
	 * 		An array of the id's of each layer in the network
	 * @param idStrings
	 * 		A 2D List of the id's of each neuron in the network, arranged by layer
	 */
	public void createNeuralNet(List< Matrix > weights, List< Matrix > biases) {
		if (weights.size() != biases.size()) {
			log("ERROR: # of weights and biases do not match:");
			log("# of weight matrices = " + weights.size());
			log("# of bias matrices = " + biases.size());
			System.exit(1);
		}
		
		neuralLayers = new ArrayList<>();
		for (int i = 0; i < weights.size(); i++) {
			neuralLayers.add(new NeuralLayer(weights.get(i), biases.get(i)));
		}
	}
	
	public Matrix calculateOutput(Matrix input, int i) {
		if (i == 0) {
			return neuralLayers.get(i).calculateSigmoid(input);
		} else {
			return neuralLayers.get(i).calculateSigmoid(calculateOutput(input, i-1));
		}
	}
	
	/**
	 * Returns number of grammatical mistakes
	 * 
	 * @param e
	 * 		Essay to be graded
	 * @return number of grammatical mistakes made in essay
	 */
	public double checkGrammar(Essay e) {
		String essay = e.getEssay();
		double numBadGrammar = 0;

		// Check for spaces before punctuation
		String punctuation = ",.;:?/!";
		for (int i = 1; i < essay.length(); i++) {
//			System.out.println(essay.charAt(i) + " | " + punctuation.indexOf(essay.charAt(i)) + " | " + essay.substring(i-1, i).equals(" "));
			if (punctuation.indexOf(essay.charAt(i)) >= 0 && essay.substring(i-1, i).equals(" ")) {
				numBadGrammar++;
				System.out.println("{" + essay.substring(i-1, i+1) + "}");
			}
		}
		return numBadGrammar / essay.length();
	}
	
	/**
	 * Checks if essay is within word limit
	 * 
	 * @param e
	 * 		Essay to be graded
	 * @return true if within word limit, false otherwise
	 */
	public boolean checkWordLimit(Essay e) {
		String essay = e.getEssay();
		return essay.length() >= e.getLength()[0] && essay.length() < e.getLength()[1];
	}
	
	/**
	 * Returns number of misspelled words
	 * @param e
	 * 		Essay to be graded
	 * @return an integer of the number of misspelled words
	 */
	public double checkSpelling(Essay e) {
		String essay = e.getEssay();
		System.out.println("Essay: " + essay);
		String[] words = essay.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
		System.out.println("Words:");
		
		double numMisspelled = 0;
		boolean isRealWord;
		for (int i = 0; i < words.length; i++) {
			isRealWord = Arrays.stream(wordList).anyMatch(words[i]::equals);
			if (!isRealWord) {
				numMisspelled++;
			}
			System.out.println(words[i] + "; " + isRealWord + "; " + numMisspelled);
			
		}
		
		return numMisspelled / words.length;
	}
	
	/**
	 * Loads dictionary of words from text file as String[]
	 */
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
			e.printStackTrace();
		}
	}
	
	/**
	 * Purely for debugging purposes
	 * @param n
	 * 		The number of words to print from the wordList
	 */
	public void printWords(int n) {
		for (int i = 0; i < n; i++) {
			System.out.println(wordList[i]);
		}
	}
	
	/**
	 * Prints a String to the console
	 * @param msg
	 * 		message to be printed
	 */
	private void log(String msg) {
		System.out.println(msg);
	}

}
