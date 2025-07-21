class Layer {
	
	Neuron[] neurons;
	
	public Layer(
		int size,
		int count,
		ALU alu
	) {
		this.neurons = new Neuron[count];
		for (
			int x = 0;
			x < count;
			x++
		) {
			this.neurons[x] = new Neuron(
				size, alu
			);
		}
	}
	
	public double[] activate(
		double[] inputs
	) {
		double[] outputs = new double[
			this.neurons.length
		];
		for (
			int x = 0;
			x < this.neurons.length;
			x++
		) {
			outputs[x] = this
				.neurons[x]
				.activate(inputs);
		}
		return outputs;
	}
	
	public void train(
		double[] inputs,
		double[] targets,
		double rate
	) {
		for (
			int x = 0;
			x < this.neurons.length;
			x++
		) {
			this.neurons[x].train(
				inputs, targets[x], rate
			);
		}
	}
	
}