package deeplearningGroup1;

import java.util.Scanner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Jarvis is the neural network class which grades essays through the use of a
 * trained neural network, consisting of a list of Neural Layers with associated weights and biases
 * @author Steven Rose
 * @version 1.0
 * @see NeuralLayer
 * @see Essay
 */
public class Jarvis {
	
	/**
	 * fileNameWordList is the name of the file holding all the words to spell check against
	 */
	private final String fileNameWordList = "words.txt";
	
	private final static String essayTrainingFileName = "resources/Essay Data/training_set_rel3_set1.xlsx";
	
	private final static String jarvisWeightsBiases = "neural_net_weights_biases.xlsx";
	
	public final static boolean debugging = false;
	
	/**
	 * wordList is the list of words for checking spelling
	 */
	private String[] wordList;
	
	/**
	 * neuralLayers is a list of all the layers of the neural network, and holds the weights and biases of each layer
	 */
	private List<NeuralLayer> neuralLayers;
	
	public static void main(String[] args) {
		long startTime = System.nanoTime();
		Jarvis j = new Jarvis();
		j.log("----------START----------");
		Essay essay = new Essay(1, 100, 5, "topic");
		essay.writeEssay("This is my tesst essay .");
		
		try {
			j.train(essayTrainingFileName, 100);
		} catch (IOException e) {
			e.printStackTrace();
		}
		j.log("----------END----------");
		long endTime = System.nanoTime();
		System.out.println("Time Elapsed = " + ((endTime - startTime) / 1e9) + " s");
	}
	
	/**
	 * creates a new Jarvis, loads the wordList, sets the weights and biases of the neural network based on data from a file, then creates the neural network from those weights/biases
	 */
	public Jarvis() {
		
		loadWordList();
		
		String[] layerNames = {"input", "hidden 1", "output"};
		/* Current size of neural network:
		 * 		3 inputs
		 * 		5 hidden
		 * 		4 outputs
		 */
		
		List<List<Matrix>> savedData = new ArrayList<>();
		try {
			savedData = loadFromExcel(jarvisWeightsBiases);
		} catch (IOException e) {
			e.printStackTrace();
		}
		List <Matrix> weights = savedData.get(0);
		List <Matrix> biases = savedData.get(1);
		
		/*
		int randMax = 10;
		weights = new ArrayList<>();
		weights.add(Matrix.random(5, 3, randMax));
		weights.add(Matrix.random(4, 5, randMax));
		biases = new ArrayList<>();
		biases.add(Matrix.random(5, 1, randMax));
		biases.add(Matrix.random(4, 1, randMax));
		*/
		
		 createNeuralNet(layerNames, weights, biases);
	}

	private List <List <Matrix>> loadFromExcel(String fileName) throws IOException {
		FileInputStream fis = new FileInputStream(new File(fileName));
		Workbook workbook = new XSSFWorkbook(fis);
		Sheet sheet;
		Row row;
		
		List <Matrix> weights = new ArrayList<>();
		List <Matrix> biases = new ArrayList<>();
		double[][] data;
		int x,y;
		for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
			sheet = workbook.getSheetAt(i);
			y = sheet.getLastRowNum()+1;
			x = sheet.getRow(0).getLastCellNum();
			data = new double[y][x];
			for (int j = 0; j < y; j++) {
				row = sheet.getRow(j);
				for (int k = 0; k < x; k++) {
					data[j][k] = row.getCell(k).getNumericCellValue();
				}
			}
			
			if (i % 2 == 0) {
				weights.add(new Matrix(data));
			} else {
				biases.add(new Matrix(data));
			}
		}
		
		workbook.close();
		fis.close();
		
		List <List <Matrix>> result = new ArrayList<>();
		result.add(weights);
		result.add(biases);
		return result;
	}
	
	private void saveToExcel(String fileName) throws IOException {
		Workbook workbook = new XSSFWorkbook();
		
		Sheet sheet;
		Row row;
		Cell cell;
		Matrix weights, biases;
		for (int i = 0; i < neuralLayers.size(); i++) {
			//Save the Weights for this layer
			sheet = workbook.createSheet("weights " + i);
			weights = neuralLayers.get(i).getWeights();
			for (int j = 0; j < weights.getM(); j++) {
				row = sheet.createRow(j);
				for (int k = 0; k < weights.getN(); k++) {
					cell = row.createCell(k);
					cell.setCellValue(weights.get(j, k));
				}
			}
			
			//Save the Biases for this layer
			sheet = workbook.createSheet("biases " + i);
			biases = neuralLayers.get(i).getBias();
			for (int j = 0; j < biases.getM(); j++) {
				row = sheet.createRow(j);
				for (int k = 0; k < biases.getN(); k++) {
					cell = row.createCell(k);
					cell.setCellValue(biases.get(j, k));
				}
			}
		}
		
		FileOutputStream fileOut = new FileOutputStream(fileName);
        workbook.write(fileOut);
        fileOut.close();
        workbook.close();
	}
	
	/**
	 * Creates and populates the neuralLayers List
	 * @param names
	 * 		Array of strings for ids of each neural layer
	 * @param weights
	 * 		List of Matrix of weights from one layer to the next, each Matrix of size [output x input]
	 * @param biases
	 * 		List of column Matrix of biases for each layer, each bias of size [output x 1]
	 */
	private void createNeuralNet(String[] names, List< Matrix > weights, List< Matrix > biases) {
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
	 * 		Column Matrix of inputs to the neural network
	 * @return
	 * 		Column Matrix of values from 0..1 for each output neuron
	 */
	public Matrix calculateOutput(Matrix input) {
		return calculateOutput(input, neuralLayers.size()-1);
	}
	
	/**
	 * Iteratively calculates output of each layer in the network based on the output of the previous layer
	 * @param input
	 * 		Column Matrix of input values from previous layer
	 * @param i
	 * 		Which layer the output is being calculated for
	 * @return
	 * 		Column Matrix of output for current layer
	 */
	private Matrix calculateOutput(Matrix input, int i) {
		if (i == 0) {
			return neuralLayers.get(i).calculateSigmoid(input);
		} else {
			return neuralLayers.get(i).calculateSigmoid(calculateOutput(input, i-1));
		}
	}
	
	/**
	 * Grades an essay
	 * @param essay
	 * 		Essay to be graded
	 * @return
	 * 		Grade for the essay
	 */
	public String gradeEssay(Essay essay) {
		Matrix input = getInputMatrix(essay);
		Matrix output = calculateOutput(input);
		output.print();
		log("\ninput:");
		input.print();
		log("\nweights:");
		neuralLayers.get(0).getWeights().print();
		log("\nbiases:");
		neuralLayers.get(0).getBias().print();
		log("\nweighted input:");
		neuralLayers.get(0).getWeightedInput().print();
		log("\nactivations:");
		neuralLayers.get(0).getActivations().print();
		log("\nlayers: " + neuralLayers.size());
		
		
		String[] grades = {"A", "B", "C", "D", "F"};
		String grade = "No Grade";
		double gradeNumber = 0;
		for (int i = 0; i < output.getM(); i++) {
			if (output.get(i, 0) > gradeNumber) {
				grade = grades[i];
				gradeNumber = output.get(i, 0);
			}
		}
		return grade;
	}
	
	private void train(String fileName, int batchSize) throws IOException {
		
		FileInputStream fis = new FileInputStream(new File(fileName));
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheetAt(0);
		
		Iterator<Row> rowIter = sheet.iterator();
		Row row;
		int rowNum = 0;
		int maxRowNum = sheet.getLastRowNum();
		log("maxRowNum = " + maxRowNum);

		for (int batch = 0; batch < maxRowNum-1; batch += batchSize) {
			int batchSizeActual = Math.min(batchSize, maxRowNum - batch);
			Essay[] essays = new Essay[batchSizeActual];
			Matrix[] grades = new Matrix[batchSizeActual];
			double numGrade;
			while (rowIter.hasNext() && rowNum < (batch + batchSizeActual)) {
				row = rowIter.next();
				essays[rowNum - batch] = new Essay(1, Integer.MAX_VALUE, Integer.MAX_VALUE, "training");
				essays[rowNum - batch].writeEssay(row.getCell(2).getStringCellValue());
				log("essay =\n" + essays[rowNum - batch].getEssay());
				numGrade = row.getCell(6).getNumericCellValue();
				grades[rowNum - batch] = new Matrix(neuralLayers.get(neuralLayers.size()-1).getBias().getM(), 1);
				if (numGrade > 9) {
					grades[rowNum - batch].set(0, 0, 1); //A
				} else if (numGrade > 6) {
					grades[rowNum - batch].set(1, 0, 1); //B
				} else if (numGrade > 3) {
					grades[rowNum - batch].set(2, 0, 1); //C
				} else {
					grades[rowNum - batch].set(3, 0, 1); //D
				}
				rowNum++;
			}
			
			System.out.println("row # = " + rowNum);

			List< Matrix[][] > gradient = new ArrayList<>();
			for (int i = 0; i < batchSizeActual; i++) {
				gradient.add(train(essays[i], grades[i]));
			}

			log("\n----------------------------\nGradient:\n");
			Matrix[][] avgGradient = new Matrix[2][neuralLayers.size()];
			for (int i = 0; i < batchSizeActual; i++) {
				log("\nessay #: " + i);
				for (int j = 0; j < neuralLayers.size(); j++) {
					if (i == 0) {
						avgGradient[0][j] = new Matrix(gradient.get(i)[0][j].getM(), 1);
						avgGradient[1][j] = new Matrix(gradient.get(i)[1][j].getM(), gradient.get(i)[1][j].getN());
					}


					log("bias error:");
					gradient.get(i)[0][j].print();
					log("weights error:");
					gradient.get(i)[1][j].print();
					avgGradient[0][j] = avgGradient[0][j].plus(gradient.get(i)[0][j]);
					avgGradient[1][j] = avgGradient[1][j].plus(gradient.get(i)[1][j]);
				}

			}

			for (int j = 0; j < neuralLayers.size(); j++) {
				avgGradient[0][j] = avgGradient[0][j].divide(batchSize);
				avgGradient[1][j] = avgGradient[1][j].divide(batchSize);
			}

			NeuralLayer nl;
			log("\n-----------------------\ncurrent neural net:");
			for (int i = 0; i < neuralLayers.size(); i++) {
				log("neural layer #" + (i+1));
				nl = neuralLayers.get(i);
				nl.print();
			}

			log("\n\naverage bias errors:");
			for (int j = 0; j < neuralLayers.size(); j++) {
				avgGradient[0][j].print();
				log("");
			}

			log("\naverage weights errors:");
			for (int j = 0; j < neuralLayers.size(); j++) {
				avgGradient[1][j].print();
				log("");
			}

			log("-----------------------\nnew neural net:");
			for (int i = 0; i < neuralLayers.size(); i++) {
				log("neural layer #" + (i+1));
				nl = neuralLayers.get(i);
				nl.changeBias(avgGradient[0][i]);
				nl.changeWeights(avgGradient[1][i]);
				nl.print();
			}

			saveToExcel(jarvisWeightsBiases);
		}

		//TODO: save new biases/weights to database
		workbook.close();
		fis.close();
	}

	private Matrix[][] train(Essay essay, Matrix desiredOutput) {
		Matrix input = getInputMatrix(essay);
		Matrix output = calculateOutput(input);
		Matrix delC_A = output.minus(desiredOutput).multiply(-2);

		log("\n----------------\nTraining...");
		log("input:");
		input.print();
		log("output:");
		output.print();
		log("desired output:");
		desiredOutput.print();
		log("--------------------");

		NeuralLayer nl, nlNext;
		for (int i = neuralLayers.size()-1; i >= 0; i--) {
			nl = neuralLayers.get(i);
			if (i == neuralLayers.size()-1) {
				nl.calculateError(delC_A);
				nl.calculateCost(desiredOutput);
			} else {
				nlNext = neuralLayers.get(i+1);
				nl.calculateError(nlNext.getWeights(), nlNext.getBiasError());
			}
			if (i == 0) {
				nl.calculateWeightError(input);
			} else {
				nl.calculateWeightError(neuralLayers.get(i-1).getActivations());
			}

		}

		Matrix[][] grad = new Matrix[2][neuralLayers.size()];
		for (int i = 0; i < neuralLayers.size(); i++) {
			nl = neuralLayers.get(i);
			log("neural layer #" + (i+1));
			nl.printAll();
			grad[0][i] = new Matrix(nl.getBiasError().getData());
			grad[1][i] = new Matrix(nl.getWeightsError().getData());
		}
		
		return grad;
	}
	
	private Matrix getInputMatrix(Essay essay) {
		double spelling = checkSpelling(essay);
		double wordLimit = (checkWordLimit(essay) ? 1 : 0);
		double grammar = checkGrammar(essay);
		//log("spelling = " + spelling);
		//log("wordLimit = " + wordLimit);
		//log("grammar = " + grammar);
		Matrix input = new Matrix(new double[][] {
				{spelling},
				{wordLimit},
				{grammar}
		});
		return input;
	}
	
	/**
	 * Returns number of grammatical mistakes
	 * 
	 * @param e
	 * 		Essay to be graded
	 * @return
	 * 		Number of grammatical mistakes made in essay
	 */
	public double checkGrammar(Essay e) {
		String essay = e.getEssay();
		double numBadGrammar = 0;

		// Check for spaces before punctuation
		String punctuation = ",.;:?/!'";
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
	 * @param e
	 * 		Essay to be graded
	 * @return
	 * 		True if within word limit, false otherwise
	 */
	public boolean checkWordLimit(Essay e) {
		String essay = e.getEssay();
		return essay.length() >= e.getLength()[0] && essay.length() < e.getLength()[1];
	}
	
	/**
	 * Returns number of misspelled words
	 * @param e
	 * 		Essay to be graded
	 * @return
	 * 		Number of misspelled words as a percentage of total words in the essay
	 */
	public double checkSpelling(Essay e) {
		String essay = e.getEssay();
		String[] words = essay.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
		
		double numMisspelled = 0;
		boolean isRealWord;
		for (int i = 0; i < words.length; i++) {
			isRealWord = Arrays.stream(wordList).anyMatch(words[i]::equals);
			if (!isRealWord) {
				numMisspelled++;
			}
			
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
	 * Prints a String to the console and a log file
	 * @param msg
	 * 		Message to be printed
	 */
	private void log(String msg) {
		if (debugging) {
			System.out.println(msg);
			PrintWriter out;
			try {
				out = new PrintWriter(new FileWriter("log_file.txt", true));
				out.println(msg);
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
