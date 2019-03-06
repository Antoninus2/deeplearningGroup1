package deeplearningGroup1;

/**
 * NeuralLayer holds the weights and biases corresponding to a single layer in a neural network in {@link Matrix} form
 * @author Steven Rose
 * version 1.0
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
		for (int i = 0; i < m.getM(); i++) {
			for (int j = 0; j < m.getN(); j++) {
				m.set(i, j, 1/(1+Math.exp(-1*m.get(i, j))));
			}
		}
		return m;
	}
}
