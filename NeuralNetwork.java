import java.util.Arrays;

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
        return softmax(output);
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

    public double[] softmax(double[] x) {
        double max = Arrays.stream(x).max().orElse(0);
        double sum = 0;
        double[] result = new double[x.length];
        for (int i = 0; i < x.length; i++) {
            result[i] = Math.exp(x[i] - max); // prevent overflow
            sum += result[i];
        }
        for (int i = 0; i < result.length; i++) {
            result[i] /= sum;
        }
        return result;
    }

}
