public class Main {
    
    public static void result(
        Layer layer,
        double[] input
    ) {
        double[] output = layer.activate(input);
        if (output[0] >= 0.5 && output[1] <= 0.5) {
            System.out.println("Cat");
        } else if (output[1] >= 0.5 && output[0] <= 0.5) {
            System.out.println("Dog");
        } else {
            System.out.println("Uncertain");
        }
    }
    
    public static void main(
        String[] args
    ) {
        
        double[] cat = {1.0, 0.0};
        double[] dog = {0.0, 1.0};
        
        double[][] inputs = {
            cat,
            dog
        };
        
        double[][] targets = {
            cat,
            dog
        };

        ALU alu = new ALU();
        Layer inputLayer = new Layer(
            inputs.length,
            inputs.length,
            alu
        );

        // single layer, no hidden layer, no output layer

        for (int epoch = 0; epoch < 1000; epoch++) {
            for (int i = 0; i < inputs.length; i++) {
                inputLayer.train(inputs[i], targets[i], 0.1);
            }
        }
        
        result(inputLayer, cat);
        result(inputLayer, dog);
        result(inputLayer, new double[]{1.0, 1.0});
        result(inputLayer, new double[]{0.0, 0.0});
    }
    
}

