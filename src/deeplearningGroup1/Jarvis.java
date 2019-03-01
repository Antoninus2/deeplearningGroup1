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
	private NeuralNet ai;
	
	public static void main(String args[]) {
		Essay essay = new Essay(1, 200, 4, "starter topic");
		essay.writeEssay("Hello, worldd !");
		Jarvis j = new Jarvis();
		j.loadWordList();
		int spelling = j.checkSpelling(essay);
		int wordLimit = (j.checkWordLimit(essay) ? 1 : 0);
		int grammar = j.checkGrammar(essay);
		
		j.ai.print();
	}
	
	public Jarvis() {
		
		/**
		 * layerNames holds the IDs of each of the layers in the network
		 * 
		 * idStrings holds the ids of each neuron in the network, arranged by layer, with input first
		 * 
		 * the size of these two arrays must be the same
		 */
		String[] layerNames = {
				"input layer",
				"hidden layer 1",
				"output layer"
		};
		List< String[] > idStrings = new ArrayList<>();
		idStrings.add(new String[] {
				"in - misspell",
				"in - grammar",
				"in - length"
		});
		idStrings.add(new String[] {
				"hid - 1",
				"hid - 2",
				"hid - 3",
				"hid - 4"
		});
		idStrings.add(new String[] {
				"output"
		});
		
		/**
		 * weights is the weight of each input connection per neuron, arranged by layer
		 * 		# col in each double[] = # neurons in previous layer
		 * 		# row in each double[] = # neurons in current layer
		 */
		List< List< double[] >> weights = new ArrayList<>();
		weights.add(new ArrayList<>());
		weights.get(0).add(new double[] {.01, .02, .03});
		weights.get(0).add(new double[] {.04, .05, .06});
		weights.get(0).add(new double[] {.07, .08, .09});
		weights.get(0).add(new double[] {.10, .11, .12});
		weights.add(new ArrayList<>());
		weights.get(1).add(new double[] {.13, .14, .15, 16});
		
		createNeuralNet(layerNames, idStrings, weights);
	}
	
	/**
	 * Creates a neural network based on the input id strings
	 * @param layerNames
	 * 		An array of the id's of each layer in the network
	 * @param idStrings
	 * 		A 2D List of the id's of each neuron in the network, arranged by layer
	 */
	
	public void createNeuralNet(String[] layerNames, List< String[] > idStrings, List< List< double[] >> weights) {
		
		if (layerNames.length != idStrings.size()) {
			log("ERROR: length of layerNames != size of idStrings");
			log("Exiting...");
			System.exit(1);
		}
		
		if (weights != null) {
			// TODO: check dimensions of weights vs everything else
		}
		
		/**
		 * neuronLayerList is a 2D array of neurons, arranged by layer, with input first
		 * 
		 * connectionInputList is a constantly changing list of the connections each neuron has to the layer before it, if any
		 * 
		 * connectionOutputList is a constantly changing list of the connections each neuron has to the layer after it, if any
		 * 
		 * layers is a list of the neural layers of the network
		 */
		List< List< Neuron >> neuronLayerList = new ArrayList<>();
		List< Connection > connectionInputList;
		List< Connection > connectionOutputList;
		List< List< Connection >> previousOutputConnections, currentInputConnections;
		List< NeuralNetLayer > layers = new ArrayList<>();
		Neuron currentN, nextN;
		
		/**
		 * Iterates through each id in idStrings and makes a new neuron based on that id, storing it in neuronLayerList
		 */
		log("idStrings size: " + idStrings.size());
		for (int i = 0; i < idStrings.size(); i++) {
			String[] idList = idStrings.get(i);
			log("\ni = " + i);
			log("Adding to list\n");
			neuronLayerList.add(new ArrayList<>());
			for (int j = 0; j < idList.length; j++) {
				neuronLayerList.get(i).add(new Neuron(idList[j]));
				System.out.println(idList[j]);
			}
		}
		
		/**
		 * Iterates through each neuron in each layer, making the connections to the neurons before/after it
		 * 		NOTE: The output connections of a layer directly correspond to the input connections of the next layer
		 */
		log("\nMaking Connections...");
		previousOutputConnections = null;
		for (int i = 0; i < neuronLayerList.size(); i++) {
			log("layer # = " + i);
			List< Neuron > layer = neuronLayerList.get(i);
			currentInputConnections = flipConnections(previousOutputConnections);
			previousOutputConnections = new ArrayList<>();
			for (int j = 0; j < layer.size(); j++) {
				currentN = layer.get(j);
				log("current Neuron = " + currentN.getID());
				connectionInputList = new ArrayList<>();
				connectionOutputList = new ArrayList<>();
				if (currentInputConnections == null) {
					connectionInputList = null;
				} else {
					connectionInputList = currentInputConnections.get(j);
				}
				currentN.setInputConnections(connectionInputList);

				if (i < neuronLayerList.size()-1) {
					for (int k = 0; k < neuronLayerList.get(i+1).size(); k++) {
						nextN = neuronLayerList.get(i+1).get(k);
						log("\tnext Neuron = " + nextN.getID());
						if (weights == null) {
							connectionOutputList.add(new Connection(currentN, nextN));
						}else {
							connectionOutputList.add(new Connection(currentN, nextN, (weights.get(i).get(k))[j]));
						}
					}
				} else {
					connectionOutputList = null;
				}
				currentN.setOutputConnections(connectionOutputList);
				previousOutputConnections.add(connectionOutputList);
				log("Size of prevOutput = " + previousOutputConnections.size());
			}
		}
		
		/**
		 * Creates the network layers based on the neurons from neuronLayerList
		 */
		log("\nMaking Network Layers...");
		for (int i = 0; i < layerNames.length; i++) {
			layers.add(new NeuralNetLayer(layerNames[i], neuronLayerList.get(i)));
		}
		
		/**
		 * Creates the network based on the network layers
		 */
		log("\nMaking Network...");
		if (layerNames.length > 2) {
			log("# of hidden layers = " + layers.subList(1, layers.size()-1).size());
			ai = new NeuralNet("ai Neural Network", layers.get(0), layers.subList(1, layers.size()-1), layers.get(layers.size()-1));
		} else {
			ai = new NeuralNet("ai Neural Network", layers.get(0), layers.get(layers.size()));
		}
		log("Completed making AI Neural Network\n");
	}
	
	public List< List< Connection >> flipConnections(List< List< Connection >> input) {
		if (input == null) return null;
		List< List< Connection >> output = new ArrayList<>();
		int oldx = input.get(0).size();
		int oldy = input.size();
		
		for (int y = 0; y < oldx; y++) {
			output.add(new ArrayList<>());
			for (int x = 0; x < oldy; x++) {
				output.get(y).add(input.get(x).get(y));
			}
		}
		return output;
	}
	
	/**
	 * Returns number of grammatical mistakes
	 * 
	 * @param e
	 * 		Essay to be graded
	 * @return number of grammatical mistakes made in essay
	 */
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
			e.printStackTrace();
		}
	}
	
	// Purely for debugging purposes
	public void printWords(int n) {
		for (int i = 0; i < n; i++) {
			System.out.println(wordList[i]);
		}
	}
	
	private void log(String msg) {
		System.out.println(msg);
	}

}
