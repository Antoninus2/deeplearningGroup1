package deeplearningGroup1;


import java.util.ArrayList;
import java.util.List;

/**
 * Represents a neuron model comprised of: </br>
 * <ul>
 * <li>Summing part  - input summing function</li>
 * <li>Activation function</li>
 * <li>Input connections</li>
 * <li>Output connections</li>
 * </ul>
 */
public class Neuron {
	/**
	 * Neuron's identifier
	 */
	private String id;
	
	/**
	 * Collection of neuron's input connections (connections to this neuron)
	 */
	protected List < Connection > inputConnections;
	
	/**
	 * Collection of neuron's output connections (connections from this to other
	 * neurons)
	 */
	protected List < Connection > outputConnections;
	
	/**
	 * Input summing function for this neuron
	 */
	protected InputSummingFunction inputSummingFunction;
	
	/**
	 * Activation function for this neuron
	 */
	protected ActivationFunction activationFunction;
	
	/**
	 * Default constructor
	 */
	public Neuron(String id) {
		this.inputConnections = new ArrayList < > ();
		this.outputConnections = new ArrayList < > ();
		this.id = id;
	}
	
	/**
	 * Calculates the neuron's output
	 */
	public double calculateOutput() {
		double totalInput = inputSummingFunction.getOutput(inputConnections);
		return activationFunction.getOutput(totalInput);
	}
	
	/**
	 * Sets the list of input connections for this neuron
	 * 
	 * @param inputConnections
	 * 		list of input connections for this neuron
	 */
	public void setInputConnections(List < Connection > inputConnections) {
		this.inputConnections = inputConnections;
	}
	
	/**
	 * Sets the list of output connections for this neuron
	 * 
	 * @param outputConnections
	 * 		list of output connections for this neuron
	 */
	public void setOutputConnections(List < Connection > outputConnections) {
		this.outputConnections = outputConnections;
	}
	
	public String getID() {
		return id;
	}
	
	public void print() {
		System.out.println("\n---Neuron---\nID: {" + id + "}");
		if (inputConnections != null) {
			System.out.println("\n# inputs:  " + inputConnections.size());
			for (Connection c : inputConnections) {
				c.print();
			}
		} else {
			System.out.println("\n# inputs:  0");
		}
		if (outputConnections != null) {
			System.out.println("\n# outputs: " + outputConnections.size());
			for (Connection c : outputConnections) {
				c.print();
			}
		} else {
			System.out.println("\n# outputs:  0");
		}
	}
	
	
}