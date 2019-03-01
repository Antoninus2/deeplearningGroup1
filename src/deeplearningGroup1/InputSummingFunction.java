package deeplearningGroup1;

import java.util.List;

public interface InputSummingFunction {
	
	/**
	 * Performs calculations based on the output values of the input neurons
	 * 
	 * @param inputConnections
	 * 		neuron's input connections
	 * @return total input for the neuron
	 */
	
	public double getOutput(List< Connection > inputConnections);

}
