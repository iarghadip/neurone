public class NeuralNetwork {

    private Layer[] layers;
    private ALU alu;

    NeuralNetwork(
        int[] structure,
        ALU alu
    ) {
        this.alu = alu;
        this.layers = new Layer[structure.length - 1];
        for (int i = 0; i < structure.length - 1; i++) {
            this.layers[i] = new Layer(structure[i], structure[i + 1], alu);
        }
    }

    public double[] predict(
        double[] input
    ) {
        double[] output = input;
        for (Layer layer : layers) {
            output = layer.activate(output);
        }
        return output;
    }

    public void train(
        double[] input,
        double[] target,
        double learningRate
    ) {
        double[][] activations = new double[layers.length + 1][];
        activations[0] = input;

        for (int i = 0; i < layers.length; i++) {
            activations[i + 1] = layers[i].activate(activations[i]);
        }

        // Only trains the last layer — no backpropagation
        layers[layers.length - 1].train(
            activations[activations.length - 2],
            target,
            learningRate
        );
    }
}
