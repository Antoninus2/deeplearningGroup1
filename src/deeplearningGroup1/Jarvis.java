package deeplearningGroup1;

import javafx.util.Pair;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import scala.collection.mutable.HashMap;

/**
 * Jarvis is the neural network class which grades essays through the use of a trained neural network, 
 * consisting of a list of Neural Layers with associated weights and biases.  It has the ability to train 
 * the neural net through the use of backpropagation, and it can also test the neural net to see how close 
 * to the expected outcome it is.  All training and testing data comes from external excel files, and the 
 * results of the testing are output as excel files under /resources.
 * @author Steven Rose
 * @version 2.0
 * @see NeuralLayer
 * @see GrammarCheck
 * @see Essay
 */
@SuppressWarnings("restriction")
public class Jarvis {

	/**
	 * A list of all the layers of the neural network which hold the weights and biases of each layer.
	 */
	private List<NeuralLayer> neuralLayers;
	
	/**
	 * The relative path to the excel file holding all the weights and bias data.
	 */
	private final static String jarvisWeightsBiases = "resources/neural_net_weights_biases.xlsx";

	/**
	 * An external grammar checking tool.
	 */
	private GrammarCheck gram;
	
	/**
	 * Sets whether all of the logs should be output to the console and saved to an external file.  If set 
	 * to true, significantly increases runtime.
	 */
	final static boolean debugging = false;

	/**
	 * Automated method for training and testing the neural net on data from a number of external files. It 
	 * will test first, output the results of that test, then train and test again, outputting the results of 
	 * that test as well for comparison.
	 * @param args
	 * 		Unused.
	 */
	public static void main(String[] args) {
		long startTime = System.nanoTime();
		Jarvis j = new Jarvis();
		j.log("----------START----------");

		try {
			j.test("resources/Essay Data/testing_set1.xlsx");
			j.train("resources/Essay Data/training_set1.xlsx", 50);
			j.train("resources/Essay Data/training_set7.xlsx", 50);
			j.train("resources/Essay Data/training_set8.xlsx", 50);
			j.test("resources/Essay Data/testing_set1.xlsx");
		} catch (IOException e) {
			e.printStackTrace();
		}
		j.log("----------END----------");
		long endTime = System.nanoTime();
		System.out.println("Time Elapsed = " + ((endTime - startTime) / 1e9) + " s");
	}

	/**
	 * Creates a new Jarvis, sets the weights and biases of the neural network based on data from a file, 
	 * then creates the neural network from those weights/biases.  It also initializes the {@link GrammarCheck} 
	 * gram.
	 */
	public Jarvis() {

		//loadWordList();

		String[] layerNames = {"input", "hidden 0", "hidden 1",
				"hidden 2", "hidden 3", "hidden 4", "hidden 5", "output"};
		/* Current size of neural network:
		 * 		346 inputs
		 * 		24 hidden 0
		 * 		24 hidden 1
		 * 		24 hidden 2
		 * 		24 hidden 3
		 * 		24 hidden 4
		 * 		24 hidden 5
		 * 		5 outputs
		 */

		Pair< List <List <Matrix>>, Boolean > savedData = null;
		try {
			savedData = loadWeightsBiases(jarvisWeightsBiases);
		} catch (IOException e) {
			e.printStackTrace();
		}
		List <Matrix> weights = savedData.getKey().get(0);
		List <Matrix> biases = savedData.getKey().get(1);
		
		createNeuralNet(layerNames, weights, biases);
		gram = new GrammarCheck();
		
		if (savedData.getValue() == false)
			try {
				saveWeightsBiases(jarvisWeightsBiases);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	/**
	 * Creates and populates the neuralLayers List.
	 * @param names
	 * 		Array of strings for ids of each neural layer.
	 * @param weights
	 * 		List of Matrices of weights from one layer to the next, each Matrix of size [output x input].
	 * @param biases
	 * 		List of column Matrices of biases for each layer, each Matrix of size [output x 1].
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
	 * Grades an essay.
	 * @param essay
	 * 		Essay to be graded.
	 * @return
	 * 		Grade for the essay.
	 */
	public String gradeEssay(Essay essay) {
		Matrix input = gram.check(essay.getEssay());
		Matrix output = calculateOutput(input, neuralLayers.size()-1);
		
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

		return matrixToGrade(output);
	}

	/**
	 * Iteratively calculates output of each layer in the network based on the output of the previous layer.
	 * @param input
	 * 		Column Matrix of input values from previous layer.
	 * @param i
	 * 		Which layer the output is being calculated for.
	 * @return
	 * 		Column Matrix of output for current layer.
	 */
	private Matrix calculateOutput(Matrix input, int i) {
		if (i == 0) {
			return neuralLayers.get(i).calculateSigmoid(input);
		} else {
			return neuralLayers.get(i).calculateSigmoid(calculateOutput(input, i-1));
		}
	}

	/**
	 * Trains the neural net based on data from a given file.
	 * @param fileName
	 * 		Relative path to file.
	 * @param batchSize
	 * 		How many essays to train at once, also controls how often the weights and biases are saved during 
	 * training (lower = saves more often, but is less efficient at learning).
	 * @throws IOException
	 * 		Thrown in the event the file is already open in another program, or a similar IO Exception occurs.
	 */
	private void train(String fileName, int batchSize) throws IOException {

		FileInputStream fis = new FileInputStream(new File(fileName));
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheetAt(0);

		Row row;
		int rowNum = 0;
		int maxRowNum = sheet.getLastRowNum();
		log("maxRowNum = " + maxRowNum);

		for (int batch = 0; batch < maxRowNum-1; batch += batchSize) {
			int batchSizeActual = Math.min(batchSize, maxRowNum - batch);
			String[] essays = new String[batchSizeActual];
			Matrix[] grades = new Matrix[batchSizeActual];
			double numGrade;
			for (int i = 0; i < batchSizeActual; i++) {
				row = sheet.getRow(rowNum);
				essays[i] = row.getCell(2).getStringCellValue();
				log("essay =\n" + essays[i]);
				numGrade = row.getCell(6).getNumericCellValue();
				grades[i] = gradeToMatrix(numGrade);
				rowNum++;
			}

			log("row # = " + rowNum);

			List< Matrix[][] > gradient = new ArrayList<>();
			for (int i = 0; i < batchSizeActual; i++) {
				log("Essay # = " + (rowNum - batchSizeActual + i));
				System.out.println("Essay # = " + (rowNum - batchSizeActual + i));
				gradient.add(calculateGradient(essays[i], grades[i]));
			}

			log("\n----------------------------\nGradient:\n");
			Matrix[][] avgGradient = new Matrix[2][neuralLayers.size()];
			for (int i = 0; i < batchSizeActual; i++) {
				log("\nessay #: " + (i+batch*batchSize));
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

			saveWeightsBiases(jarvisWeightsBiases);
		}

		workbook.close();
		fis.close();
	}

	/**
	 * Transforms a grade given as a number from 1-12 to an alphabetical grade.  Useful for training, as the data is given with numerical grades from 1-12.
	 * @param d
	 * 		Numerical representation of grade, [1-12].
	 * @return
	 * 		Returns a column Matrix representation of that grade for comparison to neural net output.
	 */
	private Matrix gradeToMatrix(double d) {
		Matrix m = new Matrix(neuralLayers.get(neuralLayers.size()-1).getBias().getM(), 1);
		if (d > 9) {
			m.set(0, 0, 1); //A
		} else if (d > 6) {
			m.set(1, 0, 1); //B
		} else if (d > 3) {
			m.set(2, 0, 1); //C
		} else {
			m.set(3, 0, 1); //D
		}
		return m;
	}
	
	/**
	 * Transforms a column Matrix representation of a grade to its corresponding string.
	 * @param m
	 * 		Column Matrix of grade from output of neural net.
	 * @return
	 * 		Returns a string of [A-D].
	 */
	private String matrixToGrade(Matrix m) {
		String[] grades = {"A", "B", "C", "D"};
		String grade = "No Grade";
		double gradeNumber = 0;
		for (int i = 0; i < m.getM(); i++) {
			if (m.get(i, 0) > gradeNumber) {
				grade = grades[i];
				gradeNumber = m.get(i, 0);
			}
		}
		return grade;
	}

	/**
	 * Calculates the gradient of the cost function for each weight and bias.  This is used for training the neural net using backpropagation.
	 * @param essay
	 * 		The essay to be trained on.
	 * @param desiredOutput
	 * 		Matrix representation of the desired grade for the essay.
	 * @return
	 * 		A Matrix array of the gradient for this essay.
	 * @see #matrixToGrade(Matrix)
	 */
	private Matrix[][] calculateGradient(String essay, Matrix desiredOutput) {
		Matrix input = gram.check(essay);
		Matrix output = calculateOutput(input, neuralLayers.size()-1);
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

	/**
	 * Loads the weights and biases from a file, or if necessary generates random weights and biases to be used for the neural net.
	 * @param fileName
	 * 		The file the data is to be pulled from.
	 * @return
	 * 		An array of Matrices holding the data from the file, and a boolean for whether the file existed or not
	 * @throws IOException
	 * 		Thrown in the event the file is open in another program or a similar IO Exception occurs. Is not thrown for the file not existing at all.
	 * @see javafx.util.Pair#Pair(Object, Object)
	 */
	private Pair< List <List <Matrix>>, Boolean > loadWeightsBiases(String fileName) throws IOException {
		File file = new File(fileName);
		List <Matrix> weights = new ArrayList<>();
		List <Matrix> biases = new ArrayList<>();
		Boolean fileExists;
		
		if (file.exists()) {
			fileExists = true;
			FileInputStream fis = new FileInputStream(file);
			Workbook workbook = new XSSFWorkbook(fis);
			Sheet sheet;
			Row row;
			
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
		} else {
			fileExists = false;
			int randMax = 10;
			weights.add(Matrix.random(24, 346, randMax));
			weights.add(Matrix.random(24, 24, randMax));
			weights.add(Matrix.random(24, 24, randMax));
			weights.add(Matrix.random(24, 24, randMax));
			weights.add(Matrix.random(24, 24, randMax));
			weights.add(Matrix.random(24, 24, randMax));
			weights.add(Matrix.random(4, 24, randMax));

			biases.add(Matrix.random(24, 1, randMax));
			biases.add(Matrix.random(24, 1, randMax));
			biases.add(Matrix.random(24, 1, randMax));
			biases.add(Matrix.random(24, 1, randMax));
			biases.add(Matrix.random(24, 1, randMax));
			biases.add(Matrix.random(24, 1, randMax));
			biases.add(Matrix.random(4, 1, randMax));
		}
		
		List <List <Matrix>> result = new ArrayList<>();
		result.add(weights);
		result.add(biases);
		return new Pair< List <List <Matrix>>, Boolean >(result, fileExists);
	}

	/**
	 * Saves the weights and biases of the neural net to the specified file, overwriting any existing data.
	 * @param fileName
	 * 		File to be saved to.
	 * @throws IOException
	 * 		Thrown in the event the file is open in another program or a similar IO Exception occurs.
	 */
	private void saveWeightsBiases(String fileName) throws IOException {
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

		System.out.println("Saved!");

		FileOutputStream fileOut = new FileOutputStream(fileName);
		workbook.write(fileOut);
		fileOut.close();
		workbook.close();
	}

	/**
	 * Tests the neural net based on data from a given file.
	 * @param fileName
	 * 		File to use for testing.
	 * @throws IOException
	 * 		Thrown in the event the file is already open in another program, or a similar IO Exception occurs.
	 */
	private void test(String fileName) throws IOException {
		FileInputStream fis = new FileInputStream(new File(fileName));
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheetAt(0);

		Row row;
		int maxRowNum = sheet.getLastRowNum();
		//maxRowNum = 5;
		log("maxRowNum = " + maxRowNum);
		Matrix[] gradesExpected = new Matrix[maxRowNum];
		Matrix[] gradesActual = new Matrix[maxRowNum];
		Matrix input;
		String[] essays = new String[maxRowNum];

		for (int i = 0; i < maxRowNum; i++) {
			log("Essay # = " + i);
			System.out.println("Essay # = " + i);
			row = sheet.getRow(i);
			essays[i] = row.getCell(2).getStringCellValue();
			gradesExpected[i] = gradeToMatrix(row.getCell(3).getNumericCellValue());
			input = gram.check(essays[i]);
			gradesActual[i] = calculateOutput(input, neuralLayers.size()-1);
		}

		workbook.close();
		fis.close();

		saveTestData(gradesExpected, gradesActual);
		saveTestResults(essays, gradesExpected, gradesActual);
	}
	
	/**
	 * Saves the test data in an excel file to compare the raw output for each essay being tested on.
	 * @param gradesExpected
	 * 		Column Matrix of expected grade for essay
	 * @param gradesActual
	 * 		Column Matrix of grade calculated by neural net for essay.
	 * @throws IOException
	 * 		Thrown in the event the file is already open in another program, or a similar IO Exception occurs.
	 */
	private void saveTestData(Matrix[] gradesExpected, Matrix[] gradesActual) throws IOException {
		String fileName = "resources/test_results_data.xlsx";
		File file = new File(fileName);
		Workbook workbook;
		if (file.exists()) {
			FileInputStream fis = new FileInputStream(file);
			workbook = new XSSFWorkbook(fis);
			fis.close();
		} else {
			workbook = new XSSFWorkbook();
		}
		
		String[] grades = {"A", "B", "C", "D", "F"};
		int testNum = workbook.getNumberOfSheets();
		Sheet sheet = workbook.createSheet("Test Data " + testNum);
		Row row = sheet.createRow(0);
		row.createCell(0).setCellValue("Expected Output");
		for (int i = 0; i < gradesExpected[0].getM(); i++) {
			row = sheet.createRow(i+1);
			row.createCell(0).setCellValue(grades[i]);
			for (int j = 0; j < gradesExpected.length; j++) {
				row.createCell(j+1).setCellValue(gradesExpected[j].get(i, 0));
			}
		}
		
		row = sheet.createRow(gradesExpected[0].getM() + 2);
		row.createCell(0).setCellValue("Actual Output");
		for (int i = 0; i < gradesActual[0].getM(); i++) {
			row = sheet.createRow(i+gradesExpected[0].getM()+3);
			row.createCell(0).setCellValue(grades[i]);
			for (int j = 0; j < gradesActual.length; j++) {
				row.createCell(j+1).setCellValue(gradesActual[j].get(i, 0));
			}
		}
		
		FileOutputStream fileOut;
		try {
			fileOut = new FileOutputStream(fileName);
			workbook.write(fileOut);
			fileOut.close();
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Saves the results based on the test data to see how accurate the neural net is overall.
	 * @param essays
	 * 		Essays that the neural net was trained on.
	 * @param gradesExpected
	 * 		List of column Matrices of the expected grade for each essay.
	 * @param gradesActual
	 * 		List of column Matrices of the grade calculated by the neural net for each essay.
	 */
	private void saveTestResults(String[] essays, Matrix[] gradesExpected, Matrix[] gradesActual) {
		String fileName = "resources/test_results.xlsx";
		File file = new File(fileName);
		Workbook workbook = null;
		
		try {
			FileInputStream fis = new FileInputStream(file);
			workbook = new XSSFWorkbook(fis);
			fis.close();
		} catch (FileNotFoundException e) {
			workbook = new XSSFWorkbook();
		} catch (IOException e) {
			e.printStackTrace();
		}

		int testNum = workbook.getNumberOfSheets();
		Sheet sheet = workbook.createSheet("Test Results " + testNum);
		Row row = sheet.createRow(0);
		row.createCell(0).setCellValue("Essay");
		row.createCell(1).setCellValue("Expected Grade");
		row.createCell(2).setCellValue("Actual Grade");
		row.createCell(4).setCellValue("Expected Grade");
		row.createCell(5).setCellValue("Actual Grade");
		row.createCell(6).setCellValue("Difference");

		HashMap< String, Integer > map = new HashMap<>();
		map.put("A", 4);
		map.put("B", 3);
		map.put("C", 2);
		map.put("D", 1);
		
		String expectedGrade, actualGrade;
		
		for (int i = 0; i < essays.length; i++) {
			expectedGrade = matrixToGrade(gradesExpected[i]);
			actualGrade = matrixToGrade(gradesActual[i]);
			
			row = sheet.createRow(i+1);
			row.createCell(0).setCellValue(essays[i]);
			row.createCell(1).setCellValue(expectedGrade);
			row.createCell(2).setCellValue(actualGrade);
			row.createCell(3);
			row.createCell(4).setCellValue(map.get(expectedGrade).get());
			row.createCell(5).setCellValue(map.get(actualGrade).get());
			row.createCell(6).setCellFormula("E" + (i+2) + "-F" + (i+2));
		}
		
		for (int i = 1; i <= 6; i++) {
			sheet.autoSizeColumn(i);
		}

		FileOutputStream fileOut;
		try {
			fileOut = new FileOutputStream(fileName);
			workbook.write(fileOut);
			fileOut.close();
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * Prints a String to the console and writes it to a log file if {@link #debugging} 
	 * is set to true.
	 * @param msg
	 * 		Message to be printed.
	 */

	void log(String msg) {
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
