package deeplearningGroup1;

/**
 * @author Steven Rose
 * @version 0.8
 * @see Matrix
 */

import java.util.Scanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Jarvis {
	
	/**
	 * fileNameWordList is the name of the file holding all the words to spellcheck against
	 */
	private final String fileNameWordList = "words.txt";
	
	/**
	 * wordList is the list of words for checking spelling
	 */
	private String[] wordList;
	
	/**
	 * neuralLayers is a list of all the layers of the neural network, and holds the weights and biases of each layer
	 */
	private List<NeuralLayer> neuralLayers;
	
	/*
	public static void main(String args[]) {
		Essay essay = new Essay(1, 200, 4, "starter topic");
		essay.writeEssay("Hello , worldd !");
	}
	*/
	/**
	 * creates a new Jarvis, loads the wordList, sets the weights and biases of the neural network, then creates the neural network from those weights/biases
	 */
	public Jarvis() {
		
		loadWordList();
		
		String[] layerNames = {"input", "hidden 1", "output"};
		
		List <Matrix> weights = new ArrayList<>();
		weights.add(new Matrix(new double[][] {
			{.01, -.02, .03},
			{.04, .05, -.06},
			{.07, -.08, -.09},
			{-.10, .11, .12}
		}));
		weights.add(new Matrix(new double[][] {
			{.13, -.14, .15, -.16},
			{.13, -.14, .15, -.16},
			{.13, -.14, .15, -.16},
			{.13, -.14, .15, -.16},
			{.13, -.14, .15, -.16}
		}));
		
		List <Matrix> biases = new ArrayList<>();
		biases.add(new Matrix(new double[][] {
			{-1},
			{2},
			{-3},
			{4}
		}));
		
		biases.add(new Matrix(new double[][] {
			{5},
			{5},
			{5},
			{5},
			{5}
		}));
		
		 createNeuralNet(layerNames, weights, biases);
	}
	
	/**
	 * Creates a neural network based on the input id strings
	 * @param layerNames
	 * 		An array of the id's of each layer in the network
	 * @param idStrings
	 * 		A 2D List of the id's of each neuron in the network, arranged by layer
	 */
	public void createNeuralNet(String[] names, List< Matrix > weights, List< Matrix > biases) {
		if (weights.size() != biases.size()) {
			log("ERROR: # of weights and biases do not match:");
			log("# of weight matrices = " + weights.size());
			log("# of bias matrices = " + biases.size());
			System.exit(1);
		}
		
		neuralLayers = new ArrayList<>();
		for (int i = 0; i < weights.size(); i++) {
			neuralLayers.add(new NeuralLayer(names[i], weights.get(i), biases.get(i)));
		}
	}
	
	/**
	 * calculates the output of the neural network given an input
	 * @param input
	 * 		a column matrix of inputs to the neural network
	 * @return
	 * 		a column matrix of values from 0..1 for each output neuron
	 */
	public Matrix calculateOutput(Matrix input) {
		return calculateOutput(input, neuralLayers.size()-1);
	}
	
	/**
	 * Iteratively calculates output of each layer in the network based on the output of the previous layer
	 * @param input
	 * 		column matrix of input values from previous layer
	 * @param i
	 * 		which layer the output is being calculated for
	 * @return
	 * 		
	 */
	public Matrix calculateOutput(Matrix input, int i) {
		if (i == 0) {
			return neuralLayers.get(i).calculateSigmoid(input);
		} else {
			return neuralLayers.get(i).calculateSigmoid(calculateOutput(input, i-1));
		}
	}
	
	public Matrix deltaOutput(Matrix output, Matrix desiredOutput) {
		Matrix delC_delA = output.minus(desiredOutput).multiply(2);
		Matrix delSigma_delZ = new Matrix(output.getM(), 1);
		for (int i = 0; i < output.getM(); i++) {
			double a = output.get(i, 1);
			delSigma_delZ.set(i, 1, a*Math.exp(-a) / Math.pow(1+Math.exp(-a), 2));
		}
		return delC_delA.elementMultiply(delSigma_delZ);
	}
	
	public void deltaLayer(Matrix deltaLayerPlusOne) {
		
	}
	
	public double gradeEssay(Essay essay) {
		double spelling = checkSpelling(essay);
		double wordLimit = (checkWordLimit(essay) ? 1 : 0);
		double grammar = checkGrammar(essay);
		log("spelling = " + spelling);
		log("wordLimit = " + wordLimit);
		log("grammar = " + grammar);
		Matrix input = new Matrix(new double[][] {
				{spelling},
				{wordLimit},
				{grammar}
		});
		Matrix output = calculateOutput(input);
		output.print();
		Matrix desiredOutput = new Matrix(output.getM(), output.getN());
		desiredOutput.set(0, 0, .5);
		Matrix dC_da = new Matrix(output.getM(), output.getN());
		for (int i = 0; i < dC_da.getM(); i++) {
			for (int j = 0; j < dC_da.getN(); j++) {
				dC_da.set(i, j, 2*(output.get(i, j) - desiredOutput.get(i, j)));
			}
		}
		
		return output.get(0, 0);
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
			//log(essay.charAt(i) + " | " + punctuation.indexOf(essay.charAt(i)) + " | " + essay.substring(i-1, i).equals(" "));
			if (punctuation.indexOf(essay.charAt(i)) >= 0 && essay.substring(i-1, i).equals(" ")) {
				numBadGrammar++;
				//log("{" + essay.substring(i-1, i+1) + "}");
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
		//log("Essay: " + essay);
		String[] words = essay.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
		//log("Words:");
		
		double numMisspelled = 0;
		boolean isRealWord;
		for (int i = 0; i < words.length; i++) {
			isRealWord = Arrays.stream(wordList).anyMatch(words[i]::equals);
			if (!isRealWord) {
				numMisspelled++;
			}
			//log(words[i] + "; " + isRealWord + "; " + numMisspelled);
			
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
