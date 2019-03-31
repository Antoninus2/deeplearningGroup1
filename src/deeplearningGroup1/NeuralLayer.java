package deeplearningGroup1;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * NeuralLayer holds the weights and biases corresponding to a single layer in a neural network in {@link Matrix} form
 * @author Steven Rose
 * @version 1.0
 * @see Jarvis
 */
public class NeuralLayer {
	
	/**
	 * id is a unique string id for a layer of the neural network
	 */
	private String id;
	
	/**
	 * weights is a 2D {@link Matrix} of the weights for a layer of the neural network
	 */
	private Matrix weights;
	
	/**
	 * bias is a 2D {@link Matrix} of the biases for a layer of the neural network
	 */
	private Matrix bias;
	
	private Matrix activations;
	
	private Matrix weightedInput;
	
	private Matrix outputError; //error in output of neuron, del C / del Z
	
	private Matrix weightsError;
	
	private Matrix cost;
	
	/**
	 * Creates a new NeuralLayer with an id, and known weights and biases provided
	 * @param id
	 * 		String id for this layer
	 * @param weights
	 * 		Matrix of weights for this layer
	 * @param bias
	 * 		Matrix of biases for this layer
	 */
	public NeuralLayer(String id, Matrix weights, Matrix bias) {
		this.id = id;
		this.weights = weights;
		this.bias = bias;
		this.activations = new Matrix(bias.getM(), 1);
		this.weightedInput = this.activations;
	}
	
	/**
	 * Creates an empty NeuralLayer with an id and the size of the Matrices for the weights and biases
	 * @param id
	 * 		String id for this layer
	 * @param weightWidth
	 * 		Width of the matrix for weights (This is equal to the number of inputs to this layer)
	 * @param weightHeight
	 * 		Height of the matrix for weights (This is equal to the number of neurons in this layer)
	 * @param biasHeight
	 * 		height of the matrix for biases (This is equal to the number of neurons in this layer)
	 */
	public NeuralLayer(String id, int weightWidth, int weightHeight, int biasHeight) {
		this.id = id;
		double[][] weights = new double[weightHeight][weightWidth];
		double[][] bias = new double[biasHeight][1];
		
		for (int i = 0; i < weightHeight; i++) {
			for (int j = 0; j < weightWidth; j++) {
				weights[i][j] = 20*Math.random() - 10;
			}
		}
		
		for (int i = 0; i < biasHeight; i++) {
			bias[i][0] = 20*Math.random() - 10;
		}
		
		this.weights = new Matrix(weights);
		this.bias = new Matrix(bias);
		this.activations = new Matrix(biasHeight, 1);
		this.weightedInput = this.activations;
	}
	
	/**
	 * Calculates the sigmoid for each neuron in this layer
	 * @param input
	 * 		Column Matrix of inputs to this layer
	 * @return
	 * 		Column matrix of activations for this layer
	 */
	public Matrix calculateSigmoid(Matrix input) {
		if (weights.getN() != input.getM()) {
			System.out.println("ERROR: sigmoid size incompatibility:\nweights width = " + weights.getN() + ", input height = " + input.getM());
			System.exit(1);
		}
		Matrix m = weights.multiply(input).plus(bias);
		weightedInput = new Matrix(m.getData());
		for (int i = 0; i < m.getM(); i++) {
			for (int j = 0; j < m.getN(); j++) {
				m.set(i, j, 1/(1+Math.exp(-1*m.get(i, j))));
			}
		}
		activations = new Matrix(m.getData());
		return m;
	}
	
	public Matrix calculateError(Matrix delC_A) {
		Matrix z = weightedInput;
		Matrix delA_Z = new Matrix(z.getM(), z.getN());
		double x;
		for (int i = 0; i < z.getM(); i++) {
			x = Math.exp(-1*z.get(i, 0));
			delA_Z.set(i, 0, x/Math.pow(1+x, 2));
		}
		outputError = delC_A.elementMultiply(delA_Z);
		return outputError;
	}
	
	public Matrix calculateError(Matrix weightsNext, Matrix errorNext) {
		Matrix z = weightedInput;
		Matrix delA_Z = new Matrix(z.getM(), 1);
		double x;
		for (int i = 0; i < z.getM(); i++) {
			x = Math.exp(-1*z.get(i, 0));
			delA_Z.set(i, 0, x/Math.pow(1+x, 2));
		}
		outputError = weightsNext.transpose().multiply(errorNext).elementMultiply(delA_Z);
		return outputError;
	}
	
	public Matrix calculateWeightError(Matrix outputPrevious) {
		Matrix m = new Matrix(weights.getM(), weights.getN());
		for (int i = 0; i < m.getM(); i++)
			for (int j = 0; j < m.getN(); j++)
				m.set(i, j, outputPrevious.get(j, 0) * outputError.get(i, 0));
		
		weightsError = m;
		return m;
	}
	
	public Matrix calculateCost(Matrix desiredOutputs) {
		cost = desiredOutputs.minus(activations).pow(2); //quadratic cost function
		return cost;
	}
	
	public void changeBias(Matrix delta) {
		bias = bias.plus(delta);
	}
	
	public void changeWeights(Matrix delta) {
		weights = weights.plus(delta);
	}
	
	/**
	 * Returns the Matrix of weights for this layer
	 * @return
	 * 		Matrix of weights
	 */
	public Matrix getWeights() {
		return weights;
	}
	
	/**
	 * Returns the Matrix of biases for this layer
	 * @return
	 * 		Matrix of biases
	 */
	public Matrix getBias() {
		return bias;
	}
	
	public Matrix getActivations() {
		return activations;
	}
	
	public Matrix getWeightedInput() {
		return weightedInput;
	}
	
	public Matrix getBiasError() {
		return outputError;
	}
	
	public Matrix getWeightsError() {
		return weightsError;
	}
	
	public void print() {
		log("id: " + id);
		log("weights:");
		weights.print();
		log("biases:");
		bias.print();
	}
	
	public void printAll() {
		print();
		log("weighted inputs:");
		weightedInput.print();
		log("activations:");
		activations.print();
		log("bias error:");
		outputError.print();
		log("weights error:");
		weightsError.print();
		if (cost != null) {
			log("cost:");
			cost.print();
		}
	}
	
	/**
	 * Prints a String to the console and a log file
	 * @param msg
	 * 		Message to be printed
	 */
	private void log(String msg) {
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
