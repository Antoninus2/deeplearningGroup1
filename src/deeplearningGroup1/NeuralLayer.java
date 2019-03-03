package deeplearningGroup1;

public class NeuralLayer {
	
	private Matrix weights;
	
	private Matrix bias;
	
	public NeuralLayer(Matrix weights, Matrix bias) {
		this.weights = weights;
		this.bias = bias;
	}
	
	public NeuralLayer(int weightWidth, int weightHeight, int biasHeight) {
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
		log("Calculating sigmoid...");
		if (weights.getWidth() != input.getHeight()) {
			log("ERROR: sigmoid size incompatibility:\nweights width = " + weights.getWidth() + ", input height = " + input.getHeight());
			System.exit(1);
		}
		Matrix m = weights.multiply(input).plus(bias);
		for (int i = 0; i < m.getHeight(); i++) {
			for (int j = 0; j < m.getWidth(); j++) {
				m.set(i, j, 1/(1+Math.exp(-1*m.get(i, j))));
			}
		}
		return m;
	}
	
	private void log(String msg) {
		System.out.println(msg);
	}

}
