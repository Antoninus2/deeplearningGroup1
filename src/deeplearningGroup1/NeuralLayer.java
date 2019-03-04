package deeplearningGroup1;

public class NeuralLayer {
	
	private String id;
	
	private Matrix weights;
	
	private Matrix bias;
	
	public NeuralLayer(String id, Matrix weights, Matrix bias) {
		this.id = id;
		this.weights = weights;
		this.bias = bias;
	}
	
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
	
	public Matrix getWeights() {
		return weights;
	}
	
	public Matrix getBias() {
		return bias;
	}
	
	public Matrix calculateSigmoid(Matrix input) {
		if (weights.getN() != input.getM()) {
			log("ERROR: sigmoid size incompatibility:\nweights width = " + weights.getN() + ", input height = " + input.getM());
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
	
	public void print() {
		
	}
	
	private void log(String msg) {
		System.out.println(msg);
	}

}
