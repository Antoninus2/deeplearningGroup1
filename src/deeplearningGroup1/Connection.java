package deeplearningGroup1;

public class Connection {
	
	/**
	 * neurons that connection is coming from/going to
	 */
	private Neuron fromNeuron, toNeuron;
	
	/**
	 * weight of connection
	 */
	private double weight;
	
	/**
	 * Creates a new connection between two neurons, randomized weight
	 * 
	 * @param fromNeuron
	 * 		neuron to connect from
	 * @param toNeuron
	 * 		neuron to connect to
	 */
	public Connection(Neuron fromNeuron, Neuron toNeuron) {
		this.fromNeuron = fromNeuron;
		this.toNeuron = toNeuron;
		this.weight = Math.random();
	}
	
	/**
	 * Creates a new connection between two neurons with specified weight
	 * 
	 * @param fromNeuron
	 * 		neuron to connect from
	 * @param toNeuron
	 * 		neuron to connect to
	 * @param weight
	 * 		weight of connection between neurons
	 */
	public Connection(Neuron fromNeuron, Neuron toNeuron, double weight) {
		this(fromNeuron, toNeuron);
		this.weight = weight;
	}
	
	/**
	 * Returns weight for this connection
	 * 
	 * @return weight for this connection
	 */
	public double getWeight() {
		return weight;
	}
	
	/**
	 * Set the weight for this connection
	 * 
	 * @param weight
	 * 		weight for this connection
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}
	
	/**
	 * Returns the input for this connection - the output of the fromNeuron
	 * 
	 * @return input received through this connection
	 */
	public double getInput() {
		return fromNeuron.calculateOutput();
	}
	
	/**
	 * Returns weight input of this connection
	 * 
	 * @return weighted input of this connection
	 */
	public double getWeightedInput() {
		return weight * fromNeuron.calculateOutput();
	}
	
	/**
	 * Returns from neuron for this connection
	 * 
	 * @return from neuron for this connection
	 */
	public Neuron getFromNeuron() {
		return fromNeuron;
	}
	
	/**
	 * Returns to neuron for this connection
	 * 
	 * @return to neuron for this connection
	 */
	public Neuron getToNeuron() {
		return toNeuron;
	}
	
	public void print() {
		System.out.println("---Connection---\n{" + fromNeuron.getID() + "} -> {" + toNeuron.getID() + "}");
		System.out.println("Weight = " + weight);
	}

}
