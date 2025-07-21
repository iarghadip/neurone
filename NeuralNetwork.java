public class NeuralNetwork {

    private Layer[] layers;
    private ALU alu;

    NeuralNetwork(
        int[] strcuture,
        ALU alu
    ) {
        this.alu = alu;
        this.layers = new Layer[strcuture.length - 1];
        for (int i = 0; i < strcuture.length - 1; i++) {
            this.layers[i] = new Layer(strcuture[i], strcuture[i + 1], alu);
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
        double[] errors = new double[target.length];
        for (int i = 0; i < target.length; i++) {
            errors[i] = target[i];
        }
        layers[layers.length - 1].train(activations[activations.length - 2], target, learningRate);
    }

}