import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;

public class Main {
    public static void main(String[] args) {
        Vocabulary vocabulary = new Vocabulary();
        ALU alu = new ALU();
        Category category = new Category();
        Scanner scanner = new Scanner(System.in);

        int inputSize = vocabulary.size(); // Fixed input vector size (word limit)
        int outputSize = category.getCategoryCount();
        int hiddenSize = inputSize * outputSize;

        NeuralNetwork neuralNetwork = new NeuralNetwork(
            new int[]{inputSize, hiddenSize, outputSize},
            alu
        );

        // ------ Training Phase ------
        System.out.println("Training from database/training.csv...");

        try (BufferedReader br = new BufferedReader(new FileReader("database/training.csv"))) {
            br.readLine(); // Skip header
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", 2);
                if (parts.length != 2) continue;

                String label  = parts[0].trim();
                String sentence = parts[1].trim();

                double[] inputVector = vocabulary.vectorize(sentence);
                try {
                    double[] targetVector = category.targetHotVector(label);
                    for (int epoch = 0; epoch < 100; epoch++) {
                        neuralNetwork.train(inputVector, targetVector, 0.1);
                    }
                } catch (IllegalArgumentException e) {
                    System.err.println("⚠ Skipping unknown category: " + label);
                }
            }
        } catch (Exception e) {
            System.err.println("❌ Failed to train from CSV: " + e.getMessage());
        }

        // ------ Prediction Phase ------
        System.out.println("\nTraining complete ✅");
        System.out.println("Type a sentence to predict the category (type 'exit' to quit):");

        while (true) {
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("exit")) break;

            double[] vector = vocabulary.vectorize(input);
            double[] output = neuralNetwork.predict(vector);
            String predictedLabel = category.getLabelFromVector(output);

            System.out.println("Prediction: " + predictedLabel);
        }

        scanner.close();
    }
}
