class Neuron {
	
	private double[] weights;
	private double bias;
	private ALU alu;
	
	public Neuron(
		int size,
		ALU alu
	) {
		this.weights = new double[size];
		for (
			int x = 0;
			x < size;
			x++
		) {
			this.weights[x] = 0.5;
		}
		this.bias = 0.0;
		this.alu = alu;
	}
	
	public double activate(
		double[] inputs
	) {
		return this.alu.sigmoid(
			this.alu.pdot(
				weights, inputs
			) + bias
		);
	}
	
	public void train(
		double[] inputs,
		double target,
		double rate
	) {
		double z = this.alu.pdot(
			this.weights, inputs
		) + this.bias;
		double a = this.alu.sigmoid(z);
		double gradient = (
			a - target
		) * this.alu.dsigmoid(a);
		for (
			int i = 0;
			i < this.weights.length;
			i++
		) {
			this.weights[i] -= rate *
				gradient *
				inputs[i];
		}
		this.bias -= rate * gradient;
	}
	
}
